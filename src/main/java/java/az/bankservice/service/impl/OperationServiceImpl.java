package java.az.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.az.bankservice.enumeration.accounts.AccountStatus;
import java.az.bankservice.enumeration.accounts.AccountType;
import java.az.bankservice.enumeration.accounts.CurrencyType;
import java.az.bankservice.enumeration.transaction.TransactionStatus;
import java.az.bankservice.enumeration.transaction.TransactionType;
import java.az.bankservice.model.accounts.AccountResponse;
import java.az.bankservice.model.accounts.TransferMoneyRequest;
import java.az.bankservice.model.accounts.WithdrawalRequest;
import java.az.bankservice.model.exchange.ExchangeRequestDto;
import java.az.bankservice.model.transactions.TransactionAccountRequest;
import java.az.bankservice.model.transactions.TransactionResponse;
import java.az.bankservice.service.ExchangeService;
import java.az.bankservice.service.OperationService;
import java.az.bankservice.service.util.AccountUtilService;
import java.az.bankservice.service.util.TransactionUtilService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final TransactionUtilService transactionUtilService;
    private final ExchangeService exchangeService;
    private final AccountUtilService accountUtilService;

    @Override
    public String getBalance(String accountNumber) {
        log.info("Getting current balance from account {}", accountNumber);
        var account = accountUtilService.getByAccountNumber(accountNumber);
        return account.getCurrentBalance().toString();
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public void transferMoney(TransferMoneyRequest transferMoneyRequest) {

        log.info("Transferring money from {} to {}. Details: {}",
                transferMoneyRequest.getFromAccountNumber(),
                transferMoneyRequest.getToAccountNumber(),
                transferMoneyRequest.getTransactionAccountRequest());

        var fromAccount = accountUtilService.getByAccountNumber(transferMoneyRequest.getFromAccountNumber());
        var toAccount = accountUtilService.getByAccountNumber(transferMoneyRequest.getToAccountNumber());
        if (fromAccount.getAccountType() == AccountType.DEPOSIT || toAccount.getAccountType() == AccountType.DEPOSIT) {
            throw new IllegalArgumentException("Invalid account type: " + AccountType.DEPOSIT);
        }
        if (fromAccount.getStatus() != AccountStatus.ACTIVE || toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account status is not active!");
        }
        BigDecimal transferAmount = transferMoneyRequest.getTransactionAccountRequest().getAmount();

        validateAndDebitAccount(fromAccount, transferMoneyRequest.getPin(), transferAmount, toAccount.getCurrencyType());

        transferMoneyRequest.getTransactionAccountRequest().setAmount(transferAmount);
        var fromTransaction = transactionUtilService.create(fromAccount.getId(),
                transferMoneyRequest.getTransactionAccountRequest(), TransactionType.TRANSFER);

        BigDecimal updateAmount = toAccount.getCurrentBalance().add(transferAmount);
        accountUtilService.updateBalance(toAccount.getAccountNumber(), updateAmount);
        var toTransaction = transactionUtilService.create(toAccount.getId(),
                transferMoneyRequest.getTransactionAccountRequest(), TransactionType.TRANSFER);

        executeTransfer(fromAccount, toAccount, fromTransaction, toTransaction);
    }

    @Override
    @Transactional
    public void withdrawal(WithdrawalRequest withdrawalRequest) {
        log.info("Withdrawals from {}. Details: {}",
                withdrawalRequest.getFromAccountNumber(),
                withdrawalRequest);

        AccountResponse fromAccount = accountUtilService.getByAccountNumber(withdrawalRequest.getFromAccountNumber());
        BigDecimal transferAmount = withdrawalRequest.getAmount();

        validateAndDebitAccount(fromAccount, withdrawalRequest.getPin(), transferAmount, withdrawalRequest.getCurrencyType());

        withdrawalRequest.setAmount(transferAmount);
        var transaction = new TransactionAccountRequest(transferAmount, "Withdrawal");
        var fromTransaction = transactionUtilService.create(fromAccount.getId(), transaction, TransactionType.WITHDRAWAL);

        updateTransactionsStatus(List.of(fromTransaction), TransactionStatus.SUCCESSFUL);
    }

    private void validateAndDebitAccount(AccountResponse fromAccount, String pin, BigDecimal amount, CurrencyType currencyType) {
        accountUtilService.validatePin(fromAccount, pin);
        BigDecimal transferAmount = performExchangeIfNeeded(fromAccount, amount, currencyType);
        validateTransaction(fromAccount, transferAmount);
        BigDecimal updateAmount = fromAccount.getCurrentBalance().subtract(transferAmount);
        accountUtilService.updateBalance(fromAccount.getAccountNumber(), updateAmount);
    }

    private BigDecimal performExchangeIfNeeded(AccountResponse fromAccount, BigDecimal amount, CurrencyType toCurrency) {
        if (fromAccount.getCurrencyType() != toCurrency) {
            return performExchange(fromAccount.getCurrencyType(), amount, toCurrency);
        } else {
            return amount;
        }

    }

    private BigDecimal performExchange(CurrencyType fromCurrency, BigDecimal amount, CurrencyType toCurrency) {
        if (fromCurrency == CurrencyType.AZN) {
            return BigDecimal.valueOf(exchangeService.fromAZN(
                    ExchangeRequestDto.builder().amount(amount.doubleValue())
                            .currencyType(toCurrency).build()).getConvertedAmount());
        } else {
            return BigDecimal.valueOf(exchangeService.toAZN(
                    ExchangeRequestDto.builder().amount(amount.doubleValue())
                            .currencyType(fromCurrency).build()).getConvertedAmount());
        }
    }

    private void updateTransactionsStatus(List<TransactionResponse> transactions, TransactionStatus transactionStatus) {
        transactions.forEach(transaction -> transactionUtilService.updateStatus(transaction.getId(), transactionStatus));
    }

    private void executeTransfer(AccountResponse fromAccount, AccountResponse toAccount,
                                 TransactionResponse fromTransaction, TransactionResponse toTransaction) {
        try {
            accountUtilService.updateBalance(fromAccount.getAccountNumber(), fromAccount.getCurrentBalance().subtract(fromTransaction.getAmount()));
            accountUtilService.updateBalance(toAccount.getAccountNumber(), toAccount.getCurrentBalance().add(toTransaction.getAmount()));

            updateTransactionsStatus(List.of(fromTransaction, toTransaction), TransactionStatus.SUCCESSFUL);
        } catch (Exception ex) {
            updateTransactionsStatus(List.of(fromTransaction, toTransaction), TransactionStatus.FAILED);
        }
    }

    private void validateTransaction(AccountResponse fromAccount, BigDecimal amount) {
        List<String> errors = new ArrayList<>();
        if (!validateTransactionLimit(fromAccount, amount)) {
            errors.add("Transaction limit exceeded");
        }
        if (!validateSufficientBalance(fromAccount, amount)) {
            errors.add("Insufficient funds on the sender's account");
        }
        if (!validateAvailableBalanceAfterTransfer(fromAccount, amount)) {
            errors.add("Exceeding the allowable limit of funds on the recipient's account");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", errors));
        }
    }

    private boolean validateTransactionLimit(AccountResponse account, BigDecimal amount) {
        return account.getTransactionLimit() == null ||
                account.getTransactionLimit().compareTo(amount) >= 0;
    }

    private boolean validateSufficientBalance(AccountResponse account, BigDecimal amount) {
        return account.getCurrentBalance().compareTo(amount) >= 0;
    }

    private boolean validateAvailableBalanceAfterTransfer(AccountResponse account, BigDecimal amount) {
        return account.getCurrentBalance().add(amount).compareTo(account.getAvailableBalance()) <= 0;

    }

}

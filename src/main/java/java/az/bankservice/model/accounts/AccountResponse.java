package java.az.bankservice.model.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.accounts.AccountStatus;
import java.az.bankservice.enumeration.accounts.AccountType;
import java.az.bankservice.enumeration.accounts.CurrencyType;
import java.az.bankservice.model.transactions.TransactionAccountResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String branchCode;
    private String accountNumber;
    private LocalDate accountExpireDate;
    private CurrencyType currencyType;
    private AccountType accountType;
    private AccountStatus status;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private Long userId;
    private BigDecimal transactionLimit;
    private List<TransactionAccountResponse> transactionResponseList;
}

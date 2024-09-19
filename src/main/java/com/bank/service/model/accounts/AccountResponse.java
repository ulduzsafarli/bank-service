package com.bank.service.model.accounts;

import com.bank.service.model.transactions.TransactionAccountResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bank.service.enumeration.accounts.AccountStatus;
import com.bank.service.enumeration.accounts.AccountType;
import com.bank.service.enumeration.accounts.CurrencyType;
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

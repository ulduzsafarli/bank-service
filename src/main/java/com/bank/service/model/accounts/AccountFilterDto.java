package com.bank.service.model.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bank.service.enumeration.accounts.AccountStatus;
import com.bank.service.enumeration.accounts.AccountType;
import com.bank.service.enumeration.accounts.CurrencyType;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountFilterDto {
    private String branchCode;
    private LocalDate createdAt;
    private LocalDate accountExpireDate;
    private String accountNumber;
    private CurrencyType currencyType;
    private AccountType accountType;
    private AccountStatus status;
    private BigDecimal currentBalance;
    private BigDecimal transactionLimit;
}

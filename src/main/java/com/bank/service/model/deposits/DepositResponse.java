package com.bank.service.model.deposits;

import com.bank.service.model.accounts.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {
    private Long id;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private boolean yearlyInterest;
    private AccountResponse account;
}

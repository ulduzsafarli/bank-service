package com.bank.service.service.util;

import com.bank.service.model.accounts.AccountResponse;
import java.math.BigDecimal;

public interface AccountUtilService {

    AccountResponse getByAccountNumber(String accountNumber);

    void validatePin(AccountResponse account, String pin);

    void updateBalance(String accountNumber, BigDecimal subtract);

}

package java.az.bankservice.service.util;

import java.az.bankservice.model.accounts.AccountResponse;
import java.math.BigDecimal;

public interface AccountUtilService {

    AccountResponse getByAccountNumber(String accountNumber);

    void validatePin(AccountResponse account, String pin);

    void updateBalance(String accountNumber, BigDecimal subtract);

}

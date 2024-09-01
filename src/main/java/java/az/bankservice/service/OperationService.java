package java.az.bankservice.service;

import java.az.bankservice.model.accounts.TransferMoneyRequest;
import java.az.bankservice.model.accounts.WithdrawalRequest;

public interface OperationService {
    void transferMoney(TransferMoneyRequest transferMoneyRequest);

    void withdrawal(WithdrawalRequest withdrawalRequest);

    String getBalance(String accountNumber);
}

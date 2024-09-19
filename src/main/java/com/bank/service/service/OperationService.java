package com.bank.service.service;

import com.bank.service.model.accounts.TransferMoneyRequest;
import com.bank.service.model.accounts.WithdrawalRequest;

public interface OperationService {
    void transferMoney(TransferMoneyRequest transferMoneyRequest);

    void withdrawal(WithdrawalRequest withdrawalRequest);

    String getBalance(String accountNumber);
}

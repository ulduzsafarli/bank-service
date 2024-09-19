package com.bank.service.service.util;

import com.bank.service.enumeration.transaction.TransactionStatus;
import com.bank.service.enumeration.transaction.TransactionType;
import com.bank.service.model.transactions.TransactionAccountRequest;
import com.bank.service.model.transactions.TransactionResponse;

public interface TransactionUtilService {
    TransactionResponse create(Long accountId, TransactionAccountRequest transactionAccountRequest,
                               TransactionType transactionType);

    void updateStatus(Long id, TransactionStatus transactionStatus);

}

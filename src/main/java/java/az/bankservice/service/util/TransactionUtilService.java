package java.az.bankservice.service.util;

import java.az.bankservice.enumeration.transaction.TransactionStatus;
import java.az.bankservice.enumeration.transaction.TransactionType;
import java.az.bankservice.model.transactions.TransactionAccountRequest;
import java.az.bankservice.model.transactions.TransactionResponse;

public interface TransactionUtilService {
    TransactionResponse create(Long accountId, TransactionAccountRequest transactionAccountRequest,
                               TransactionType transactionType);

    void updateStatus(Long id, TransactionStatus transactionStatus);

}

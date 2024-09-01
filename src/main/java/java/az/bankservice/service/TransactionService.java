package java.az.bankservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.az.bankservice.model.transactions.TransactionFilterDto;
import java.az.bankservice.model.transactions.TransactionResponse;

public interface TransactionService {
    TransactionResponse getByID(Long transactionId);
    Page<TransactionResponse> findByFilter(TransactionFilterDto transactionFilterDto, Pageable pageable);
}

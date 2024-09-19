package com.bank.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.service.model.transactions.TransactionFilterDto;
import com.bank.service.model.transactions.TransactionResponse;

public interface TransactionService {
    TransactionResponse getByID(Long transactionId);
    Page<TransactionResponse> findByFilter(TransactionFilterDto transactionFilterDto, Pageable pageable);
}

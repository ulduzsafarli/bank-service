package com.bank.service.service.impl;

import com.bank.service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bank.service.entities.Transaction;
import com.bank.service.enumeration.transaction.TransactionStatus;
import com.bank.service.enumeration.transaction.TransactionType;
import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.mapper.TransactionMapper;
import com.bank.service.model.transactions.TransactionAccountRequest;
import com.bank.service.model.transactions.TransactionFilterDto;
import com.bank.service.model.transactions.TransactionRequest;
import com.bank.service.model.transactions.TransactionResponse;
import com.bank.service.repository.TransactionRepository;
import com.bank.service.service.util.TransactionUtilService;
import com.bank.service.util.specifications.TransactionSpecifications;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService, TransactionUtilService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private static final String NOT_FOUND = "Transactions not found for account ID: %s";

    @Override
    public TransactionResponse create(Long accountId,
                                      TransactionAccountRequest transactionAccountRequest,
                                      TransactionType transactionType) {
        log.info("Creating transaction for account number {} for transferring money, details: {}", accountId, transactionAccountRequest);
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .amount(transactionAccountRequest.getAmount())
                .comments(transactionAccountRequest.getComments())
                .type(transactionType)
                .status(TransactionStatus.PENDING)
                .transactionUUID(UUID.randomUUID().toString())
                .accountId(accountId).build();
        var transactionEntity = transactionMapper.fromRequestDto(transactionRequest);
        transactionRepository.save(transactionEntity);
        return transactionMapper.toResponseDto(transactionEntity);
    }

    @Override
    public void updateStatus(Long id, TransactionStatus transactionStatus) {
        log.info("Updating status for transaction ID {} to status {}", id, transactionStatus);
        var transaction = getByID(id);
        transaction.setStatus(transactionStatus);
        transactionRepository.save(transactionMapper.fromResponseDto(transaction));
    }

    @Override
    public TransactionResponse getByID(Long id) {
        log.info("Receiving all {} transactions", id);
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND, id)));
        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public Page<TransactionResponse> findByFilter(TransactionFilterDto transactionFilterDto, Pageable pageable) {
        log.info("Searching transactions by filter: {}", transactionFilterDto);
        Specification<Transaction> accountSpecification = TransactionSpecifications.getAccountSpecification(transactionFilterDto);
        Page<Transaction> transactionsPage = transactionRepository.findAll(accountSpecification, pageable);
        return transactionsPage.map(transactionMapper::toResponseDto);
    }
}

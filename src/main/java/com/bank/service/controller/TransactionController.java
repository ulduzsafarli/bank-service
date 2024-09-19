package com.bank.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.bank.service.model.transactions.TransactionFilterDto;
import com.bank.service.model.transactions.TransactionResponse;
import com.bank.service.service.TransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/search")
    public Page<TransactionResponse> getByFilter(TransactionFilterDto transactionFilterDto,
                                                 @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {
        return transactionService.findByFilter(transactionFilterDto, pageable);
    }

    @GetMapping("/{id}")
    public TransactionResponse getById(@PathVariable Long id) {
        return transactionService.getByID(id);
    }
}

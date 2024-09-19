package com.bank.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.service.enumeration.accounts.AccountStatus;
import com.bank.service.model.accounts.AccountCreateDto;
import com.bank.service.model.accounts.AccountFilterDto;
import com.bank.service.model.accounts.AccountRequest;
import com.bank.service.model.accounts.AccountResponse;

public interface AccountService {

    Page<AccountResponse> findByFilter(AccountFilterDto accountFilterDto, Pageable pageRequest);

    AccountResponse getById(Long accountId);

    AccountResponse create(AccountCreateDto account);

    AccountResponse update(Long accountId, AccountRequest account);

    void delete(Long accountId);

    void updateStatus(Long id, AccountStatus accountStatusUpdate);
}

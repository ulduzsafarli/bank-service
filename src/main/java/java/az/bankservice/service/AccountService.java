package java.az.bankservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.az.bankservice.enumeration.accounts.AccountStatus;
import java.az.bankservice.model.accounts.AccountCreateDto;
import java.az.bankservice.model.accounts.AccountFilterDto;
import java.az.bankservice.model.accounts.AccountRequest;
import java.az.bankservice.model.accounts.AccountResponse;

public interface AccountService {

    Page<AccountResponse> findByFilter(AccountFilterDto accountFilterDto, Pageable pageRequest);

    AccountResponse getById(Long accountId);

    AccountResponse create(AccountCreateDto account);

    AccountResponse update(Long accountId, AccountRequest account);

    void delete(Long accountId);

    void updateStatus(Long id, AccountStatus accountStatusUpdate);
}

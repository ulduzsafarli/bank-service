package com.bank.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.bank.service.enumeration.accounts.AccountStatus;
import com.bank.service.model.accounts.AccountCreateDto;
import com.bank.service.model.accounts.AccountFilterDto;
import com.bank.service.model.accounts.AccountRequest;
import com.bank.service.model.accounts.AccountResponse;
import com.bank.service.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/search")
    public Page<AccountResponse> getByFilter(AccountFilterDto accountFilterDto,
                                             @PageableDefault(direction = Sort.Direction.ASC) Pageable pageable) {
        return accountService.findByFilter(accountFilterDto, pageable);
    }

    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody @Valid AccountCreateDto account) {
        return accountService.create(account);
    }

    @PutMapping("/{id}")
    public AccountResponse update(@PathVariable Long id, @Valid @RequestBody AccountRequest account) {
        return accountService.update(id, account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @PutMapping("/{id}/status/{status}")
    public void updateStatus(@PathVariable Long id, @PathVariable AccountStatus status) {
        accountService.updateStatus(id, status);
    }

}

package com.bank.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.service.entities.Account;
import com.bank.service.enumeration.NotificationType;
import com.bank.service.enumeration.accounts.AccountStatus;
import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.mapper.AccountMapper;
import com.bank.service.model.accounts.AccountCreateDto;
import com.bank.service.model.accounts.AccountFilterDto;
import com.bank.service.model.accounts.AccountRequest;
import com.bank.service.model.accounts.AccountResponse;
import com.bank.service.model.notifications.NotificationRequest;
import com.bank.service.repository.AccountRepository;
import com.bank.service.service.AccountService;
import com.bank.service.service.util.AccountUtilService;
import com.bank.service.service.NotificationService;
import com.bank.service.service.util.UserUtilService;
import com.bank.service.util.GenerateRandom;
import com.bank.service.util.specifications.AccountSpecifications;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService, AccountUtilService {

    private static final String WITH_ID_NOT_FOUND = "Account with ID %s not found.";
    private static final String WITH_NUMBER_NOT_FOUND = "Account with number %s not found.";

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserUtilService userUtilService;
    private final NotificationService notificationService;

    @Override
    public Page<AccountResponse> findByFilter(AccountFilterDto accountFilterDto, Pageable pageRequest) {
        log.info("Searching accounts by filter: {}", accountFilterDto);
        Specification<Account> accountSpecification = AccountSpecifications.getAccountSpecification(accountFilterDto);
        Page<Account> accountEntityPage = accountRepository.findAll(accountSpecification, pageRequest);
        return accountEntityPage.map(accountMapper::toDto);
    }

    @Override
    public AccountResponse getById(Long id) {
        log.info("Retrieving account by ID: {}", id);
        return accountRepository.findById(id)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public AccountResponse getByAccountNumber(String accountNumber) {
        log.info("Retrieving account by account number: {}", accountNumber);
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(WITH_NUMBER_NOT_FOUND, accountNumber)));
    }

    @Override
    public void updateBalance(String accountNumber, BigDecimal newBalance) {
        log.info("Updating balance for account number: {}", accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException(String.format(WITH_NUMBER_NOT_FOUND, accountNumber)));

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            sendNotification(account.getId(), "Insufficient funds in the account for updating your balance", NotificationType.ALERT);
            throw new IllegalArgumentException("Insufficient funds in the account");
        }

        account.setCurrentBalance(newBalance);
        accountRepository.save(account);
        sendNotification(account.getId(), "Your balance was updated successfully", NotificationType.UPDATE);
    }

    @Override
    @Transactional
    public AccountResponse create(AccountCreateDto accountDto) {
        log.info("Creating account for user: {}", accountDto.userId());
        userUtilService.createCif(accountDto.userId());

        Account account = accountMapper.fromRequestDtoForUser(accountDto);
        account.setStatus(AccountStatus.ACTIVE);
        account.setPin(passwordEncoder.encode(accountDto.pin()));
        account.setAccountNumber(GenerateRandom.generateAccountNumber());

        accountRepository.save(account);

        sendNotification(accountDto.userId(), "Your account has been successfully created. Details:\n" + account, NotificationType.MESSAGE);

        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountResponse update(Long id, AccountRequest accountRequest) {
        log.info("Updating account by ID: {}", id);
        Account account = getAccountById(id);

        accountMapper.updateEntityFromDto(accountRequest, account);
        accountRepository.save(account);

        sendNotification(account.getId(), "Successfully updated your account. Details:\n" + account, NotificationType.MESSAGE);

        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public void delete(Long accountId) {
        log.info("Deleting account by ID: {}", accountId);
        accountRepository.deleteById(accountId);
    }

    @Override
    public void updateStatus(Long id, AccountStatus newStatus) {
        log.info("Updating status for account ID: {}", id);
        Account account = getAccountById(id);

        if (account.getStatus().equals(newStatus)) {
            throw new IllegalArgumentException("Account is already " + newStatus);
        }

        account.setStatus(newStatus);
        accountRepository.save(account);
        sendNotification(account.getId(), "The status of your account has been updated", NotificationType.ALERT);
    }

    @Override
    public void validatePin(AccountResponse account, String pin) {
        log.info("Validating PIN for account number: {}", account.getAccountNumber());
        Account accountEntity = getAccountById(account.getId());

        boolean isPinValid = passwordEncoder.matches(pin, accountEntity.getPin());
        log.info(isPinValid ? "PIN verification successful." : "PIN verification failed.");

        if (!isPinValid) {
            throw new IllegalArgumentException("Invalid PIN provided");
        }
    }

    private Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(WITH_ID_NOT_FOUND, id)));
    }

    private void sendNotification(Long userId, String message, NotificationType notificationType) {
        notificationService.create(NotificationRequest.builder()
                .message(message)
                .type(notificationType)
                .userId(userId)
                .build());
    }
}

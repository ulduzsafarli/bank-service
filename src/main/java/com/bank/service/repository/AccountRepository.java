package com.bank.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.service.entities.Account;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findAll(Specification<Account> spec, Pageable pageRequest);

    Optional<Account> findByAccountNumber(String accountNumber);
}

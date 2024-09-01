package java.az.bankservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.az.bankservice.entities.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageRequest);
    Optional<Transaction> findByTransactionUUID(String transactionUUID);
}

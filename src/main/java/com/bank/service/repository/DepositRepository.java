package com.bank.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.service.entities.Deposit;
import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Query("SELECT d FROM Deposit d LEFT JOIN FETCH d.account WHERE EXTRACT(DAY FROM d.account.createdAt) = :dayOfMonth")
    List<Deposit> findDepositsCreatedOnDate(@Param("dayOfMonth") int dayOfMonth);
}
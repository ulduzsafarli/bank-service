package com.bank.service.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bank.service.entities.abstractentity.Auditable;
import com.bank.service.enumeration.transaction.TransactionStatus;
import com.bank.service.enumeration.transaction.TransactionType;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transactions", schema = "public")
public class Transaction extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column(name = "transaction_uuid",unique = true)
    private String transactionUUID;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
package java.az.bankservice.model.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.transaction.TransactionStatus;
import java.az.bankservice.enumeration.transaction.TransactionType;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String transactionUUID;
    private String comments;
    private Long accountId;
}

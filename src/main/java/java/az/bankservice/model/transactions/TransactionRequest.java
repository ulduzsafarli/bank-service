package java.az.bankservice.model.transactions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.transaction.TransactionStatus;
import java.az.bankservice.enumeration.transaction.TransactionType;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank(message="Amount must not be blank")
    private BigDecimal amount;
    @Size(max = 1000, message = "The max size of message is 1000")
    private TransactionType type;
    private TransactionStatus status;
    private String transactionUUID;
    private String comments;
    private Long accountId;
}

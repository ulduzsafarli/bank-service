package java.az.bankservice.model.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matrix.izumbankapp.enumeration.accounts.CurrencyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponseDto {
    private Double amount;
    private CurrencyType fromCurrency;
    private CurrencyType toCurrency;
    private Double convertedAmount;
}

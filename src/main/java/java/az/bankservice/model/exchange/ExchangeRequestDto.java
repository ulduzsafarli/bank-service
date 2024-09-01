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
public class ExchangeRequestDto {
    private Double amount;
    private CurrencyType currencyType;
}

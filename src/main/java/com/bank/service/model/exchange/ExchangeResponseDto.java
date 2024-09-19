package com.bank.service.model.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bank.service.enumeration.accounts.CurrencyType;

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

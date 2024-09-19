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
public class ExchangeRequestDto {
    private Double amount;
    private CurrencyType currencyType;
}

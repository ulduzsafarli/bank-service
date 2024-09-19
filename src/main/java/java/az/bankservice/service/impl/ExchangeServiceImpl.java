package java.az.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.az.bankservice.enumeration.accounts.CurrencyType;
import java.az.bankservice.model.exchange.ExchangeRequestDto;
import java.az.bankservice.model.exchange.ExchangeResponseDto;
import java.az.bankservice.service.ExchangeService;
import java.az.bankservice.util.FetchingUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    @Override
    public ExchangeResponseDto fromAZN(ExchangeRequestDto exchange) {
        return performExchange(exchange, true);
    }

    @Override
    public ExchangeResponseDto toAZN(ExchangeRequestDto exchange) {
        return performExchange(exchange, false);
    }

    private ExchangeResponseDto performExchange(ExchangeRequestDto exchange, boolean isFromAZN) {
        log.info("Performing exchange {} {} with amount {}", isFromAZN ? "from AZN to" : "to AZN from", exchange.getCurrencyType(),
                exchange.getAmount());

        Map<String, BigDecimal> rates = FetchingUtil.fetchRates();
        if (!rates.containsKey(exchange.getCurrencyType().name())) {
            throw new IllegalArgumentException("Unsupported currency: " + exchange.getCurrencyType());
        }

        BigDecimal rate = rates.get(exchange.getCurrencyType().name());
        BigDecimal originalAmount = BigDecimal.valueOf(exchange.getAmount());
        BigDecimal convertedAmount = isFromAZN ? originalAmount.divide(rate, 2, RoundingMode.HALF_EVEN) :
                originalAmount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);

        return ExchangeResponseDto.builder()
                .amount(exchange.getAmount())
                .fromCurrency(isFromAZN ? CurrencyType.AZN : exchange.getCurrencyType())
                .toCurrency(isFromAZN ? exchange.getCurrencyType() : CurrencyType.AZN)
                .convertedAmount(convertedAmount.doubleValue())
                .build();
    }

}



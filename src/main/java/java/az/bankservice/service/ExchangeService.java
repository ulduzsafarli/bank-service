package java.az.bankservice.service;

import java.az.bankservice.model.exchange.ExchangeRequestDto;
import java.az.bankservice.model.exchange.ExchangeResponseDto;

public interface ExchangeService {

    ExchangeResponseDto fromAZN(ExchangeRequestDto exchange);

    ExchangeResponseDto toAZN(ExchangeRequestDto exchangeResponseDto);

}
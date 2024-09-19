package com.bank.service.service;

import com.bank.service.model.exchange.ExchangeRequestDto;
import com.bank.service.model.exchange.ExchangeResponseDto;

public interface ExchangeService {

    ExchangeResponseDto fromAZN(ExchangeRequestDto exchange);

    ExchangeResponseDto toAZN(ExchangeRequestDto exchangeResponseDto);

}
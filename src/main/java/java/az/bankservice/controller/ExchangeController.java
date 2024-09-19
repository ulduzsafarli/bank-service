package java.az.bankservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.az.bankservice.model.exchange.ExchangeRequestDto;
import java.az.bankservice.model.exchange.ExchangeResponseDto;
import java.az.bankservice.service.ExchangeService;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/from-AZN")
    public ExchangeResponseDto fromAZN(@Valid @RequestBody ExchangeRequestDto exchangeResponseDto) {
        return exchangeService.fromAZN(exchangeResponseDto);
    }

    @PostMapping("/to-AZN")
    public ExchangeResponseDto toAZN(@Valid @RequestBody ExchangeRequestDto exchangeResponseDto) {
        return exchangeService.toAZN(exchangeResponseDto);
    }

}
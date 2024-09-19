package com.bank.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.service.service.CurrencyService;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public void fetch() {
        currencyService.fetch();
    }

    @GetMapping("/file")
    public String getFile() {
        return currencyService.getFile();
    }
}

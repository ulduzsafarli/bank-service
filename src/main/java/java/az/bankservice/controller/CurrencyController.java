package java.az.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.az.bankservice.service.CurrencyService;

@RestController
@RequestMapping("/api/v1/currency")
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

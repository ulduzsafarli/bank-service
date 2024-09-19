package com.bank.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.bank.service.model.accounts.TransferMoneyRequest;
import com.bank.service.model.accounts.WithdrawalRequest;
import com.bank.service.service.OperationService;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/transfer")
    public void transferToAccount(@Valid @RequestBody TransferMoneyRequest transferMoneyRequest) {
        operationService.transferMoney(transferMoneyRequest);
    }

    @PostMapping("/withdrawal")
    public void withdrawal(@Valid @RequestBody WithdrawalRequest withdrawalRequest) {
        operationService.withdrawal(withdrawalRequest);
    }

    @GetMapping("/{accountNumber}/balance")
    public String getBalance(@PathVariable String accountNumber) {
        return operationService.getBalance(accountNumber);
    }
}

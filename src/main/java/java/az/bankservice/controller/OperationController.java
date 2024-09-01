package java.az.bankservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.az.bankservice.model.accounts.TransferMoneyRequest;
import java.az.bankservice.model.accounts.WithdrawalRequest;
import java.az.bankservice.service.OperationService;

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

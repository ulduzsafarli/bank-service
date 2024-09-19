package java.az.bankservice.model.accounts;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.accounts.AccountStatus;
import java.az.bankservice.enumeration.accounts.AccountType;
import java.az.bankservice.enumeration.accounts.CurrencyType;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    @NotBlank(message = "Branch code must not be null")
    @Pattern(regexp = "\\d{3}", message = "Branch code must contain 3 digits")
    private String branchCode;
    @Future(message = "Account expire date must be in the future")
    private LocalDate accountExpireDate;
    @NotNull(message = "Currency type must not be null")
    private CurrencyType currencyType;
    @NotNull(message = "Account type must not be null")
    private AccountType accountType;
    @NotNull(message = "Account status must not be null")
    private AccountStatus status;
    @NotNull(message = "Available balance must not be null")
    private BigDecimal availableBalance;
    @NotNull(message = "Current balance must not be null")
    private BigDecimal currentBalance;
    @DecimalMax(value = "10000", message = "Transaction limit must be at most 10000")
    private BigDecimal transactionLimit;
    @NotBlank(message = "PIN must not be null")
    @Pattern(regexp = "\\d{4}", message = "PIN should contain 4 digits")
    private String pin;
}

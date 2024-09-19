package com.bank.service.util.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import com.bank.service.entities.Account;
import com.bank.service.model.accounts.AccountFilterDto;

import static com.bank.service.util.specifications.SpecificationUtil.*;


@UtilityClass
public class AccountSpecifications {
    public static Specification<Account> getAccountSpecification(AccountFilterDto filter) {

        var spec = Specification.<Account>where(
                        likeIgnoreCase("branchCode", filter.getBranchCode()))
                .and(isEqual("accountNumber", filter.getAccountNumber()))
                .and(isEqual("currencyType", filter.getCurrencyType()))
                .and(isEqual("accountType", filter.getAccountType()))
                .and(isEqual("status", filter.getStatus()))
                .and(isEqual("currentBalance", filter.getCurrentBalance()))
                .and(isEqual("transactionLimit", filter.getTransactionLimit()));

        if (filter.getCreatedAt() != null) {
            spec = spec.and(filterByDates(filter.getCreatedAt(), filter.getAccountExpireDate(), "createdAt"));
        }
        if (filter.getAccountExpireDate() != null) {
            spec = spec.and(filterByDates(filter.getCreatedAt(), filter.getAccountExpireDate(), "accountExpireDate"));
        }

        return spec;
    }
}

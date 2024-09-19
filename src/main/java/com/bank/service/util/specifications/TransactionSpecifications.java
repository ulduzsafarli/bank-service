package com.bank.service.util.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import com.bank.service.entities.Transaction;
import com.bank.service.model.transactions.TransactionFilterDto;

@UtilityClass
public class TransactionSpecifications {

    public static Specification<Transaction> getAccountSpecification(TransactionFilterDto transactionFilterDto) {
        var spec = Specification.<Transaction>where(
                        SpecificationUtil.isEqual("status", transactionFilterDto.getStatus()))
                .and(SpecificationUtil.isEqual("type", transactionFilterDto.getType()));

        if (transactionFilterDto.getFromAmount() != null) {
            spec = spec.and(SpecificationUtil.greaterThanOrEqualTo("amount", transactionFilterDto.getFromAmount()));
        }

        if (transactionFilterDto.getToAmount() != null) {
            spec = spec.and(SpecificationUtil.lessThanOrEqualTo("amount", transactionFilterDto.getToAmount()));
        }

        return spec;
    }


}

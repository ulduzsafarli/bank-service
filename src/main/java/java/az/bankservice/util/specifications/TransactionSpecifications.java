package java.az.bankservice.util.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.az.bankservice.entities.Transaction;
import java.az.bankservice.model.transactions.TransactionFilterDto;

import static java.az.bankservice.util.specifications.SpecificationUtil.*;

@UtilityClass
public class TransactionSpecifications {

    public static Specification<Transaction> getAccountSpecification(TransactionFilterDto transactionFilterDto) {
        var spec = Specification.<Transaction>where(
                        isEqual("status", transactionFilterDto.getStatus()))
                .and(isEqual("type", transactionFilterDto.getType()));

        if (transactionFilterDto.getFromAmount() != null) {
            spec = spec.and(greaterThanOrEqualTo("amount", transactionFilterDto.getFromAmount()));
        }

        if (transactionFilterDto.getToAmount() != null) {
            spec = spec.and(lessThanOrEqualTo("amount", transactionFilterDto.getToAmount()));
        }

        return spec;
    }


}

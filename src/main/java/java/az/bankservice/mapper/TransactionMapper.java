package java.az.bankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.az.bankservice.entities.Account;
import java.az.bankservice.entities.Transaction;
import java.az.bankservice.model.transactions.TransactionRequest;
import java.az.bankservice.model.transactions.TransactionResponse;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {
    @Mapping(target = "account", source = "accountId", qualifiedByName = "toAccountEntity")
    Transaction fromRequestDto(TransactionRequest transactionRequest);
    @Mapping(target = "account", source = "accountId", qualifiedByName = "toAccountEntity")
    Transaction fromResponseDto(TransactionResponse transactionResponse);
    @Mapping(source = "account.id", target = "accountId")
    TransactionResponse toResponseDto(Transaction transaction);

    @Named("toAccountEntity")
    default Account toAccountEntity(Long accountId) {
        if (accountId == null) {
            return null;
        }
        Account account = new Account(accountId);
        account.setId(accountId);
        return account;
    }
}

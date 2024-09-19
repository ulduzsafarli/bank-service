package com.bank.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.bank.service.entities.Account;
import com.bank.service.entities.Transaction;
import com.bank.service.model.transactions.TransactionRequest;
import com.bank.service.model.transactions.TransactionResponse;
import com.bank.service.model.transactions.TransactionAccountResponse;

import java.util.List;

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

    // Mapping individual Transaction to TransactionAccountResponse
    TransactionAccountResponse toTransactionAccountResponse(Transaction transaction);

    // Mapping List<Transaction> to List<TransactionAccountResponse>
    List<TransactionAccountResponse> map(List<Transaction> transactions);

    // Mapping individual TransactionAccountResponse to Transaction
    Transaction toTransaction(TransactionAccountResponse transactionAccountResponse);

    // Mapping List<TransactionAccountResponse> to List<Transaction>
    List<Transaction> mapToEntityList(List<TransactionAccountResponse> transactionResponses);
}

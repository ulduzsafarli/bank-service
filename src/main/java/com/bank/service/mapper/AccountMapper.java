package com.bank.service.mapper;

import org.mapstruct.*;
import com.bank.service.entities.Account;
import com.bank.service.model.accounts.AccountCreateDto;
import com.bank.service.model.accounts.AccountRequest;
import com.bank.service.model.accounts.AccountResponse;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TransactionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "transactions", target = "transactionResponseList")
    AccountResponse toDto(Account account);

    @InheritInverseConfiguration
    @Mapping(source = "transactionResponseList", target = "transactions")
    Account toEntity(AccountResponse accountResponse);

    @Mapping(target = "user.id", source = "userId")
    Account fromRequestDtoForUser(AccountCreateDto accountCreateDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(AccountRequest accountRequest, @MappingTarget Account accountEntity);
}

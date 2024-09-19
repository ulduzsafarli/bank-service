package java.az.bankservice.mapper;

import org.mapstruct.*;
import java.az.bankservice.entities.Account;
import java.az.bankservice.model.accounts.AccountCreateDto;
import java.az.bankservice.model.accounts.AccountRequest;
import java.az.bankservice.model.accounts.AccountResponse;

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

    @Mapping(target = "id", ignore = true) // Ignore ID for updates if not needed
    Account updateEntityFromDto(AccountRequest accountRequest, @MappingTarget Account accountEntity);
}

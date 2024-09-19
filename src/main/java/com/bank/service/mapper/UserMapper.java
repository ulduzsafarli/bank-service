package com.bank.service.mapper;

import com.bank.service.model.users.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.bank.service.entities.User;
import com.bank.service.entities.Account;
import com.bank.service.model.accounts.AccountsUserResponse;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, AccountMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    // Use the specific UserProfileMapper to map UserProfile to UserProfileDto
    @Mapping(source = "userProfile", target = "userProfileDto")
    UserResponse toDto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "cif", target = "cif")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "accounts", target = "accounts")
    UserAccountsResponse toAccountsDto(User user);

    User toEntity(UserRequest userRequest);

    User toEntity(UserCreateDto userCreateDto);

    User toEntity(UserResponse userResponse);

    User updateEntityFromRequest(UserUpdateDto userCreateDto, @MappingTarget User user);

    // Mapping method for converting a list of Account entities to a list of AccountsUserResponse
    List<AccountsUserResponse> map(List<Account> accounts);

    // Optional: Mapping method for converting a single Account entity to AccountsUserResponse
    AccountsUserResponse map(Account account);
}

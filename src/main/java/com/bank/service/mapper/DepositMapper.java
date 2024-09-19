package com.bank.service.mapper;

import org.mapstruct.*;

import com.bank.service.entities.Deposit;
import com.bank.service.model.deposits.DepositResponse;

@Mapper(componentModel = "spring", uses = {AccountMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepositMapper {
    DepositResponse toResponseDto(Deposit deposit);
    @Mapping(source = "account", target = "account")
    Deposit toEntity(DepositResponse depositResponse);
}

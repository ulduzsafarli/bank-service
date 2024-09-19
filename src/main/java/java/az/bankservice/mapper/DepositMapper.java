package java.az.bankservice.mapper;

import org.mapstruct.*;

import java.az.bankservice.entities.Deposit;
import java.az.bankservice.model.deposits.DepositResponse;

@Mapper(componentModel = "spring", uses = {AccountMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepositMapper {
    DepositResponse toResponseDto(Deposit deposit);
    @Mapping(source = "account", target = "account")
    Deposit toEntity(DepositResponse depositResponse);
}

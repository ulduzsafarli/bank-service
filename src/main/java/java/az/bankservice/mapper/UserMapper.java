package java.az.bankservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.az.bankservice.entities.User;
import java.az.bankservice.model.users.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
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
}

package java.az.bankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.az.bankservice.entities.UserProfile;
import java.az.bankservice.model.users.profile.UserProfileDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserProfileMapper {

    UserProfileDto toDto(UserProfile userProfile);

    UserProfile toEntity(UserProfileDto userProfileDto);
}
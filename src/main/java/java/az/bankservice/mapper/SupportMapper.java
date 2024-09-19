package java.az.bankservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.az.bankservice.entities.Support;
import java.az.bankservice.model.support.SupportDto;
import java.az.bankservice.model.support.SupportResponseDto;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupportMapper {
    Support toEntity(SupportDto supportDto);
    SupportResponseDto toResponseList(Support support);
}

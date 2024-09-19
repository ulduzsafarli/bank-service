package com.bank.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.bank.service.entities.Support;
import com.bank.service.model.support.SupportDto;
import com.bank.service.model.support.SupportResponseDto;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupportMapper {
    Support toEntity(SupportDto supportDto);
    SupportResponseDto toResponseList(Support support);
}

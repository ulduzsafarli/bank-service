package java.az.bankservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.matrix.izumbankapp.dao.entities.Support;
import org.matrix.izumbankapp.model.support.SupportDto;
import org.matrix.izumbankapp.model.support.SupportResponseDto;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupportMapper {
    Support toEntity(SupportDto supportDto);
    SupportResponseDto toResponseList(Support support);
}

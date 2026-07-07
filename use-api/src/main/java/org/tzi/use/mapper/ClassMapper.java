package org.tzi.use.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.entities.ClassNTT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassMapper {
    ClassDTO toDTO(ClassNTT entity);

    ClassNTT toEntity(ClassDTO dto);
}

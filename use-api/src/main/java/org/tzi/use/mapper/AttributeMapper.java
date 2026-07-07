package org.tzi.use.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.tzi.use.DTO.AttributeDTO;
import org.tzi.use.entities.AttributeNTT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeMapper {
    AttributeDTO toDTO(AttributeNTT entity);

    AttributeNTT toEntity(AttributeDTO dto);
}


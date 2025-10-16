package org.tzi.use.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.tzi.use.DTO.InvariantDTO;
import org.tzi.use.entities.InvariantNTT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface InvariantMapper {
    InvariantDTO toDTO(InvariantNTT entity);

    InvariantNTT toEntity(InvariantDTO dto);
}

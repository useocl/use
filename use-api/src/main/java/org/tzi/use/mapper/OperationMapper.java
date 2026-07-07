package org.tzi.use.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.tzi.use.DTO.OperationDTO;
import org.tzi.use.entities.OperationNTT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationMapper {
    OperationDTO toDTO(OperationNTT entity);

    OperationNTT toEntity(OperationDTO dto);
}


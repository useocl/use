package org.tzi.use.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.tzi.use.DTO.AssociationDTO;
import org.tzi.use.entities.AssociationNTT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssociationMapper {
    AssociationDTO toDTO(AssociationNTT entity);

    AssociationNTT toEntity(AssociationDTO dto);
}

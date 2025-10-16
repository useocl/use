package org.tzi.use.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.tzi.use.DTO.PrePostConditionDTO;
import org.tzi.use.entities.PrePostConditionNTT;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrePostConditionMapper {
    PrePostConditionDTO toDTO(PrePostConditionNTT entity);

    PrePostConditionNTT toEntity(PrePostConditionDTO dto);
}

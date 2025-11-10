package org.tzi.use.rest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.AttributeDTO;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.DTO.OperationDTO;
import org.tzi.use.DTO.PrePostConditionDTO;
import org.tzi.use.repository.ClassRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;

    public ClassDTO getClassByName(String className) {
        return null;
    }

    public List<AttributeDTO> getAttributes(String className) {
        return null;
    }

    public List<OperationDTO> getOperations(String className) {
        return null;
    }

    public List<PrePostConditionDTO> getPrePostConditions(String className) {
        return null;
    }

    public AttributeDTO addAttribute(String className, AttributeDTO attributeDTO) {
        return null;
    }

    public OperationDTO addOperation(String className, OperationDTO operationDTO) {
        return null;
    }

    public PrePostConditionDTO addPrePostCondition(String className, PrePostConditionDTO prePostCondition) {
        return null;
    }

    public PrePostConditionDTO getPrePostConditionByName(String className, String conditionName) {
        return null;
    }

    public OperationDTO getOperationByName(String className, String operationName) {
        return null;
    }

    public AttributeDTO getAttributeByName(String className, String attributeName) {
        return null;
    }
}

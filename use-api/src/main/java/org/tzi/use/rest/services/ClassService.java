package org.tzi.use.rest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.AttributeDTO;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.DTO.OperationDTO;
import org.tzi.use.DTO.PrePostConditionDTO;
import org.tzi.use.entities.ClassNTT;
import org.tzi.use.mapper.*;
import org.tzi.use.repository.ClassRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;
    private final ClassMapper classMapper;
    private final AttributeMapper attributeMapper;
    private final OperationMapper operationMapper;
    private final PrePostConditionMapper prePostConditionMapper;

    public ClassDTO getClassByName(String className) {
        Optional<ClassNTT> classNTTOpt = classRepo.findById(className);
        return classMapper.toDTO(classNTTOpt.get());
    }

    public List<AttributeDTO> getAttributes(String className) {
        Optional<ClassNTT> classNTTOpt = classRepo.findById(className);
        return classNTTOpt.get().getAttributes().stream().map(attributeMapper::toDTO).toList();
    }

    public List<OperationDTO> getOperations(String className) {
        Optional<ClassNTT> classNTTOpt = classRepo.findById(className);
        return classNTTOpt.get().getOperations().stream().map(operationMapper::toDTO).toList();
    }

//    public List<PrePostConditionDTO> getPrePostConditions(String className) {
//        Optional<ClassNTT> classNTTOpt = classRepo.findById(className);
//        return classNTTOpt.get().getPrePostCond().stream().map(prePostConditionMapper::toDTO).toList();
//    }

    public AttributeDTO addAttribute(String className, AttributeDTO attributeDTO) {
        return null;
    }

    public OperationDTO addOperation(String className, OperationDTO operationDTO) {
        return null;
    }

//    public PrePostConditionDTO addPrePostCondition(String className, PrePostConditionDTO prePostCondition) {
//        return null;
//    }

//    public PrePostConditionDTO getPrePostConditionByName(String className, String conditionName) {
//        return null;
//    }

    public OperationDTO getOperationByName(String className, String operationName) {
        return null;
    }

    public AttributeDTO getAttributeByName(String className, String attributeName) {
        return null;
    }
}

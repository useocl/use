package org.tzi.use.rest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.AttributeDTO;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.DTO.OperationDTO;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.entities.AttributeNTT;
import org.tzi.use.entities.ClassNTT;
import org.tzi.use.entities.OperationNTT;
import org.tzi.use.mapper.*;
import org.tzi.use.repository.ClassRepo;
import org.tzi.use.repository.ModelRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;
    private final ModelRepo modelRepo;
    private final ClassMapper classMapper;
    private final AttributeMapper attributeMapper;
    private final OperationMapper operationMapper;
    private final PrePostConditionMapper prePostConditionMapper;
    private final UseModelFacade useModelFacade;

    public ClassDTO getClassByName(String className) {
        ClassNTT classNTT = classRepo.findById(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        return classMapper.toDTO(classNTT);
    }

    public List<AttributeDTO> getAttributes(String className) {
        ClassNTT classNTT = classRepo.findById(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        return classNTT.getAttributes().stream().map(attributeMapper::toDTO).toList();
    }

    public List<OperationDTO> getOperations(String className) {
        ClassNTT classNTT = classRepo.findById(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        return classNTT.getOperations().stream().map(operationMapper::toDTO).toList();
    }

//    public List<PrePostConditionDTO> getPrePostConditions(String className) {
//        Optional<ClassNTT> classNTTOpt = classRepo.findById(className);
//        return classNTTOpt.get().getPrePostCond().stream().map(prePostConditionMapper::toDTO).toList();
//    }

    public AttributeDTO createAttribute(String className, AttributeDTO attributeDTO) throws UseApiException {
        AttributeNTT attributeNTT = attributeMapper.toEntity(attributeDTO);
        ClassNTT classNTT = classRepo.findById(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        // Resolve model name that contains this class
        String modelName = getModelNameForClass(className);
        useModelFacade.createAttribute(modelName, className, attributeNTT);
        classNTT.getAttributes().add(attributeNTT);
        classRepo.save(classNTT);
        return attributeMapper.toDTO(attributeNTT);
    }

    private String getModelNameForClass(String className) {
        return modelRepo.findByClassesName(className)
                .map(m -> m.getName())
                .orElseThrow(() -> new IllegalArgumentException("Model not found for class: " + className));
    }

    public OperationDTO createOperation(String className, OperationDTO operationDTO) throws UseApiException {
        OperationNTT operationNTT = operationMapper.toEntity(operationDTO);
        ClassNTT classNTT = classRepo.findById(className)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));

        String modelName = getModelNameForClass(className);
         useModelFacade.createOperation(modelName, className, operationNTT);

        classNTT.getOperations().add(operationNTT);
        classRepo.save(classNTT);
        return operationMapper.toDTO(operationNTT);
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

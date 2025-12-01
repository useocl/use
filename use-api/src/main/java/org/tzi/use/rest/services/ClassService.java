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
import org.tzi.use.entities.ModelNTT;
import org.tzi.use.entities.OperationNTT;
import org.tzi.use.mapper.*;
import org.tzi.use.repository.ModelRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ModelRepo modelRepo;
    private final ClassMapper classMapper;
    private final AttributeMapper attributeMapper;
    private final OperationMapper operationMapper;
    private final PrePostConditionMapper prePostConditionMapper;
    private final UseModelFacade useModelFacade;

    public ClassDTO getClassByName(String className) {
        ModelNTT model = modelRepo.findByClassesName(className)
                .orElseThrow(() -> new IllegalArgumentException("Model not found for class: " + className));
        ClassNTT classNTT = model.getClasses().stream()
                .filter(c -> c.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        return classMapper.toDTO(classNTT);
    }

    public List<AttributeDTO> getAttributes(String className) {
        ModelNTT model = modelRepo.findByClassesName(className)
                .orElseThrow(() -> new IllegalArgumentException("Model not found for class: " + className));
        ClassNTT classNTT = model.getClasses().stream()
                .filter(c -> c.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        return classNTT.getAttributes().stream().map(attributeMapper::toDTO).toList();
    }

    public List<OperationDTO> getOperations(String className) {
        ModelNTT model = modelRepo.findByClassesName(className)
                .orElseThrow(() -> new IllegalArgumentException("Model not found for class: " + className));
        ClassNTT classNTT = model.getClasses().stream()
                .filter(c -> c.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
        return classNTT.getOperations().stream().map(operationMapper::toDTO).toList();
    }

//    public List<PrePostConditionDTO> getPrePostConditions(String className) {
//        Optional<ClassNTT> classNTTOpt = classRepo.findById(className);
//        return classNTTOpt.get().getPrePostCond().stream().map(prePostConditionMapper::toDTO).toList();
//    }

    public AttributeDTO createAttribute(String className, AttributeDTO attributeDTO) throws UseApiException {
        AttributeNTT attributeNTT = attributeMapper.toEntity(attributeDTO);

        // Find model containing this class
        ModelNTT model = modelRepo.findByClassesName(className)
                .orElseThrow(() -> new IllegalArgumentException("Model not found for class: " + className));
        ClassNTT classNTT = model.getClasses().stream()
                .filter(c -> c.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));

        // Create attribute in USE model
        useModelFacade.createAttribute(model.getName(), className, attributeNTT);

        // Add to entity and save through model
        classNTT.getAttributes().add(attributeNTT);
        modelRepo.save(model);
        return attributeMapper.toDTO(attributeNTT);
    }

    public OperationDTO createOperation(String className, OperationDTO operationDTO) throws UseApiException {
        OperationNTT operationNTT = operationMapper.toEntity(operationDTO);

        // Find model containing this class
        ModelNTT model = modelRepo.findByClassesName(className)
                .orElseThrow(() -> new IllegalArgumentException("Model not found for class: " + className));
        ClassNTT classNTT = model.getClasses().stream()
                .filter(c -> c.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));

        // Create operation in USE model
        useModelFacade.createOperation(model.getName(), className, operationNTT);

        // Add to entity and save through model
        classNTT.getOperations().add(operationNTT);
        modelRepo.save(model);
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

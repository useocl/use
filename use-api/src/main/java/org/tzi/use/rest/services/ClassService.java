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
    private final UseModelFacade useModelFacade;
    private final ModelService modelService;

    /*  Create  */
    public AttributeDTO createAttribute(String modelName, String className, AttributeDTO attributeDTO) throws UseApiException {
        AttributeNTT attributeNTT = attributeMapper.toEntity(attributeDTO);
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);

        useModelFacade.createAttribute(modelNTT, className, attributeNTT);

        classNTT.getAttributes().add(attributeNTT);
        modelRepo.save(modelNTT);
        return attributeMapper.toDTO(attributeNTT);
    }

    public OperationDTO createOperation(String modelName, String className, OperationDTO operationDTO) throws UseApiException {
        OperationNTT operationNTT = operationMapper.toEntity(operationDTO);
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);

        useModelFacade.createOperation(modelNTT, className, operationNTT);

        classNTT.getOperations().add(operationNTT);
        modelRepo.save(modelNTT);
        return operationMapper.toDTO(operationNTT);
    }

    /*  Get  */
    public ClassDTO getClassByName(String modelName, String className) {
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);
        return classMapper.toDTO(classNTT);
    }

    public List<AttributeDTO> getAttributes(String modelName, String className) {
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);
        return classNTT.getAttributes().stream().map(attributeMapper::toDTO).toList();
    }

    public List<OperationDTO> getOperations(String modelName, String className) {
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);
        return classNTT.getOperations().stream().map(operationMapper::toDTO).toList();
    }

    public OperationDTO getOperationByName(String modelName, String className, String operationName) {
        return getOperations(modelName,className).stream()
                .filter(op -> op.getOperationName().equals(operationName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Operation not found: " + operationName));
    }

    public AttributeDTO getAttributeByName(String modelName, String className, String attributeName) {
        return getAttributes(modelName, className).stream()
                .filter(attr -> attr.getName().equals(attributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeName));
    }

    /*  Delete  */
    public void deleteAttribute(String modelName, String className, String attributeName) {
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);

        boolean removed = classNTT.getAttributes().removeIf(attr -> attr.getName().equals(attributeName));
        if (!removed) {
            throw new IllegalArgumentException("Attribute not found: " + attributeName);
        }
        modelRepo.save(modelNTT);
    }

    public void deleteOperation(String modelName, String className, String operationName) {
        ModelNTT modelNTT = modelService.findModelByNameOrThrow(modelName);
        ClassNTT classNTT = findClassByNameOrThrow(modelNTT, className);

        boolean removed = classNTT.getOperations().removeIf(op -> op.getOperationName().equals(operationName));
        if (!removed) {
            throw new IllegalArgumentException("Operation not found: " + operationName);
        }
        modelRepo.save(modelNTT);
    }

    /*  Helper Methods  */
    private ClassNTT findClassByNameOrThrow(ModelNTT modelNTT, String className) {
        return modelNTT.getClasses().stream()
                .filter(c -> c.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + className));
    }
}

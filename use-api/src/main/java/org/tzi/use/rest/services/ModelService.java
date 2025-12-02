package org.tzi.use.rest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.*;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.entities.*;
import org.tzi.use.mapper.*;
import org.tzi.use.repository.ModelRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepo modelRepo;
    private final ModelMapper modelMapper;
    private final ClassMapperImpl classMapperImpl;
    private final InvariantMapperImpl invariantMapperImpl;
    private final AssociationMapperImpl associationMapperImpl;
    private final PrePostConditionMapper prePostConditionMapper;
    private final UseModelFacade useModelFacade;
    private final InvariantMapper invariantMapper;
    private final AssociationMapper associationMapper;
    private final ClassMapper classMapper;
    private final PrePostConditionMapperImpl prePostConditionMapperImpl;

    /*  Create  */
    public ModelDTO createModel(ModelDTO modelDTO) {
        if (modelRepo.findById(modelDTO.getName()).isPresent()) {
            throw new DuplicateKeyException("Model name already exists");
        }
        ModelNTT modelNTT = modelMapper.toEntity(modelDTO);

        useModelFacade.createModel(modelNTT.getName());

        modelRepo.save(modelNTT);
        return modelMapper.toDTO(modelNTT);
    }

    public ClassDTO createClass(String modelName, ClassDTO classDTO) throws UseApiException {

        ClassNTT classNTT = classMapper.toEntity(classDTO);

        ModelNTT modelNTT = findModelByNameOrThrow(modelName);

        //TODO doesnt uma already throw an error if class exists?
        boolean classExists = modelNTT.getClasses().stream().anyMatch(c -> c.getName().equals(classDTO.getName()));
        if (classExists) {
            throw new DuplicateKeyException("Class name already exists in model: " + modelName);
        }

        useModelFacade.createClass(modelNTT, classNTT.getName());
        modelNTT.getClasses().add(classNTT);
        modelRepo.save(modelNTT);

        return classMapper.toDTO(classNTT);
    }

    public PrePostConditionDTO createPrePostCondition(String modelName, PrePostConditionDTO prePostConditionDTO, String className) throws UseApiException {
        PrePostConditionNTT prePostConditionNTT = prePostConditionMapper.toEntity(prePostConditionDTO);
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);

        useModelFacade.createPrePostCondition(modelNTT, prePostConditionNTT, className);
        String name = className + "::" + prePostConditionDTO.getOperationName() + prePostConditionDTO.getName();

        modelNTT.getPrePostConditions().put(name, prePostConditionNTT);
        modelRepo.save(modelNTT);
        return prePostConditionMapper.toDTO(prePostConditionNTT);
    }

    public InvariantDTO createInvariant(String modelName, InvariantDTO invariantDTO, String className) throws UseApiException {
        InvariantNTT invariantNTT = invariantMapper.toEntity(invariantDTO);
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);

        useModelFacade.createInvariant(modelNTT, invariantNTT, className);
        modelNTT.getInvariants().put(className, invariantNTT);
        modelRepo.save(modelNTT);
        return invariantMapper.toDTO(invariantNTT);
    }

    public AssociationDTO createAssociation(String modelName, AssociationDTO association) throws UseApiException {
        AssociationNTT associationNTT = associationMapper.toEntity(association);
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);

        useModelFacade.createAssociation(modelNTT, associationNTT);
        modelNTT.getAssociations().put(associationNTT.getEnd1ClassName(), associationNTT);
//        modelNTT.getAssociations().put(associationNTT.getEnd2ClassName(), associationNTT);
        modelRepo.save(modelNTT);
        return associationMapper.toDTO(associationNTT);
    }

    /*    Get   */
    public ModelDTO getModelByName(String modelName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        return modelMapper.toDTO(modelNTT);
    }

    public List<ModelDTO> getAllModels() {
        return modelRepo.findAll().stream().map(modelMapper::toDTO).toList();
    }


    public List<ClassDTO> getModelClasses(String modelName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        return modelNTT.getClasses().stream().map(classMapperImpl::toDTO).toList();
    }

    public List<AssociationDTO> getModelAssociations(String modelName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        return modelNTT.getAssociations().values().stream().map(associationMapperImpl::toDTO).toList();
    }

    public List<InvariantDTO> getModelInvariants(String modelName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        return modelNTT.getInvariants().values().stream().map(invariantMapperImpl::toDTO).toList();
    }

    public List<PrePostConditionDTO> getModelPrePostConditions(String modelName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        return modelNTT.getPrePostConditions().values().stream().map(prePostConditionMapperImpl::toDTO).toList();
    }

    public AssociationDTO getAssociationByName(String modelName, String associationName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        AssociationNTT associationNTT = modelNTT.getAssociations().get(associationName);
        return associationMapper.toDTO(associationNTT);
    }

    public InvariantDTO getInvariantByName(String modelName, String invariantName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        InvariantNTT invariantNTT = modelNTT.getInvariants().get(invariantName);
        return invariantMapper.toDTO(invariantNTT);
    }

    public PrePostConditionDTO getPrePostConditionByName(String modelName, String prePostConditionName) {
        ModelNTT modelNTT = findModelByNameOrThrow(modelName);
        PrePostConditionNTT prePostConditionNTT = modelNTT.getPrePostConditions().get(prePostConditionName);
        return prePostConditionMapper.toDTO(prePostConditionNTT);
    }

    /*  Helper Methods  */
    private ModelNTT findModelByNameOrThrow(String modelName) {
        return modelRepo.findById(modelName).orElseThrow(() -> new IllegalArgumentException("Model not found: " + modelName));
    }

}

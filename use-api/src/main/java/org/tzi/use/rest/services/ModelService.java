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
import java.util.Optional;

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


    /**
     * Creates a new model with the given name
     *
     * @param modelDTOreq The DTO containing the model name and other properties
     * @return The created model with assigned ID
     * @throws UseApiException If there's an error creating the model
     */
    public ModelDTO createModel(ModelDTO modelDTOreq) {
        if (modelRepo.findById(modelDTOreq.getName()).isPresent()) {
            throw new DuplicateKeyException("Model name already exists");
        }
        ModelNTT tmp_modelntt = modelMapper.toEntity(modelDTOreq);

        useModelFacade.createModel(tmp_modelntt.getName());

        modelRepo.save(tmp_modelntt);
        return modelMapper.toDTO(tmp_modelntt);
    }

    public ClassDTO createClass(String modelName, ClassDTO classDTOreq) throws UseApiException {
        ClassNTT tmp_classntt = classMapperImpl.toEntity(classDTOreq);

        ModelNTT modelOfClass = modelRepo.findById(modelName)
                .orElseThrow(() -> new IllegalArgumentException("Model not found: " + modelName));

        // Check if class already exists in this model
        boolean classExists = modelOfClass.getClasses().stream()
                .anyMatch(c -> c.getName().equals(classDTOreq.getName()));
        if (classExists) {
            throw new DuplicateKeyException("Class name already exists in model: " + modelName);
        }

        // Create the class in the USE model
        useModelFacade.createClass(modelOfClass, tmp_classntt.getName());
        modelOfClass.getClasses().add(tmp_classntt);

        modelRepo.save(modelOfClass);
        return classMapperImpl.toDTO(tmp_classntt);

    }

    /**
     * Retrieves a model by its ID
     *
     * @return The model DTO
     * @throws UseApiException If the model is not found
     */
    public ModelDTO getModelByName(String name) {
        //isnt it a better practice to map it to a ntt first and then do the findBy?
        Optional<ModelNTT> tmp_modelntt = modelRepo.findById(name);
        return modelMapper.toDTO(tmp_modelntt.get());
    }

    /**
     * Retrieves all available models
     *
     * @return List of all model DTOs
     */
    public List<ModelDTO> getAllModels() {
        return modelRepo.findAll().stream().map(modelMapper::toDTO).toList();
    }

    public AssociationDTO getAssociationByName(String modelName, String associationName) {
        return null;
    }

    public InvariantDTO getInvariantByName(String modelName, String invariantName) {
        return null;
    }

    public List<ClassDTO> getModelClasses(String modelName){
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
            return modelOpt.get().getClasses().stream()
                    .map(classMapperImpl::toDTO)
                    .toList();
    }

    public List<AssociationDTO> getModelAssociations(String modelName) {
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
return modelOpt.get().getAssociations().values().stream().map(associationMapperImpl::toDTO).toList();
    }

    public List<InvariantDTO> getModelInvariants (String modelName) {
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
        return modelOpt.get().getInvariants().values().stream()
                .map(invariantMapperImpl::toDTO)
                .toList();
    }

    public InvariantDTO createInvariant(String modelName, InvariantDTO invariantDTOreq, String className) throws UseApiException {
        InvariantNTT tmp_invariantntt = invariantMapperImpl.toEntity(invariantDTOreq);

        Optional<ModelNTT> modelOfInvariant = modelRepo.findById(modelName);
        // find the class inside the model by className

        //TODO wenn die facade ein error bekommt wird die zeile darunter ausgeführt?
        useModelFacade.createInvariant(modelOfInvariant.get(),tmp_invariantntt, className);
        modelOfInvariant.get().getInvariants().put(className, tmp_invariantntt);
        //TODO
        modelRepo.save(modelOfInvariant.get());
        return invariantMapperImpl.toDTO(tmp_invariantntt);
    }

    public AssociationDTO createAssociation(String modelName, AssociationDTO association) throws UseApiException {
        AssociationNTT tmp_associationntt = associationMapperImpl.toEntity(association);
        Optional<ModelNTT> modelOfAssociation = modelRepo.findById(modelName);
        useModelFacade.createAssociation( modelOfAssociation.get(),tmp_associationntt);
        modelOfAssociation.get().getAssociations().put(tmp_associationntt.getEnd2ClassName(),tmp_associationntt);
        modelOfAssociation.get().getAssociations().put(tmp_associationntt.getEnd1ClassName(), tmp_associationntt);
        modelRepo.save(modelOfAssociation.get());
        return associationMapperImpl.toDTO(tmp_associationntt);
    }

    public PrePostConditionDTO createPrePostCondition(String modelName, PrePostConditionDTO prePostConditionDTO, String className) throws UseApiException {
        PrePostConditionNTT tmp_prepostconditionntt = prePostConditionMapper.toEntity(prePostConditionDTO);
        Optional<ModelNTT> modelOfPrePostCondition = modelRepo.findById(modelName);
        useModelFacade.createPrePostCondition(modelOfPrePostCondition.get(),tmp_prepostconditionntt, className);
        String name = className + "::" + prePostConditionDTO.getOperationName()
                + prePostConditionDTO.getName();

        modelOfPrePostCondition.get().getPrePostConditions().put(name, tmp_prepostconditionntt);
        modelRepo.save(modelOfPrePostCondition.get());
        return prePostConditionMapper.toDTO(tmp_prepostconditionntt);
    }

    public List<PrePostConditionDTO> getModelPrePostConditions(String modelName) {
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
        return modelOpt.get().getPrePostConditions().values().stream()
                .map(prePostConditionMapper::toDTO)
                .toList();
    }

    public PrePostConditionDTO getPrePostConditionByName(String modelName, String prePostConditionName) {
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
        PrePostConditionNTT prePostCondition = modelOpt.get().getPrePostConditions().get(prePostConditionName);
        return prePostConditionMapper.toDTO(prePostCondition);
    }
}

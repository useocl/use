package org.tzi.use.rest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.AssociationDTO;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.DTO.InvariantDTO;
import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.entities.ClassNTT;
import org.tzi.use.entities.InvariantNTT;
import org.tzi.use.entities.ModelNTT;
import org.tzi.use.mapper.AssociationMapperImpl;
import org.tzi.use.mapper.ClassMapperImpl;
import org.tzi.use.mapper.InvariantMapperImpl;
import org.tzi.use.mapper.ModelMapper;
import org.tzi.use.repository.ClassRepo;
import org.tzi.use.repository.ModelRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepo modelRepo;
    private final ClassRepo classRepo;
    private final ModelMapper modelMapper;
    private final ClassMapperImpl classMapperImpl;
    private final InvariantMapperImpl invariantMapperImpl;
    private final AssociationMapperImpl associationMapperImpl;


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

        UseModelFacade.createModel(tmp_modelntt.getName());

        modelRepo.save(tmp_modelntt);
        return modelMapper.toDTO(tmp_modelntt);
    }

    public ClassDTO createClass(String modelName, ClassDTO classDTOreq) throws UseApiException {
        if(classRepo.findById(classDTOreq.getName()).isPresent()) {
            throw new DuplicateKeyException("Class name already exists");
        }

        ClassNTT tmp_classntt = classMapperImpl.toEntity(classDTOreq);

        Optional<ModelNTT> modelOfClass = modelRepo.findById(modelName);
        //check for modelOfClass presence is nessary? -> I think no because cannot create model via api if it doesn't exist

        // if the class has attributed and operations this would not be enough
        UseModelFacade.createClass(tmp_classntt.getName());
        modelOfClass.get().addClass(tmp_classntt);

        modelRepo.save(modelOfClass.get());
        classRepo.save(tmp_classntt);
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

    public List<ClassDTO> getModelClasses(String modelName){
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
            return modelOpt.get().getClasses().stream()
                    .map(classMapperImpl::toDTO)
                    .toList();
    }

    public List<AssociationDTO> getModelAssociations(String modelName) {
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
        return modelOpt.get().getAssociations().stream().map(associationMapperImpl::toDTO).toList();
    }

    public List<InvariantDTO> getModelInvariants (String modelName) {
        Optional<ModelNTT> modelOpt = modelRepo.findById(modelName);
        return modelOpt.get().getInvariants().stream()
                .map(invariantMapperImpl::toDTO)
                .toList();
    }

    public InvariantDTO createInvariant(String modelName, InvariantDTO invariantDTOreq, String className) throws UseApiException {
        InvariantNTT tmp_invariantntt = invariantMapperImpl.toEntity(invariantDTOreq);

        Optional<ModelNTT> modelOfInvariant = modelRepo.findById(modelName);
        // find the class inside the model by className

        UseModelFacade.createInvariant(invariantDTOreq, className, modelName);
        modelOfInvariant.get().getInvariants().add(tmp_invariantntt);
        //TODO
        modelRepo.save(modelOfInvariant.get());
        return invariantMapperImpl.toDTO(tmp_invariantntt);
    }

    public void createAssociation(String modelName, AssociationDTO association) {

    }
}

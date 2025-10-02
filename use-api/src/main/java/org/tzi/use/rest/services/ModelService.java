package org.tzi.use.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.entities.ModelNTT;
import org.tzi.use.mapper.ModelMapper;
import org.tzi.use.repository.ModelRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepo modelRepo;
    @Autowired
    private ModelMapper modelMapper;


    /**
     * Creates a new model with the given name
     *
     * @param modelDTOreq The DTO containing the model name and other properties
     * @return The created model with assigned ID
     * @throws UseApiException If there's an error creating the model
     */
    public ModelDTO createModel(ModelDTO modelDTOreq) throws UseApiException {
        ModelNTT tmp_modelntt = modelMapper.toEntity(modelDTOreq);
        UseModelApi modelApi = UseModelFacade.createModel(tmp_modelntt.getName());

        // Save to repository
        modelRepo.save(tmp_modelntt);
        return modelMapper.toDTO(tmp_modelntt);
    }

    /**
     * Retrieves a model by its ID
     *
     * @param id The ID of the model to retrieve
     * @return The model DTO
     * @throws UseApiException If the model is not found
     */
    public ModelDTO getModelByName(String name) throws UseApiException {
        Optional<ModelNTT> tmp_modelntt = modelRepo.findById(name);
        ModelDTO tmp_modeldto = modelMapper.toDTO(tmp_modelntt.get());
        return tmp_modeldto;
    }

    /**
     * Retrieves all available models
     *
     * @return List of all model DTOs
     */
    public List<ModelDTO> getAllModels() {
        return modelRepo.findAll()
                .stream()
                .map(modelMapper::toDTO)
                .toList();
    }
}

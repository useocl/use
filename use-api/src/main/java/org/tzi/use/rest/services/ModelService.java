package org.tzi.use.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.repository.ModelRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModelService {
    @Autowired
    private ModelRepo modelRepo;

    // In-memory storage for model API instances
    private final Map<String, UseModelApi> modelInstances = new HashMap<>();

    /**
     * Creates a new model with the given name
     *
     * @param modelDTO The DTO containing the model name and other properties
     * @return The created model with assigned ID
     * @throws UseApiException If there's an error creating the model
     */
    public ModelDTO createModel(ModelDTO modelDTO) throws UseApiException {
        // Generate a unique ID for the model
        String modelId = UUID.randomUUID().toString();

        // Create a new model using the UseModelFacade
        UseModelApi modelApi = UseModelFacade.createModel(modelDTO.getName());

        // Store the model instance for future use
        modelInstances.put(modelId, modelApi);

        // Update the DTO with the ID
        modelDTO.setId(modelId);

        // Save to repository
        return modelRepo.save(modelDTO);
    }

    /**
     * Retrieves a model by its ID
     *
     * @param id The ID of the model to retrieve
     * @return The model DTO
     * @throws UseApiException If the model is not found
     */
    public ModelDTO getModelById(String id) throws UseApiException {
        Optional<ModelDTO> modelDTO = modelRepo.findById(id);
        if (modelDTO.isEmpty()) {
            throw new UseApiException("Model not found with ID: " + id);
        }

        return modelDTO.get();
    }

    /**
     * Retrieves all available models
     *
     * @return List of all model DTOs
     */
    public List<ModelDTO> getAllModels() {
        return modelRepo.findAll();
    }
}

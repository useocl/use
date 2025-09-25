package org.tzi.use.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.repository.ModelRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModelService {
    @Autowired
    private ModelRepo modelRepo;

    // In-memory storage for model API instances
    private final Map<String, UseModelApi> modelInstances = new HashMap<>();

    public ModelDTO createModel(ModelDTO modelDTO) throws UseApiException {
        // Generate a unique ID for the model
        String modelId = UUID.randomUUID().toString();

        // Create a new model using the ModelFacade
        UseModelApi modelApi = UseModelFacade.createModel(modelDTO.getName());

        // Store the model instance for future use
        modelInstances.put(modelId, modelApi);

        // Update the DTO with the ID
        modelDTO.setId(modelId);

        // Save to repository
        return modelRepo.save(modelDTO);
    }


    public ModelDTO getModelById(String id) throws UseApiException {
        Optional<ModelDTO> modelDTO = modelRepo.findById(id);
        if (modelDTO.isEmpty()) {
            throw new UseApiException("Model not found with ID: " + id);
        }

        return modelDTO.get();
    }
}

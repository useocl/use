package org.tzi.use.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.api.UseApiException;
import org.tzi.use.rest.services.ModelService;

@RestController
@RequestMapping("/api")
public class ModelController {

    @Autowired
    private ModelService modelService;

    /**
     * Create a new model
     * @param modelDTO The model data to create
     * @return The created model with HATEOAS links
     */
    @PostMapping("/model")
    public ResponseEntity<EntityModel<ModelDTO>> createModel(@RequestBody ModelDTO modelDTO) {
        try {
            ModelDTO createdModel = modelService.createModel(modelDTO);

            // Create an EntityModel (HATEOAS) with the response
            EntityModel<ModelDTO> entityModel = EntityModel.of(createdModel);

            // Add HATEOAS links
            // Link to self
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ModelController.class)
                    .getModelById(createdModel.getId()))
                    .withSelfRel());

            // Link to get all classes in this model
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ModelController.class)
                    .getModelClasses(createdModel.getId()))
                    .withRel("classes"));

            // Link to get all associations in this model
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ModelController.class)
                    .getModelAssociations(createdModel.getId()))
                    .withRel("associations"));

            // Link to get all invariants in this model
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ModelController.class)
                    .getModelInvariants(createdModel.getId()))
                    .withRel("invariants"));

            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ModelController.class)
                    .getModelPrePostConditions(createdModel.getId()))
                    .withRel("prepostconditions"));

            return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
        } catch (UseApiException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Placeholder method to retrieve a model by ID
     */
    @GetMapping("/model/{id}")
    public ResponseEntity<EntityModel<ModelDTO>> getModelById(@PathVariable String id) {
        // Implementation to be added
        return null;
    }

    /**
     * Placeholder method to retrieve all classes in a model
     */
    @GetMapping("/model/{id}/classes")
    public ResponseEntity<?> getModelClasses(@PathVariable String id) {
        // Implementation to be added
        return null;
    }

    /**
     * Placeholder method to retrieve all associations in a model
     */
    @GetMapping("/model/{id}/associations")
    public ResponseEntity<?> getModelAssociations(@PathVariable String id) {
        // Implementation to be added
        return null;
    }

    /**
     * Placeholder method to retrieve all invariants in a model
     */
    @GetMapping("/model/{id}/invariants")
    public ResponseEntity<?> getModelInvariants(@PathVariable String id) {
        // Implementation to be added
        return null;
    }

    /**
     * Placeholder method to retrieve all pre/post conditions in a model
     */
    @GetMapping("/model/{id}/prepostconditions")
    public ResponseEntity<?> getModelPrePostConditions(@PathVariable String id) {
        // Implementation to be added
        return null;
    }

    /*
    Endpoints that are needed (prefix /api):
    POST /model - Create a new model
    POST /model/class - Add a new class to a model
    GET /model/{id} - Retrieve a model by ID
    GET /model/{id}/classes - Retrieve all classes in a model
    GET /model/{id}/associations - Retrieve all association in a model
    GET /model/{id}/invariants - Retrieve all invariants in a model
    GET /model/{id}/prepostconditions - Retrieve all pre- / post-conditions in a model
     */
}

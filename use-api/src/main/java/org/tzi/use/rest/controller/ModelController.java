package org.tzi.use.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.rest.services.ModelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModelController {

    // No need for @Autowired with @RequiredArgsConstructor
    // because Lombok generates a constructor for final fields
    // and Spring will use that constructor for dependency injection
    private final ModelService modelService;

    /**
     * Create a new model
     * @param modelDTO The model data to create
     * @return The created model with HATEOAS links
     */
    @PostMapping("/model")
    public ResponseEntity<EntityModel<ModelDTO>> createModel(@RequestBody ModelDTO modelDTO){
        ModelDTO createdModel = modelService.createModel(modelDTO);

        // Create an EntityModel (HATEOAS) with the response
        EntityModel<ModelDTO> entityModel = EntityModel.of(createdModel);

        // Add HATEOAS links
        // Link to self
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelByName(createdModel.getName()))
                .withSelfRel());

        // Link to get all classes in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelClasses(createdModel.getName()))
                .withRel("classes"));

        // Link to get all associations in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelAssociations(createdModel.getName()))
                .withRel("associations"));

        // Link to get all invariants in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelInvariants(createdModel.getName()))
                .withRel("invariants"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    /**
     * Retrieve a model by its ID
     * @param name The ID of the model to retrieve
     * @return The model with HATEOAS links
     */
    @GetMapping("/model/{name}")
    public ResponseEntity<EntityModel<ModelDTO>> getModelByName(@PathVariable String name) {
        ModelDTO model = modelService.getModelByName(name);

        // Create an EntityModel (HATEOAS) with the response
        EntityModel<ModelDTO> entityModel = EntityModel.of(model);

        // Add HATEOAS links
        // Link to self
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelByName(name))
                .withSelfRel());

        // Link to get all classes in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelClasses(name))
                .withRel("classes"));

        // Link to get all associations in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelAssociations(name))
                .withRel("associations"));

        // Link to get all invariants in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelInvariants(name))
                .withRel("invariants"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    /**
     * Retrieve all models
     * @return List of all models with HATEOAS links
     */
    @GetMapping("/models")
    public ResponseEntity<CollectionModel<EntityModel<ModelDTO>>> getAllModels() {
        List<ModelDTO> models = modelService.getAllModels();

        // Convert each model to an EntityModel with links
        List<EntityModel<ModelDTO>> modelEntities = models.stream()
            .map(model -> {
                EntityModel<ModelDTO> entityModel = EntityModel.of(model);

                    // Add self link
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ModelController.class)
                            .getModelByName(model.getName()))
                            .withSelfRel());

                    // Add classes link
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ModelController.class)
                            .getModelClasses(model.getName()))
                            .withRel("classes"));

                    // Add associations link
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ModelController.class)
                            .getModelAssociations(model.getName()))
                            .withRel("associations"));

                    // Add invariants link
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ModelController.class)
                            .getModelInvariants(model.getName()))
                            .withRel("invariants"));


                return entityModel;
            })
            .collect(Collectors.toList());

        // Create a CollectionModel (container of EntityModels)
        CollectionModel<EntityModel<ModelDTO>> collectionModel =
            CollectionModel.of(modelEntities);

        // Add link to this collection
        collectionModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getAllModels())
                .withSelfRel());

        // Add link to create a new model
            collectionModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ModelController.class)
                    .createModel(null))
                    .withRel("create-model"));


        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PostMapping("/model/{name}/class")
    public ResponseEntity<?> get(@PathVariable String name) {
        // Implementation to be added
        return null;
    }

    /**
     * Placeholder method to retrieve all classes in a model
     */
    @GetMapping("/model/{name}/classes")
    public ResponseEntity<?> getModelClasses(@PathVariable String name) {
        // Implementation to be added
        return null;
    }

    @Deprecated
    /**
     * Deprecated for now
     * Placeholder method to retrieve all associations in a model
     */
    @GetMapping("/model/{name}/associations")
    public ResponseEntity<?> getModelAssociations(@PathVariable String name) {
        // Implementation to be added
        return null;
    }

    /**
     * Placeholder method to retrieve all invariants in a model
     */
    @GetMapping("/model/{name}/invariants")
    public ResponseEntity<?> getModelInvariants(@PathVariable String name) {
        // Implementation to be added
        return null;
    }

    /*
    Endpoints that are needed (prefix /api):
    POST /model - Create a new model :check:
    POST /model/class - Add a new class to a model
    POST /model/association - Add a new association to a model
    POST /model/invariant - Add a new invariant to a model
    GET /model/{id} - Retrieve a model by ID
    GET /model/{id}/classes - Retrieve all classes in a model
    GET /model/{id}/associations - Retrieve all association in a model
    GET /model/{id}/invariants - Retrieve all invariants in a model
     */
}

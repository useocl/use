package org.tzi.use.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.tzi.use.DTO.AssociationDTO;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.DTO.InvariantDTO;
import org.tzi.use.DTO.ModelDTO;
import org.tzi.use.api.UseApiException;
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

    @PostMapping("/model/{modelName}/class")
    public ResponseEntity<EntityModel<ClassDTO>> createClass(@PathVariable String modelName, @RequestBody ClassDTO classDTO) throws UseApiException {
        ClassDTO createdClass = modelService.createClass(modelName, classDTO);

        // Wrap the created class in an EntityModel for HATEOAS
        EntityModel<ClassDTO> entityModel = EntityModel.of(createdClass);

        // Link to the parent model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelByName(modelName))
                .withRel("model"));

        // Link to the classes listing for this model (also used as self since there's no single-class GET)
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelClasses(modelName))
                .withSelfRel());

        // Link to create another class in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(ModelController.class)
                .slash("model").slash(modelName).slash("class")
                .withRel("create-class"));

        // Link to associations for this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelAssociations(modelName))
                .withRel("associations"));

        // Link to invariants for this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelInvariants(modelName))
                .withRel("invariants"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    /**
     * Placeholder method to retrieve all classes in a model
     */
    @GetMapping("/model/{modelName}/classes")
    public ResponseEntity<?> getModelClasses(@PathVariable String modelName) {
        List<ClassDTO> modelClasses = modelService.getModelClasses(modelName);

        // Convert each class to an EntityModel with links
        List<EntityModel<ClassDTO>> classEntities = modelClasses.stream()
                .map(classDTO -> {
                    EntityModel<ClassDTO> entityModel = EntityModel.of(classDTO);

                    // Link to the parent model
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelByName(modelName))
                            .withRel("model"));

                    // Link to this classes collection (self)
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelClasses(modelName))
                            .withSelfRel());

                    // Link to create another class
                    try {
                        entityModel.add(WebMvcLinkBuilder.linkTo(
                                        WebMvcLinkBuilder.methodOn(ModelController.class)
                                                .createClass(modelName, null))
                                .withRel("create-class"));
                    } catch (UseApiException e) {
                        throw new RuntimeException(e);
                    }

                    // Link to associations
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelAssociations(modelName))
                            .withRel("associations"));

                    // Link to invariants
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelInvariants(modelName))
                            .withRel("invariants"));

                    return entityModel;
                })
                .collect(Collectors.toList());

        // Create a CollectionModel
        CollectionModel<EntityModel<ClassDTO>> collectionModel = CollectionModel.of(classEntities);

        // Add self-link to the collection
        collectionModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ModelController.class)
                                .getModelClasses(modelName))
                .withSelfRel());

        // Add link to create a new class
        try {
            collectionModel.add(WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(ModelController.class)
                                    .createClass(modelName, null))
                    .withRel("create-class"));
        } catch (UseApiException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }


    /**
     * Placeholder method to retrieve all associations in a model
     */
    @GetMapping("/model/{name}/associations")
    public ResponseEntity<?> getModelAssociations(@PathVariable String modelName) {
        List<AssociationDTO> associations = modelService.getModelAssociations(modelName);
        return null;
    }

    @PutMapping("/model/{modelName}/association")
    public ResponseEntity<?> createAssociation(@PathVariable String modelName, @RequestBody AssociationDTO association) {
        modelService.createAssociation(modelName, association);

        return null;
    }

    /**
     * Retrieve all invariants in a model.
     * @param modelName The name of the model.
     * @return A collection of invariants with HATEOAS links.
     */
    @GetMapping("/model/{modelName}/invariants")
    public ResponseEntity<CollectionModel<EntityModel<InvariantDTO>>> getModelInvariants(@PathVariable String modelName) {
        List<InvariantDTO> invariants = modelService.getModelInvariants(modelName);

        List<EntityModel<InvariantDTO>> invariantEntities = invariants.stream()
                .map(invariant -> {
                    EntityModel<InvariantDTO> entityModel = EntityModel.of(invariant);

                    // Link to the parent model
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelByName(modelName))
                            .withRel("model"));

                    // Self-link to this collection
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelInvariants(modelName))
                            .withSelfRel());

                    // Link to classes
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelClasses(modelName))
                            .withRel("classes"));

                    // Link to associations
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ModelController.class)
                                            .getModelAssociations(modelName))
                            .withRel("associations"));

                    return entityModel;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<InvariantDTO>> collectionModel = CollectionModel.of(invariantEntities);

        // Self-link for the collection
        collectionModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ModelController.class)
                                .getModelInvariants(modelName))
                .withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PostMapping("/model/{modelName}/{className}/invariant")
    public ResponseEntity<EntityModel<InvariantDTO>> createInvariant(@PathVariable String modelName,@PathVariable String className, @RequestBody InvariantDTO invariantDTO) throws UseApiException {
        InvariantDTO createdInvariant = modelService.createInvariant(modelName, invariantDTO, className);

        // Wrap the created invariant in an EntityModel for HATEOAS
        EntityModel<InvariantDTO> entityModel = EntityModel.of(createdInvariant);

        // Link to the parent model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelByName(modelName))
                .withRel("model"));

        // Link to the invariants listing for this model (also used as self since there's no single-invariant GET)
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelInvariants(modelName))
                .withSelfRel());

        // Link to create another invariant in this model
        entityModel.add(WebMvcLinkBuilder.linkTo(ModelController.class)
                .slash("model").slash(modelName).slash("invariant")
                .withRel("create-invariant"));

        // Link to classes for this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelClasses(modelName))
                .withRel("classes"));

        // Link to associations for this model
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ModelController.class)
                .getModelAssociations(modelName))
                .withRel("associations"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
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

package org.tzi.use.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tzi.use.DTO.*;
import org.tzi.use.api.UseApiException;
import org.tzi.use.rest.services.ModelService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModelController {

    // No need for @Autowired with @RequiredArgsConstructor
    // because Lombok generates a constructor for final fields
    // and Spring will use that constructor for dependency injection
    private final ModelService modelService;

    // ========================================
    // GET Mappings
    // ========================================

    /**
     * Retrieve a model by its ID
     *
     * @param modelName The ID of the model to retrieve
     * @return The model with HATEOAS links
     */
    @GetMapping("/model/{modelName}")
    public ResponseEntity<EntityModel<ModelDTO>> getModelByName(@PathVariable String modelName) throws UseApiException {
        ModelDTO modelDTO = modelService.getModelByName(modelName);

        EntityModel<ModelDTO> entityModel = EntityModel.of(modelDTO);

        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withSelfRel());
        entityModel.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withRel("classes"));
        entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelName)).withRel("associations"));
        entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withRel("invariants"));
        entityModel.add(linkTo(methodOn(ModelController.class).getPrePostConditions(modelName)).withRel("prePostConditions"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping("/model/{modelName}/association/{associationName}")
    public ResponseEntity<EntityModel<AssociationDTO>> getModelAssociationByName(@PathVariable String modelName, @PathVariable String associationName) throws UseApiException {
        AssociationDTO association = modelService.getAssociationByName(modelName, associationName);

        EntityModel<AssociationDTO> entityModel = EntityModel.of(association);

        entityModel.add(linkTo(methodOn(ModelController.class).getModelAssociationByName(modelName, associationName)).withSelfRel());
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelName)).withRel("associations"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping("/model/{modelName}/invariant/{invariantName}")
    public ResponseEntity<EntityModel<InvariantDTO>> getModelInvariantByName(@PathVariable String modelName, @PathVariable String invariantName) throws UseApiException {
        InvariantDTO invariant = modelService.getInvariantByName(modelName, invariantName);

        EntityModel<InvariantDTO> entityModel = EntityModel.of(invariant);

        entityModel.add(linkTo(methodOn(ModelController.class).getModelInvariantByName(modelName, invariantName)).withSelfRel());
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withRel("invariants"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping("/models")
    public ResponseEntity<CollectionModel<EntityModel<ModelDTO>>> getAllModels() throws UseApiException {
        List<ModelDTO> models = modelService.getAllModels();

        List<EntityModel<ModelDTO>> modelEntities = new ArrayList<>();
        for (ModelDTO modelDTO : models) {
            EntityModel<ModelDTO> entityModel = EntityModel.of(modelDTO);
            entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelDTO.getName())).withSelfRel());
            entityModel.add(linkTo(methodOn(ModelController.class).getClasses(modelDTO.getName())).withRel("classes"));
            entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelDTO.getName())).withRel("associations"));
            entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelDTO.getName())).withRel("invariants"));

            modelEntities.add(entityModel);
        }

        CollectionModel<EntityModel<ModelDTO>> collectionModel = CollectionModel.of(modelEntities);
        collectionModel.add(linkTo(methodOn(ModelController.class).getAllModels()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ModelController.class).createModel(null)).withRel("create model"));


        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }


    @GetMapping("/model/{modelName}/classes")
    public ResponseEntity<?> getClasses(@PathVariable String modelName) throws UseApiException {
        List<ClassDTO> modelClasses = modelService.getModelClasses(modelName);

        List<EntityModel<ClassDTO>> classEntities = new ArrayList<>();
        for (ClassDTO classOfModelName : modelClasses) {
            EntityModel<ClassDTO> entityModel = EntityModel.of(classOfModelName);

            entityModel.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withSelfRel());
            entityModel.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, null)).withRel("class by name"));

            classEntities.add(entityModel);
        }

        CollectionModel<EntityModel<ClassDTO>> collectionModel = CollectionModel.of(classEntities);
        collectionModel.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        collectionModel.add(linkTo(methodOn(ModelController.class).createClass(modelName, null)).withRel("create class"));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }


    /**
     * Placeholder method to retrieve all associations in a model
     */
    @GetMapping("/model/{modelName}/associations")
    public ResponseEntity<CollectionModel<EntityModel<AssociationDTO>>> getAssociations(@PathVariable String modelName) throws UseApiException {
        List<AssociationDTO> associations = modelService.getModelAssociations(modelName);

        List<EntityModel<AssociationDTO>> associationEntities = new ArrayList<>();
        for (AssociationDTO associationDTO : associations) {
            EntityModel<AssociationDTO> entityModel = EntityModel.of(associationDTO);

            entityModel.add(linkTo(methodOn(ModelController.class).getModelAssociationByName(modelName, associationDTO.getAssociationName())).withSelfRel());
            associationEntities.add(entityModel);
        }

        CollectionModel<EntityModel<AssociationDTO>> collectionModel = CollectionModel.of(associationEntities);

        collectionModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelName)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        collectionModel.add(linkTo(methodOn(ModelController.class).createAssociation(modelName, null)).withRel("create association"));


        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }


    @GetMapping("/model/{modelName}/invariants")
    public ResponseEntity<CollectionModel<EntityModel<InvariantDTO>>> getInvariants(@PathVariable String modelName) throws UseApiException {
        List<InvariantDTO> invariants = modelService.getModelInvariants(modelName);


        List<EntityModel<InvariantDTO>> invariantEntities = new ArrayList<>();
        for (InvariantDTO invariantDTO : invariants) {
            EntityModel<InvariantDTO> entityModel = EntityModel.of(invariantDTO);

            entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withSelfRel());

            invariantEntities.add(entityModel);
        }

        CollectionModel<EntityModel<InvariantDTO>> collectionModel = CollectionModel.of(invariantEntities);

        collectionModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        collectionModel.add(linkTo(methodOn(ModelController.class).createInvariant(modelName,null,null)).withRel("create invariant"));


        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/model/{modelName}/prepostconditions")
    public ResponseEntity<CollectionModel<EntityModel<PrePostConditionDTO>>> getPrePostConditions(@PathVariable String modelName) throws UseApiException {
        List<PrePostConditionDTO> prePostConditions = modelService.getModelPrePostConditions(modelName);


        List<EntityModel<PrePostConditionDTO>> prePostConditionEntities = new ArrayList<>();
        for (PrePostConditionDTO prePostConditionDTO : prePostConditions) {
            EntityModel<PrePostConditionDTO> entityModel = EntityModel.of(prePostConditionDTO);

            entityModel.add(linkTo(methodOn(ModelController.class).getPrePostConditions(modelName)).withSelfRel());

            prePostConditionEntities.add(entityModel);
        }

        CollectionModel<EntityModel<PrePostConditionDTO>> collectionModel = CollectionModel.of(prePostConditionEntities);

        collectionModel.add(linkTo(methodOn(ModelController.class).getPrePostConditions(modelName)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        collectionModel.add(linkTo(methodOn(ModelController.class).createprepostcondition(modelName,null,null)).withRel("create prepostcondition"));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/model/{modelName}/prepostcondition/{prePostConditionName}")
    public ResponseEntity<EntityModel<PrePostConditionDTO>> getModelPrePostCondByName(@PathVariable String modelName, @PathVariable String prePostConditionName) throws UseApiException {
        PrePostConditionDTO prePostCondition = modelService.getPrePostConditionByName(modelName, prePostConditionName);

        EntityModel<PrePostConditionDTO> entityModel = EntityModel.of(prePostCondition);

        entityModel.add(linkTo(methodOn(ModelController.class).getModelPrePostCondByName(modelName, prePostConditionName)).withSelfRel());
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));
        entityModel.add(linkTo(methodOn(ModelController.class).getPrePostConditions(modelName)).withRel("prePostConditions"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    // ========================================
    // POST Mappings
    // ========================================

    /**
     * Create a new model
     *
     * @param modelDTO The model data to create
     * @return The created model with HATEOAS links
     */
    @PostMapping("/model")
    public ResponseEntity<EntityModel<ModelDTO>> createModel(@RequestBody ModelDTO modelDTO) throws UseApiException {
        ModelDTO createdModel = modelService.createModel(modelDTO);

        // Create an EntityModel (HATEOAS) with the response
        EntityModel<ModelDTO> entityModel = EntityModel.of(createdModel);

        // Add HATEOAS links
        // Link to self
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(createdModel.getName())).withSelfRel());

        // Link to get all classes in this model
        entityModel.add(linkTo(methodOn(ModelController.class).getClasses(createdModel.getName())).withRel("classes"));

        // Link to get all associations in this model
        entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(createdModel.getName())).withRel("associations"));

        // Link to get all invariants in this model
        entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(createdModel.getName())).withRel("invariants"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PostMapping("/model/{modelName}/class")
    public ResponseEntity<EntityModel<ClassDTO>> createClass(@PathVariable String modelName, @RequestBody ClassDTO classDTO) throws UseApiException {
        ClassDTO createdClass = modelService.createClass(modelName, classDTO);

        // Wrap the created class in an EntityModel for HATEOAS
        EntityModel<ClassDTO> entityModel = EntityModel.of(createdClass);

        // Link to the parent model
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));

        // Link to the classes listing for this model (also used as self since there's no single-class GET)
        entityModel.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withSelfRel());

        // Link to create another class in this model
        entityModel.add(linkTo(ModelController.class).slash("model").slash(modelName).slash("class").withRel("create-class"));

        // Link to associations for this model
        entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelName)).withRel("associations"));

        // Link to invariants for this model
        entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withRel("invariants"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PostMapping("/model/{modelName}/association")
    public ResponseEntity<EntityModel<AssociationDTO>> createAssociation(@PathVariable String modelName, @RequestBody AssociationDTO association) throws UseApiException {
        AssociationDTO createdAssociation = modelService.createAssociation(modelName, association);

        EntityModel<AssociationDTO> entityModel = EntityModel.of(createdAssociation);

        entityModel.add(linkTo(methodOn(ModelController.class).getModelAssociationByName(modelName, createdAssociation.getAssociationName())).withSelfRel());
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PostMapping("/model/{modelName}/{className}/invariant")
    public ResponseEntity<EntityModel<InvariantDTO>> createInvariant(@PathVariable String modelName, @PathVariable String className, @RequestBody InvariantDTO invariantDTO) throws UseApiException {
        InvariantDTO createdInvariant = modelService.createInvariant(modelName, invariantDTO, className);

        // Wrap the created invariant in an EntityModel for HATEOAS
        EntityModel<InvariantDTO> entityModel = EntityModel.of(createdInvariant);

        // Link to the parent model
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));

        // Link to the invariants listing for this model (also used as self since there's no single-invariant GET)
        entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withSelfRel());

        // Link to create another invariant in this model
        entityModel.add(linkTo(ModelController.class).slash("model").slash(modelName).slash("invariant").withRel("create-invariant"));

        // Link to classes for this model
        entityModel.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withRel("classes"));

        // Link to associations for this model
        entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelName)).withRel("associations"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PostMapping("/model/{modelName}/{className}/prepostcondition")
    public ResponseEntity<EntityModel<PrePostConditionDTO>> createprepostcondition(@PathVariable String modelName, @PathVariable String className, @RequestBody PrePostConditionDTO prePostConditionDTO) throws UseApiException {
        PrePostConditionDTO createdPrePostCondition = modelService.createPrePostCondition(modelName, prePostConditionDTO, className);

        // Wrap the created pre/post condition in an EntityModel for HATEOAS
        EntityModel<PrePostConditionDTO> entityModel = EntityModel.of(createdPrePostCondition);

        // Link to the parent model
        entityModel.add(linkTo(methodOn(ModelController.class).getModelByName(modelName)).withRel("model"));

        // Link to the classes listing for this model (also used as self since there's no single-prepostcondition GET)
        entityModel.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withSelfRel());

        // Link to create another pre/post condition in this model
        entityModel.add(linkTo(ModelController.class).slash("model").slash(modelName).slash("prepostcondition").withRel("create-prepostcondition"));

        // Link to invariants for this model
        entityModel.add(linkTo(methodOn(ModelController.class).getInvariants(modelName)).withRel("invariants"));

        // Link to associations for this model
        entityModel.add(linkTo(methodOn(ModelController.class).getAssociations(modelName)).withRel("associations"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }
}

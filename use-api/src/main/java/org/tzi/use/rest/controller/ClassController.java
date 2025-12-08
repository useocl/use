package org.tzi.use.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tzi.use.DTO.AttributeDTO;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.DTO.OperationDTO;
import org.tzi.use.api.UseApiException;
import org.tzi.use.rest.services.ClassService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/models/{modelName}")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/class/{className}")
    public ResponseEntity<EntityModel<ClassDTO>> getClassByName(@PathVariable String modelName, @PathVariable String className) throws UseApiException {
        ClassDTO classDTO = classService.getClassByName(modelName, className);

        EntityModel<ClassDTO> entityModels = EntityModel.of(classDTO);

        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getAttributes(modelName, className)).withRel("attributes"));
        entityModels.add(linkTo(methodOn(ClassController.class).getOperations(modelName, className)).withRel("operations"));
        entityModels.add(linkTo(methodOn(ModelController.class).getClasses(modelName)).withRel("classes"));

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/class/{className}/attributes")
    public ResponseEntity<CollectionModel<EntityModel<AttributeDTO>>> getAttributes(@PathVariable String modelName, @PathVariable String className) throws UseApiException {
        List<AttributeDTO> attributes = classService.getAttributes(modelName, className);
        List<EntityModel<AttributeDTO>> attributeModels = new ArrayList<>();
        for (AttributeDTO attr : attributes) {
            EntityModel<AttributeDTO> attributeDTOEntityModel = EntityModel.of(attr);

            attributeDTOEntityModel.add(linkTo(methodOn(ClassController.class).getAttributeByName(modelName, className, attr.getName())).withSelfRel());

            attributeModels.add(attributeDTOEntityModel);
        }

        CollectionModel<EntityModel<AttributeDTO>> entityModels = CollectionModel.of(attributeModels);
        entityModels.add(linkTo(methodOn(ClassController.class).getAttributes(modelName, className)).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withRel("class"));

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/class/{className}/attribute/{attributeName}")
    public ResponseEntity<EntityModel<AttributeDTO>> getAttributeByName(@PathVariable String modelName, @PathVariable String className, @PathVariable String attributeName) throws UseApiException {
        AttributeDTO attribute = classService.getAttributeByName(modelName, className, attributeName);

        EntityModel<AttributeDTO> entityModels = EntityModel.of(attribute);

        entityModels.add(linkTo(methodOn(ClassController.class).getAttributeByName(modelName, className, attributeName)).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getAttributes(modelName, className)).withRel("attributes"));
        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withRel("class"));

        return ResponseEntity.ok(entityModels);
    }


    @GetMapping("/class/{className}/operations")
    public ResponseEntity<CollectionModel<EntityModel<OperationDTO>>> getOperations(@PathVariable String modelName, @PathVariable String className) throws UseApiException {
        List<OperationDTO> operations = classService.getOperations(modelName, className);
        List<EntityModel<OperationDTO>> operationModels = new ArrayList<>();
        for (OperationDTO op : operations) {
            EntityModel<OperationDTO> operationDTOEntityModel = EntityModel.of(op);

            operationDTOEntityModel.add(linkTo(methodOn(ClassController.class).getOperationByName(modelName, className, op.getOperationName())).withSelfRel());

            operationModels.add(operationDTOEntityModel);
        }

        CollectionModel<EntityModel<OperationDTO>> entityModels = CollectionModel.of(operationModels);

        entityModels.add(linkTo(methodOn(ClassController.class).getOperations(modelName, className)).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withRel("class"));

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/class/{className}/operation/{operationName}")
    public ResponseEntity<EntityModel<OperationDTO>> getOperationByName(@PathVariable String modelName, @PathVariable String className, @PathVariable String operationName) throws UseApiException {
        OperationDTO operation = classService.getOperationByName(modelName, className, operationName);

        EntityModel<OperationDTO> entityModels = EntityModel.of(operation);

        entityModels.add(linkTo(methodOn(ClassController.class).getOperationByName(modelName, className, operationName)).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getOperations(modelName, className)).withRel("operations"));
        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withRel("class"));


        return ResponseEntity.ok(entityModels);
    }



    @PostMapping("/class/{className}/attribute")
    public ResponseEntity<EntityModel<AttributeDTO>> addAttribute(@PathVariable String modelName, @PathVariable String className, @RequestBody AttributeDTO attributeDTO) throws UseApiException {
        AttributeDTO newAttribute = classService.createAttribute(modelName, className, attributeDTO);
        EntityModel<AttributeDTO> entityModels = EntityModel.of(newAttribute);

        entityModels.add(linkTo(methodOn(ClassController.class).getAttributeByName(modelName, className, newAttribute.getName())).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withRel("class"));


        return new ResponseEntity<>(entityModels, HttpStatus.CREATED);
    }

    @PostMapping("/class/{className}/operation")
    public ResponseEntity<EntityModel<OperationDTO>> addOperation(@PathVariable String modelName, @PathVariable String className, @RequestBody OperationDTO operationDTO) throws UseApiException {
        OperationDTO newOperation = classService.createOperation(modelName, className, operationDTO);

        EntityModel<OperationDTO> entityModels = EntityModel.of(newOperation);

        entityModels.add(linkTo(methodOn(ClassController.class).getOperationByName(modelName, className, newOperation.getOperationName())).withSelfRel());
        entityModels.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withRel("class"));

        return new ResponseEntity<>(entityModels, HttpStatus.CREATED);
    }
}

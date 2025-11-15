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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/models/{modelName}")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/class/{className}")
    public ResponseEntity<EntityModel<ClassDTO>> getClassByName(@PathVariable String modelName, @PathVariable String className) {
        ClassDTO classDTO = classService.getClassByName(className);
        EntityModel<ClassDTO> entityModel = EntityModel.of(classDTO);
        entityModel.add(linkTo(methodOn(ClassController.class).getClassByName(modelName, className)).withSelfRel());
        entityModel.add(linkTo(methodOn(ClassController.class).getAttributes(modelName, className)).withRel("attributes"));
        entityModel.add(linkTo(methodOn(ClassController.class).getOperations(modelName, className)).withRel("operations"));
//        entityModel.add(linkTo(methodOn(ClassController.class).getPrePostConditions(modelName, className)).withRel("preposts"));
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/class/{className}/attributes")
    public ResponseEntity<CollectionModel<EntityModel<AttributeDTO>>> getAttributes(@PathVariable String modelName, @PathVariable String className) {
        List<AttributeDTO> attributes = classService.getAttributes(className);
        List<EntityModel<AttributeDTO>> attributeModels = attributes.stream()
                .map(attr -> EntityModel.of(attr,
                        linkTo(methodOn(ClassController.class).getAttributeByName(modelName, className, attr.getName())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AttributeDTO>> collectionModel = CollectionModel.of(attributeModels);
        collectionModel.add(linkTo(methodOn(ClassController.class).getAttributes(modelName, className)).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/class/{className}/attribute/{attributeName}")
    public ResponseEntity<EntityModel<AttributeDTO>> getAttributeByName(@PathVariable String modelName, @PathVariable String className, @PathVariable String attributeName) {
        // Assuming ClassService has a method to get a single attribute by name
        AttributeDTO attribute = classService.getAttributeByName(className, attributeName);
        EntityModel<AttributeDTO> entityModel = EntityModel.of(attribute);
        entityModel.add(linkTo(methodOn(ClassController.class).getAttributeByName(modelName, className, attributeName)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }


    @GetMapping("/class/{className}/operations")
    public ResponseEntity<CollectionModel<EntityModel<OperationDTO>>> getOperations(@PathVariable String modelName, @PathVariable String className) {
        List<OperationDTO> operations = classService.getOperations(className);
        List<EntityModel<OperationDTO>> operationModels = operations.stream()
                .map(op -> EntityModel.of(op,
                        linkTo(methodOn(ClassController.class).getOperationByName(modelName, className, op.getOperationName())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<OperationDTO>> collectionModel = CollectionModel.of(operationModels);
        collectionModel.add(linkTo(methodOn(ClassController.class).getOperations(modelName, className)).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/class/{className}/operation/{operationName}")
    public ResponseEntity<EntityModel<OperationDTO>> getOperationByName(@PathVariable String modelName, @PathVariable String className, @PathVariable String operationName) {
        // Assuming ClassService has a method to get a single operation by name
        OperationDTO operation = classService.getOperationByName(className, operationName);
        EntityModel<OperationDTO> entityModel = EntityModel.of(operation);
        entityModel.add(linkTo(methodOn(ClassController.class).getOperationByName(modelName, className, operationName)).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

//    @GetMapping("/class/{className}/preposts")
//    public ResponseEntity<CollectionModel<EntityModel<PrePostConditionDTO>>> getPrePostConditions(@PathVariable String modelName, @PathVariable String className) {
//        List<PrePostConditionDTO> conditions = classService.getPrePostConditions(className);
//        List<EntityModel<PrePostConditionDTO>> conditionModels = conditions.stream()
//                .map(cond -> EntityModel.of(cond,
//                        linkTo(methodOn(ClassController.class).getPrePostConditionByName(modelName, className, cond.getName())).withSelfRel()))
//                .collect(Collectors.toList());
//
//        CollectionModel<EntityModel<PrePostConditionDTO>> collectionModel = CollectionModel.of(conditionModels);
//        collectionModel.add(linkTo(methodOn(ClassController.class).getPrePostConditions(modelName, className)).withSelfRel());
//        return ResponseEntity.ok(collectionModel);
//    }

//    @GetMapping("/class/{className}/prepost/{conditionName}")
//    public ResponseEntity<EntityModel<PrePostConditionDTO>> getPrePostConditionByName(@PathVariable String modelName, @PathVariable String className, @PathVariable String conditionName) {
//        // Assuming ClassService has a method to get a single pre/post condition by name
//        PrePostConditionDTO condition = classService.getPrePostConditionByName(className, conditionName);
//        EntityModel<PrePostConditionDTO> entityModel = EntityModel.of(condition);
//        entityModel.add(linkTo(methodOn(ClassController.class).getPrePostConditionByName(modelName, className, conditionName)).withSelfRel());
//        return ResponseEntity.ok(entityModel);
//    }


    @PostMapping("/class/{className}/attribute")
    public ResponseEntity<EntityModel<AttributeDTO>> addAttribute(@PathVariable String modelName, @PathVariable String className, @RequestBody AttributeDTO attributeDTO) throws UseApiException {
        AttributeDTO newAttribute = classService.createAttribute(className, attributeDTO);
        EntityModel<AttributeDTO> entityModel = EntityModel.of(newAttribute);
        entityModel.add(linkTo(methodOn(ClassController.class).getAttributeByName(modelName, className, newAttribute.getName())).withSelfRel());
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PostMapping("/class/{className}/operation")
    public ResponseEntity<EntityModel<OperationDTO>> addOperation(@PathVariable String modelName, @PathVariable String className, @RequestBody OperationDTO operationDTO) throws UseApiException {
        OperationDTO newOperation = classService.createOperation(className, operationDTO);
        EntityModel<OperationDTO> entityModel = EntityModel.of(newOperation);
        entityModel.add(linkTo(methodOn(ClassController.class).getOperationByName(modelName, className, newOperation.getOperationName())).withSelfRel());
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

//    @PostMapping("/class/{className}/prepost")
//    public ResponseEntity<EntityModel<PrePostConditionDTO>> addPrePostCondition(@PathVariable String modelName, @PathVariable String className, @RequestBody PrePostConditionDTO prePostConditionDTO) {
//        PrePostConditionDTO newCondition = classService.addPrePostCondition(className, prePostConditionDTO);
//        EntityModel<PrePostConditionDTO> entityModel = EntityModel.of(newCondition);
//        entityModel.add(linkTo(methodOn(ClassController.class).getPrePostConditionByName(modelName, className, newCondition.getName())).withSelfRel());
//        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
//    }
}

package org.tzi.use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tzi.use.DTO.AssociationDTO;
import org.tzi.use.DTO.InvariantDTO;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.entities.*;
import org.tzi.use.repository.ModelRepo;

import java.util.HashMap;
import java.util.Map;

@Component
public class UseModelFacade {

    private final Map<String, ModelNTT> modelNTTCache = new HashMap<>();
    @Autowired
    private ModelRepo modelRepo;

    private UseModelApi createUMAfromModelNTT(ModelNTT modelNTT) {
        UseModelApi result = new UseModelApi(modelNTT.getName());

        return result;
    }


    /**
     * Gets or creates a model API instance.
     * First attempts to load from repository, then creates if not found.
     * Caches the result for performance.
     */
    private ModelNTT getOrCreateModel(String modelName) {
        return modelNTTCache.computeIfAbsent(modelName, name -> {
            ModelNTT modelNTT = new ModelNTT(modelName);

            // Try to load existing model from repository
            modelRepo.findById(name).ifPresent(model -> {
                loadModelIntoCache(modelNTT, model);
            });

            return modelNTT;
        });
    }

    /**
     * Loads a persisted model from the repository into the UseModelApi instance.
     * This reconstructs the model structure (classes, attributes, operations, etc.)
     * from the stored ModelNTT entity.
     *
     * @param modelNTT The UseModelApi instance to load the model into
     * @param model    The persisted ModelNTT entity from the repository
     */
    private void loadModelIntoCache(ModelNTT modelNTT, ModelNTT model) {
        // Load all classes
        if (model.getClasses() != null) {
            for (ClassNTT classNTT : model.getClasses()) {
                modelNTT.addClass(classNTT);

                // Load attributes for this class
                if (classNTT.getAttributes() != null) {
                    for (AttributeNTT attribute : classNTT.getAttributes()) {
                        classNTT.getAttributes().add(attribute);
                    }
                }

                // Load operations for this class
                if (classNTT.getOperations() != null) {
                    for (OperationNTT operation : classNTT.getOperations()) {
                        classNTT.getOperations().add(operation);
                    }
                }
            }
        }

        // Load associations
        if (model.getAssociations() != null) {
            for (AssociationNTT association : model.getAssociations()) {
                modelNTT.getAssociations().add(association);
            }
        }
        if (model.getInvariants() != null) {
            for (InvariantNTT invariant : model.getInvariants()) {
                modelNTT.getInvariants().add(invariant);
            }
        }

//             Load pre/post conditions
        if (model.getPrePostConditions() != null) {
            for (Map.Entry<String, PrePostConditionNTT> entry : model.getPrePostConditions().entrySet()) {
                modelNTT.getPrePostConditions().put(entry.getKey(), entry.getValue());
            }
        }

    }


    public void createModel(String modelName) {
//        ModelNTT modelNTT = getOrCreateModel(modelName);
        new UseModelApi().createModel(modelName);
    }


    public void createClass(String modelName, String className) throws UseApiException {
        ModelNTT modelNTT = getOrCreateModel(modelName);

        UseModelApi useModelApi = loadUseModelApiFromModelNTT(modelNTT);
        useModelApi.createClass(className, false);
    }

    public void createInvariant(InvariantDTO invariantDTOreq, String className, String modelName) throws UseApiException {
//        UseModelApi api = getOrCreateModel(modelName);
        createClass(modelName, className);

//        createOperation(modelName, className, new OperationNTT("calculateAge", new String[][]{}, "Boolean"));
        // Create a query operation WITH a body
//        api.createAttribute(className, "birthYear", "Integer");
//        api.createAttribute(className, "calculateAge", "Integer");
//        api.createQueryOperation(
//                className,
//                "calculateAge",
//                new String[][]{},      // no parameters
//                "Integer",             // return type (should be Integer, not Boolean)
//                "(2024 - self.birthYear)",  // the actual implementation
//                false                  // not a constructor
//        );
//        // The invariant body from the request is "self.calculateAge >= 18"
//        String originalInvBody = invariantDTOreq.getInvBody();
//
//        // The definition of the 'calculateAge' operation
//        String calculateAgeBody = "(2024 - self.birthYear)";
//
//        // Replace the call to 'calculateAge' in the invariant with its actual OCL expression
//        String expandedInvBody = originalInvBody.replace("self.calculateAge", calculateAgeBody);

//        api.createInvariant(invariantDTOreq.getInvName(), className, invariantDTOreq.getInvBody(), invariantDTOreq.isExistential());

        // Save updated model to repository
    }

    public void createAssociation(AssociationDTO association, String modelName) throws UseApiException {
//        UseModelApi api = getOrCreateModel(modelName);
        ModelNTT modelNTT = getOrCreateModel(modelName);

        createClass(modelName, association.getEnd1ClassName());
        createClass(modelName, association.getEnd2ClassName());
//        api.createAssociation(association.getAssociationName(), association.getEnd1ClassName(), association.getEnd1RoleName(), association.getEnd1Multiplicity(), association.getEnd1Aggregation().ordinal(),
//                association.getEnd2ClassName(), association.getEnd2RoleName(), association.getEnd2Multiplicity(), association.getEnd2Aggregation().ordinal());

        // Save updated model to repository
    }

    public void createAttribute(String modelName, String className, AttributeNTT attributeNTT) throws UseApiException {
        ModelNTT modelNTT = getOrCreateModel(modelName);
        UseModelApi useModelApi = loadUseModelApiFromModelNTT(modelNTT);
        useModelApi.createAttribute(className, attributeNTT.getName(), attributeNTT.getType());
        modelRepo.save(modelNTT);
        // Save updated model to repository
    }

    private UseModelApi loadUseModelApiFromModelNTT(ModelNTT modelNTT) throws UseApiException {
        UseModelApi useModelApi = new UseModelApi();
        useModelApi.createModel(modelNTT.getName());

        // Load classes
        if (modelNTT.getClasses() != null) {
            for (ClassNTT classNTT : modelNTT.getClasses()) {
                useModelApi.createClass(classNTT.getName(), false);

                // Load attributes
                if (classNTT.getAttributes() != null) {
                    for (AttributeNTT attribute : classNTT.getAttributes()) {
                        useModelApi.createAttribute(classNTT.getName(), attribute.getName(), attribute.getType());
                    }
                }

                // Load operations
                if (classNTT.getOperations() != null) {
                    for (OperationNTT operation : classNTT.getOperations()) {
                        useModelApi.createOperation(classNTT.getName(), operation.getOperationName(), operation.getParameter(), operation.getReturnType());
                    }
                }
            }
        }

        // Load associations
        if (modelNTT.getAssociations() != null) {
            for (AssociationNTT association : modelNTT.getAssociations()) {
                useModelApi.createAssociation(association.getAssociationName(), association.getEnd1ClassName(), association.getEnd1RoleName(), association.getEnd1Multiplicity(), association.getEnd1Aggregation().ordinal(), association.getEnd2ClassName(), association.getEnd2RoleName(), association.getEnd2Multiplicity(), association.getEnd2Aggregation().ordinal());
            }
        }

        return useModelApi;
    }

    public void createOperation(String modelName, String className, OperationNTT operationNTT) throws UseApiException {
//        UseModelApi api = getOrCreateModel(modelName);
        createClass(modelName, className);
//        api.createOperation(className, operationNTT.getOperationName(), operationNTT.getParameter(), operationNTT.getReturnType());

        // Save updated model to repository
    }

    public void createPrePostCondition(PrePostConditionNTT ppc, String className, String modelName) throws UseApiException {
//        UseModelApi api = getOrCreateModel(modelName);
        createClass(modelName, className);
//        api.createPrePostCondition(className, ppc.getOperationName(), ppc.getName(), ppc.getCondition(), ppc.isPre());

    }

}

package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.DTO.*;
import org.tzi.use.entities.AttributeNTT;
import org.tzi.use.entities.OperationNTT;
import org.tzi.use.entities.PrePostConditionNTT;
import org.tzi.use.uml.mm.MClass;

import java.util.HashMap;
import java.util.Map;

public class UseModelFacade {
    // Cache UseModelApi instances per model name to maintain state
    private static final Map<String, UseModelApi> modelApiCache = new HashMap<>();

    /**
     * Gets or creates a UseModelApi instance for the given model name.
     * This ensures we maintain the same model instance across multiple operations.
     */
    private static UseModelApi getOrCreateModelApi(String modelName) {
        return modelApiCache.computeIfAbsent(modelName, name -> {
            UseModelApi api = new UseModelApi();
            api.createModel(name);
            return api;
        });
    }

    public static void createModel(String modelName) {
        // Use getOrCreateModelApi to ensure idempotent behavior
        getOrCreateModelApi(modelName);
    }

    /**
     * Creates a class in the specified model if it doesn't already exist.
     * This is idempotent - calling it multiple times with the same parameters is safe.
     *
     * @param modelName The name of the model containing the class
     * @param className The name of the class to create
     * @throws UseApiException If there's an error creating the class
     */
    public static void createClass(String modelName, String className) throws UseApiException {
        UseModelApi api = getOrCreateModelApi(modelName);

        // Check if class already exists
        MClass existingClass = api.getClass(className);
        if (existingClass == null) {
            api.createClass(className, false);
        }
        // If class already exists, do nothing (idempotent)
    }

    public static void createInvariant(InvariantDTO invariantDTOreq, String className, String modelName) throws UseApiException {
        UseModelApi api = getOrCreateModelApi(modelName);
        createClass(modelName, className);

//        createOperation(modelName, className, new OperationNTT("calculateAge", new String[][]{}, "Boolean"));
        // Create a query operation WITH a body
        api.createAttribute(className, "birthYear", "Integer");
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

        api.createInvariant(invariantDTOreq.getInvName(), className, invariantDTOreq.getInvBody(), invariantDTOreq.isExistential());

    }

    public static void createAssociation(AssociationDTO association, String modelName) throws UseApiException {
        UseModelApi api = getOrCreateModelApi(modelName);
        createClass(modelName, association.getEnd1ClassName());
        createClass(modelName, association.getEnd2ClassName());
        api.createAssociation(association.getAssociationName(), association.getEnd1ClassName(), association.getEnd1RoleName(), association.getEnd1Multiplicity(), association.getEnd1Aggregation().ordinal(),
                association.getEnd2ClassName(), association.getEnd2RoleName(), association.getEnd2Multiplicity(), association.getEnd2Aggregation().ordinal());
    }

    public static void createAttribute(String modelName, String className, AttributeNTT attributeNTT) throws UseApiException {
        UseModelApi api = getOrCreateModelApi(modelName);
        createClass(modelName, className);
        api.createAttribute(className, attributeNTT.getName(), attributeNTT.getType());
    }


    public static void createOperation(String modelName, String className, OperationNTT operationNTT) throws UseApiException {
        UseModelApi api = getOrCreateModelApi(modelName);
        createClass(modelName, className);
        api.createOperation(className, operationNTT.getOperationName(), operationNTT.getParameter(), operationNTT.getReturnType());
    }

    /**
     * Clears the cached model for the given model name.
     * This is useful when you want to start fresh with a model.
     * @param modelName The name of the model to clear
     */
    public static void clearModel(String modelName) {
        modelApiCache.remove(modelName);
    }

    /**
     * Clears all cached models.
     * This is useful for testing or when you want to reset the entire state.
     */
    public static void clearAllModels() {
        modelApiCache.clear();
    }

    /**
     * Gets the UseModelApi instance for a specific model name.
     * Returns null if the model hasn't been created yet.
     * @param modelName The name of the model
     * @return The UseModelApi instance or null if not found
     */
    public static UseModelApi getModelApi(String modelName) {
        return modelApiCache.get(modelName);
    }

    public static void createPrePostCondition(PrePostConditionNTT ppc, String className, String modelName) throws UseApiException {
        UseModelApi api = getOrCreateModelApi(modelName);
        createClass(modelName, className);
        api.createPrePostCondition(className,ppc.getOperationName(),ppc.getName(), ppc.getCondition(), ppc.isPre());
    }
}

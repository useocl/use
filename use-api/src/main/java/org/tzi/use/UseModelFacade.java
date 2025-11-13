package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.DTO.*;
import org.tzi.use.entities.AttributeNTT;
import org.tzi.use.entities.OperationNTT;

public class UseModelFacade {
    private static final UseModelApi useModelApi = new UseModelApi();

    public static void createModel(String modelName) {
        useModelApi.createModel(modelName);
    }


    public static void createClass(String className) throws UseApiException {
            useModelApi.createClass(className, false);
    }

    public static void createInvariant(InvariantDTO invariantDTOreq, String className, String modelName) throws UseApiException {
        createModel(modelName);
        createClass(className);
        useModelApi.createInvariant(invariantDTOreq.getInvName(),className, invariantDTOreq.getInvBody(), invariantDTOreq.isExistential());
    }

    public static void createAssociation(AssociationDTO association, String modelName) throws UseApiException {
        createModel(modelName);
        createClass(association.getEnd1ClassName());
        createClass(association.getEnd2ClassName());
        useModelApi.createAssociation(association.getAssociationName(), association.getEnd1ClassName(), association.getEnd1RoleName(), association.getEnd1Multiplicity(), association.getEnd1Aggregation().ordinal(),
                association.getEnd2ClassName(), association.getEnd2RoleName(), association.getEnd2Multiplicity(), association.getEnd2Aggregation().ordinal());
    }

    public static void createAttribute(String modelName, String className, AttributeNTT attributeNTT) {
        createModel(modelName);
        //TODO might change to methode signature with UseApiException
        try {
            createClass(className);
        } catch (UseApiException e) {
            throw new RuntimeException(e);
        }
        try {
            useModelApi.createAttribute(className, attributeNTT.getName(), attributeNTT.getType());
        } catch (UseApiException e) {
            throw new RuntimeException(e);
        }

    }


    public static void createOperation(String modelName, String className, OperationNTT operationNTT) {
        createModel(modelName);
        //TODO might change to methode signature with UseApiException
        try {
            createClass(className);
        } catch (UseApiException e) {
            throw new RuntimeException(e);
        }
        try {
            useModelApi.createOperation(className, operationNTT.getOperationName(), operationNTT.getParameter(), operationNTT.getReturnType());
        } catch (UseApiException e) {
            throw new RuntimeException(e);
        }
    }
}

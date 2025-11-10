package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.DTO.*;
import org.tzi.use.entities.ClassNTT;
import org.tzi.use.entities.ModelNTT;
import org.tzi.use.uml.mm.MInvalidModelException;

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
}

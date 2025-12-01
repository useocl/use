package org.tzi.use;

import org.springframework.stereotype.Component;
import org.tzi.use.DTO.AssociationDTO;
import org.tzi.use.DTO.InvariantDTO;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.entities.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class UseModelFacade {

    private final Map<String, UseModelApi> umaCache = new HashMap<>();

    private static String extractClassName(String concatedClassName) {
        String[] parts = concatedClassName.split("::");
        return parts[0];
    }

    private UseModelApi getUMAfromModelNTT(ModelNTT modelNTT) throws UseApiException {

        if (umaCache.containsKey(modelNTT.getName())) return umaCache.get(modelNTT.getName());

        UseModelApi result = new UseModelApi(modelNTT.getName());
        for (ClassNTT aClass : modelNTT.getClasses()) {
            result.createClass(aClass.getName(), false);
            for (AttributeNTT attribute : aClass.getAttributes()) {
                result.createAttribute(aClass.getName(), attribute.getName(), attribute.getType());
            }
            for (OperationNTT operation : aClass.getOperations()) {
                result.createOperation(aClass.getName(), operation.getOperationName(), operation.getParameter(), operation.getReturnType());
            }
        }
        for (Map.Entry<String, PrePostConditionNTT> ppcClass : modelNTT.getPrePostConditions().entrySet()) {
            result.createPrePostCondition(extractClassName(ppcClass.getKey()), ppcClass.getValue().getOperationName(), ppcClass.getValue().getName(), ppcClass.getValue().getCondition(), ppcClass.getValue().isPre());
        }

        for (Map.Entry<String, AssociationNTT> assocClass : modelNTT.getAssociations().entrySet()) {
            result.createAssociation(assocClass.getValue().getAssociationName(), assocClass.getValue().getEnd1ClassName(), assocClass.getValue().getEnd1RoleName(), assocClass.getValue().getEnd1Multiplicity(), assocClass.getValue().getEnd1Aggregation().ordinal(), assocClass.getValue().getEnd2ClassName(), assocClass.getValue().getEnd2RoleName(), assocClass.getValue().getEnd2Multiplicity(), assocClass.getValue().getEnd2Aggregation().ordinal());
        }

        for (Map.Entry<String, InvariantNTT> invariantClass : modelNTT.getInvariants().entrySet()) {
            result.createInvariant(invariantClass.getValue().getInvName(), extractClassName(invariantClass.getKey()), invariantClass.getValue().getInvBody(), invariantClass.getValue().isExistential());
        }

        umaCache.put(modelNTT.getName(), result);
        return result;
    }

    public void createModel(String modelName) {
        //TOOD what do to when creating a new model?
        new UseModelApi().createModel(modelName);
    }


    public void createClass(ModelNTT modelNTT, String className) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createClass(className, false);
    }

    public void createInvariant(InvariantDTO invariantDTOreq, String className, ModelNTT modelNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createInvariant(invariantDTOreq.getInvName(), className, invariantDTOreq.getInvBody(), invariantDTOreq.isExistential());
    }

    public void createAssociation(AssociationDTO association, ModelNTT modelNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createAssociation(association.getAssociationName(), association.getEnd1ClassName(), association.getEnd1RoleName(), association.getEnd1Multiplicity(), association.getEnd1Aggregation().ordinal(), association.getEnd2ClassName(), association.getEnd2RoleName(), association.getEnd2Multiplicity(), association.getEnd2Aggregation().ordinal());
    }

    public void createAttribute(ModelNTT modelNTT, String className, AttributeNTT attributeNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createAttribute(className, attributeNTT.getName(), attributeNTT.getType());
    }

    public void createOperation(ModelNTT modelNTT, String className, OperationNTT operationNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createOperation(className, operationNTT.getOperationName(), operationNTT.getParameter(), operationNTT.getReturnType());
    }

    public void createPrePostCondition(PrePostConditionNTT ppc, String className, ModelNTT modelNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createPrePostCondition(className, ppc.getOperationName(), ppc.getName(), ppc.getCondition(), ppc.isPre());
    }

}

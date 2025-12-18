package org.tzi.use;

import org.springframework.stereotype.Component;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.entities.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class UseModelFacade {

    private final Map<String, UseModelApi> umaCache = new HashMap<>();

    public void createModel(String modelName) {
        new UseModelApi().createModel(modelName);
    }

    public void createClass(ModelNTT modelNTT, ClassNTT classNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createClass(classNTT.getName(), false);

                for (AttributeNTT attribute : classNTT.getAttributes()) {
                    uma.createAttribute(classNTT.getName(), attribute.getName(), attribute.getType());
                }
                for (OperationNTT operation : classNTT.getOperations()) {
                    uma.createOperation(classNTT.getName(), operation.getOperationName(), operation.getParameter(), operation.getReturnType());
                }
    }

    public void createInvariant(ModelNTT modelNTT, InvariantNTT invariant, String className) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createInvariant(invariant.getInvName(), className, invariant.getInvBody(), invariant.isExistential());
    }

    public void createAssociation(ModelNTT modelNTT, AssociationNTT association) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createAssociation(
                association.getAssociationName(),
                association.getEnd1ClassName(),
                association.getEnd1RoleName(),
                association.getEnd1Multiplicity(),
                association.getEnd1Aggregation().ordinal(),
                association.getEnd2ClassName(),
                association.getEnd2RoleName(),
                association.getEnd2Multiplicity(),
                association.getEnd2Aggregation().ordinal()
        );
    }

    public void createAttribute(ModelNTT modelNTT, String className, AttributeNTT attributeNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createAttribute(className, attributeNTT.getName(), attributeNTT.getType());
    }

    public void createOperation(ModelNTT modelNTT, String className, OperationNTT operationNTT) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createOperation(className, operationNTT.getOperationName(), operationNTT.getParameter(), operationNTT.getReturnType());
    }

    public void createPrePostCondition(ModelNTT modelNTT, PrePostConditionNTT ppc, String className) throws UseApiException {
        UseModelApi uma = getUMAfromModelNTT(modelNTT);
        uma.createPrePostCondition(className, ppc.getOperationName(), ppc.getName(), ppc.getCondition(), ppc.isPre());
    }


    /*  Helper Methods  */

    private static String extractClassName(String concatenatedClassName) {
        String[] parts = concatenatedClassName.split("::");
        return parts[0];
    }

    private UseModelApi getUMAfromModelNTT(ModelNTT modelNTT) throws UseApiException {
        UseModelApi cached = umaCache.get(modelNTT.getName());
        if (cached != null) {
            return cached;
        }

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
        for (Map.Entry<String, PrePostConditionNTT> ppcEntry : modelNTT.getPrePostConditions().entrySet()) {
            PrePostConditionNTT ppc = ppcEntry.getValue();
            result.createPrePostCondition(
                    extractClassName(ppcEntry.getKey()),
                    ppc.getOperationName(),
                    ppc.getName(),
                    ppc.getCondition(),
                    ppc.isPre()
            );
        }

        for (Map.Entry<String, AssociationNTT> assocEntry : modelNTT.getAssociations().entrySet()) {
            AssociationNTT association = assocEntry.getValue();
            result.createAssociation(
                    association.getAssociationName(),
                    association.getEnd1ClassName(),
                    association.getEnd1RoleName(),
                    association.getEnd1Multiplicity(),
                    association.getEnd1Aggregation().ordinal(),
                    association.getEnd2ClassName(),
                    association.getEnd2RoleName(),
                    association.getEnd2Multiplicity(),
                    association.getEnd2Aggregation().ordinal()
            );
        }

        for (Map.Entry<String, InvariantNTT> invariantEntry : modelNTT.getInvariants().entrySet()) {
            InvariantNTT invariant = invariantEntry.getValue();
            result.createInvariant(
                    invariant.getInvName(),
                    extractClassName(invariantEntry.getKey()),
                    invariant.getInvBody(),
                    invariant.isExistential()
            );
        }

        umaCache.put(modelNTT.getName(), result);
        return result;
    }

}

package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.DTO.*;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;

public class MClassFacade {

    public static ClassDTO createMClass(ClassDTO aClass) throws UseApiException, MInvalidModelException {
        UseModelApi useModelApi = new UseModelApi();
        MClass mClass;
        mClass = useModelApi.createClass(aClass.getName_mclass(), false);
        for (Attribute attribute : aClass.getAttributes()) {
            useModelApi.createAttribute(mClass.name(), attribute.getName_attr(), attribute.getType());

        }
        //parameter liste wird noch nicht unterstützt. -> daher new String [][]
        for (Operation operation : aClass.getOperations()) {
            useModelApi.createOperation(mClass.name(), operation.getName(), new String[0][0], operation.getBody());
        }

        for(PrePostConditionDTO prePostConditionDTO : aClass.getPrePostConditions()) {
            useModelApi.createPrePostCondition(mClass.name(), prePostConditionDTO.getOperationName(), prePostConditionDTO.getName(), prePostConditionDTO.getCondition(), prePostConditionDTO.isPre());
        }

        for (Invariant invariant: aClass.getInvariants()) {
            useModelApi.createInvariant(mClass.name(), invariant.getInvName(), invariant.getInvBody(),invariant.isExistential());
        }

        return aClass;
    }
}

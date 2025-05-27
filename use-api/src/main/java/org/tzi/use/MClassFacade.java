package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.model.*;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;

public class MClassFacade {

    public static UseClass createMClass(UseClass aClass) throws UseApiException, MInvalidModelException {
        UseModelApi useModelApi = new UseModelApi();
        MClass mClass;
        mClass = useModelApi.createClass(aClass.getName_mclass(), false);
        for (Attribute attribute : aClass.getAttributes()) {
            useModelApi.createAttribute(mClass.name(), attribute.getName_attr(), attribute.getType());

        }
        for (Operation operation : aClass.getOperations()) {
            useModelApi.createOperation(mClass.name(), operation.getHead(), new String[0][0], operation.getBody());
        }

        for(PrePostCondition prePostCondition : aClass.getPrePostConditions()) {
            useModelApi.createPrePostCondition(mClass.name(), prePostCondition.getOperationName(), prePostCondition.getName(), prePostCondition.getCondition(), prePostCondition.isPre());
        }

        for (Invariant invariant: aClass.getInvariants()) {
            useModelApi.createInvariant(mClass.name(), invariant.getInvName(), invariant.getInvBody(),invariant.isExistential());
        }

        return aClass;
    }
}

package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.model.Attribute;
import org.tzi.use.model.Operation;
import org.tzi.use.model.UseClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;

public class MClassFacade {

    public static UseClass createMClass(UseClass aClass) throws UseApiException, MInvalidModelException {
        UseModelApi useModelApi = new UseModelApi();
        MClass mClass;
        mClass = useModelApi.createClass(aClass.getName_mclass(), false);
        //for each attr add
        for (Attribute attribute : aClass.getAttributes()) {
            MAttribute tmp_mAttribute = useModelApi.createAttribute(mClass.name(), attribute.getName_attr(), attribute.getType());

        }
        //for each operation add
        for (Operation operation : aClass.getOperations()) {
            MOperation tmp_mOperation = useModelApi.createOperation(mClass.name(), operation.getHead(), new String[0][0], operation.getBody());
        }
        //TODO return the MClass for later to be saved inside the DB
        return aClass;
    }
}

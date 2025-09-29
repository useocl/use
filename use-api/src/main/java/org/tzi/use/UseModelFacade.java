package org.tzi.use;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.DTO.*;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;

public class UseModelFacade {
    private static final UseModelApi useModelApi = new UseModelApi();

    public static UseModelApi createModel(String modelName) {
        return new UseModelApi(modelName);
    }

    @Deprecated
    public static ClassDTO createMClass(ClassDTO aClass) throws UseApiException, MInvalidModelException {
//        MClass mClass;
//        mClass = useModelApi.createClass(aClass.getName_mclass(), false);
//        for (AttributeDTO attributeDTO : aClass.getAttributes()) {
//            useModelApi.createAttribute(mClass.name(), attributeDTO.getName_attr(), attributeDTO.getType());
//
//        }
//        //parameter liste wird noch nicht unterstützt. -> daher new String [][]
//        for (OperationDTO operationDTO : aClass.getOperations()) {
//            useModelApi.createOperation(mClass.name(), operationDTO.getName(), new String[0][0], operationDTO.getBody());
//        }
//
//        for(PrePostConditionDTO prePostConditionDTO : aClass.getPrePostConditions()) {
//            useModelApi.createPrePostCondition(mClass.name(), prePostConditionDTO.getOperationName(), prePostConditionDTO.getName(), prePostConditionDTO.getCondition(), prePostConditionDTO.isPre());
//        }
//
//        for (InvariantDTO invariantDTO : aClass.getInvariants()) {
//            useModelApi.createInvariant(mClass.name(), invariantDTO.getInvName(), invariantDTO.getInvBody(), invariantDTO.isExistential());
//        }
//
        return null;
    }
}

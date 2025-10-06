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
}

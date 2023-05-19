package org.tzi.use.uml.mm;

import java.util.Map;
import java.util.TreeMap;

public class MMultiModel {
    private Map<String, MModel> fModels;
    private String fName;
    private String fFilename; // name of .use file

    public MMultiModel(String name) {
        fName = name;
        fModels = new TreeMap<>();
        fFilename = "";
    }

    public void addModel(MModel model) throws Exception { //TODO: add custom exception
        if (fModels.containsKey(model.name()))
            throw new Exception("MLModel already contains a model `"
                    + model.name() + "'.");
        // TODO: find out if we need to check more exceptions
        fModels.put(model.name(), model);
        // TODO: add to GenGraph
        //model.setMLModel(this); TODO: add setMLModel method
    }

    public void setFilename(String name) {
        fFilename = name;
    }

}

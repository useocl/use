package org.tzi.use.parser;

import org.tzi.use.uml.mm.MModel;

import java.util.HashSet;
import java.util.Set;

/**
 * The ImportContext class is responsible for caching imported models and preventing
 * cyclic dependencies during model compilation. It keeps track of the models that are
 * already imported and those that are currently being compiled.
 */
public class ImportContext {
    private final Set<MModel> fImportedModels;

    // Tracks models in compilation to prevent cycles in imported models
    private final Set<String> modelsBeingCompiled;

    public ImportContext(String modelName) {
        fImportedModels = new HashSet<>();
        modelsBeingCompiled = new HashSet<>();
        modelsBeingCompiled.add(modelName);
    }

    public void addImportedModel(MModel model) {
        fImportedModels.add(model);
    }

    public Set<MModel> importedModels() {
        return fImportedModels;
    }

    public void addModelBeingCompiled(String name) {
        modelsBeingCompiled.add(name);
    }

    public void removeModelBeingCompiled(String name) {
        modelsBeingCompiled.remove(name);
    }

    public boolean isModelBeingCompiled(String string) {
        return modelsBeingCompiled.contains(string);
    }
}

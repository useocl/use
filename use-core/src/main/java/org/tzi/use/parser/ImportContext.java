/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.tzi.use.parser;

import org.tzi.use.uml.mm.MModel;

import java.util.HashSet;
import java.util.Set;

/**
 * The ImportContext class is responsible for caching imported models and preventing
 * cyclic dependencies during model compilation. It keeps track of the models that are
 * already imported and those that are currently being compiled.
 * @author Matthias Marschalk
 */
public class ImportContext {
    private final Set<MModel> importedModels;

    // Tracks models in compilation to prevent cycles in imported models
    private final Set<String> modelsBeingCompiled;

    public ImportContext(String modelName) {
        importedModels = new HashSet<>();
        modelsBeingCompiled = new HashSet<>();
        modelsBeingCompiled.add(modelName);
    }

    public void addImportedModel(MModel model) {
        importedModels.add(model);
    }

    public Set<MModel> importedModels() {
        return importedModels;
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

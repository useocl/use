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
package org.tzi.use.parser.use;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ImportContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.api.IEnumType;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MImportedModel;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Node of the abstract syntax tree constructed by the parser.
 * Represents an import statement used for the resolution and inclusion
 * of external model files and their specified elements.
 * @author Matthias Marschalk
 */
public class ASTImportStatement extends AST {
    private final List<String> elementIdentifiers;
    private final String artifact;
    private final boolean isWildcard;

    public ASTImportStatement(List<String> elementIdentifiers, String artifact, boolean isWildcard) {
        this.elementIdentifiers = elementIdentifiers;
        this.artifact = artifact;
        this.isWildcard = isWildcard;
    }

    public List<String> getElementIdentifiers() {
        return elementIdentifiers;
    }

    public String getArtifact() {
        return artifact;
    }

    /**
     * Imports the model from the specified artifact, and adds imported elements to the context
     * of the parent model. Uses the import context to detect and prohibit cycles, as well as cache
     * already compiled models.
     *
     * @param importContext tracks imported models and cyclic imports.
     * @param ctx           context of the current parent model
     * @param currentModel  being compiled and to which the import is added.
     * @throws SemanticException      If a semantic error occurs, such as unresolved symbols or cyclic dependencies.
     * @throws MInvalidModelException If the imported model is invalid or cannot be compiled.
     * @throws IOException            If there are issues accessing the specified file representing the artifact.
     */
    public void resolveAndImport(ImportContext importContext, Context ctx, MModel currentModel) throws SemanticException, MInvalidModelException, IOException {
        Path resolvedIdentifier = resolveURI(artifact, ctx.getfFileUri());
        try (InputStream iStream = Files.newInputStream(resolvedIdentifier)) {
            // Check import context for already compiled models to avoid redundant compilation.
            MModel model = importContext.importedModels().stream().filter(mModel -> mModel.filename().equals(resolvedIdentifier.toString())).findFirst().orElse(null);
            if (model == null) {
                // Cycle detection
                if (importContext.isModelBeingCompiled(resolvedIdentifier.toUri().toString())) {
                    throw new SemanticException("Cycle detected in model imports for model " + currentModel.name() + ".");
                } else {
                    importContext.addModelBeingCompiled(resolvedIdentifier.toUri().toString());
                }

                model = USECompiler.compileImportedSpecification(iStream, resolvedIdentifier.toString(),
                        resolvedIdentifier.toUri(), ctx, importContext);
                // model may be null if compilation failed (e.g. a cycle is detected)
                if (model == null) {
                    return;
                }
                importContext.addImportedModel(model);

                // Model compilation successful, remove from cycle detection set
                importContext.removeModelBeingCompiled(resolvedIdentifier.toString());
            }

            // Filter elementIdentifiers: report qualifier mismatch but exclude
            // any symbols that do not belong to the actual imported model.
            Set<String> filteredSymbols = new HashSet<>();
            if (isWildcard) {
                // wildcard: keep as-is
                filteredSymbols.addAll(elementIdentifiers);
            } else {
                for (String symbol : elementIdentifiers) {
                    try {
                        String elementName = getElementName(symbol, model.name());
                        MClassifier element = findElementInModel(model, elementName);
                        if (element != null) {
                            // mark qualified access on the classifier if the
                            // original symbol used a qualifier
                            if (symbol.contains("#")) {
                                element.setQualifiedAccess(true);
                                filteredSymbols.add(symbol);
                            } else {
                                filteredSymbols.add(elementName);
                            }
                        } else {
                            // element simply not present in imported model;
                            // For unqualified imports we must report the
                            // non-positioned error expected by tests (e.g.
                            // "Could not find element for imported symbol: B in model t31_import.").
                            // For qualified imports, skip reporting here and
                            // let the later type resolver produce the
                            // position-aware message.
                            if (!symbol.contains("#")) {
                                ctx.reportError(new SemanticException("Could not find element for imported symbol: " + symbol + " in model " + model.name() + "."));
                            }
                        }
                    } catch (SemanticException ex) {
                        // qualifier mismatch: report and skip the symbol
                        ctx.reportError(ex);
                    }
                }
            }
            MImportedModel importedModel = new MImportedModel(model, isWildcard, filteredSymbols);
            currentModel.addImportedModel(importedModel);

            // Add imported types to parent context using the filtered symbol set
            for (MClassifier cls : importedModel.getClassifiers()) {
                // Convert MM classifiers/data types to runtime OCL Types using the TypeFactory
                Type oclType;
                if (cls instanceof org.tzi.use.uml.mm.MDataType) {
                    oclType = TypeFactory.mkDataType((org.tzi.use.uml.mm.MDataType) cls);
                } else {
                    // classes and associations: classifier types
                    oclType = TypeFactory.mkClassifierType(cls);
                }
                // Determine symbol key: if filteredSymbols contains qualified or plain name, prefer qualified
                String qualKey = model.name() + "#" + cls.name();
                String key = cls.name();
                if (filteredSymbols.contains(qualKey)) {
                    key = qualKey;
                } else if (filteredSymbols.contains(cls.name())) {
                    key = cls.name();
                }
                ctx.typeTable().add(key, oclType, null);
            }

            for (IEnumType enumType : importedModel.getEnumTypes()) {
                Type oclEnum = TypeFactory.mkEnum(enumType.name(), enumType.getLiterals());
                String qualKey = model.name() + "#" + enumType.name();
                String key = enumType.name();
                if (filteredSymbols.contains(qualKey)) {
                    key = qualKey;
                } else if (filteredSymbols.contains(enumType.name())) {
                    key = enumType.name();
                }
                ctx.typeTable().add(key, oclEnum, null);
            }

            for (MSignal signal : importedModel.getSignals()) {
                Type msgType = TypeFactory.mkMessageType(signal);
                String qualKey = model.name() + "#" + signal.name();
                String key = signal.name();
                if (filteredSymbols.contains(qualKey)) {
                    key = qualKey;
                } else if (filteredSymbols.contains(signal.name())) {
                    key = signal.name();
                }
                ctx.typeTable().add(key, msgType, null);
            }

        } catch (IOException e) {
            throw new IOException("Could not import model at specified file path: " + artifact + ".");
        }
    }

    private Path resolveURI(String artifactPath, URI currentModelUri) throws IOException {
        if (!currentModelUri.getScheme().equals("file")) {
            throw new IOException("URI scheme is invalid. Currently only file URIs are supported for importing models.");
        }
        if (!artifactPath.endsWith(".use")) {
            artifactPath += ".use";
        }

        Path artifactPathResolved = Paths.get(artifactPath).normalize();

        if (artifactPathResolved.isAbsolute()) {
            return artifactPathResolved;
        }

        return Paths.get(currentModelUri).toAbsolutePath().getParent().resolve(artifactPathResolved).normalize();
    }

    private String getElementName(String token, String modelName) throws SemanticException {
        String[] parts = token.split("#");
        if (parts.length != 2) {
            return parts[0];
        } else {
            if (!parts[0].equals(modelName)) {
                throw new SemanticException("Model qualifier " + parts[0] + " does not match model name: " + modelName);
            }
            return parts[1];
        }
    }

    private String getElementNameForSymTable(String elementName) {
        for (String symbol : getElementIdentifiers()) {
            String[] parts = symbol.split("#");
            if (parts.length == 2) {
                if (elementName.equals(parts[1])) {
                    return symbol;
                }
            }
        }
        return elementName;
    }

    private MClassifier findElementInModel(MModel model, String elementName) {
        MClassifier element = model.getClassifier(elementName);
        if (element == null) {
            element = (MClassifier) model.enumType(elementName);
        }
        if (element == null) {
            element = model.getSignal(elementName);
        }
        if (element == null) {
            element = model.getAssociation(elementName);
        }
        return element;
    }
}

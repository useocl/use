package org.tzi.use.parser.use;

import org.antlr.runtime.Token;
import org.tzi.use.parser.*;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MImportedModel;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.EnumType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Node of the abstract syntax tree constructed by the parser.
 * Represents an import statement used for the resolution and inclusion
 * of external model files and their specified elements.
 */
public class ASTImportStatement extends AST {
    private final List<Token> fSymbols;
    private final String artifact;
    private final boolean isWildcard;

    public ASTImportStatement(List<Token> symbols, String artifact, boolean isWildcard) {
        this.fSymbols = symbols;
        this.artifact = artifact;
        this.isWildcard = isWildcard;
    }

    public List<Token> getfSymbols() {
        return fSymbols;
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
        Path resolvedIdentifier = resolvePath(artifact, currentModel.filename());
        try (InputStream iStream = Files.newInputStream(resolvedIdentifier)) {
            // Check import context for already compiled models to avoid redundant compilation.
            MModel model = importContext.importedModels().stream().filter(mModel -> mModel.filename().equals(resolvedIdentifier.toString())).findFirst().orElse(null);
            if (model == null) {
                // Cycle detection
                if (importContext.isModelBeingCompiled(resolvedIdentifier.toString())) {
                    throw new SemanticException("Cycle detected in model imports for model " + currentModel.name() + ".");
                } else {
                    importContext.addModelBeingCompiled(resolvedIdentifier.toString());
                }

                model = USECompiler.compileImportedSpecification(iStream, resolvedIdentifier.toString(), ctx, importContext);
                // model may be null if compilation failed (e.g. a cycle is detected)
                if (model == null) {
                    return;
                }
                importContext.addImportedModel(model);

                // Model compilation successful, remove from cycle detection set
                importContext.removeModelBeingCompiled(resolvedIdentifier.toString());
            }

            MImportedModel importedModel = new MImportedModel(model, isWildcard, fSymbols.stream().map(Token::getText).collect(Collectors.toSet()));

            // Check if every specified symbol is present in the model
            if (!isWildcard) {
                for (Token token : fSymbols) {
                    String elementName = token.getText();
                    MClassifier element = findElementInModel(model, elementName);
                    if (element == null) {
                        throw new SemanticException(new SrcPos(token), "Could not find element for imported symbol: " + token.getText() + " in model " + importedModel.name() + ".");
                    }
                }
            }

            currentModel.addImportedModel(importedModel);

            // Add imported types to parent context
            for (MClassifier cls : importedModel.getClassifiers()) {
                ctx.typeTable().add(cls.name(), cls, null);
            }

            for (EnumType enumType : importedModel.getEnumTypes()) {
                ctx.typeTable().add(enumType.name(), enumType, null);
            }

            for (MSignal signal : importedModel.getSignals()) {
                ctx.typeTable().add(signal.name(), signal, null);
            }

        } catch (IOException e) {
            throw new IOException("Could not import model at specified file path: " + artifact + ".");
        }
    }

    private Path resolvePath(String artifactPath, String currentModelPath) {
        if (!artifactPath.endsWith(".use")) {
            artifactPath += ".use";
        }

        Path artifactPathResolved = Paths.get(artifactPath).normalize();

        if (artifactPathResolved.isAbsolute()) {
            return artifactPathResolved;
        }

        return Paths.get(currentModelPath).toAbsolutePath().getParent().resolve(artifactPathResolved).normalize();
    }

    private MClassifier findElementInModel(MModel model, String elementName) {
        MClassifier element = model.getClassifier(elementName);
        if (element == null) {
            element = model.enumType(elementName);
        }
        if (element == null) {
            element = model.getSignal(elementName);
        }
        return element;
    }
}

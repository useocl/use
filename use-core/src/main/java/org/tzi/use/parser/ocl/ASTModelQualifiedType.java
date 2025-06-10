package org.tzi.use.parser.ocl;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.Type;

/**
 * Represents an type with a model qualifier in an OCL expression. This class resolves qualified type names
 * of the form {@code modelName#typeName} to enum or object types defined in the model or its imports, given that
 * the model name matches.
 */
public class ASTModelQualifiedType extends ASTSimpleType {
    private final Token fModelQualifier;

    public ASTModelQualifiedType(Token modelQualifier, Token name) {
        super(name);
        this.fModelQualifier = modelQualifier;
    }

    public Type gen(Context ctx) throws SemanticException {
        String name = fModelQualifier.getText() + "#" + getfName().getText();

        Type res = ctx.model().enumType(name);
        if (res != null) {
            return res;
        }

        res = ctx.model().getClassifier(name);
        if (res != null) {
            return res;
        }

        res = ctx.typeTable().lookup(name);
        if (res != null) {
            return res;
        }
        throw new SemanticException(getfName(), "Could not find model qualified type '" + getfName().getText() + "'.");
    }
}

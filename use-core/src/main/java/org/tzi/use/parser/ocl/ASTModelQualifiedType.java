package org.tzi.use.parser.ocl;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeAdapters;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.api.IEnumType;

/**
 * Represents an type with a model qualifier in an OCL expression. This class resolves qualified type names
 * of the form {@code modelName#typeName} to enum or object types defined in the model or its imports, given that
 * the model name matches.
 */
public class ASTModelQualifiedType extends ASTSimpleType {
    private final Token modelQualifier;

    public ASTModelQualifiedType(Token modelQualifier, Token name) {
        super(name);
        this.modelQualifier = modelQualifier;
    }

    public Type gen(Context ctx) throws SemanticException {
        String name = modelQualifier.getText() + "#" + getName().getText();

        IEnumType enumType = ctx.model().enumType(name);
         if (enumType != null) {
             return TypeFactory.mkEnum(enumType.name(), enumType.getLiterals());
         }

         org.tzi.use.uml.mm.MClassifier mmClf = ctx.model().getClassifier(name);
        if (mmClf != null) {
             return TypeAdapters.asOclType(mmClf);
         }

        Type res = ctx.typeTable().lookup(name);
         if (res != null) {
             return res;
         }
         throw new SemanticException(getName(), "Could not find model qualified type '" + getName().getText() + "'.");
     }
}

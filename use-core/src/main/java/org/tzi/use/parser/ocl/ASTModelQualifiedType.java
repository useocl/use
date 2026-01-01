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
        System.err.println("[TRES] ASTModelQualifiedType.gen called: token='" + getName().getText() + "', name='" + name + "', ctx.model=" + (ctx.model()==null?"<null>":ctx.model().name()));

        IEnumType enumType = ctx.model().enumType(name);
        System.err.println("[TRES] lookup enumType('" + name + "') -> " + (enumType != null));
         if (enumType != null) {
             return TypeFactory.mkEnum(enumType.name(), enumType.getLiterals());
         }

         org.tzi.use.uml.mm.MClassifier mmClf = ctx.model().getClassifier(name);
        System.err.println("[TRES] lookup classifier('" + name + "') -> " + (mmClf != null));
        if (mmClf != null) {
            try {
                String clfName = mmClf.name();
                String qn = mmClf.qualifiedName();
                String mdl = mmClf.model() == null ? "<null>" : mmClf.model().name();
                System.err.println("[TRES] found classifier details: name='" + clfName + "', qualifiedName='" + qn + "', model='" + mdl + "'");
            } catch (Exception e) {
                System.err.println("[TRES] error inspecting mmClf: " + e.getMessage());
            }
             return TypeAdapters.asOclType(mmClf);
         }

        Type res = ctx.typeTable().lookup(name);
        System.err.println("[TRES] lookup typeTable('" + name + "') -> " + (res != null));
         if (res != null) {
             return res;
         }
         throw new SemanticException(getName(), "Could not find model qualified type '" + getName().getText() + "'.");
     }
}

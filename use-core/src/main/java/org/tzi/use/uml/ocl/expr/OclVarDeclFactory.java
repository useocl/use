package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.api.IVarDecl;
import org.tzi.use.uml.api.IVarDeclList;
import org.tzi.use.uml.api.IVarDeclFactory;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.ocl.type.TypeAdapters;

/** OCL implementation of the IVarDeclFactory API. */
public class OclVarDeclFactory implements IVarDeclFactory {

    @Override
    public IVarDecl mkVarDecl(String name, IType type) {
        // Adapt API-level IType to low-level OCL Type reliably
        Type oclType = TypeAdapters.asOclType(type);
        return new VarDecl(name, oclType);
    }

    @Override
    public IVarDeclList mkVarDeclList(boolean allHaveSameType) {
        return new VarDeclList(allHaveSameType);
    }
}

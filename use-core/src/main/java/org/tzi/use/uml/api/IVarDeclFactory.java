package org.tzi.use.uml.api;

/**
 * Factory interface for creating IVarDecl and IVarDeclList instances.
 * Implementations live in the ocl or sys layer. mm uses only this API.
 */
public interface IVarDeclFactory {
    /** Create a new IVarDecl with given name and type. */
    IVarDecl mkVarDecl(String name, IType type);

    /** Create an empty IVarDeclList. */
    IVarDeclList mkVarDeclList(boolean allHaveSameType);
}


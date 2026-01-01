package org.tzi.use.uml.api;

import java.util.Iterator;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.SemanticException;

/**
 * API interface for a list of variable declarations used by mm.
 * Implemented by ocl.VarDeclList.
 */
public interface IVarDeclList extends Iterable<IVarDecl> {
    int size();
    boolean isEmpty();
    IVarDecl varDecl(int n);
    void add(IVarDecl decl);
    boolean containsName(String varName);
    void toString(java.lang.StringBuilder sb);
    void addVariablesToSymtable(Symtable vars) throws SemanticException;
}


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

// $Id$

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;


/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTPrePost extends AST {
    private Token fClassName;
    private Token fOpName;
    private List<ASTVariableDeclaration> fParamList;
    private ASTType fResultType; // optional
    private List<ASTPrePostClause> fPrePostClauses;

    public ASTPrePost(Token classname, Token opname, 
                      List<ASTVariableDeclaration> paramList, ASTType resultType) {
        fClassName = classname;
        fOpName = opname;
        fParamList = paramList;
        fResultType = resultType;
        fPrePostClauses = new ArrayList<ASTPrePostClause>();
    }

    public void addPrePostClause(ASTPrePostClause ppc) {
        fPrePostClauses.add(ppc);
    }

    void gen(Context ctx) throws SemanticException {
        // find class
        MClass cls = ctx.model().getClass(fClassName.getText());
        if (cls == null )
            throw new SemanticException(fClassName, 
                                        "Undefined class `" + fClassName.getText() + 
                                        "'.");
        // find operation in class
        MOperation op = cls.operation(fOpName.getText(), false);
        if (op == null )
            throw new SemanticException(fOpName, 
                                        "Class `" + fClassName.getText() + 
                                        "' has no operation `" + fOpName.getText() + 
                                        "'.");


        // map params to VarDeclList
        VarDeclList varDeclList = new VarDeclList(false);
        
        for (ASTVariableDeclaration astDecl : fParamList) {
            VarDecl decl = astDecl.gen(ctx);
            try {
                varDeclList.add(decl);
            } catch (IllegalArgumentException ex) {
                throw new SemanticException(astDecl.name(), "Redefinition of `" +
                                            decl.name() + "'.");
            }
        }

        // check for identical signature
        if (! op.paramList().equals(varDeclList) )
            throw new SemanticException(fOpName, "This signature of operation `" + 
                                        fOpName.getText() +
                                        "' does not match its previous declaration in class `" +
                                        fClassName.getText() + "'.");

        Type resultType = null;
        if (fResultType != null ) {
            resultType = fResultType.gen(ctx);
            if (! op.hasResultType() )
                throw new SemanticException(fResultType.getStartToken(), 
                                            "Operation `" + 
                                            fOpName.getText() +
                                            "' has no result type in its previous declaration in class `" +
                                            fClassName.getText() + "'.");

            if (! resultType.equals(op.resultType()) )
                throw new SemanticException(fResultType.getStartToken(), 
                                            "Expected result type `" + op.resultType() +
                                            "', found `" + resultType + "'.");
        } else {
            if (op.hasResultType() )
                throw new SemanticException(fOpName, 
                                            "Expected result type `" + op.resultType() + "'.");
        }

        // enter parameters into scope of expression
        Symtable vars = ctx.varTable();
        vars.enterScope();

        for (ASTVariableDeclaration astDecl : fParamList) {
            VarDecl decl = astDecl.gen(ctx);
            vars.add(astDecl.name(), decl.type());
        }

        for (ASTPrePostClause ppc : fPrePostClauses) {
            ppc.gen(ctx, cls, op);
        }

        vars.exitScope(); 
    }
}

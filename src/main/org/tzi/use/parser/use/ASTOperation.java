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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.ExpUndefined;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;


/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTOperation extends AST {
    private MyToken fName;
    private List fParamList;    // (ASTVariableDeclaration)
    private ASTType fType;  // (optional)
    private ASTExpression fExpr; // (optional)
    private MOperation fOperation; // the operation is generated in two passes
    private List fPrePostClauses;
    
    // for UML AL
    private ASTALAction fExecutableBody;

    public ASTOperation(MyToken name, List paramList, 
                        ASTType t, ASTExpression expr, ASTALActionList list) {
        fName = name;
        fParamList = paramList;
        fType = t;
        fExpr = expr;
        fOperation = null;
        fPrePostClauses = new ArrayList();
        fExecutableBody = list;
    }

    public void addPrePostClause(ASTPrePostClause ppc) {
        fPrePostClauses.add(ppc);
    }

    public MOperation genSignature(Context ctx) 
        throws SemanticException 
    {
        // map params to VarDeclList
        VarDeclList varDeclList = new VarDeclList(false);
        Iterator it = fParamList.iterator();
        while (it.hasNext() ) {
            ASTVariableDeclaration astDecl = (ASTVariableDeclaration) it.next();
            VarDecl decl = astDecl.gen(ctx);
            try {
                varDeclList.add(decl);
            } catch (IllegalArgumentException ex) {
                throw new SemanticException(astDecl.name(), "Redefinition of `" +
                                            decl.name() + "'.");
            }
        }
        Type resultType = null;
        if (fType != null )
            resultType = fType.gen(ctx);
        fOperation = ctx.modelFactory().createOperation(fName.getText(), varDeclList,
                                                        resultType);
        // sets the line position of the USE-Model in this attribute
        fOperation.setPositionInModel( fName.getLine() );
        
        // HACK: set a temporary expression here, otherwise generating
        // the body expression will fail if the body contains a
        // recursive call to this operation, or a forward reference is
        // made to another operation
        if (fExpr != null ) {
            try {
                fOperation.setExpression(new ExpUndefined(fOperation.resultType()));
            } catch (MInvalidModelException ex) {
                throw new RuntimeException("setting temporary expression failed");
            }
        }
        return fOperation;
    }

    public void genFinal(Context ctx) 
        throws SemanticException 
    {
        // fOperation is null if genSignature exited with an Exception
        if (fOperation == null )
            return;

        // enter parameters into scope of expression
        Symtable vars = ctx.varTable();
        vars.enterScope();

        Iterator it = fParamList.iterator();
        while (it.hasNext() ) {
            ASTVariableDeclaration astDecl = (ASTVariableDeclaration) it.next();
            VarDecl decl = astDecl.gen(ctx);
            vars.add(astDecl.name(), decl.type());
        }

        // for UML AL
        if (fExecutableBody != null) {
            if (fOperation.resultType() != null) {
                vars.add("result", fOperation.resultType(),null);
            }
            boolean sideEffectFree = ctx.isSideEffectFree();
            ctx.setIsSideEffectFree(false);
            fOperation.setAction(fExecutableBody.gen(ctx));
            ctx.setIsSideEffectFree(sideEffectFree);
        }
        
        try {
            if (fExpr != null ) {
                Expression expr = fExpr.gen(ctx);
                fOperation.setExpression(expr);
            }

            it = fPrePostClauses.iterator();
            while (it.hasNext() ) {
                ASTPrePostClause ppc = (ASTPrePostClause) it.next();
                ppc.gen(ctx, ctx.currentClass(), fOperation);
            }
        } catch (MInvalidModelException ex) {
            throw new SemanticException(fName, ex);
        } finally {
            vars.exitScope(); 
        }
    }
}


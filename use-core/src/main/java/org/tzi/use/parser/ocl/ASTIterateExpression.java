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

package org.tzi.use.parser.ocl;

import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ExprContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpIterate;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.expr.VarInitializer;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTIterateExpression extends ASTExpression {
    private Token fIterateToken;
    private ASTExpression fRange; // may be null
    private ASTElemVarsDeclaration fDeclList;
    private ASTVariableInitialization fInit;
    private ASTExpression fExpr;

    public ASTIterateExpression(Token iterateToken,
                                ASTExpression range, 
                                ASTElemVarsDeclaration declList,
                                ASTVariableInitialization init,
                                ASTExpression expr) {
        fIterateToken = iterateToken;
        fRange = range;
        fDeclList = declList;
        fInit = init;
        fExpr = expr;
    }

    public Expression gen(Context ctx) throws SemanticException {
        Expression res, range, expr;

        // check for empty range: do we have a context expression that
        // is implicitly assumed to be the source expression?
        if (fRange != null )
            range = fRange.gen(ctx);
        else {
            ExprContext ec = ctx.exprContext();
            if (! ec.isEmpty() ) {
                // construct source expression
                ExprContext.Entry e = ec.peek();
                range = new ExpVariable(e.fName, e.fType);
            } else
                throw new SemanticException(fIterateToken, 
                                            "Need a collection to apply `iterate'.");
        }

        if (! range.type().isKindOfCollection(VoidHandling.INCLUDE_VOID) )
            throw new SemanticException(fIterateToken, 
                                        "Source of `iterate' expression must be a collection, " + 
                                        "found source expression of type `" + range.type() + "'.");

        VarDeclList declList = fDeclList.gen(ctx, range);
        VarInitializer init = fInit.gen(ctx);

        // enter declared variable into scope of argument expression
        Symtable vars = ctx.varTable();
        vars.enterScope();
        if (! declList.isEmpty() ) {
            // add element variables
        	declList.addVariablesToSymtable(vars);
        }
        vars.add(fInit.nameToken(), init.type());
        expr = fExpr.gen(ctx);
        vars.exitScope(); 
        try {
            res = new ExpIterate(declList, init, range, expr);
        } catch (ExpInvalidException ex) {
            throw new SemanticException(fIterateToken, ex);
        }
        return res;
    }

    @Override
	public void getFreeVariables(Set<String> freeVars) {
		if (fRange != null) {
			fRange.getFreeVariables(freeVars);
		}
	
		HashSet<String> freeVarsInExpr = new HashSet<String>();
		fExpr.getFreeVariables(freeVarsInExpr);
		Set<String> declaredVars = fDeclList.getVarNames();
		freeVarsInExpr.removeAll(declaredVars);
		freeVarsInExpr.remove(fInit.nameToken().getText());
		
		freeVars.addAll(freeVarsInExpr);	
	}

    @Override
	public String toString() {
	    return "(\"iterate\" " + fRange + " " + fDeclList + " " + fInit + " " + fExpr + ")";
	}
}

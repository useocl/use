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

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author  Mark Richters
 */
public class ASTInvariantClause extends ASTAnnotatable {
	Token fName;      // optional
    ASTExpression fExpr;

    public ASTInvariantClause(Token name, ASTExpression e) {
        fName = name;
        fExpr = e;
    }

    public String toString() {
        return fExpr.toString();
    }

    void gen(Context ctx, List<Token> varTokens, MClassifier cf) {
    	gen(ctx, varTokens, cf, true);
    }
    
    MClassInvariant gen(Context ctx, List<Token> varTokens, MClassifier cf, boolean addToModel) {
        // enter context variable into scope of invariant
        Symtable vars = ctx.varTable();
        vars.enterScope();

        List<String> varNames = new ArrayList<String>();
        MClassInvariant inv = null;
        
        try {
            if (varTokens != null && varTokens.size() > 0) {                
            	for (Token var : varTokens) {
            		vars.add(var, cf);
            		ctx.exprContext().push(var.getText(), cf);
            		varNames.add(var.getText());
            	}
            } else {
                // create pseudo-variable "self"
                vars.add("self", cf, null);
                ctx.exprContext().push("self", cf);
                varNames.add("self");
            }

            Expression expr = fExpr.gen(ctx);
            String invName = null;
            
			if (fName == null) {
				invName = ctx.model().createModelElementName("inv");
			} else {
				invName = fName.getText();
			}
            
            inv = onCreateMClassInvariant(ctx, cf, varNames, expr, invName);
            
            this.genAnnotations(inv);
            
            // sets the line position of the USE-Model in this  invariant
            inv.setPositionInModel( fExpr.getStartToken().getLine() );
            if(addToModel){
            	ctx.model().addClassInvariant(inv);
            }
        } catch (MInvalidModelException ex) {
            ctx.reportError(fExpr.getStartToken(), ex);
        } catch (ExpInvalidException ex) {
            ctx.reportError(fExpr.getStartToken(), ex);
        } catch (SemanticException ex) {
            ctx.reportError(ex);
        }
        vars.exitScope(); 
        ctx.exprContext().pop();
        return inv;
    }

	protected MClassInvariant onCreateMClassInvariant(Context ctx, MClassifier cf,
			List<String> varNames, Expression expr, String invName)
			throws ExpInvalidException {
		MClassInvariant inv = 
		    ctx.modelFactory().createClassInvariant(invName, varNames, cf, expr, false);
		return inv;
	}
}
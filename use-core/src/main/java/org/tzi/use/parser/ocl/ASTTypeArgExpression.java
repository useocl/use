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

import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.config.Options;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ExprContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.expr.ExpAsType;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpIsKindOf;
import org.tzi.use.uml.ocl.expr.ExpIsTypeOf;
import org.tzi.use.uml.ocl.expr.ExpSelectByKind;
import org.tzi.use.uml.ocl.expr.ExpSelectByType;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;

/**
 * Node of the abstract syntax tree constructed by the parser, 
 * which represents one of the following type expressions or
 * a shorthand collect of them:
 * <ul>
 *   <li>oclAsType</li>
 *   <li>oclIsKindOf</li>
 *   <li>oclIsTypeOf</li>
 * </ul>
 * 
 * @author  Mark Richters
 * @author  Lars Hamann
 */
public class ASTTypeArgExpression extends ASTExpression {
    private Token fOpToken;
    private ASTExpression fSourceExpr; // may be null
    private ASTType fTargetType;
    private boolean fFollowsArrow;

    public ASTTypeArgExpression(Token opToken, 
                                ASTExpression source, 
                                ASTType targetType,
                                boolean followsArrow) {
        fOpToken = opToken;
        fSourceExpr = source;
        fTargetType = targetType;
        fFollowsArrow = followsArrow;
    }

    public Expression gen(Context ctx) throws SemanticException {
        Expression res = null;
        Expression expr;
        Type t = fTargetType.gen(ctx);

        // check for empty source: do we have a context expression that
        // is implicitly assumed to be the source expression?
        if (fSourceExpr != null )
            expr = fSourceExpr.gen(ctx);
        else {
            ExprContext ec = ctx.exprContext();
            if (! ec.isEmpty() ) {
                // construct source expression
                ExprContext.Entry e = ec.peek();
                expr = new ExpVariable(e.fName, e.fType);
            } else
                throw new SemanticException(fOpToken, "Need an object to apply `" +
                                            fOpToken.getText() + "'.");
        }
    
        if (!expr.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID) && fFollowsArrow) {
        	ctx.reportWarning(fOpToken, "application of `" + fOpToken.getText() + 
                    "' to a single value should be done with `.' " +
                    "instead of `->'.");
        }
        // If we find a source expression of type Collection(T), 
        // this might be a shorthand for the collect operation, 
        // e.g. `c.oclIsKindOf(Employee)'
        // is a valid shorthand for `c->collect(e |
        // e.oclIsKindOf(Employee))'
        if (expr.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID) && !fFollowsArrow) {
            if (Options.disableCollectShorthand )
                throw new SemanticException(fOpToken, MSG_DISABLE_COLLECT_SHORTHAND);
        
            CollectionType cType = (CollectionType)expr.type();
            Type elemType = cType.elemType();
                    
            // transform c.oclIsKindOf(t) into c->collect($e | $e.oclIsKindOf(t))
            ExpVariable eVar = new ExpVariable("$e", elemType);
            Expression collectBody = genExpr(eVar, t);
            res = genImplicitCollect(expr, collectBody, elemType);
        } else {
            res = genExpr(expr, t);
            // Because of multiple inheritance only oclIsTypeOf results always in false when no relation is given  
            if ((res instanceof ExpIsTypeOf) && ! expr.type().conformsTo(t) && ! t.conformsTo(expr.type()) )
                ctx.reportWarning(fTargetType.getStartToken(), 
                                  "Expression is always false since the expression's type `" +
                                  expr.type() + 
                                  "' is neither a subtype nor a supertype of the target type `" +
                                  t + "'.");
        }

        return res;
    }

    private Expression genExpr(Expression expr, Type t) throws SemanticException {
    	try {
        	String typeOperation = fOpToken.getText();
        	
        	switch(typeOperation) {
        	case "oclAsType":
                return new ExpAsType(expr, t);
        	case "oclIsKindOf":
                return new ExpIsKindOf(expr, t);
        	case "oclIsTypeOf":
                return new ExpIsTypeOf(expr, t);
        	case "selectByKind":
        		return new ExpSelectByKind(expr, t);
        	case "selectByType":
        		return new ExpSelectByType(expr, t);
            default:
                throw new SemanticException(fOpToken, "Unexpected type operation: " + fOpToken.getText());
        	}
        	
        } catch (ExpInvalidException ex) {
            throw new SemanticException(fTargetType.getStartToken(), ex.getMessage());
        }
    }

	@Override
	public void getFreeVariables(Set<String> freeVars) {
		if (fSourceExpr != null) {
			fSourceExpr.getFreeVariables(freeVars);
		}
	}
}

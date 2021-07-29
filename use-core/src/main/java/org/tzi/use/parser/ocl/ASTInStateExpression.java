/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.parser.ocl;

import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.config.Options;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ExprContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.ocl.expr.ExpOclInState;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.util.StringUtil;

/**
 * @author Lars Hamann
 *
 */
public class ASTInStateExpression extends ASTExpression {
	private Token fOpToken;
    private ASTExpression fSourceExpr; // may be null
    private Token fStateIdentifier;
    private boolean fFollowsArrow;

    public ASTInStateExpression(Token opToken, 
                                ASTExpression source, 
                                Token stateIdentifier,
                                boolean followsArrow) {
        fOpToken = opToken;
        fSourceExpr = source;
        fStateIdentifier = stateIdentifier;
        fFollowsArrow = followsArrow;
    }

    public Expression gen(Context ctx) throws SemanticException {
        Expression res = null;
        Expression expr;
        
        // check for empty source: do we have a context expression that
        // is implicitly assumed to be the source expression?
        if (fSourceExpr != null ) {
            expr = fSourceExpr.gen(ctx);
        } else {
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
        
            CollectionType cType = (CollectionType ) expr.type();
            Type elemType = cType.elemType();
            
            // transform c.oclIsKindOf(t) into c->collect($e | $e.oclIsKindOf(t))
            ExpVariable eVar = new ExpVariable("$e", elemType);
            Expression collectBody = genExpr(eVar);
            res = genImplicitCollect(expr, collectBody, elemType);
        } else {
            res = genExpr(expr);
        }

        return res;
    }

    private Expression genExpr(Expression sourceExpr) throws SemanticException 
    {
    	if (!sourceExpr.type().isTypeOfClass()) {
    		throw new SemanticException(fOpToken, "Need an object to apply `oclInState(" + this.fStateIdentifier.getText() + ")'.");
    	}
    	
    	MClass srcClass = (MClass)sourceExpr.type();
    	String stateName = fStateIdentifier.getText();
        
    	Set<MProtocolStateMachine> psms = srcClass.getAllOwnedProtocolStateMachines(); 
    	if (psms.isEmpty()) {
            throw new SemanticException(fOpToken, "Invalid use of oclInState, because the class " + StringUtil.inQuotes(srcClass) + " has no defined state machines.");
        }
    
    	MState state = null;
    	
    	for (MProtocolStateMachine sm : psms) {
    		state = sm.getState(stateName);
    		if (state != null) {
    			break;
    		}
    	}
    	
    	if (state == null) {
			throw new SemanticException(
					fOpToken,
					"Invalid use of oclInState, because the class "
							+ StringUtil.inQuotes(srcClass)
							+ " has no state machine containing a state with the given name "
							+ StringUtil.inQuotes(stateName) + ".");
    	}
    	
    	return new ExpOclInState(sourceExpr, state);
    }

	@Override
	public void getFreeVariables(Set<String> freeVars) {
		if (fSourceExpr != null) {
			fSourceExpr.getFreeVariables(freeVars);
		}
	}
}

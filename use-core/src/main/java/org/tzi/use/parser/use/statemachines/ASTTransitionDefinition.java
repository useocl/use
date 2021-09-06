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
package org.tzi.use.parser.use.statemachines;

import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.commonbehavior.communications.MTrigger;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MProtocolTransition;
import org.tzi.use.uml.mm.statemachines.MPseudoState;
import org.tzi.use.uml.mm.statemachines.MPseudoStateKind;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.mm.statemachines.MVertex;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.util.StringUtil;

/**
 * @author Lars Hamann
 *
 */
public class ASTTransitionDefinition extends AST {

	protected Token source;
	
	protected Token target;
	
	protected ASTExpression preCondition;
	
	protected ASTExpression postCondition;
	
	protected Token event;
	
	protected Token operationName;
	
	protected List<ASTVariableDeclaration> operationArgs;
	
	/**
	 * @param source
	 * @param target
	 */
	public ASTTransitionDefinition(Token source, Token target) {
		this.source = source;
		this.target = target;
	}

	/**
	 * @param astExpression
	 */
	public void setPreCondition(ASTExpression astExpression) {
		preCondition = astExpression;		
	}

	/**
	 * @param e
	 */
	public void setEvent(Token e) {
		event = e;
	}

	/**
	 * @param o
	 */
	public void setOperation(Token o) {
		operationName = o;
	}

	/**
	 * @param astExpression
	 */
	public void setPostCondition(ASTExpression astExpression) {
		postCondition = astExpression;
	}

	/**
	 * @param args
	 */
	public void setOperationArgs(List<ASTVariableDeclaration> args) {
		operationArgs = args;
	}

	/**
	 * @param ctx
	 * @param sm
	 * @return
	 */
	public MTransition gen(Context ctx, MStateMachine sm) throws SemanticException {
		
		boolean hasErrors = false;
		MTransition result;
		
		MVertex sourceVertex = sm.getDefaultRegion().getSubvertex(source.getText());
		
		if (sourceVertex == null) {
			ctx.reportError(source, "Unknown source vertex " + StringUtil.inQuotes(source.getText()));
			hasErrors = true;
		}
		
		MVertex targetVertex = sm.getDefaultRegion().getSubvertex(target.getText());
		if (targetVertex == null) {
			ctx.reportError(source, "Unknown target vertex " + StringUtil.inQuotes(target.getText()));
			hasErrors = true;
		}
		
		if (hasErrors) return null;
		
		if (sm instanceof MProtocolStateMachine) {
			result = new MProtocolTransition(sm.getDefaultRegion(), sourceVertex, targetVertex);
		} else {
			result = new MTransition(sm.getDefaultRegion(), sourceVertex, targetVertex);
		}

		if (event != null) {
			/*
			 * UML Spec: [6] An initial transition at the topmost level (region
			 * of a statemachine) either has no trigger or it has a trigger with
			 * the stereotype "create".
			 * self.source.oclIsKindOf(Pseudostate)
			 *   implies (self.source.oclAsType(Pseudostate).kind = #initial)
			 *   implies (self.source.container = self.stateMachine.top) implies
			 *   ((self.trigger->isEmpty) or (self.trigger.stereotype.name = 'create'))
			 */
			if (sourceVertex instanceof MPseudoState
					&& ((MPseudoState) sourceVertex).getKind() == MPseudoStateKind.initial) {
				if (!event.getText().equals("create")) {
					throw new SemanticException(event, "Initial transition must be unnamed or named " + StringUtil.inQuotes("create"));
				}

				result.setTrigger(MTrigger.create(event.getText(), ctx.currentClass()));
			}
		} else if (operationName != null ){
			// Operation call trigger?
			MOperation op = ctx.currentClass().operation(operationName.getText(), true);
			
			if (op == null) {
				ctx.reportError(source, "Unknown operation " + StringUtil.inQuotes(operationName.getText()));
				hasErrors = true;
			} else if (op.hasExpression()) {
				ctx.reportError(source, "The query operation " + StringUtil.inQuotes(op.toString()) + " cannot be used as a transition event, because query operations are not allowed to change the system state.");
				hasErrors = true;
			}
			
			if (op != null) {
				result.setTrigger(MTrigger.create(op));
			}
		}
		
		Expression prePostConditionExp = null;
		if (preCondition != null) {
			prePostConditionExp = genPrePost(result, ctx, sm, true, this.preCondition);
			result.setGuard(prePostConditionExp);
		}
				
		if (result instanceof MProtocolTransition && postCondition != null) {
			prePostConditionExp = genPrePost(result, ctx, sm, false, this.postCondition);
			((MProtocolTransition)result).setPostCondition(prePostConditionExp);
		}

		return result;
	}

	private Expression genPrePost(MTransition t, Context ctx, MStateMachine sm, boolean isPre, ASTExpression expr) throws SemanticException {
		MClass cls = sm.getContext();
		Expression conditionExp = null;
		
        // enter context variable into scope of invariant
        Symtable vars = ctx.varTable();
        vars.enterScope();

        try {
            // create pseudo-variable "self"
            vars.add("self", cls, null);
            ctx.exprContext().push("self", cls);

            t.getTrigger().buildEnvironment(vars, ctx.exprContext(), isPre);
            
            ctx.setInsidePostCondition(!isPre);
            conditionExp = expr.gen(ctx);
            ctx.setInsidePostCondition(false);
            
            if (!conditionExp.type().isTypeOfBoolean()) {
				throw new SemanticException(expr.getStartToken(), "A "
						+ (isPre ? "guard" : "post condition")
						+ " must be a boolean expression.");
            }
        } catch (NullPointerException ex) {
        	// Can be raised by MModel if the owning operation
        	// was not successfully generated.
        	return null;
        }
        
        vars.exitScope(); 
        ctx.exprContext().pop();

        return conditionExp;
	}
}

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

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.statemachines.MFinalState;
import org.tzi.use.uml.mm.statemachines.MPseudoState;
import org.tzi.use.uml.mm.statemachines.MPseudoStateKind;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.statemachines.MVertex;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.util.StringUtil;

/**
 * @author Lars Hamann
 *
 */
public class ASTStateDefinition extends AST {

	protected final Token name;
	
	protected Token type;

	protected ASTExpression stateInvariant;
	
	protected MVertex genVertex;
	
	/**
	 * @param sn
	 */
	public ASTStateDefinition(Token sn) {
		name = sn;
	}

	/**
	 * @param stateType
	 */
	public void setType(Token stateType) {
		type = stateType;
	}

	/**
	 * @param astExpression
	 */
	public void setStateInvariant(ASTExpression astExpression) {
		stateInvariant = astExpression;
	}

	/**
	 * @param ctx
	 * @return
	 */
	public MVertex gen(Context ctx) {
		if (type != null) {
			if (type.getText().equals("final")) {
				genVertex = new MFinalState(name.getText());
			} else {
				try {
					MPseudoStateKind kind = MPseudoStateKind.valueOf(type.getText());
					genVertex = new MPseudoState(name.getText(), kind);
				} catch (IllegalArgumentException e) {
					ctx.reportError(name, "Unknown pseudo state type " + StringUtil.inQuotes(type.getText()));
				}
			}
		} else {
			genVertex = new MState(name.getText());
		}
		
		return genVertex;
	}

	/**
	 * @param ctx
	 * @throws SemanticException 
	 */
	public void genStateInvariant(Context ctx, MStateMachine sm) throws SemanticException {
		if (stateInvariant == null || !(genVertex instanceof MState)) return;
		
		MState genState = (MState)genVertex;
		MClass cls = sm.getContext();
		Expression conditionExp = null;
		
        // enter context variable into scope of invariant
        Symtable vars = ctx.varTable();
        vars.enterScope();

        try {
            // create pseudo-variable "self"
            vars.add("self", cls, null);
            ctx.exprContext().push("self", cls);

            conditionExp = stateInvariant.gen(ctx);
                        
            if (!conditionExp.type().isTypeOfBoolean()) {
				throw new SemanticException(stateInvariant.getStartToken(),
						"A state invariant must be a boolean expression.");
            }
            
            genState.setStateInvariant(conditionExp);
        } catch (NullPointerException ex) {
        	// Can be raised by MModel if the owning operation
        	// was not successfully generated.
        }
        
        vars.exitScope(); 
        ctx.exprContext().pop();
	}

}

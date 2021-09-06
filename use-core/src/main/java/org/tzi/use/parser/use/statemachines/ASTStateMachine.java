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

import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.statemachines.MStateMachine;

/**
 * @author Lars Hamann
 *
 */
public class ASTStateMachine extends AST {

	/**
	 * The name of the state machine
	 */
	protected Token name;
	
	protected Set<ASTStateDefinition> stateDefinitions = new HashSet<ASTStateDefinition>();
	
	protected Set<ASTTransitionDefinition> transitionDefinition = new HashSet<ASTTransitionDefinition>();
	
	/**
	 * Sets the name of the state machine. 
	 * @param t
	 */
	public void setName(Token t) {
		this.name = t;
	}
	
	/**
	 * @param s
	 */
	public void addStateDefinition(ASTStateDefinition s) {
		stateDefinitions.add(s);
	}
	/**
	 * @param t
	 */
	public void addTransitionDefinition(ASTTransitionDefinition t) {
		transitionDefinition.add(t);
	}
	
	/**
	 * @param ctx
	 */
	public MStateMachine gen(Context ctx) throws SemanticException {
		MStateMachine sm = new MStateMachine(name.getText(), new SrcPos(name), ctx.currentClass());
				
		return sm;
	}

	/**
	 * @param ctx
	 * @return
	 */
	public void genTransitionsAndStateInvariants(Context ctx) throws SemanticException {

	}
}

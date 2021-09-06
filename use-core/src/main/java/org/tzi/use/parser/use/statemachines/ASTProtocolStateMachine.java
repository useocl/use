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

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MPseudoState;
import org.tzi.use.uml.mm.statemachines.MPseudoStateKind;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.mm.statemachines.MVertex;
import org.tzi.use.uml.sys.MSystemException;

/**
 * @author Lars Hamann
 *
 */
public class ASTProtocolStateMachine extends ASTStateMachine {
	
	protected MProtocolStateMachine psm;
	
	/**
	 * @param ctx
	 */
	@Override
	public MStateMachine gen(Context ctx) throws SemanticException {
		
		psm = new MProtocolStateMachine(name.getText(),
				new SrcPos(name), ctx.currentClass());
		MRegion defaultRegion = psm.getDefaultRegion();
		
		boolean hasInitialState = false;
		
		for (ASTStateDefinition s : this.stateDefinitions) {
			MVertex v = s.gen(ctx);
			if (v != null) {
				try {
					defaultRegion.addSubvertex(v);
				} catch (MSystemException e) {
					throw new SemanticException(s.name, e);
				}
				
				if (v instanceof MPseudoState && ((MPseudoState)v).getKind() == MPseudoStateKind.initial) {
					hasInitialState = true;
				}
			}
		}
		
		if (!hasInitialState)
			throw new SemanticException("A protocol state machine needs one pseudostate of kind 'initial'!");
		
		return psm;
	}

	@Override
	public void genTransitionsAndStateInvariants(Context ctx) throws SemanticException {
		for (ASTTransitionDefinition t : this.transitionDefinition) {
			MTransition ct = t.gen(ctx, psm);
			if (ct != null) {
				try {
					psm.getDefaultRegion().addTransition(ct);
				} catch (MSystemException e) {
					throw new SemanticException(t.source, e.getMessage());
				}
			}
		}
		
		for (ASTStateDefinition s : this.stateDefinitions) {
			s.genStateInvariant(ctx, psm);
		}
	}
}

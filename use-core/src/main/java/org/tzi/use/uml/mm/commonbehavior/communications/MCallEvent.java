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
package org.tzi.use.uml.mm.commonbehavior.communications;

import org.tzi.use.parser.ExprContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.VarDecl;

/**
 * @author Lars Hamann
 *
 */
public class MCallEvent extends MMessageEvent {
	protected final MOperation operation;
	
	public MCallEvent(MOperation operation) {
		this.operation = operation;
	}

	/**
	 * @return the operation
	 */
	public MOperation getOperation() {
		return operation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return operation.signature() ;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.commonbehavior.communications.MEvent#buildEnvironment(org.tzi.use.parser.Symtable, org.tzi.use.parser.ExprContext, boolean)
	 */
	@Override
	public void buildEnvironment(Symtable vars, ExprContext exprContext,
			boolean isPre) throws SemanticException {
		super.buildEnvironment(vars, exprContext, isPre);
		
		// Add parameter
		for (VarDecl parameter : this.operation.paramList()) {
			vars.add(parameter.name(), parameter.type(), null);
		}
		
        // add special variable `result' in postconditions with result value
        if (! isPre && operation.hasResultType() ) {
        	vars.add("result", operation.resultType(), null);
        }
	}
}

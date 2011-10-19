/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.parser.generator;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.util.StringUtil;

/**
 * AST class for a barrier using an invariant
 * @author Lars Hamann
 *
 */
public class ASTGBarrierInvariant extends ASTGBarrier {
	
	Token className;
	
	Token invariantName;
	
	public ASTGBarrierInvariant(Token className, Token invName) {
		this.className = className;
		this.invariantName = invName;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.parser.generator.ASTGBarrier#genExpression(org.tzi.use.parser.Context)
	 */
	@Override
	protected Expression genExpression(Context ctx) throws SemanticException {
		String invName = className.getText() + "::" + invariantName.getText();
		MClassInvariant inv = ctx.model().getClassInvariant(invName);
		
		if (inv == null)
			throw new SemanticException(className, "Invariant " + StringUtil.inQuotes(invName) +" does not exist.");
				
		return inv.expandedExpression();
	}
	
	
}

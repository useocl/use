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

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * AST class for a barrier defined by an OCL expression.
 * @author Lars Hamann
 *
 */
public class ASTGBarrierExpression extends ASTGBarrier {
	ASTExpression expression;
	
	public ASTGBarrierExpression(ASTGocl exp) {
		expression = exp.getExpression();
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.parser.generator.ASTGBarrier#genExpression(org.tzi.use.parser.Context)
	 */
	@Override
	protected Expression genExpression(Context ctx) throws SemanticException {
		Expression theExpression = expression.gen(ctx);
		
		if (!theExpression.type().isTypeOfBoolean())
			throw new SemanticException(expression.getStartToken(), "Barrier expression must be a boolean expression.");
		
		return theExpression;
	}
}

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

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.expr.ExpRange;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author  Mark Richters
 */
public class ASTCollectionItem extends AST {
    ASTExpression fFirst;
    ASTExpression fSecond; // may be null

    public void setFirst(ASTExpression expr) {
        fFirst = expr;
    }

    public void setSecond(ASTExpression expr) {
        fSecond = expr;
    }

    // for soil type checking
	public void getFreeVariables(Set<String> freeVars) {
		fFirst.getFreeVariables(freeVars);
		if (fSecond != null) {
			fSecond.getFreeVariables(freeVars);
		}
	}

	public Expression gen(Context ctx) throws SemanticException {
		Expression first = fFirst.gen(ctx);
		
		if (fSecond == null) {
			return first; 
		} else {
			Expression second = fSecond.gen(ctx);
			
			if (!(first.type().isTypeOfInteger() && second.type().isTypeOfInteger()))
				throw new SemanticException("Ranges must be of type Integer.");
			
			return new ExpRange(first, second);
		}
	}
}    


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

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Encapsulates an {@link MOperationCallStatement} 
 * used as an assignment value.
 */
public class MRValueOperationCall extends MRValue {
	/** The encapsulated command */
	private MOperationCallStatement fOperationCallStatement;
	
	/**
	 * Constructs a new <code>MRValueOperationCall</code> which
	 * encapsulates <code>operationCallStatement<(code>.
     * 
	 * @param operationCallStatement
	 */
    public MRValueOperationCall(MOperationCallStatement operationCallStatement) {
		
		fOperationCallStatement = operationCallStatement;
	}

	@Override
	public Value evaluate(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
        return fOperationCallStatement.execute(context, result);
	}

	/**
	 * @return the fOperationCallStatement
	 */
	public MOperationCallStatement getOperationCallStatement() {
		return fOperationCallStatement;
	}
	
	@Override
	public Type getType() {		
		return fOperationCallStatement.getReturnType();
	}

	@Override
	public String toString() {
		return fOperationCallStatement.shellCommand();
	}
}

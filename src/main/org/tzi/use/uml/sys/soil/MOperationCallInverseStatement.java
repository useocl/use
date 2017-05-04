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

package org.tzi.use.uml.sys.soil;

import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Internal statement to revert an operation call
 * from soil.
 * @author Lars Hamann
 *
 */
public class MOperationCallInverseStatement extends MStatement {

	private final MOperationCall operationCall;
	
	/**
	 * @param operationCall
	 */
	public MOperationCallInverseStatement(MOperationCall operationCall) {
		this.operationCall = operationCall;
	}

	@Override
	protected String shellCommand() {
		return "";
	}

	@Override
	public String toString() {
		return "Revert " + operationCall.toString();
	}

	@Override
	public Value execute(SoilEvaluationContext context, StatementEvaluationResult result) throws EvaluationFailedException {
		if (!this.operationCall.hasExecutedTransitions())
			return null;

		for (Map.Entry<MProtocolStateMachineInstance, Set<MTransition>> entry : this.operationCall.getExecutedTransitions().entrySet()) {			
			for (MTransition t : entry.getValue()) {
			   context.getSystem().revertTransition(result, entry.getKey(), t);
			}
		}
		
		return null;
	}

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}

}

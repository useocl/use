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

package org.tzi.use.uml.sys.soil;

import java.util.Map;
import java.util.Map.Entry;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.ppcHandling.DoNothingPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.OpEnterOpExitPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MEnterOperationStatement extends MStatement {
	/** TODO */
	private Expression fObject;
	/** TODO */
	private MOperation fOperation;
	/** TODO */
	private Map<String, Expression> fArguments;
	/** TODO */
	private PPCHandler fCustomPPCHandler;

	
	/**
	 * TODO
	 * @param object
	 * @param operation
	 * @param arguments
	 */
	public MEnterOperationStatement(
			Expression object, 
			MOperation operation, 
			Map<String, Expression> arguments) {
		
		fObject = object;
		fOperation = operation;
		fArguments = arguments;
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param operation
	 * @param arguments
	 * @param customPPCHandler
	 */
	public MEnterOperationStatement(
			Expression object,
			MOperation operation,
			Map<String, Expression> arguments,
			PPCHandler customPPCHandler) {
		
		this(object, operation, arguments);
		fCustomPPCHandler = customPPCHandler;
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		// evaluate self
		MObject self = evaluateObjectExpression(fObject);
		
		// evaluate arguments
		Value[] arguments = new Value[fArguments.size()];
		int i=0;
		for (Entry<String, Expression> argument : fArguments.entrySet()) {
			Value argValue = evaluateExpression(argument.getValue(), false);
			arguments[i] = argValue;
			++i;
		}
		
		MOperationCall opCall = 
			enterOperation(
				self, 
				fOperation, 
				arguments, 
				fCustomPPCHandler == null ? 
						OpEnterOpExitPPCHandler.getDefaultOutputHandler() : fCustomPPCHandler,
				true);
		
		// build inverse statement if necessary
		
		if (!opCall.enteredSuccessfully()) {
			return;
		}
		
		Expression resultExpression = null;
		
		if (fOperation.hasResultType()) {
			resultExpression = 
				new ExpressionWithValue(UndefinedValue.instance);
		}
		
		fResult.prependToInverseStatement(
				new MExitOperationStatement(
						resultExpression,
						DoNothingPPCHandler.getInstance()));
	}

	
	@Override
	protected String shellCommand() {
		StringBuilder result = new StringBuilder();
		
		result.append("openter ");
		result.append(fObject);
		result.append(" ");
		result.append(fOperation);
		result.append("(");
		StringUtil.fmtSeq(result, fArguments.values(), ", ");
		result.append(")");
		
		return result.toString();
	}


	@Override
	public boolean hasSideEffects() {
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();
	}


	/* (non-Javadoc)
	 * @see org.tzi.use.uml.sys.soil.MStatement#mayGenerateUnqiueNames()
	 */
	@Override
	public boolean mayGenerateUnqiueNames() {
		return false;
	}
}

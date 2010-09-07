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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.ppcHandling.ExpressionPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.SoilPPCHandler;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;
import org.tzi.use.util.soil.exceptions.evaluation.UndefinedOperationException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MOperationCallStatement extends MStatement {
	/** TODO */
	private Expression fObject;
	/** TODO */
	private MOperation fOperation;
	/** TODO */
	private LinkedHashMap<String, Expression> fArguments;
	/** TODO */
	private Value fReturnValue; // may be null!
	
	
	/**
	 * TODO
	 * @param object
	 * @param operation
	 * @param arguments
	 */
	public MOperationCallStatement(
			Expression object,
			MOperation operation,
			LinkedHashMap<String, Expression> arguments) {
		
		fObject = object;
		fOperation = operation;
		fArguments = arguments;
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param operation
	 */
	public MOperationCallStatement(
			Expression object,
			MOperation operation) {
		
		this(
				object, 
				operation, 
				new LinkedHashMap<String, Expression>(0));
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Expression getObject() {
		return fObject;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MOperation getOperation() {
		return fOperation;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Map<String, Expression> getArguments() {
		return fArguments;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Value getReturnValue() {
		return fReturnValue;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Type getReturnType() {
		return fOperation.resultType();
	}
	

	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		MStatement operationBody = fOperation.getStatement();
		
		if (operationBody == null) {
			throw new UndefinedOperationException(this, fOperation);
		}
				
		// just to check if self exists
		MObject self = evaluateObjectExpression(fObject);
		
		// evaluate arguments
		// evaluate arguments
		Value[] arguments = new Value[fArguments.size()];
		int i=0;
		for (Entry<String, Expression> argument : fArguments.entrySet()) {
			String argName = argument.getKey();
			Value argValue = evaluateExpression(argument.getValue(), false);
			arguments[i] = argValue;
			++i;
		}
		
		MOperationCall operationCall = 
			enterOperation(
					self, 
					fOperation, 
					arguments, 
					fContext.isInExpression() ? 
							new ExpressionPPCHandler() : 
								new SoilPPCHandler(),
					false);
		
		try {
			evaluateSubStatement(operationBody);
			
			if (fOperation.hasResultType()) {
				fReturnValue = fVarEnv.lookUp("result");
			}
		} catch (EvaluationFailedException e) {
			operationCall.setExecutionFailed(true);
			throw e;
		} finally {
			exitOperation(fReturnValue);
		}
	}
	
	
	@Override
	protected String shellCommand() {
		StringBuilder result = new StringBuilder();
		
		result.append(fObject);
		result.append(".");
		result.append(fOperation);
		result.append("(");
		StringUtil.addToStringBuilder(result, fArguments.values());
		result.append(")");
		
		return result.toString();
	}
	
	
	@Override
	public boolean hasSideEffects() {
		if (fObject.hasSideEffects() || fOperation.hasSideEffects()) {
			return true;
		}
		
		for (Expression argument : fArguments.values()) {
			if (argument.hasSideEffects()) {
				return true;
			}
		}
		
		return false;
	}


	@Override
	public String toString() {
		return shellCommand();
	}
}

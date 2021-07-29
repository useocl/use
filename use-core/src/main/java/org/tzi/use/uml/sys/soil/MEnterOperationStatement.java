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

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.ppcHandling.DoNothingPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.OpEnterOpExitPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * The statement class for the legacy openter command.
 * @author Daniel Gent
 *
 */
public class MEnterOperationStatement extends MStatement {
	/** The expression leading to the receiving object of the operation enter */
	private Expression fObject;
	/** The operation to enter */
	private MOperation fOperation;
	/** The arguments for the operation enter */
	private Expression[] fArguments;
	/** A custom PPC handler */
	private PPCHandler fCustomPPCHandler;

	/**
	 * Creates a new statement for a legacy openter command. 
	 * @param object The expression leading to the receiving object of the operation enter.
	 * @param operation The operation to enter.
	 * @param arguments The arguments for the operation enter.
	 */
	public MEnterOperationStatement(
			Expression object, 
			MOperation operation, 
			Expression[] arguments) {
		
		fObject = object;
		fOperation = operation;
		fArguments = arguments;
	}
	
	/**
	 * Creates a new statement for a legacy openter command providing a custom PPC handler.
	 * @param object The expression leading to the receiving object of the operation enter.
	 * @param operation The operation to enter.
	 * @param arguments The arguments for the operation enter.
	 * @param customPPCHandler A custom PPC handler.
	 */
	public MEnterOperationStatement(
			Expression object,
			MOperation operation,
			Expression[] arguments,
			PPCHandler customPPCHandler) {
		
		this(object, operation, arguments);
		fCustomPPCHandler = customPPCHandler;
	}
	
	/**
	 * @return the fObject
	 */
	public Expression getObject() {
		return fObject;
	}

	/**
	 * @return the fOperation
	 */
	public MOperation getOperation() {
		return fOperation;
	}

	/**
	 * @return the fArguments
	 */
	public Expression[] getArguments() {
		return fArguments;
	}

	/**
	 * @return the fCustomPPCHandler
	 */
	public PPCHandler getCustomPPCHandler() {
		return fCustomPPCHandler;
	}

	@Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result) throws EvaluationFailedException {
		
		// evaluate self
		MObject self = EvalUtil.evaluateObjectExpression(context, fObject);
		
		// evaluate arguments
		Value[] arguments = new Value[fArguments.length];
		for (int i = 0; i < fArguments.length; ++i) {
			Value argValue = EvalUtil.evaluateExpression(context, fArguments[i], false);
			arguments[i] = argValue;
		}
		
		MOperationCall operationCall = new MOperationCall(this, self, fOperation, arguments);
		
		operationCall.setPreferredPPCHandler(fCustomPPCHandler == null ? 
								OpEnterOpExitPPCHandler.getDefaultOutputHandler() : fCustomPPCHandler);
		
		try {
			context.getSystem().enterNonQueryOperation(context, result, operationCall, true);
		} catch (MSystemException e) {
			throw new EvaluationFailedException(e);
		}
		
		MOperationCall opCall = operationCall;
		
		// build inverse statement if necessary
		
		if (!opCall.enteredSuccessfully()) {
			return null;
		}
		
		Expression resultExpression = null;
		
		if (fOperation.hasResultType()) {
			resultExpression = 
				new ExpressionWithValue(UndefinedValue.instance);
		}
		
		result.prependToInverseStatement(
				new MExitOperationStatement(
						resultExpression,
						DoNothingPPCHandler.getInstance()));
		
		return null;
	}
	
	@Override
	protected String shellCommand() {
		StringBuilder result = new StringBuilder();
		
		result.append("openter ");
		result.append(fObject);
		result.append(" ");
		result.append(fOperation.name());
		result.append("(");
		StringUtil.fmtSeq(result, fArguments, ", ");
		result.append(")");
		
		return result.toString();
	}

	@Override
	public String toString() {
		return shellCommand();
	}

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}
}

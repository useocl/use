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

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.library.LibraryOperation;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Library operations like, e. g., ReadLine
 * have no source.
 * This class delegates the execute operation
 * to the concrete library operation.
 * @author Lars Hamann
 *
 */
public class MLibraryOperationCallStatement extends MOperationCallStatement {

	/** The library operation to execute **/
	private LibraryOperation operation;
	
	/** The argument expressions **/
	private Expression[] arguments;
	
	public MLibraryOperationCallStatement(LibraryOperation operation, Expression[] args) {
		this.operation = operation;
		this.arguments = args;
	}
	
	/**
	 * @return the operation
	 */
	public LibraryOperation getOperation() {
		return operation;
	}

	/**
	 * @return the arguments
	 */
	public Expression[] getArguments() {
		return arguments;
	}
	
	/**
	 * @return the return type of the operation
	 */
	@Override
	public Type getReturnType() {
		return operation.getReturnType();
	}

	@Override
	public Value execute(SoilEvaluationContext context, StatementEvaluationResult result) throws EvaluationFailedException {
		Value[] argValues = new Value[arguments.length];
		
		for (int i = 0; i < arguments.length; ++i) {
			argValues[i] = EvalUtil.evaluateExpression(context, arguments[i]);	
		}
		
		return operation.execute(context, argValues);
	}

	@Override
	protected String shellCommand() {
		return operation.getName() + "(" + StringUtil.fmtSeq(arguments, ",") + ")";
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

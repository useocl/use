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
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.ppcHandling.ExpressionPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.SoilPPCHandler;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Statement for an operation call to an
 * operation which is defined with a soil body.
 * 
 * @author Daniel Gent
 * 
 */
public class MObjectOperationCallStatement extends MOperationCallStatement {
    /** Expression which leads to an object to send the operation call to. */
	private Expression fObject;
	
	/** The operation to call. */
	private MOperation fOperation;
	
	/** argument expressions */
	private Expression[] fArguments;
	
    /**
     * Constructs a new operation call statement, to call
     * an operation that has a SOIL body.
     * 
     * @param object Expression which leads to an object to send the operation call to.
     * @param operation The operation to call.
     * @param arguments Mapping of argument names to value expressions
     */
	public MObjectOperationCallStatement(Expression object, MOperation operation, Expression[] arguments) {
        fObject = object;
        fOperation = operation;
        fArguments = arguments;
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
     * The return type of the called operation.
     * 
     * @return
     */
    public Type getReturnType() {
        return fOperation.resultType();
    }

    @Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result) throws EvaluationFailedException {

        // just to check if self exists
        MObject self = EvalUtil.evaluateObjectExpression(context, fObject);

        MOperation operation = self.cls().operation(fOperation.name(), true);
        MStatement operationBody = operation.getStatement();

        // evaluate arguments
        Value[] arguments = new Value[fArguments.length];
        int i = 0;
        for (Expression argument : fArguments) {
            Value argValue = EvalUtil.evaluateExpression(context, argument, false);
            arguments[i] = argValue;
            ++i;
        }
        
        MOperationCall operationCall = new MOperationCall(this, self, operation, arguments);

        operationCall.setPreferredPPCHandler(context.isInExpression() ? ExpressionPPCHandler
                .getDefaultOutputHandler() : SoilPPCHandler.getDefaultOutputHandler());

        try {
            context.getSystem().enterNonQueryOperation(context, result, operationCall, false);
        } catch (MSystemException e1) {
            throw new EvaluationFailedException(e1);
        }

        Value resultValue = null;
        
        try {
            operationBody.execute(context, result);

            if (fOperation.hasResultType()) {
                resultValue = context.getVarEnv().lookUp("result");
                return resultValue;
            } else {
            	return null;
            }
        } catch (EvaluationFailedException e) {
            operationCall.setExecutionFailed(true);
            throw e;
        } finally {
            MOperationCall currentOperation = context.getSystem().getCurrentOperation();

            if (currentOperation == null) {
                throw new EvaluationFailedException("No current operation");
            }

            if (null != null) {
                currentOperation.setPreferredPPCHandler(null);
            }

            try {
                context.getSystem().exitNonQueryOperation(context, result, resultValue);
            } catch (MSystemException e) {
                throw new EvaluationFailedException(e);
            }
            
            result.prependToInverseStatement(
					new MOperationCallInverseStatement(operationCall));
        }
    }

    @Override
    protected String shellCommand() {
        StringBuilder result = new StringBuilder();

        result.append(fObject);
        result.append(".");
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

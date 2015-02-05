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

package org.tzi.use.uml.ocl.expr;

import java.util.List;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.ppcHandling.ExpressionPPCHandler;
import org.tzi.use.util.StringUtil;

/**
 * An operation defined by a class.
 *
 * @author  Mark Richters
 */
public final class ExpObjOp extends Expression {
    private MOperation fOp;
    
    /**
     * The arguments, first one is "receiver" object
     */
    private Expression[] fArgs;
    
    public ExpObjOp(MOperation op, Expression[] args) 
        throws ExpInvalidException
    {
        super(op.resultType());
        fOp = op;
        fArgs = args;
        if (! args[0].type().isTypeOfClass() )
            throw new ExpInvalidException(
                                          "Target expression of object operation must have " +
                                          "object type, found `" + args[0].type() + "'.");

        // check for matching arguments
        VarDeclList params = fOp.paramList();
        if (params.size() != (args.length - 1) )
            throw new ExpInvalidException(
                                          "Number of arguments does not match declaration of operation `" +
                                          fOp.name() + "'. Expected " + params.size() + " argument(s), found " +
                                          (args.length - 1) + ".");

        for (int i = 1; i < args.length; i++)
            if (! args[i].type().conformsTo(params.varDecl(i - 1).type()) )
                throw new ExpInvalidException(
                                              "Type mismatch in argument `" + params.varDecl(i - 1).name() + 
                                              "'. Expected type `" + params.varDecl(i - 1).type() + 
                                              "', found `" + args[i].type() + "'.");
    }
    
    public Value eval(EvalContext ctx) {
    	if (isPre()) {
    		ctx = new EvalContext(ctx.preState(), ctx.preState(), ctx.varBindings(), ctx);
    	}
    	
    	ctx.enter(this);
    	
    	Value result = UndefinedValue.instance;
    	
    	Value selfVal = fArgs[0].eval(ctx);
    	
    	if (selfVal.isUndefined() || 
    			!(selfVal instanceof ObjectValue)) {
    		
    		ctx.exit(this, result);
    		return result;
    	}
    	
    	MObject self = ((ObjectValue)selfVal).value();
    	
    	if ((isPre() && (self.state(ctx.preState()) == null)) ||
    			(!isPre() && (self.state(ctx.postState()) == null))) {
    		
    		ctx.exit(this, result);
    		return result;
    	}
    	
    	MOperation operation = self.cls().operation(fOp.name(), true);
    	
    	if (!operation.isCallableFromOCL()) {
    		throw new RuntimeException("Cannot call operation " + operation);
    	}
    	 	
    	List<String> parameterNames = operation.paramNames();
    	Value[] arguments = new Value[parameterNames.size()];
    	for (int i = 1; i < fArgs.length; ++i) {
    		arguments[i-1]= fArgs[i].eval(ctx);
    	}
    	
    	// this must be done _after_ all parameters have been evaluated, 
    	// since the parameter names could shadow values which are 
    	// needed for a later parameter (see test\t005.*) 
    	ctx.pushVarBinding("self", selfVal);
    	for(int i = 0; i < parameterNames.size(); ++i) {
    		ctx.pushVarBinding(
    				parameterNames.get(i), 
    				arguments[i]);
    	}
    	
    	MOperationCall operationCall = 
    		new MOperationCall(this, self, operation, arguments);
    	
    	operationCall.setPreferredPPCHandler(ExpressionPPCHandler.getDefaultOutputHandler());
    	
    	MSystem system = ctx.postState().system();
    	
    	try {
    		system.enterQueryOperation(ctx, operationCall, false);
    		operationCall.setExecutionFailed(true);
    	
			if (operation.hasExpression()) {
				result = operation.expression().eval(ctx);
			}

			operationCall.setExecutionFailed(false);
		} catch (MSystemException e) {
    		throw new RuntimeException(e);
    	} finally {
    		try {
	    		if (operationCall.enteredSuccessfully()) {
	    			system.exitQueryOperation(ctx, result);
	    		}
    		} catch (Exception e){ }
    		ctx.popVarBindings(fArgs.length);
    	    ctx.exit(this, result);
    	}
    		    
    	return result;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
    	fArgs[0].toString(sb);
    	sb.append(".")
    	  .append(fOp.name())
    	  .append(atPre())
    	  .append("(");
    	
        StringUtil.fmtSeqBuffered(sb, fArgs, 1, ", ");
        
        return sb.append(")");
    }
    
    public MOperation getOperation() {
        return fOp;
    }

    /**
     * All arguments of the expression.
     * Index 0 is the receiver object (self)
     * @return
     */
    public Expression[] getArguments() {
        return fArgs;
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitObjOp(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		for (Expression e : fArgs) {
			if (e.requiresPreState()) {
				return true;
			}
		}
		
		return false;
	}
}

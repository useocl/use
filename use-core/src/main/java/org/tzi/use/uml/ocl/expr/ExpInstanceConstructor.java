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

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.value.DataTypeValueValue;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.*;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.ppcHandling.ExpressionPPCHandler;
import org.tzi.use.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A constructor defined by a classifier.
 *
 * @author Stefan Schoon
 */
public final class ExpInstanceConstructor extends Expression {

    private final MClassifier classifier;

    private final MOperation constructor;

    /**
     * The arguments.
     */
    private final Expression[] fArgs;

    public ExpInstanceConstructor(MClassifier classifier, Expression[] args) throws ExpInvalidException {
        super(classifier);
        this.classifier = classifier;
        this.constructor = classifier.operation(classifier.name(), false);
        fArgs = args;
    }

    @Override
    public Value eval(EvalContext ctx) {
        if (isPre()) {
            ctx = new EvalContext(ctx.preState(), ctx.preState(), ctx.varBindings(), ctx);
        }

        ctx.enter(this);

        List<String> parameterNames = constructor.paramNames();
        int argsSize = parameterNames.size();
        Value[] arguments = new Value[argsSize];
        Map<String, Value> argValues = new TreeMap<>();
        for (int i = 0; i < argsSize; i++) {
            arguments[i] = fArgs[i + 1].eval(ctx);
            ctx.pushVarBinding(parameterNames.get(i), arguments[i]);
            argValues.put(parameterNames.get(i), arguments[i]);
        }

        Map<String, Value> varBindings = new HashMap<>();
        for (VarBindings.Entry e : ctx.varBindings()) {
            varBindings.put(e.getVarName(), e.getValue());
        }
        MInstance self = new MDataTypeValue(classifier, classifier.name(), varBindings);
        Value result = new DataTypeValueValue(classifier, self, argValues);

        MOperationCall operationCall = new MOperationCall(this, self, constructor, arguments);
        operationCall.setPreferredPPCHandler(ExpressionPPCHandler.getDefaultOutputHandler());
        operationCall.setResultValue(result);

        MSystem system = ctx.postState().system();
        try {
            system.enterQueryOperation(ctx, operationCall, false);
            operationCall.setExecutionFailed(false);
        } catch (MSystemException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (operationCall.enteredSuccessfully()) {
                    system.exitQueryOperation(ctx);
                }
            } catch (MSystemException ignored) {
            }
            ctx.popVarBindings(argsSize);
            ctx.exit(this, result);
        }

        return result;
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

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(classifier.name()).append("(");
        StringUtil.fmtSeqBuffered(sb, fArgs, 1, ", ");
        return sb.append(")");
    }

    @Override
    public String name() {
        return constructor.name();
    }

    /**
     * All arguments of the expression.
     */
    public Expression[] getArguments() {
        return fArgs;
    }

    public MOperation getOperation() {
        return constructor;
    }

    @Override
    public void processWithVisitor(ExpressionVisitor visitor) {
        visitor.visitInstanceConstructor(this);
    }
}

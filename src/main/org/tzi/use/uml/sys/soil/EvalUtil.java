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

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Static helper class for the evaluation of expressions (from within MStatement and its subclasses).
 * @author Fabian Buettner
 *
 */
class EvalUtil {
    /**
     * TODO
     * @param expression
     * @param mustBeDefined
     * @return
     * @throws EvaluationFailedException
     */
    static Value evaluateExpression(
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            Expression expression, 
            boolean mustBeDefined) throws EvaluationFailedException {
        
        Evaluator evaluator = new Evaluator();
        
        Value value;
        
        context.enterExpression(expression);
        try {
            value = evaluator.eval(
                    expression, 
                    context.getState(), 
                    context.getVarEnv().constructVarBindings());
        } catch (MultiplicityViolationException e) {
            throw new EvaluationFailedException(stmt,
                    "Evaluation of expression "
                            + StringUtil.inQuotes(expression)
                            + " failed due to following reason:\n  "
                            + e.getMessage());
        } finally {
            context.exitExpression();
        }
        
        if (mustBeDefined && value.isUndefined()) {
            throw new EvaluationFailedException(stmt, "The value of expression " +
                    StringUtil.inQuotes(expression) +
                    " is undefined.");
        }
        
        return value;
    }
    
    
    /**
     * TODO
     * @param expression
     * @return
     * @throws EvaluationFailedException
     */
    static Value evaluateExpression(
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            Expression expression) throws EvaluationFailedException {
        
        return evaluateExpression(stmt,context, result, expression, false);
    }
    

    /**
     * TODO
     * @param expression
     * @return
     * @throws EvaluationFailedException
     */
    static MObject evaluateObjectExpression(
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            Expression expression) throws EvaluationFailedException {
        
        Value value = evaluateExpression(stmt, context, result, expression, true);
        
        if (value instanceof ObjectValue) {
            return ((ObjectValue)value).value();
        } else {
            throw new EvaluationFailedException(stmt, "Expression "
                    + StringUtil.inQuotes(expression)
                    + " is expected to evaluate to an object "
                    + ", but its type is "
                    + StringUtil.inQuotes(expression.type()) + ".");
        }
    }
        
    
    /**
     * TODO
     * @param expressions
     * @return
     * @throws EvaluationFailedException
     */
    static List<MObject> evaluateObjectExpressions(          
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            List<Expression> expressions) throws EvaluationFailedException {
        
        List<MObject> vresult = new ArrayList<MObject>(expressions.size());
        
        for (Expression expression : expressions) {
            vresult.add(evaluateObjectExpression(stmt, context, result, expression));
        }
        
        return vresult;
    }
    
    /**
     * TODO
     * @param expression
     * @return
     * @throws EvaluationFailedException
     */
    static String evaluateString(
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            Expression expression) throws EvaluationFailedException {
        
        Value value = evaluateExpression(stmt, context, result, expression, true);
    
        if (value instanceof StringValue) {
            return ((StringValue)value).value();
        } else {
            throw new EvaluationFailedException(stmt, "Expression " + 
                    StringUtil.inQuotes(expression) + 
                    " is expected to be of type " +
                    StringUtil.inQuotes("String") +
                    ", found " +
                    StringUtil.inQuotes(expression.type()) +
                    ".");
        }
    }
    
    
    /**
     * TODO
     * @param rValue
     * @return
     * @throws EvaluationFailedException 
     */
    static Value evaluateRValue(
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            MRValue rValue, 
            boolean mustBeDefined) throws EvaluationFailedException {
        
        Value value = rValue.evaluate(context, result, stmt);
        
        if (mustBeDefined && value.isUndefined()) {
            throw new EvaluationFailedException(stmt, "The value of rValue " +
                    StringUtil.inQuotes(rValue) +
                    " is undefined.");
        }
        
        return value;
    }
    
    
    /**
     * TODO 
     * @param rValue
     * @return
     * @throws EvaluationFailedException
     */
    static MObject evaluateObjectRValue(
            MStatement stmt,
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            MRValue rValue) throws EvaluationFailedException {
        
        Value value = evaluateRValue(stmt, context, result, rValue, true);
        
        if (value instanceof ObjectValue) {
            return ((ObjectValue)value).value();
        } else {
            throw new EvaluationFailedException(stmt, "RValue " +
                    StringUtil.inQuotes(rValue) +
                    " is expected to evaluate to an object " +
                    ", but its type is " +
                    StringUtil.inQuotes(rValue.getType()) +
                    ".");
        }
    }
    
}

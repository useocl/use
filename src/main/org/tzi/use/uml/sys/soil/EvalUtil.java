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
 * @author Lars Hamann
 *
 */
public class EvalUtil {
    /**
     * Evaluates the <code>expression</code> using the given <code>context</code>.
     * If <code>mustBeDefined</code> is <code>true</code> an exception is thrown,
     * if the evaluation results in the undefined value.
     * @param context The context to evaluate the expression with. 
     * @param expression The expression to evaluate.
     * @param mustBeDefined If <code>true</code>, the expression must evaluate to a value != <code>undefined</code>.
     * @return The evaluation result.
     * @throws EvaluationFailedException If a multiplicity violation occurs or the expression evaluates to undefined and <code>mustBeDefined</code> is <code>true</code>.
     */
    public static Value evaluateExpression(
            SoilEvaluationContext context,
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
            throw new EvaluationFailedException(
                    "Evaluation of expression "
                            + StringUtil.inQuotes(expression)
                            + " failed due to following reason:\n  "
                            + e.getMessage());
        } finally {
            context.exitExpression();
        }
        
        if (mustBeDefined && value.isUndefined()) {
            throw new EvaluationFailedException("The value of expression " +
                    StringUtil.inQuotes(expression) +
                    " is undefined.");
        }
        
        return value;
    }
    
    
    /**
     * Evaluates the <code>expression</code> using the given <code>context</code>.
     * 
     * @param context The context to evaluate the expression with. 
     * @param expression The expression to evaluate.
     * @return The evaluation result.
     * @throws EvaluationFailedException If a multiplicity violation occurs.
     */
    public static Value evaluateExpression(
            SoilEvaluationContext context,
            Expression expression) throws EvaluationFailedException {
        return evaluateExpression(context, expression, false);
    }
    
    /**
     * Evaluates the <code>expression</code> using the given <code>context</code>
     * and performs a type check (result is an object) to the result.
     * 
     * @param context The context to evaluate the expression with. 
     * @param expression The expression to evaluate.
     * @return The resulting object.
     * @throws EvaluationFailedException If a multiplicity violation occurs or if the expression does not return an object value.
     */
    public static MObject evaluateObjectExpression(
            SoilEvaluationContext context,
            Expression expression) throws EvaluationFailedException {
        
        Value value = evaluateExpression(context, expression, true);
        
        if (value instanceof ObjectValue) {
            return ((ObjectValue)value).value();
        } else {
            throw new EvaluationFailedException("Expression "
                    + StringUtil.inQuotes(expression)
                    + " is expected to evaluate to an object "
                    + ", but its type is "
                    + StringUtil.inQuotes(expression.type()) + ".");
        }
    }
        
    
    /**
     * Evaluates the <code>expressions</code> using the given <code>context</code>
     * and performs a type check (result is an object) to the result.
     * 
     * @param context The context to evaluate the expression with. 
     * @param expression The expression to evaluate.
     * @return The resulting objects.
     * @throws EvaluationFailedException If a multiplicity violation occurs or if the expression does not return a string value.
     */
    public static List<MObject> evaluateObjectExpressions(
            SoilEvaluationContext context,
            List<Expression> expressions) throws EvaluationFailedException {
        
        List<MObject> vresult = new ArrayList<MObject>(expressions.size());
        
        for (Expression expression : expressions) {
            vresult.add(evaluateObjectExpression(context, expression));
        }
        
        return vresult;
    }
    
    /**
     * Evaluates the <code>expression</code> using the given <code>context</code>
     * and performs a type check (result is a string) to the result.
     * 
     * @param context The context to evaluate the expression with. 
     * @param expression The expression to evaluate.
     * @return The resulting object.
     * @throws EvaluationFailedException If a multiplicity violation occurs or if the expression does not return a string value.
     */
    public static String evaluateString(
            SoilEvaluationContext context,
            Expression expression) throws EvaluationFailedException {
        
        Value value = evaluateExpression(context, expression, true);
    
        if (value instanceof StringValue) {
            return ((StringValue)value).value();
        } else {
            throw new EvaluationFailedException("Expression " + 
                    StringUtil.inQuotes(expression) + 
                    " is expected to be of type " +
                    StringUtil.inQuotes("String") +
                    ", found " +
                    StringUtil.inQuotes(expression.type()) +
                    ".");
        }
    }
    
    
    /**
     * Evaluates the <code>rValue</code> and returns the value.
     * Additional side effects are stored in <code>result</code>.
     * @param context The context used to evaluate the RValue in.
     * @param result Store for additional results, e. g., created objects.
     * @param rValue The RValue to evaluate.
     * @param mustBeDefined If <code>true</code>, an exception is raised, if the result is undefined.
     * @return The result value of the evaluation.
     * @throws EvaluationFailedException 
     */
    public static Value evaluateRValue(
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            MRValue rValue, 
            boolean mustBeDefined) throws EvaluationFailedException {
        
        Value value = rValue.evaluate(context, result);
        
        if (mustBeDefined && value.isUndefined()) {
            throw new EvaluationFailedException("The value of rValue " +
                    StringUtil.inQuotes(rValue) +
                    " is undefined.");
        }
        
        return value;
    }
    
    
    /**
     * Evaluates the <code>rValue</code> and returns the value.
     * Additional side effects are stored in <code>result</code>.
     * @param context The context used to evaluate the RValue in.
     * @param result Store for additional results, e. g., created objects.
     * @param rValue The RValue to evaluate.
     * @return The result value of the evaluation.
     * @throws EvaluationFailedException 
     */
    public static MObject evaluateObjectRValue(
            SoilEvaluationContext context,
            StatementEvaluationResult result,
            MRValue rValue) throws EvaluationFailedException {
        
        Value value = evaluateRValue(context, result, rValue, true);
        
        if (value instanceof ObjectValue) {
            return ((ObjectValue)value).value();
        } else {
            throw new EvaluationFailedException("RValue " +
                    StringUtil.inQuotes(rValue) +
                    " is expected to evaluate to an object " +
                    ", but its type is " +
                    StringUtil.inQuotes(rValue.getType()) +
                    ".");
        }
    }
    
}

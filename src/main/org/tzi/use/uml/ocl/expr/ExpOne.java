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

import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/** 
 * OCL one expression.
 * @since OCL 1.4
 * 
 * @author  Mark Richters
 * @author  Lars Hamann
 */
public class ExpOne extends ExpQuery {
    
    /**
     * Constructs a one expression.
     */
    public ExpOne(VarDeclList elemVarDecl,
                  Expression rangeExp, 
                  Expression queryExp) 
        throws ExpInvalidException
    {
        // result is of boolean type
        super(TypeFactory.mkBoolean(),
              elemVarDecl,
              rangeExp, queryExp);
    
        // queryExp must be a boolean expression
        assertBooleanQuery();
    } 
    
    /**
     * Constructs a one expression.
     */
    public ExpOne(VarDecl elemVarDecl, Expression rangeExp, Expression queryExp) throws ExpInvalidException {
    	this((elemVarDecl != null) ? new VarDeclList(elemVarDecl) : new VarDeclList(true), rangeExp, queryExp);
    }

    /** 
     * Return name of query expression.
     */
    public String name() {
        return "one";
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        Value res = UndefinedValue.instance;
    	ctx.enter(this);
    	
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        
        if (!v.isUndefined()) {
        	CollectionValue rangeVal = (CollectionValue) v;

        	if (ctx.isEnableEvalTree()) {
    			rangeVal = new SequenceValue(rangeVal.elemType(), rangeVal.getSortedElements());
    		}
        	
	        // we need recursion for the permutation of assignments of
	        // range values to all element variables.
        	int found = evalAux(0, rangeVal, ctx);
	        res = BooleanValue.get(found == 1);
        }
        
        ctx.exit(this, res);
        
        return res;
    }

	/**
	 * @param nesting
	 * @param rangeVal
	 * @param ctx
	 * @return
	 */
	private int evalAux(int nesting, CollectionValue rangeVal, EvalContext ctx) {
		int found = 0;
				
		for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(nesting).name(), elemVal);

            if (!fElemVarDecls.isEmpty() && nesting < fElemVarDecls.size() - 1) {
                // call recursively to iterate over range while
                // assigning each value to each element variable
                // eventually
                found += evalAux(nesting + 1, rangeVal, ctx);
                
                if (found > 1) {
                	if (ctx.isEnableEvalTree()) {
	                    // don't change the result value and continue iteration
	                    evalAux(nesting + 1, rangeVal, ctx);
                	} else {
                		if (!fElemVarDecls.isEmpty())
                			ctx.popVarBinding();
                		break;
                	}
                }
            } else {
                // evaluate predicate expression
                Value queryVal = fQueryExp.eval(ctx);

                // undefined query values default to false
                if (queryVal.isUndefined())
                    queryVal = BooleanValue.FALSE;

                // don't change the result value when expression is true
                found += ((BooleanValue) queryVal).value() ? 1 : 0;
                
                if (found > 1 && !ctx.isEnableEvalTree()) {
                	if (!fElemVarDecls.isEmpty())
                        ctx.popVarBinding();
                	break;
                }
            }
            
            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
		}
        
        return found;
	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitOne(this);
	}
}


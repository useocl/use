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

package org.tzi.use.uml.ocl.expr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * OCL closure expression introduced in OCL 2.3
 * @author Lars Hamann
 */
public class ExpClosure extends ExpQuery {

	public ExpClosure(VarDecl elemVarDecl,
            Expression rangeExp, 
            Expression queryExp) throws ExpInvalidException {
		// If range is OrderedSet or Sequence the result is an OrderedSet, otherwise
		// a Set (OCL 2.3 p. 58)
		super(calculateResultType(rangeExp, queryExp), 
	          (elemVarDecl != null) ? new VarDeclList(elemVarDecl) : new VarDeclList(true),
	          rangeExp, queryExp);
	}

	/**
	 * Determines the result type of the closure operation.
	 * If source is ordered a ordered set is returned,
	 * otherwise a set.
	 * The element type of the resulting collection type is
	 * retrieved from the range expression.
	 * 
	 * @param rangeExp The expression the closure operation is evaluated on. 
	 * @param queryExp The body of the query expression.
	 * @return The result type of this expression.
	 * @throws ExpInvalidException If the query expression results in an invalid type.
	 */
	protected static Type calculateResultType(Expression rangeExp,
			Expression queryExp) throws ExpInvalidException {

		CollectionType resultType;

		CollectionType rangeType = (CollectionType) rangeExp.type();
		Type flattenedQueryType;
		
		if (queryExp.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID)
				&& !queryExp.type().conformsTo(rangeType.elemType())) {
			flattenedQueryType = ((CollectionType)queryExp.type()).elemType();
		} else {
			flattenedQueryType = queryExp.type();
		}

		if (!flattenedQueryType.conformsTo(rangeType.elemType())) {
			throw new ExpInvalidException("Query expression must be of type "
					+ StringUtil.inQuotes(rangeType.elemType())
					+ " or "
					+ StringUtil.inQuotes("Collection(" + rangeType.elemType()
							+ ")") + ", but is " + StringUtil.inQuotes(queryExp.type()) + ".");
		}

		if (rangeExp.type().isTypeOfSequence() || rangeExp.type().isTypeOfOrderedSet()) {
			resultType = TypeFactory.mkOrderedSet(rangeType.elemType());
		} else {
			resultType = TypeFactory.mkSet(rangeType.elemType());
		}

		return resultType;
	}

	@Override
	public String name() {
		return "closure";
	}

	@Override
	public Value eval(EvalContext ctx) {
		ctx.enter(this);
        Value res = evalClosure(ctx);
        ctx.exit(this, res);
        return res;
	}

	/**
     * Evaluate collect expressions.
     */
    protected final Value evalClosure(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        
        if (v.isUndefined())
            return UndefinedValue.instance;
        
        CollectionValue rangeVal = (CollectionValue) v;
        
        // prepare result value
        Set<Value> resValues = new LinkedHashSet<Value>(rangeVal.collection());

        evalClosureAux(resValues, rangeVal, ctx);
        
        CollectionType resultType = (CollectionType)type();
        
        // result is collection with mapped values
        if (fRangeExp.type().isTypeOfSequence() || fRangeExp.type().isTypeOfOrderedSet())
            return new OrderedSetValue(resultType.elemType(), resValues);
        else
            return new SetValue(resultType.elemType(), resValues);
    }
    
	/**
	 * @param resValues
	 * @param elem
	 * @param ctx
	 */
	private void evalClosureAux(Set<Value> resValues, Value elem, EvalContext ctx) {
		
		Collection<Value> rangeVal;
		CollectionType resultType = (CollectionType)type();
		
		if (elem.isCollection() && !elem.type().conformsTo(resultType.elemType())) {
			rangeVal = ((CollectionValue)elem).collection();
		} else {
			rangeVal = new ArrayList<Value>(1);
			rangeVal.add(elem);
		}
				
		// loop over range elements
        for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), elemVal);

            // evaluate closure expression
            Value val = fQueryExp.eval(ctx);

            // add element or elements to result
            if (val.isCollection() && !val.type().conformsTo(resultType.elemType()) ) {
            	CollectionValue colVal = (CollectionValue)val;
            	for (Value elem2 : colVal.collection()) {
            		if (!resValues.contains(elem2)) {
            			resValues.add(elem2);
            			evalClosureAux(resValues, elem2, ctx);
            		}
            	}
            } else {
            	if (!resValues.contains(val)) {
            		resValues.add(val);
            		evalClosureAux(resValues, val, ctx);
            	}
            }
            
            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
        }

	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitClosure(this);
	}

}

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

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.*;

import java.util.*;

/**
 * Abstract base class for select, reject, collect, exists, forAll and iterate
 * expressions.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */

public abstract class ExpQuery extends Expression {

	/**
     * List of element variables that will be bound to each range element (may
     * be empty).
     */
    protected VarDeclList fElemVarDecls;

    /**
     * Range providing elements to iterate over.
     */
    protected Expression fRangeExp;

    /**
     * Expression combining elements with accumulator.
     */
    protected Expression fQueryExp;

    protected ExpQuery(Type resultType, VarDeclList elemVarDecls,
            Expression rangeExp, Expression queryExp)
            throws ExpInvalidException {
        super(resultType);
        fElemVarDecls = elemVarDecls;
        fRangeExp = rangeExp;
        fQueryExp = queryExp;

        // type of rangeExp must be a subtype of Collection, i.e. Set,
        // Sequence or Bag
        if (!fRangeExp.type().isKindOfCollection(VoidHandling.INCLUDE_VOID))
            throw new ExpInvalidException("Range expression must be of type "
                    + "`Collection', found `" + fRangeExp.type() + "'.");

        Type rangeElemType;
        
        // assert that declared element variables are equal or
        // supertypes of range element type
        if (fRangeExp.type().isTypeOfVoidType()) {
        	rangeElemType = TypeFactory.mkVoidType();
        } else {
        	rangeElemType = ((CollectionType) fRangeExp.type()).elemType();
        }

        for (int i = 0; i < fElemVarDecls.size(); i++) {
            VarDecl vd = fElemVarDecls.varDecl(i);
            if (!rangeElemType.conformsTo(vd.type()))
                throw new ExpInvalidException("Type `" + vd.type()
                        + "' of range variable `" + vd.name()
                        + "' does not match type `" + rangeElemType
                        + "' of collection elements.");
        }
    }

	@Override
	protected boolean childExpressionRequiresPreState() {
		return fRangeExp.requiresPreState() || fQueryExp.requiresPreState();
	}
	
    protected void assertBooleanQuery() throws ExpInvalidException {
        // queryExp must be a boolean expression
        if (!fQueryExp.type().isTypeOfBoolean())
            throw new ExpInvalidException("Argument expression of `" + name()
                    + "' must have boolean type, found `" + fQueryExp.type()
                    + "'.");
    }

    /**
     * Evaluate select and reject expressions.
     */
    protected final Value evalSelectOrReject(EvalContext ctx, boolean doSelect) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return UndefinedValue.instance;
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        ArrayList<Value> resValues = new ArrayList<Value>();

        if (!rangeVal.type().isInstantiableCollection())
            throw new RuntimeException("rangeVal is not of collection type: " + rangeVal.type());

        if (!fElemVarDecls.isEmpty())
            ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), null);
        
        // loop over range elements
        for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.varBindings().setPeekValue(elemVal);

            // evaluate select expression
            Value queryVal = fQueryExp.eval(ctx);

            // undefined query values default to false
            if (queryVal.isUndefined())
                queryVal = BooleanValue.FALSE;

            if (((BooleanValue) queryVal).value() == doSelect)
                resValues.add(elemVal);
        }

        if (!fElemVarDecls.isEmpty())
            ctx.popVarBinding();
        
        // result is collection with selected/rejected values
        return ((CollectionType)rangeVal.type()).createCollectionValue(resValues);
    }

    /**
     * Evaluate exists and forAll expressions. The array
     * <code>moreElemVars</code> may be null if there is at most one element
     * variable declared.
     */
    protected final Value evalExistsOrForAll(EvalContext ctx, boolean doExists) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return UndefinedValue.instance;
        CollectionValue rangeVal = (CollectionValue) v;

        // we need recursion for the permutation of assignments of
        // range values to all element variables.
        boolean res = evalExistsOrForAll0(0, rangeVal, ctx, doExists);
        return BooleanValue.get(res);
    }

    private final boolean evalExistsOrForAll0(int nesting,
            CollectionValue rangeVal, EvalContext ctx, boolean doExists) {
        // loop over range elements
        boolean res = !doExists;
        
        for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(nesting).name(),
                        elemVal);

            if (!fElemVarDecls.isEmpty() && nesting < fElemVarDecls.size() - 1) {
                // call recursively to iterate over range while
                // assigning each value to each element variable
                // eventually
                if (res != doExists)
                    res = evalExistsOrForAll0(nesting + 1, rangeVal, ctx,
                            doExists);
                else if (ctx.isEnableEvalTree())
                    // don't change the result value when expression is true
                    // (exists) or
                    // false (forAll) and continue iteration
                    evalExistsOrForAll0(nesting + 1, rangeVal, ctx, doExists);
                else {
                	if (!fElemVarDecls.isEmpty())
                        ctx.popVarBinding();
                	break;
                }
            } else {
                // evaluate predicate expression
                Value queryVal = fQueryExp.eval(ctx);

                // undefined query values default to false
                if (queryVal.isUndefined())
                    queryVal = BooleanValue.FALSE;

                // don't change the result value when expression is true
                // (exists) or
                // false (forAll) and continue iteration
                if (res != doExists && ((BooleanValue) queryVal).value() == doExists)
                    res = doExists;
                else if (!ctx.isEnableEvalTree() &&  ((BooleanValue) queryVal).value() == doExists) {
                	if (!fElemVarDecls.isEmpty())
                        ctx.popVarBinding();
                	break;
                }
                	
            }

            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
        }

        return res;
    }

    /**
     * Evaluate collect expressions.
     */
    protected final Value evalCollectNested(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        
        if (v.isUndefined())
            return UndefinedValue.instance;
        
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        Value[] resValues = new Value[rangeVal.size()];
        int i = 0;
        
        if (!rangeVal.isEmpty()) {
	        // bind element variable to range element, if variable was
	        // declared
	        if (!fElemVarDecls.isEmpty())
	            ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), null);
	        
	        // loop over range elements
	        for (Value elemVal : rangeVal) {
	
	            // bind element variable to range element, if variable was
	            // declared
	            if (!fElemVarDecls.isEmpty())
	                ctx.varBindings().setPeekValue(elemVal);
	                        
	            // evaluate collect expression
	            Value val = fQueryExp.eval(ctx);
	
	            // add element to result
	            resValues[i++] = val;
	        }
	
	        if (!fElemVarDecls.isEmpty())
	            ctx.popVarBinding();
        }
        
        // result is collection with mapped values
        if (v.type().isTypeOfSequence() || v.type().isTypeOfOrderedSet())
            return new SequenceValue(fQueryExp.type(), resValues);
        else
            return new BagValue(fQueryExp.type(), resValues);
    }

    /**
     * Collect without nesting
     * @param ctx
     * @return
     */
    protected final Value evalCollect(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        
        if (v.isUndefined())
            return UndefinedValue.instance;
        
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        Value[] resValues = new Value[rangeVal.size()];
        int i = 0;
        
        if (!rangeVal.isEmpty()) {
	        // bind element variable to range element, if variable was
	        // declared
	        if (!fElemVarDecls.isEmpty())
	            ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), null);
	        
	        // loop over range elements
	        for (Value elemVal : rangeVal) {
	
	            // bind element variable to range element, if variable was
	            // declared
	            if (!fElemVarDecls.isEmpty())
	                ctx.varBindings().setPeekValue(elemVal);
	                        
	            // evaluate collect expression
	            Value val = fQueryExp.eval(ctx);
	
	            // add element to result
	            resValues[i++] = val;
	        }
	
	        if (!fElemVarDecls.isEmpty())
	            ctx.popVarBinding();
        }
        
        // result is collection with mapped values
        if (v.type().isTypeOfSequence() || v.type().isTypeOfOrderedSet())
            return new SequenceValue(fQueryExp.type(), resValues);
        else
            return new BagValue(fQueryExp.type(), resValues);    	
    }

    /**
     * Collect without nesting the result
     * @param ctx
     * @return
     */
    protected final Value evalCollectOnNested(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        
        if (v.isUndefined())
            return UndefinedValue.instance;
        
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        List<Value> resValues = new ArrayList<Value>(rangeVal.size());
                
        if (!rangeVal.isEmpty()) {
	        // bind element variable to range element, if variable was
	        // declared
	        if (!fElemVarDecls.isEmpty())
	            ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), null);
	        
	        // loop over range elements
	        for (Value elemVal : rangeVal) {
	
	            // bind element variable to range element, if variable was
	            // declared
	            if (!fElemVarDecls.isEmpty())
	                ctx.varBindings().setPeekValue(elemVal);
	                        
	            // evaluate collect expression
	            Value val = fQueryExp.eval(ctx);
	
	            if (!val.isUndefined()) {
	            	for (Value cVal : (CollectionValue)val) {
	            		resValues.add(cVal);
	            	}
	            } else {
	            	resValues.add(val);
	            }
	        }
	
	        if (!fElemVarDecls.isEmpty())
	            ctx.popVarBinding();
        }
        
        // result is collection with mapped values
        if (v.type().isTypeOfSequence() || v.type().isTypeOfOrderedSet())
            return new SequenceValue(((CollectionType)fQueryExp.type()).elemType(), resValues);
        else
            return new BagValue(((CollectionType)fQueryExp.type()).elemType(), resValues);
    }
    
    /**
     * Evaluate isUnique expression.
     */
    protected final Value evalIsUnique(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return UndefinedValue.instance;
        CollectionValue rangeVal = (CollectionValue) v;

        // collect values for finding duplicates
        Set<Value> values = new HashSet<Value>();

        // loop over range elements
        for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), elemVal);

            // evaluate collect expression
            Value val = fQueryExp.eval(ctx);

            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();

            // stop if duplicate element is found
            if (values.contains(val))
                return BooleanValue.FALSE;
            else
                values.add(val);
        }

        // result is true if no duplicates where found
        return BooleanValue.TRUE;
    }

    /**
     * Evaluate sortedBy expressions.
     */
    protected final Value evalSortedBy(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return UndefinedValue.instance;
        CollectionValue rangeVal = (CollectionValue) v;

        ArrayList<KeyValPair> keyValList = new ArrayList<KeyValPair>();

        // loop over range elements
        for( Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), elemVal);

            // evaluate sortedBy expression and store the result as a
            // key together with elemVal
            Value key = fQueryExp.eval(ctx);
            keyValList.add(new KeyValPair(key, elemVal));

            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
        }

        // sort elements by key
        Collections.sort(keyValList, new Comparator<KeyValPair>() {
            public int compare(KeyValPair o1, KeyValPair o2) {
                return o1.fKey.compareTo(o2.fKey);
            }
        });

        // drop the keys from the list
        ListIterator<KeyValPair> listIter = keyValList.listIterator();
        Collection<Value> result = new ArrayList<Value>(keyValList.size());
        
        while (listIter.hasNext()) {
            KeyValPair kvp = listIter.next();
            result.add(kvp.fElem);
        }

        Type rangeElemType = ((CollectionType) fRangeExp.type()).elemType();

        if (this.type().isTypeOfOrderedSet()) {
            return new OrderedSetValue(rangeElemType, result);
        } else {
            return new SequenceValue(rangeElemType, result);
        }
    }

    // used by evalSortedBy
    private class KeyValPair {
        Value fKey;

        Value fElem;

        KeyValPair(Value key, Value elem) {
            fKey = key;
            fElem = elem;
        }
    }

    /**
     * Return name of concrete query expression, e.g. `select'. Defined by
     * subclasses.
     */
    @Override
    public abstract String name();

    @Override
    public StringBuilder toString(StringBuilder sb) {
        fRangeExp.toString(sb);
        sb.append("->")
          .append(this.name())
          .append("(");
        
        if (!fElemVarDecls.isEmpty()) {
            fElemVarDecls.toString(sb);
            sb.append(" | ");
        }
        
        fQueryExp.toString(sb);
        return sb.append(")");
    }

    public Expression getRangeExpression() {
        return fRangeExp;
    }

    public Expression getQueryExpression() {
        return fQueryExp;
    }

    public VarDeclList getVariableDeclarations() {
        return fElemVarDecls;
    }
}

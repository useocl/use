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

$Id$

package org.tzi.use.uml.ocl.expr;

import java.util.*;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.*;

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
        if (!fRangeExp.type().isCollection())
            throw new ExpInvalidException("Range expression must be of type "
                    + "`Collection', found `" + fRangeExp.type() + "'.");

        // assert that declard element variables are equal or
        // supertypes of range element type
        Type rangeElemType = ((CollectionType) fRangeExp.type()).elemType();

        for (int i = 0; i < fElemVarDecls.size(); i++) {
            VarDecl vd = fElemVarDecls.varDecl(i);
            if (!rangeElemType.isSubtypeOf(vd.type()))
                throw new ExpInvalidException("Type `" + vd.type()
                        + "' of range variable `" + vd.name()
                        + "' does not match type `" + rangeElemType
                        + "' of collection elements.");
        }
    }

    protected void assertBooleanQuery() throws ExpInvalidException {
        // queryExp must be a boolean expression
        if (!fQueryExp.type().isBoolean())
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
            return new UndefinedValue(type());
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        ArrayList resValues = new ArrayList();
        Type elemType = rangeVal.elemType();
        if (!rangeVal.type().isSetBagOrSequence())
            throw new RuntimeException("rangeVal is not of collection type: "
                    + rangeVal.type());

        // loop over range elements
        Iterator collIter = rangeVal.iterator();
        while (collIter.hasNext()) {
            Value elemVal = (Value) collIter.next();

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), elemVal);

            // evaluate select expression
            Value queryVal = fQueryExp.eval(ctx);

            // undefined query values default to false
            if (queryVal.isUndefined())
                queryVal = BooleanValue.FALSE;

            if (((BooleanValue) queryVal).value() == doSelect)
                resValues.add(elemVal);

            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
        }

        CollectionValue res;
        if (rangeVal.type().isSet())
            res = new SetValue(elemType, resValues);
        else if (rangeVal.type().isSequence())
            res = new SequenceValue(elemType, resValues);
        else if (rangeVal.type().isBag())
            res = new BagValue(elemType, resValues);
        else {
            // should not happen
            throw new RuntimeException("rangeVal is not of collection type: "
                    + rangeVal.type());
        }
        // result is collection with selected/rejected values
        return res;
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
            return new UndefinedValue(type());
        CollectionValue rangeVal = (CollectionValue) v;

        // we need recursion for the permutation of assignments of
        // range values to all element variables.
        boolean res = evalExistsOrForAll0(0, rangeVal, ctx, doExists);
        return BooleanValue.get(res);
    }

    private final boolean evalExistsOrForAll0(int nesting,
            CollectionValue rangeVal, EvalContext ctx, boolean doExists) {
        // loop over range elements
        Iterator collIter = rangeVal.iterator();
        boolean res = !doExists;
        // while (res != doExists && collIter.hasNext() ) {
        while (collIter.hasNext()) {
            Value elemVal = (Value) collIter.next();

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
                else
                    // don't change the result value when expression is true
                    // (exists) or
                    // false (forAll) and continue iteration
                    evalExistsOrForAll0(nesting + 1, rangeVal, ctx, doExists);
            } else {
                // evaluate predicate expression
                Value queryVal = fQueryExp.eval(ctx);

                // undefined query values default to false
                if (queryVal.isUndefined())
                    queryVal = BooleanValue.FALSE;

                // don't change the result value when expression is true
                // (exists) or
                // false (forAll) and continue iteration
                if (res != doExists
                        && ((BooleanValue) queryVal).value() == doExists)
                    res = doExists;
            }

            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
        }

        return res;
    }

    /**
     * Evaluate collect expressions.
     */
    protected final Value evalCollect(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return new UndefinedValue(type());
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        ArrayList resValues = new ArrayList();

        // loop over range elements
        Iterator collIter = rangeVal.iterator();
        while (collIter.hasNext()) {
            Value elemVal = (Value) collIter.next();

            // bind element variable to range element, if variable was
            // declared
            if (!fElemVarDecls.isEmpty())
                ctx.pushVarBinding(fElemVarDecls.varDecl(0).name(), elemVal);

            // evaluate collect expression
            Value val = fQueryExp.eval(ctx);

            // add element to result
            resValues.add(val);

            if (!fElemVarDecls.isEmpty())
                ctx.popVarBinding();
        }

        // result is collection with mapped values
        if (fRangeExp.type().isSequence())
            return new SequenceValue(fQueryExp.type(), resValues);
        else
            return new BagValue(fQueryExp.type(), resValues);
    }

    /**
     * Evaluate isUnique expression.
     */
    protected final Value evalIsUnique(EvalContext ctx) {
        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined())
            return new UndefinedValue(type());
        CollectionValue rangeVal = (CollectionValue) v;

        // collect values for finding duplicates
        Set values = new HashSet();

        // loop over range elements
        Iterator collIter = rangeVal.iterator();
        while (collIter.hasNext()) {
            Value elemVal = (Value) collIter.next();

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
            return new UndefinedValue(type());
        CollectionValue rangeVal = (CollectionValue) v;

        ArrayList keyValList = new ArrayList();

        // loop over range elements
        Iterator collIter = rangeVal.iterator();
        while (collIter.hasNext()) {
            Value elemVal = (Value) collIter.next();

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
        Collections.sort(keyValList, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((KeyValPair) o1).fKey.compareTo(((KeyValPair) o2).fKey);
            }
        });

        // drop the keys from the list
        ListIterator listIter = keyValList.listIterator();
        while (listIter.hasNext()) {
            KeyValPair kvp = (KeyValPair) listIter.next();
            listIter.set(kvp.fElem);
        }

        Type rangeElemType = ((CollectionType) fRangeExp.type()).elemType();
        return new SequenceValue(rangeElemType, keyValList);
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
    public abstract String name();

    public String toString() {
        String res = fRangeExp + "->" + this.name() + "(";
        if (!fElemVarDecls.isEmpty()) {
            res += fElemVarDecls + " | ";
        }
        res += fQueryExp + ")";
        return res;
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

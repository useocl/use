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

import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/** 
 * OCL iterate expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ExpIterate extends ExpQuery {
    private VarInitializer fAccuInitializer;

    /**
     * Constructs an iterate expression.
     */
    public ExpIterate(VarDeclList elemVarDecls,
                      VarInitializer accuInitializer,
                      Expression rangeExp,
                      Expression queryExp) 
        throws ExpInvalidException
    {
        // result type is type of accumulator
        super(accuInitializer.type(), elemVarDecls, rangeExp, queryExp);
        fAccuInitializer = accuInitializer;

        if (elemVarDecls.containsName(accuInitializer.name()) ) 
            throw new ExpInvalidException("Redefinition of variable `" + 
                                          accuInitializer.name() + "'.");
    
        // iterExp must be type conform to accuExp
        if (! queryExp.type().conformsTo(accuInitializer.type()) )
            throw new ExpInvalidException(
                                          "Iteration expression type `" + queryExp.type() +
                                          "' does not match accumulator type `" + 
                                          accuInitializer.type() + "'.");
    }

    /**
     * Constructs an iteration expression with one element variable.
     */
    public ExpIterate(VarDecl elemVarDecl,
                      VarInitializer accuInitializer,
                      Expression rangeExp, 
                      Expression queryExp) 
        throws ExpInvalidException
    {
        this(new VarDeclList(elemVarDecl), accuInitializer,
             rangeExp, queryExp);
    }

    /** 
     * Return name of query expression.
     */
    public String name() {
        return "iterate";
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        fRangeExp.toString(sb);
        sb.append("->")
          .append(this.name())
          .append("(");
        
        if (! fElemVarDecls.isEmpty() ) {
            fElemVarDecls.toString(sb);
            sb.append("; ");
        }
        
        fAccuInitializer.toString(sb);
        sb.append(" | ");
        fQueryExp.toString(sb);
        return sb.append(")");
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;

        // evaluate range
        Value v = fRangeExp.eval(ctx);
        if (v.isUndefined() ) {
        	ctx.exit(this, UndefinedValue.instance);
            return UndefinedValue.instance;
        }
        CollectionValue rangeVal = (CollectionValue) v;

        // prepare result value
        Value accuVal = fAccuInitializer.initExpr().eval(ctx);

        // we need recursion for the permutation of assignments of
        // range values to all element variables.
        res = eval0(ctx, 0, rangeVal, accuVal);
        ctx.exit(this, res);
        return res;
    }

    private final Value eval0(EvalContext ctx, 
                              int nesting, 
                              CollectionValue rangeVal,
                              Value accuVal)
    {
        // loop over range elements
        for (Value elemVal : rangeVal) {

            // bind element variable to range element, if variable was
            // declared
            if (! fElemVarDecls.isEmpty() )
                ctx.pushVarBinding(fElemVarDecls.varDecl(nesting).name(), elemVal);

            if (! fElemVarDecls.isEmpty() && nesting < fElemVarDecls.size() - 1) {
                // call recursively to iterate over range while
                // assigning each value to each element variable
                // eventually
                accuVal = eval0(ctx, nesting + 1, rangeVal, accuVal);
            } else {
                // bind accumulator variable
                ctx.pushVarBinding(fAccuInitializer.name(), accuVal);

                // evaluate iterate expression and assign new accumulator value
                accuVal = fQueryExp.eval(ctx);

                ctx.popVarBinding();
            }
        
            if (! fElemVarDecls.isEmpty() )
                ctx.popVarBinding();
        }
        return accuVal;
    }

    public VarInitializer getAccuInitializer() {
        return fAccuInitializer;
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitIterate(this);
	}
}


/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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


package org.tzi.use.gen.model;

import org.tzi.use.gen.assl.dynamics.IGCollector;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Knows whether a class invariant is activated or negated.
 * @author  Joern Bohling
 */
public class GFlaggedInvariant implements Cloneable {
    private boolean fDisabled;
    
    private boolean fNegated;
    
    private MClassInvariant fClassInvariant;
    
    private Evaluator fEvaluator;
    
    private boolean checkedByBarrier;
    
    public GFlaggedInvariant(MClassInvariant inv) {
        fDisabled = false;
        fNegated = false;
        fClassInvariant = inv;
        checkedByBarrier = false;
        fEvaluator = new Evaluator();
    }
    
    public Object clone() {
        try {
            GFlaggedInvariant shallowCopy = (GFlaggedInvariant)super.clone();
            // fEvaluator can be reused?
            fEvaluator = new Evaluator();
            
            shallowCopy.setDisabled(fDisabled);
            shallowCopy.setNegated(fNegated);
            shallowCopy.setCheckedByBarrier(checkedByBarrier);
            
            return shallowCopy;
        } catch (CloneNotSupportedException e) {
            // unexpected!
            throw new Error(e);
        }
    }

    public boolean eval(IGCollector collector, MSystemState systemState) {
        if (fDisabled) {
            return true;
        }
        
        
        Value value = fEvaluator.eval( fClassInvariant.expandedExpression(),
                                       systemState );
        
        return value.isDefined() && ((BooleanValue) value).isFalse() == fNegated;
    }

    public void setDisabled( boolean b ) {
        fDisabled = b;
    }
    
    public void setNegated( boolean b ) {
        fNegated = b;
    }

    public boolean disabled() {
        return fDisabled;
    }
    
    public boolean negated() {
        return fNegated;
    }    

    public String toString() {
        return fClassInvariant.toString()
            + (fNegated  ? " (negated)"  : "")
            + (fDisabled ? " (disabled)" : "");
    }

    public MClassInvariant classInvariant() {
        return fClassInvariant;
    }

	/**
	 * @return
	 */
	public Expression getFlaggedExpression() {
		Expression invExpr = fClassInvariant.expandedExpression();
		
		if (this.negated()) {
			try {
				invExpr = ExpStdOp.create("not", new Expression[] {invExpr});
			} catch (ExpInvalidException e) {}
		} else if (this.disabled()) {
			return new ExpressionWithValue(BooleanValue.TRUE);
		}
		
		return invExpr;
	}

	/**
	 * <code>true</code> if this invariant is validated by
	 * an automatically placed barrier. So it can be ignored
	 * by the last check.
	 * @return the checkedByBarrier
	 */
	public boolean isCheckedByBarrier() {
		return checkedByBarrier;
	}

	/**
	 * @param checkedByBarrier the checkedByBarrier to set
	 */
	public void setCheckedByBarrier(boolean checkedByBarrier) {
		this.checkedByBarrier = checkedByBarrier;
	}
}

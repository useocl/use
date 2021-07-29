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

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.TupleValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.BufferedToString;
import org.tzi.use.util.StringUtil;

/**
 * Constant tuple literal.
 *
 * @author  Mark Richters
 * @author  Lars Hamann  
 */
public final class ExpTupleLiteral extends Expression {
    private Part[] fParts;

    public static class Part implements BufferedToString {
        private String fName;
        private Expression fExpr;
        // Maybe a type was given by the user
        private Type givenType = null;
        
        public Part(String name, Expression expr) {
            fName = name;
            fExpr = expr;
        }

        public Part(String name, Expression expr, Type givenType) {
            this(name, expr);
            this.givenType = givenType;
        }
        
        public String getName(){
        	return fName;
        }
        
        public Expression getExpression() {
        	return fExpr;
        }
        
        /**
         * Returns the type of the TuplePart. Either the one given by the user
         * or if not given the type of the expression. 
         * @return
         */
        public Type getType() {
        	if (givenType == null)
        		return fExpr.type();
        	else
        		return givenType;
        }
        
        @Override
        public String toString() {
        	return this.toString(new StringBuilder()).toString();
        }
        
        @Override
        public StringBuilder toString(StringBuilder sb) {
            return sb.append(fName).append(":").append(fExpr);
        }
    }

    public ExpTupleLiteral(Part[] parts) {
        // set result type later
        super(null);

        // determine tuple type
        fParts = parts;
        TupleType.Part[] typeParts = new TupleType.Part[fParts.length];
        Expression[] childExpressions = new Expression[fParts.length];
        
        for (int i = 0; i < fParts.length; i++) {
        	 typeParts[i] = new TupleType.Part(i, fParts[i].fName, fParts[i].getType());
        	 childExpressions[i] = fParts[i].fExpr;
        }
        
        setResultType(TypeFactory.mkTuple(typeParts));
    }

    /**
     * The parts of this tuple literal
     * @return
     */
    public Part[] getParts() {
    	return fParts;
    }
    
    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;
        List<TupleValue.Part> parts = new ArrayList<TupleValue.Part>(fParts.length);
        
        for (int i = 0; i < fParts.length; i++) {
            parts.add(new TupleValue.Part(i, fParts[i].fName, fParts[i].fExpr.eval(ctx)));
        }
        
        res = new TupleValue((TupleType) type(), parts);
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Tuple {");
        StringUtil.fmtSeqBuffered(sb, fParts, ",");
        return sb.append("}");
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#processWithVisitor(org.tzi.use.uml.ocl.expr.ExpressionVisitor)
	 */
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitTupleLiteral(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.expr.Expression#childExpressionRequiresPreState()
	 */
	@Override
	protected boolean childExpressionRequiresPreState() {
		for (Part p : fParts) {
			if (p.fExpr.requiresPreState())
				return true;
		}
		return false;
	}
}
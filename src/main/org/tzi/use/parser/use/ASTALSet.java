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
package org.tzi.use.parser.use;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.al.ALAction;
import org.tzi.use.uml.al.ALSet;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * @author green
 */
public class ASTALSet extends ASTALAction {

    private ASTExpression fLValue;
    private ASTExpression fRValue;
    
    public ASTALSet(ASTExpression lValue, ASTExpression rValue) {
        fLValue = lValue;
        fRValue = rValue;
    }

    public ALAction gen(Context ctx) throws SemanticException {
        Expression lValue = fLValue.gen(ctx);
        Expression rValue = fRValue.gen(ctx);
        if (lValue instanceof ExpAttrOp) {
            ExpAttrOp op = (ExpAttrOp)lValue;
            checkTypeConformance(lValue, rValue);
            return new ALSet(op, rValue);    
        } else if (lValue instanceof ExpVariable) {
            ExpVariable v = (ExpVariable)lValue;
            checkVariableIsDeclared(ctx, v);
            checkTypeConformance(lValue, rValue);
            return new ALSet(v, rValue);
        } else {
            throw new SemanticException(fLValue.getStartToken(), "No l-value");
        }
        
        
        
        
    }

    private void checkVariableIsDeclared(Context ctx, ExpVariable v) throws SemanticException {
        if (ctx.varTable().lookup(v.getVarname()) == null) {
            throw new SemanticException(fLValue.getStartToken(), "No such variable");
        }
    }

    private void checkTypeConformance(Expression lValue, Expression rValue) throws SemanticException {
        // check type conformance of assignment
        if (! rValue.type().isSubtypeOf(lValue.type()) ) {
            throw new SemanticException(fRValue.getStartToken(), 
                                        "Type mismatch in assignment expression. " +
                                        "Expected type `" + lValue.type() + "', found `" +
                                        rValue.type() + "'.");
        }
    }
}

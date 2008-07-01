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

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.al.ALAction;
import org.tzi.use.uml.al.ALSetCreate;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * @author green
 */
public class ASTALSetCreate extends ASTALAction {

    private ASTExpression fLValue;
    private Token fCls;
    private ASTExpression fNameExpr;
    
    public ASTALSetCreate(ASTExpression lValue, Token cls, ASTExpression nameExpr) {
        fLValue = lValue;
        fCls = cls;
        fNameExpr = nameExpr;
    }

    public ALAction gen(Context ctx) throws SemanticException {
        Expression lValue = fLValue.gen(ctx);
        MClass cls = ctx.model().getClass(fCls.getText());
        if (cls==null) 
            throw new SemanticException(fCls, "No such class: " + fCls.getText());

        Expression nameExpr = null;
        if (fNameExpr!=null) {
            nameExpr = fNameExpr.gen(ctx);
            if (!nameExpr.type().toString().equals("String")) 
                throw new SemanticException(fNameExpr.getStartToken(),
                                            "object name hints must be string values");
        }
        if (lValue instanceof ExpAttrOp) {
            ExpAttrOp op = (ExpAttrOp)lValue;
            checkTypeConformance(lValue, cls);
            return new ALSetCreate(op, cls, nameExpr);    
        } else if (lValue instanceof ExpVariable) {
            ExpVariable v = (ExpVariable)lValue;
            checkVariableIsDeclared(ctx, v);
            checkTypeConformance(lValue, cls);
            return new ALSetCreate(v, cls, nameExpr);
        } else {
            throw new SemanticException(fLValue.getStartToken(), "No l-value");
        }
    }

    private void checkVariableIsDeclared(Context ctx, ExpVariable v) throws SemanticException {
        if (ctx.varTable().lookup(v.getVarname()) == null) {
            throw new SemanticException(fLValue.getStartToken(), "No such variable");
        }
    }

    private void checkTypeConformance(Expression lValue, MClass cls) throws SemanticException {
        // check type conformance of assignment
        Type clsType = TypeFactory.mkObjectType(cls);
        if (! clsType.isSubtypeOf(lValue.type())) {
            throw new SemanticException(fCls, 
                                        "Type mismatch in assignment expression. " +
                                        "Expected type `" + lValue.type() + "', found `" +
                                        clsType + "'.");
        }
    }
}

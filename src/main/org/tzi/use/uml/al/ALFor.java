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
package org.tzi.use.uml.al;

import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemException;

/**
 * A 'for each' loop. 
 * @author green
 */
public class ALFor extends ALAction {

    private String fVariable;
    private Type fVariableType;
    private Expression fExpression;
    private ALAction fBody;

    public ALFor(String varname, Type type, Expression expr, ALAction body) {
        fVariable = varname;
        fVariableType = type;
        fExpression = expr;
        fBody = body;
    }

    public String getVariableName() {
        return fVariable;
    }
    
    public Type getVariableType() {
        return fVariableType;
    }
    
    public Expression getRangeExpression() {
        return fExpression;
    }
    
    public ALAction getBody() {
        return fBody;
    }

    public void exec(EvalContext ctx) throws MSystemException {
        Value v = fExpression.eval(ctx);
        assert v.isCollection() : "expression in for loop must be a collection";
        CollectionValue col = (CollectionValue)v;

        for (Value elem : col) {
            ctx.createStackFrame();
            // TODO: type check against fVariableType
            ctx.pushVarBinding(fVariable,elem);
            fBody.exec(ctx);
            ctx.dropStackFrame();
        }
    }

    public String toString() {
        return "for " + fVariable + " : " + fVariableType + " in " + fExpression + " do " + fBody + " next";
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALFor(this);
    }

}

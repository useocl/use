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
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.al.ALAction;
import org.tzi.use.uml.al.ALActionList;
import org.tzi.use.uml.al.ALFor;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;

import antlr.Token;

/**
 * @author green
 */
public class ASTALFor extends ASTALAction {

    private MyToken fVariable;
    private ASTType fVariableType;
    private ASTExpression fExpression;
    private ASTALActionList fBody;

    public ASTALFor(Token var, ASTType type, ASTExpression expr, ASTALActionList body) {
        fVariable = (MyToken)var;
        fVariableType = type;
        fExpression = expr;
        fBody = body;
    }

    public ALAction gen(Context ctx) throws SemanticException {
        Type type = fVariableType.gen(ctx);
        Expression expr = fExpression.gen(ctx);
        if (!expr.type().isCollection()) {
            throw new SemanticException(fExpression.getStartToken(),
                    "Range expression must be of collection type but is "
                            + expr.type() + ".");
        }
        Type elemType = ((CollectionType) expr.type()).elemType();
        if (!elemType.isSubtypeOf(type)) {
            throw new SemanticException(fVariable, "Variable "
                    + fVariable.getText() + " must be a subtype " + "of "
                    + elemType + " but is " + type + ".");
        }
        ctx.varTable().enterScope();
        ctx.varTable().add(fVariable,type);
        ALActionList body = (ALActionList) fBody.gen(ctx);
        ctx.varTable().exitScope();
        return new ALFor(fVariable.getText(), type, expr, body);
    }

}

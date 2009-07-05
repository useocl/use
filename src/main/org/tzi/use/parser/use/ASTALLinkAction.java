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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * @author green
 */
public abstract class ASTALLinkAction extends ASTALAction {

    protected List<ASTExpression> fExpressions;
    protected Token fAssociation;

    public ASTALLinkAction(List<ASTExpression> exprList, Token assoc) {
        fExpressions = exprList;
        fAssociation = assoc;
    }

    protected MAssociation getAssociation(Context ctx) throws SemanticException {
        MAssociation assoc = ctx.model().getAssociation(fAssociation.getText());
        if (assoc == null )
            throw new SemanticException(fAssociation, "Association `" + 
                                        fAssociation.getText() + "' does not exist.");
    
        if (assoc.associationEnds().size() != fExpressions.size() )
            throw new SemanticException(fAssociation, "A link for association `" + 
                                        fAssociation.getText() + "' requires " + assoc.associationEnds().size() +
                                        " objects, found " + fExpressions.size() + ".");
        return assoc;
    }

    protected List<Expression> getLinkElements(Context ctx, MAssociation assoc) throws SemanticException {
        // generate expressions
        List<Expression> expressions = new ArrayList<Expression>();
        int index = 0;
        for (ASTExpression ast : fExpressions) {
            Expression exp = ast.gen(ctx);
            MAssociationEnd end = (MAssociationEnd) assoc.associationEnds().get(index);
            ObjectType t = TypeFactory.mkObjectType(end.cls());
            if (!exp.type().isSubtypeOf(t)) {
                throw new SemanticException(ast.getStartToken(), "Type mismatch for argument #"
                        + (index+1) + " of insert action: " + t + " is not a " 
                        + " subtype of " + exp.type() + ".");
            }
            expressions.add(exp);
            index++;
        }
        return expressions;
    }

}

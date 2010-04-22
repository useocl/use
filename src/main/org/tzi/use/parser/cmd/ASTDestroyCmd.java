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

package org.tzi.use.parser.cmd;

import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdDestroyObjects;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author		Mark Richters
 */
public class ASTDestroyCmd extends ASTCmd {
    private List<ASTExpression> fExprList;
    
    public ASTDestroyCmd(Token start, List<ASTExpression> exprList) {
    	super(start);
        fExprList = exprList;
    }

    public MCmd gen(Context ctx) throws SemanticException {
        // generate expressions
        Expression[] exprs = new Expression[fExprList.size()];

        int i = 0;
        for (ASTExpression astExpr : fExprList) {
            Expression expr = astExpr.gen(ctx);
            Type t = expr.type();
            if (t.isTrueObjectType() 
                || ( t.isCollection(true) && ((CollectionType) t).elemType().isTrueObjectType()) ) {
                exprs[i++] = expr;
            } else {
                throw new SemanticException(astExpr.getStartToken(), 
                                            "Expected expression of object or collection type, " +
                                            "found `" + expr.type() + "'."); 
            }   
        }
        return new MCmdDestroyObjects(getPosition(), ctx.systemState(), exprs); 
    }

	@Override
	public String toString() {
		return "destroy";
	}
}

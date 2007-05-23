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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.parser.cmd;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdLet;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTLetCmd extends ASTCmd {
    private MyToken fVar;
    private ASTType fType; // (may be null)
    private ASTExpression fExpr;

    public ASTLetCmd(MyToken var, ASTType type, ASTExpression expr) {
        fVar = var;
        fType = type;
        fExpr = expr;
    }

    public MCmd gen(Context ctx) throws SemanticException {
        MCmdLet res = null;
        Expression expr = fExpr.gen(ctx);
        Type type = null;
        if (fType != null ) {
            type = fType.gen(ctx);
            if (! expr.type().isSubtypeOf(type) )
                throw new SemanticException(fExpr.getStartToken(), 
                                            "Type of expression does not match declaration. " + 
                                            "Expected `" + type + 
                                            "', found `" + expr.type() + "'.");
        }
        res = new MCmdLet(ctx.systemState(), fVar.getText(), 
                          type, expr);
        return res;
    }
}

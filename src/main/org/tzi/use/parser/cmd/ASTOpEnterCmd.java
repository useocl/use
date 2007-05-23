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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdOpEnter;
import org.tzi.use.uml.sys.MOperationCall;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTOpEnterCmd extends ASTCmd {
    private ASTExpression fSrcExpr;
    private MyToken fOp;
    private List fArgs;     // (ASTExpression) 

    public ASTOpEnterCmd(ASTExpression source, MyToken op) {
        fSrcExpr = source;
        fOp = op;
        fArgs = new ArrayList();
    }

    public void addArg(ASTExpression arg) {
        fArgs.add(arg);
    }

    public MCmd gen(Context ctx) throws SemanticException {
        MCmdOpEnter res = null;

        // source of operation call must denote object
        Expression objExp = fSrcExpr.gen(ctx);
        Type t = objExp.type();
        if (! t.isObjectType() )
            throw new SemanticException(fSrcExpr.getStartToken(), 
                                        "Expected expression with object type, " + 
                                        "found type `" + t + "'.");

        MClass cls = ((ObjectType) t).cls();

        // find operation
        String opname = fOp.getText();
        MOperation op = cls.operation(opname, true);
        if (op == null )
            throw new SemanticException(fOp, "No operation `" + opname +
                                        "' found in class `" + cls.name() + "'.");

        VarDeclList params = op.paramList();
        if (params.size() != fArgs.size() )
            throw new SemanticException(fOp, 
                                        "Number of arguments does not match declaration of operation `" + 
                                        opname + "' in class `" + cls.name() + "'. Expected " +
                                        params.size() + " argument" +
                                        ( params.size() == 1 ? "" : "s" ) + ", found " + fArgs.size() + ".");

        // generate argument expressions
        Expression[] argExprs = new Expression[fArgs.size()];
        Iterator it = fArgs.iterator();
        int i = 0;
        while (it.hasNext() ) {
            ASTExpression astExpr = (ASTExpression) it.next();
            argExprs[i] = astExpr.gen(ctx);
            if (! argExprs[i].type().isSubtypeOf(params.varDecl(i).type()) )
                throw new SemanticException(fOp, "Type mismatch in argument " + i +
                                            ". Expected type `" + params.varDecl(i).type() + "', found `" +
                                            argExprs[i].type() + "'.");    
            i++;
        }

        MOperationCall opcall = new MOperationCall(objExp, op, argExprs);
        res = new MCmdOpEnter(ctx.systemState(), opcall);
        return res;
    }
}

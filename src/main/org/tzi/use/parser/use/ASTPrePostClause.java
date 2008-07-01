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

package org.tzi.use.parser.use;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTPrePostClause extends AST {
	Token fToken;     // pre or post
	Token fName;      // optional
    ASTExpression fExpr;

    public ASTPrePostClause(Token tok, Token name, ASTExpression e) {
        fToken = tok;
        fName = name;
        fExpr = e;
    }

    void gen(Context ctx, MClass cls, MOperation op) {
        boolean isPre = fToken.getText().equals("pre");

        // enter context variable into scope of invariant
        ObjectType ot = TypeFactory.mkObjectType(cls);
        Symtable vars = ctx.varTable();
        vars.enterScope();

        try {
            // create pseudo-variable "self"
            vars.add("self", ot, null);
            ctx.exprContext().push("self", ot);
            // add special variable `result' in postconditions with result value
            if (! isPre && op.hasResultType() )
                vars.add("result", op.resultType(), null);

            ctx.setInsidePostCondition(! isPre);
            Expression expr = fExpr.gen(ctx);
            ctx.setInsidePostCondition(false);

            String ppcName = null;
            if (fName != null )
                ppcName = fName.getText();
            
            MPrePostCondition ppc = 
                ctx.modelFactory().createPrePostCondition(ppcName, op, 
                                                          isPre, expr);
            if ( ppc != null && fName != null ) {
                ppc.setPositionInModel( fName.getLine() );
            }
            ctx.model().addPrePostCondition(ppc);
        } catch (MInvalidModelException ex) {
            ctx.reportError(fExpr.getStartToken(), ex);
        } catch (ExpInvalidException ex) {
            ctx.reportError(fExpr.getStartToken(), ex);
        } catch (SemanticException ex) {
            ctx.reportError(ex);
        }
        vars.exitScope(); 
        ctx.exprContext().pop();
    }
}

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

package org.tzi.use.parser.ocl;

import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ExprContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.base.ParserHelper;
import org.tzi.use.uml.ocl.expr.ExpAny;
import org.tzi.use.uml.ocl.expr.ExpClosure;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpIsUnique;
import org.tzi.use.uml.ocl.expr.ExpOne;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.ExpSortedBy;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTQueryExpression extends ASTExpression {
    private Token fOp;
    private ASTExpression fRange; // may be null
    private ASTElemVarsDeclaration fDeclList;
    private ASTExpression fExpr;

    public ASTQueryExpression(Token op, 
                              ASTExpression range, 
                              ASTElemVarsDeclaration declList,
                              ASTExpression expr) {
        fOp = op;
        fRange = range;
        fDeclList = declList;
        fExpr = expr;
    }

    public Expression gen(Context ctx) throws SemanticException {
        String opname = fOp.getText();
        Expression res = null;
        Expression range, expr;

        // check for empty range: do we have a context expression that
        // is implicitly assumed to be the source expression?
        if (fRange != null ) {
            range = fRange.gen(ctx);
       } else {
           ExprContext ec = ctx.exprContext();
            if (! ec.isEmpty() ) {
                // construct source expression
                ExprContext.Entry e = ec.peek();
                range = new ExpVariable(e.fName, e.fType);
            } else
                throw new SemanticException(fOp, "Need a collection to apply `" +
                                            opname + "'.");
        }

        if (!range.type().isKindOfCollection(VoidHandling.INCLUDE_VOID))
            throw new SemanticException(fOp, 
                                        "Source of `" + opname + "' expression must be a collection, " + 
                                        "found source expression of type `" + range.type() + "'.");

        VarDeclList declList = new VarDeclList(true);

        if (fDeclList.isEmpty() ) {
            // when there are no explicit var decls, we declare an
            // internal variable with the element type
            ExprContext ec = ctx.exprContext();
            CollectionType ct = (CollectionType) range.type();
            String var = ec.push(ct.elemType());
            expr = fExpr.gen(ctx);
            ec.pop();

            // use the generated element variable
            VarDecl decl = new VarDecl(var, ct.elemType());
            declList.add(decl);
        } else {
            declList = fDeclList.gen(ctx, range);

            // enter declared variable into scope before evaluating
            // the argument expression
            Symtable vars = ctx.varTable();
            vars.enterScope();
            declList.addVariablesToSymtable(vars);
            expr = fExpr.gen(ctx);
            vars.exitScope(); 
        }

        try {
            Integer id = ParserHelper.queryIdentMap.get(opname);
            if (id == null )
                throw new SemanticException(fOp,
                                            "Internal error: unknown query operation `" +
                                            opname + "'.");

            int idval = id.intValue();
            switch ( idval ) {
            case ParserHelper.Q_SELECT_ID:
            case ParserHelper.Q_COLLECT_ID:
            case ParserHelper.Q_COLLECTNESTED_ID:
            case ParserHelper.Q_REJECT_ID:
            case ParserHelper.Q_ISUNIQUE_ID:
            case ParserHelper.Q_SORTEDBY_ID:
            case ParserHelper.Q_ANY_ID:
            case ParserHelper.Q_CLOSURE_ID:
                VarDecl decl;
                if (declList.isEmpty() )
                    decl = null;
                else if (declList.size() == 1 )
                    decl = declList.varDecl(0);
                else
                    throw new SemanticException(fOp, "Only one element variable in " + 
                                                opname + " expression allowed.");
                switch ( idval ) {
                case ParserHelper.Q_SELECT_ID:
                    res = new ExpSelect(decl, range, expr);
                    break;
                case ParserHelper.Q_COLLECTNESTED_ID:
                    res = new ExpCollectNested(decl, range, expr);
                    break;
                case ParserHelper.Q_COLLECT_ID:
                    res = new ExpCollect(decl, range, expr);
                    break;
                case ParserHelper.Q_REJECT_ID:
                    res = new ExpReject(decl, range, expr);
                    break;
                case ParserHelper.Q_ISUNIQUE_ID:
                    res = new ExpIsUnique(decl, range, expr);
                    break;
                case ParserHelper.Q_SORTEDBY_ID:
                    res = new ExpSortedBy(decl, range, expr);
                    break;
                case ParserHelper.Q_ANY_ID:
                    res = new ExpAny(decl, range, expr);
                    break;
                case ParserHelper.Q_CLOSURE_ID:
                    res = new ExpClosure(decl, range, expr);
                    break;
                default:
                    // TODO: ignore or error on default?
                }
                break;
            case ParserHelper.Q_ONE_ID:
            	res = new ExpOne(declList, range, expr);
                break;            	
            case ParserHelper.Q_EXISTS_ID:
                res = new ExpExists(declList, range, expr);
                break;
            case ParserHelper.Q_FORALL_ID:
                res = new ExpForAll(declList, range, expr);
                break;
            default:
                // internal error
                throw new SemanticException(fOp,
                                            "Internal error: unknown query operation `" +
                                            opname + "'.");
            }
        } catch (ExpInvalidException ex) {
            throw new SemanticException(fOp, ex);
        }
        return res;
    }

    @Override
	public void getFreeVariables(Set<String> freeVars) {
		if (fRange != null) {
			fRange.getFreeVariables(freeVars);
		}
	
		Set<String> freeVarsInExpr = fExpr.getFreeVariables();
		freeVarsInExpr.removeAll(fDeclList.getVarNames());
	
		freeVars.addAll(freeVarsInExpr);	
	}

    @Override
	public String toString() {
	    return "(" + fOp + " " + fRange + " " + fDeclList + " " + fExpr + ")";
	}
}

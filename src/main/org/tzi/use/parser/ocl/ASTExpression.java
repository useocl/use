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

package org.tzi.use.parser.ocl;

import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.ExpCollect;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpNavigation;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public abstract class ASTExpression extends AST {
    protected static final String MSG_DISABLE_COLLECT_SHORTHAND = 
        "The OCL shorthand notation for collect has been disabled. " +
        "Try `use -h' for help on enabling it.";

    // first token of expression useful for error reporting
    private MyToken fStartToken; 

    private boolean fIsPre;

    public void setIsPre() {
        fIsPre = true;
    }

    public boolean isPre() {
        return fIsPre;
    }


    public void setStartToken(MyToken pos) {
        fStartToken = pos;
    }

    public MyToken getStartToken() {
        return fStartToken;
    }


    public abstract Expression gen(Context ctx) throws SemanticException;


    /**
     * Generates a predefined standard operation expression.
     */
    protected Expression genStdOperation(Context ctx,
                                         MyToken token, 
                                         String opname,
                                         Expression[] args) 
        throws SemanticException
    {
        Expression res = null;
        try {
            // lookup operation
            res = ExpStdOp.create(opname, args);
        } catch (ExpInvalidException ex) {
            throw new SemanticException(token, ex);
        }
        return res;
    }

    /**
     * Generates a predefined standard operation expression.
     */
    protected Expression genStdOperation(Context ctx,
                                         MyToken token, 
                                         String opname,
                                         ASTExpression[] args) 
        throws SemanticException
    {
        Expression res;
        Expression[] expargs = new Expression[args.length];
        for (int i = 0; i < args.length; i++)
            expargs[i] = args[i].gen(ctx);
        return genStdOperation(ctx, token, opname, expargs);
    }

    protected Expression genStdOperation(Context ctx,
                                         MyToken token, 
                                         String opname,
                                         List args) 
        throws SemanticException
    {
        ASTExpression[] exparr = new ASTExpression[args.size()];
        System.arraycopy(args.toArray(), 0, exparr, 0, args.size());
        return genStdOperation(ctx, token, opname, exparr);
    }

    protected Expression genNavigation( MyToken rolenameToken,
                                        MClass srcClass,
                                        Expression srcExpr,
                                        MNavigableElement dst ) 
            throws SemanticException {
        return genNavigation( rolenameToken, srcClass, srcExpr, dst, null );
    }

    protected Expression genNavigation(MyToken rolenameToken,
                                       MClass srcClass,
                                       Expression srcExpr,
                                       MNavigableElement dst,
                                       MyToken explicitRolenameToken )
        throws SemanticException
    {
        Expression res = null;

        // find source end
        MAssociation assoc = dst.association();
        Iterator it = assoc.reachableEnds().iterator();
        MNavigableElement src = null;
        while (it.hasNext() ) {
            MNavigableElement nav = (MNavigableElement) it.next();
            if (! nav.equals( dst ) ) {
                if (srcClass.isSubClassOf(nav.cls()) ) {
                    if ( explicitRolenameToken != null ) {
                        if ( ! (nav.nameAsRolename().equals( explicitRolenameToken.getText() )) ) {
                            continue;
                        }
                    }
                    if (src != null ) {
                        // if already set, the navigation path is not unique
                        throw new SemanticException(rolenameToken,
                                                    "The navigation path is ambiguous. " +
                                                    "A qualification of the source association is required.");
                    }
                    src = nav;
                }
            }
        }
        if ( src == null && explicitRolenameToken != null ) {
            throw new SemanticException( explicitRolenameToken,
                                         "Identifier `" + explicitRolenameToken.getText() + "' is not a correct"
                                         + " rolename which is associated with `" + srcClass.name() + "'" );
        }
        if ( src == null )
            throw new SemanticException(rolenameToken,
                                        "Identifier `" + rolenameToken.getText() +
                                        "' is not a role name.");
        try { 
            res = new ExpNavigation(srcExpr, src, dst);
        } catch (ExpInvalidException ex) {
            throw new SemanticException(rolenameToken, ex);
        }
        return res;
    }

    /**
     * Transforms an expression <code>$e.foo</code> into an expression
     * <code>c->collect($e | $e.foo)</code> or <code>c->collect($e |
     * $e.foo)->flatten</code> if the result of the collect is a
     * nested collection.
     *
     * @param srcExpr the source collection
     * @param expr the argument expression for collect
     * @param elemType type of elements of the source collection 
     */
    protected Expression genImplicitCollect(Expression srcExpr, 
                                            Expression expr, 
                                            Type elemType) 
    {
        Expression res = null;
        try {
            ExpCollect eCollect = 
                new ExpCollect(new VarDecl("$e", elemType), srcExpr, expr);
            res = eCollect;
        
            // is result a nested collection?
            if (res.type().isCollection() ) {
                CollectionType t = (CollectionType) res.type();
                if (t.elemType().isCollection() ) {
                    // add flatten
                    Expression[] args = { res };
                    res = ExpStdOp.create("flatten", args);
                }
            }
        } catch (ExpInvalidException ex) {
            throw new RuntimeException("genImplicitCollect failed: " + ex.getMessage());
        }
        return res;
    }
}

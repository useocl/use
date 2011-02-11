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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.expr.ExpCollectNested;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpNavigation;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

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
    private Token fStartToken; 

    private boolean fIsPre;
    
    // the text that was parsed to build this expression
    // is not set automatically while parsing at the moment
    private String fStringRep;
      
    
    public String getStringRep() {
    	// TODO: Sollte eigentlich nicht passieren
    	if (fStringRep == null) return "<no string representation>";
    	return fStringRep;
    }
    
    public void setStringRep(String stringRep) {
    	fStringRep = stringRep.trim().replaceAll("  ", " ");
    }

    public void setIsPre() {
        fIsPre = true;
    }

    public boolean isPre() {
        return fIsPre;
    }


    public void setStartToken(Token pos) {
        fStartToken = pos;
    }

    public Token getStartToken() {
        return fStartToken;
    }

    public abstract Expression gen(Context ctx) throws SemanticException;
        
    /**
     * TODO
     * @param freeVars
     */
    public abstract void getFreeVariables(HashSet<String> freeVars);
    
    /**
     * TODO
     * @return
     */
    public HashSet<String> getFreeVariables() {
    	HashSet<String> result = new HashSet<String>();
    	getFreeVariables(result);
    	return result;
    }


    /**
     * Generates a predefined standard operation expression.
     */
    protected Expression genStdOperation(Context ctx,
    									 Token token, 
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
    									 Token token, 
                                         String opname,
                                         ASTExpression[] args) 
        throws SemanticException
    {
        Expression[] expargs = new Expression[args.length];
        
        for (int i = 0; i < args.length; i++)
            expargs[i] = args[i].gen(ctx);
        
        return genStdOperation(ctx, token, opname, expargs);
    }

    protected Expression genStdOperation(Context ctx,
    									 Token token, 
                                         String opname,
                                         List<ASTExpression> args) 
        throws SemanticException
    {
        ASTExpression[] exparr = new ASTExpression[args.size()];
        args.toArray(exparr);
        
        return genStdOperation(ctx, token, opname, exparr);
    }

    protected Expression genNavigation( Token rolenameToken,
                                        MClass srcClass,
                                        Expression srcExpr,
                                        MNavigableElement dst ) 
            throws SemanticException {
        return genNavigation( null, rolenameToken, srcClass, srcExpr, dst, Collections.<ASTExpression>emptyList(), Collections.<ASTExpression>emptyList() );
    }

    protected Expression genNavigation(Context ctx, Token rolenameToken,
                                       MClass srcClass,
                                       Expression srcExpr,
                                       MNavigableElement dst,
                                       List<ASTExpression> explicitRolenameOrQualifiers,
                                       List<ASTExpression> qualifiers )
        throws SemanticException
    {
        Expression res = null;

        // find source end
        MNavigableElement src = null;
        
        if (navigationNeedsExplicitRolename(srcClass, dst)) {
        	if (explicitRolenameOrQualifiers.size() == 0) {
        		// an explicit rolename is needed, but not provided
                throw new SemanticException(rolenameToken,
                                            "The navigation path from " + StringUtil.inQuotes(srcClass.name()) + " to " + StringUtil.inQuotes(dst.nameAsRolename()) + " is ambiguous, " +
                                            "but no qualification of the source association was given.");
        	}
        	
        	if (explicitRolenameOrQualifiers.size() > 1) {
        		// an explicit rolename is needed, but more then one was provided
        		throw new SemanticException(rolenameToken,
                        "More then one qualification for the ambiguous navigation path from " + StringUtil.inQuotes(srcClass.name()) + " to " + StringUtil.inQuotes(dst.nameAsRolename()) +
                        " was given. May be you interchanged it with qualifier values?");
        	}
        	
        	ASTExpression explicitRolenameExp = explicitRolenameOrQualifiers.get(0);
        	
        	if (!(explicitRolenameExp instanceof ASTOperationExpression)) {
        		// Must be a OperationExpression which encapsulates an IDENT
        		throw new SemanticException(rolenameToken,
                        "Invalid qualification given");
        	}
        	
        	ASTOperationExpression explicitRolenameOpExp = (ASTOperationExpression)explicitRolenameExp;
        	Token explicitRolenameToken = explicitRolenameOpExp.getOpToken();

        	src = dst.association().getSourceEnd(srcClass, dst, explicitRolenameToken.getText());
        	
        	if ( src == null ) {
                throw new SemanticException( explicitRolenameToken,
                                             "Identifier `" + explicitRolenameToken.getText() + "' is not a correct"
                                             + " rolename which is associated with `" + srcClass.name() + "'" );
            }
        } else {
        	if (!explicitRolenameOrQualifiers.isEmpty()) {
	        	if (!qualifiers.isEmpty()) {
					throw new SemanticException(rolenameToken,
							"No qualification required for navigation path from "
									+ StringUtil.inQuotes(srcClass.name()) + " to "
									+ StringUtil.inQuotes(dst.nameAsRolename())
									+ "required!");
	        	}
	        	
	        	// Qualifiers are provided
	        	qualifiers = explicitRolenameOrQualifiers;
        	}
        	
        	src = dst.association().getSourceEnd(srcClass, dst, null);
        }
        
        List<Expression> qualifierExpressions;
        if (qualifiers.isEmpty()) {
        	qualifierExpressions = Collections.emptyList();
        } else {
        	qualifierExpressions = new ArrayList<Expression>();
        	for (ASTExpression qualifierExp : qualifiers) {
        		qualifierExpressions.add(qualifierExp.gen(ctx));
        	}
        }
                
        if ( src == null )
            throw new SemanticException(rolenameToken,
                                        "Identifier `" + rolenameToken.getText() +
                                        "' is not a role name.");
        try { 
            res = new ExpNavigation(srcExpr, src, dst, qualifierExpressions);
        } catch (ExpInvalidException ex) {
            throw new SemanticException(rolenameToken, ex);
        }
        return res;
    }

    /**
	 * True if a navigation from an object of class <code>srcClass</code> to
	 * the association end <code>dst</code> needs an explicit rolename.
	 * 
	 * Only reflexive associations with more then two reachable ends
     * can have an ambiguous path.
	 * @param srcClass The <code>MClass</code> to navigate from
	 * @param dst The <code>MNavigableElement</code> to navigate to.
	 * @return <code>true</code> if the navigation needs a rolename, otherwise <code>false</code>.
	 */
	protected boolean navigationNeedsExplicitRolename(MClass srcClass, MNavigableElement dst) {
		// Only associations with ends > 2 can have ambiguous navigation expressions.
		if (dst.association().reachableEnds().size() == 2) return false;
		
		int possibleSourceEnds = 0;
		
		for (MNavigableElement nav : dst.association().reachableEnds()) {
            if (! nav.equals( dst ) ) {
                if (srcClass.isSubClassOf(nav.cls()) ) {
                    possibleSourceEnds++;
                }
            }
        }
		
		return possibleSourceEnds > 1;
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
            ExpCollectNested eCollect = 
                new ExpCollectNested(new VarDecl("$e", elemType), srcExpr, expr);
            res = eCollect;
        
            // is result a nested collection?
            if (res.type().isCollection(true) ) {
                CollectionType t = (CollectionType) res.type();
                if (t.elemType().isCollection(true) ) {
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

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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MMultiplicity;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAssociationEnd extends AST {
    private Token fName;
    private ASTMultiplicity fMultiplicity;
    private Token fRolename;  // optional: may be null!
    private boolean fOrdered;
    private boolean isUnion = false;
    private List<Token> subsetsRolename = new ArrayList<Token>();
    private List<Token> redefinesRolenames = new ArrayList<Token>();
    /**
     * Ost for the optional derive expression
     */
    private ASTExpression derivedExpression = null;
    /**
     * Saves the generated association end for a second
     * "compile run".
     */
    private MAssociationEnd mAend;
    
    public ASTAssociationEnd(Token name, ASTMultiplicity mult) {
        fName = name;
        fMultiplicity = mult;
        fOrdered = false;
    }

    /**
     * The name of the class this association end targets
     * @return
     */
    public String getClassName()
    {
    	return fName.getText();
    }
    
    public void setRolename(Token rolename) {
        fRolename = rolename;
    }

    public Token getRolename() {
    	return fRolename;
    }

    /**
     * Returns the specified rolename or if none specified
     * computes it.
     * The existence of the class used for the default rolename is not checked.
     * @param ctx
     * @return
     */
    public String getRolename(Context ctx) {
    	if (this.fRolename != null) {
    		return fRolename.getText();
    	}
    	else
    	{
    		return ctx.model().getClass(fName.getText()).nameAsRolename();
    	}
    }
    
    public void setOrdered() {
        fOrdered = true;
    }

    public void setUnion(boolean newValue) {
    	isUnion = newValue;
    }
    
    public boolean isUnion() {
    	return isUnion;
    }
    
    public void addSubsetsRolename(Token rolename) {
    	subsetsRolename.add(rolename);
    }
    
    public List<Token> getSubsetsRolenames() {
    	return subsetsRolename;
    }
    
    public void addRedefinesRolename(Token rolename) {
    	redefinesRolenames.add(rolename);
    }
    
    public List<Token> getRedefinesRolenames() {
    	return redefinesRolenames;
    }
    
    public void setDerived(ASTExpression exp) {
    	this.derivedExpression = exp;
    }
    
    public boolean isDerived() {
    	return derivedExpression != null;
    }
    
    public MAssociationEnd gen(Context ctx, int kind) throws SemanticException {
        // lookup class at association end in current model
        MClass cls = ctx.model().getClass(fName.getText());
        if (cls == null )
            // this also renders the rest of the association useless
            throw new SemanticException(fName, "Class `" + fName.getText() +
                                        "' does not exist in this model.");
    
        MMultiplicity mult = fMultiplicity.gen(ctx);
        if (fOrdered && ! mult.isCollection() ) {
            ctx.reportWarning(fName, "Specifying `ordered' for " +
                              "an association end targeting single objects has no effect.");
            fOrdered = false;
        }
        
         mAend = ctx.modelFactory().createAssociationEnd(cls, 
        		 	getRolename(ctx), mult, kind, fOrdered);

        mAend.setUnion(this.isUnion);
        return mAend;
    }

    /**
     * If given generates the expression that is linked to 
     * this association end as a derive expression.
     * @param ctx
     */
    public void genDerived(Context ctx) throws SemanticException {
    	if (!this.isDerived()) return;
    	
		Symtable vars = ctx.varTable();
        vars.enterScope();
        Type otherType = mAend.getAllOtherAssociationEnds().get(0).cls().type();
        vars.add("self", otherType, null);
        ctx.exprContext().push("self", otherType);
    	
    	Expression exp = derivedExpression.gen(ctx);
    	
    	// We can ignore redefinition here
    	if (!exp.type().isSubtypeOf(mAend.getType())) {
    		throw new SemanticException(derivedExpression.getStartToken(), 
    				"The type " +
    				StringUtil.inQuotes(exp.type().toString()) + " of the derive expression at association end " +
    				StringUtil.inQuotes(mAend.association().toString() + "::" + getRolename(ctx)) + " does not conform to the end type " + StringUtil.inQuotes(mAend.getType()) + ".");
    	}
    	
    	mAend.setDeriveExpression(exp);
    	
		ctx.varTable().exitScope();
		ctx.exprContext().pop();
    }
    
    public String toString() {
        return (fRolename == null ? "unnamed end on " + getClassName() : fRolename.getText());
    }
}

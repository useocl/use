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
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author  Mark Richters
 * @author  Lars Hamann
 */
public class ASTAttribute extends ASTAnnotatable {
    private Token fName;
    private ASTType fType;
    private ASTExpression deriveExpression;
    private ASTExpression initExpression;
    
    private MAttribute attribute = null;
    
    public ASTAttribute(Token name, ASTType type) {
        fName = name;
        fType = type;
    }

    /**
     * True if a derive expression for this attribute is specified. 
     * @return <code>true</code>, if this attribute is derived by an OCL expression.
     */
    public boolean isDerived() {
    	return deriveExpression != null;
    }
    
    public MAttribute gen(Context ctx) throws SemanticException {
        attribute = ctx.modelFactory().createAttribute(fName.getText(), fType.gen(ctx));
        // sets the line position of the USE-Model in this attribute
        attribute.setPositionInModel( fName.getLine() );
        
        this.genAnnotations(attribute);
        
        return attribute;
    }

    /**
     * If given, generates the expression that is linked to 
     * this attribute as a derive expression.
     * @param ctx
     */
    public void genDerived(Context ctx) throws SemanticException {
    	if (!this.isDerived()) return;
    	
    	Expression exp = deriveExpression.gen(ctx);
    	
    	// We can ignore redefinition here
    	if (!exp.type().conformsTo(attribute.type())) {
    		throw new SemanticException(deriveExpression.getStartToken(), 
    				"The type " +
    				StringUtil.inQuotes(exp.type().toString()) + " of the derive expression at attribute " +
    				StringUtil.inQuotes(attribute.toString()) + " does not conform to the attribute type " + 
    				StringUtil.inQuotes(attribute.type()) + ".");
    	}
    	
    	attribute.setDeriveExpression(exp);
    }
    
    /**
     * If given, generates the expression that is linked to 
     * this attribute as an initialize expression.
     * @param ctx
     */
    public void genInit(Context ctx) throws SemanticException {
    	if (this.initExpression == null) return;

		Expression exp = initExpression.gen(ctx);
		
    	// We can ignore redefinition here
    	if (!exp.type().conformsTo(attribute.type())) {
    		throw new SemanticException(initExpression.getStartToken(), 
    				"The type " +
    				StringUtil.inQuotes(exp.type().toString()) + " of the init expression at attribute " +
    				StringUtil.inQuotes(attribute.toString()) + " does not conform to the attribute type " + 
    				StringUtil.inQuotes(attribute.type()) + ".");
    	}
    	
    	attribute.setInitExpression(exp);
    }
    
    public void setDeriveExpression(ASTExpression exp) {
    	this.deriveExpression = exp;
    }
    
    public void setInitExpression(ASTExpression exp) {
    	this.initExpression = exp;
    }
    
    public Token nameToken() {
        return fName;
    }

    public String toString() {
        return "(" + fName + " " + fType + ")";
    }
}

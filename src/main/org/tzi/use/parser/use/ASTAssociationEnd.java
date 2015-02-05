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
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.ASTElemVarsDeclaration;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MMultiplicity;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAssociationEnd extends ASTAnnotatable {
    private Token fName;
    private ASTMultiplicity fMultiplicity;
    private Token fRolename;  // optional: may be null!
    private boolean fOrdered;
    private boolean isUnion = false;
    
    /**
     * List of subsetted association end names.
     * Initialized with an empty immutable collection
     * which is replaced at the first time of writing. 
     */
    private List<Token> subsetsRolename = Collections.emptyList();
    
    /**
     * List of redefined association end names.
     * Initialized with an empty immutable collection
     * which is replaced at the first time of writing. 
     */
    private List<Token> redefinesRolenames = Collections.emptyList();
    
    /**
     * List of qualifiers. Initialized with an empty immutable collection
     * which is replaced at the first time of writing. 
     */
    private List<ASTVariableDeclaration> qualifiers = Collections.emptyList();
    
    
    /**
     * Parameter declarations for the derive expression
     */
    private ASTElemVarsDeclaration deriveParameter = null;
    
    /**
     * AST for the optional derive expression
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

    /**
     * Gets the specified role name, if any.
     * Null if no role name was specified. 
     * @return The <code>Token</code> of the specified role name or <code>null</code> if no name was specified.
     */
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
    
    /**
     * Marks this and as ordered, e. g., the modifier <code>ordered</code> was specified. 
     */
    public void setOrdered() {
        fOrdered = true;
    }

    /**
     * Marks this association and as a derived union, e. g., the modifier <code>union</code> was specified. 
     */
    public void setUnion(boolean newValue) {
    	isUnion = newValue;
    }
    
    /**
     * True if this end was marked as a derived union, e. g., the modifier <code>union</code> was specified.
     * @return
     */
    public boolean isUnion() {
    	return isUnion;
    }
    
    /**
     * Adds a name to the list of association ends this end subsets. 
     * @param name The <code>Token</code> of the specified association end name this end should subset.
     */
    public void addSubsetsRolename(Token name) {
    	// Lazy initialization of the list.
    	if (subsetsRolename.size() == 0)
    		subsetsRolename = new ArrayList<Token>();
    	
    	subsetsRolename.add(name);
    }
    
    /**
     * Returns a read only list of the association end names this association end subsets.
     * @return Unmodifiable <code>List</code> of the names this end subsets.
     */
    public List<Token> getSubsetsRolenames() {
    	return Collections.unmodifiableList(subsetsRolename);
    }
    
    /**
     * Adds a name to the list of association end names this end redefines. 
     * @param name The <code>Token</code> of the association end name this end should redefine.
     */
    public void addRedefinesRolename(Token rolename) {
    	// Lazy initialization of the list.
    	if (redefinesRolenames.size() == 0)
    		redefinesRolenames = new ArrayList<Token>();
    	
    	redefinesRolenames.add(rolename);
    }
    
    /**
     * Returns a read only list of the association end names this association end redefines.
     * @return Unmodifiable <code>List</code> of the association end names this end redefines.
     */
    public List<Token> getRedefinesRolenames() {
    	return Collections.unmodifiableList(redefinesRolenames);
    }
    
    /**
     * Marks this association end as derived by providing a corresponding derive expression.<br/>
     * The Type of the expression must match the type of the association end.
     * @param exp The <code>ASTExpression</code> the derived values are calculated from.
     * @param parameter The parameter defined for the derive expression. Can be <code>null</code>.
     */
    public void setDerived(ASTExpression exp, ASTElemVarsDeclaration parameter) {
    	this.derivedExpression = exp;
    	if (parameter != null && !parameter.isEmpty())
    		this.deriveParameter = parameter;
    }
    
    /**
     * True if a derive expression for this association end is specified. 
     * @return True if this association end is derived by an OCL expression.
     */
    public boolean isDerived() {
    	return derivedExpression != null;
    }
    
    /**
     * Specifies a the list of qualifiers if this end is reached by a qualified association. 
     * @param qualifier
     */
    public void setQualifiers(List<ASTVariableDeclaration> qualifier) {
    	this.qualifiers = qualifier;
    }
    
    /**
     * Gets the list of defined qualifiers at this end. 
     * @param qualifier
     */
    public List<ASTVariableDeclaration> getQualifiers() {
    	return this.qualifiers;
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
        
        List<VarDecl> generatedQualifiers;
        if (qualifiers.size() == 0) {
        	generatedQualifiers = Collections.emptyList();
        } else {
        	generatedQualifiers = new ArrayList<VarDecl>(qualifiers.size());
        	
        	for (ASTVariableDeclaration var : qualifiers ) {
        		generatedQualifiers.add(var.gen(ctx));
        	}
        }
        
        mAend = ctx.modelFactory().createAssociationEnd(cls, getRolename(ctx), 
        												mult, kind, fOrdered, generatedQualifiers);

        mAend.setUnion(this.isUnion);
        mAend.setDerived(this.derivedExpression != null);
        
        this.genAnnotations(mAend);
        
        return mAend;
    }

    /**
     * If given generates the expression that is linked to 
     * this association end as a derive expression.
     * @param ctx
     */
    public void genDerived(Context ctx) throws SemanticException {
    	if (!this.isDerived()) return;
    	
    	VarDeclList parameter = new VarDeclList(false);
    	boolean exprContextChanged = false;
    	Symtable vars = ctx.varTable();
    	vars.enterScope();
    	
    	
    	try {
	    	if (this.deriveParameter == null || this.deriveParameter.isEmpty()) { 
	    		// Short notation using self
	    		if (this.mAend.association().associationEnds().size() == 2) {
	    			MClass ot = mAend.getAllOtherAssociationEnds().get(0).cls();
	    			parameter.add(new VarDecl("self", ot));
	    			ctx.exprContext().push("self", ot);
	    			exprContextChanged = true;
	    		} else {
	    			throw new SemanticException(fName, "Derived n-ary associations must define parameter for the derive expression.");
	    		}
	    	} else {
	    		if (this.deriveParameter.getVarNames().size() != mAend.association().associationEnds().size() - 1) {
	    			throw new SemanticException(fName, "Invalid number of parameter for derive expression!");
	    		}
	    		
	    		int parIndex = 0;
	    		for (int index = 0; index < mAend.association().associationEnds().size(); ++index) {
		    		if (mAend.association().associationEnds().get(index) != mAend ) {		    			
		    			// Use association end type. Can be more generic in declaration
		    			Type varType = mAend.association().associationEnds().get(index).cls();
		    			
		    			ASTType astType = deriveParameter.getVarTypes().get(parIndex);
		    			if (astType != null) {
		    				Type declaredType =  astType.gen(ctx);
		    				if (!varType.conformsTo(declaredType)) {
		    					throw new SemanticException(astType.getStartToken(), "The derive parameter must be of type " + StringUtil.inQuotes(varType.toString()) + " or one of its supertypes.");
		    				}
		    				varType = declaredType;
		    			}
		    			
		    			parameter.add(new VarDecl(deriveParameter.getVarTokens().get(parIndex), varType));
		    			parIndex++;
		    		}
	    		}
	    	}
			
	    	parameter.addVariablesToSymtable(vars);
	    	
	    	Expression exp = derivedExpression.gen(ctx);
	    	
	    	// We can ignore redefinition here
	    	if (!exp.type().conformsTo(mAend.getType())) {
	    		throw new SemanticException(derivedExpression.getStartToken(), 
	    				"The type " +
	    				StringUtil.inQuotes(exp.type().toString()) + " of the derive expression at association end " +
	    				StringUtil.inQuotes(mAend.association().toString() + "::" + getRolename(ctx)) + " does not conform to the end type " + StringUtil.inQuotes(mAend.getType()) + ".");
	    	}
	    	
	    	mAend.setDeriveExpression(parameter, exp);
    	} finally {
    		ctx.varTable().exitScope();
    		if (exprContextChanged)
    			ctx.exprContext().pop();
    	}
    }
    
    @Override
    public String toString() {
        return (fRolename == null ? "unnamed end on " + getClassName() : fRolename.getText());
    }
}

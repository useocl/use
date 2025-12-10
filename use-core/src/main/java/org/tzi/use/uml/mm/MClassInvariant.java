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

package org.tzi.use.uml.mm;

import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.api.IInvariantExpressionFactory;
import org.tzi.use.uml.ocl.expr.*;

import java.util.List;


/**
 * A class invariant is a boolean expression that must hold in every
 * system state for each object of a class.
 *
 * @author      Mark Richters 
 */
public final class MClassInvariant extends MModelElementImpl implements UseFileLocatable {

    private final IInvariantExpressionFactory exprFactory;

    /**
     * context type
     */
    private MClassifier fClassifier;
    
    /**
     * boolean expression
     */
    private IExpression fBody;

    /**
     * The body expression expanded by <code>forAll</code> or <code>exists</code>
     */
    private IExpression fExpanded;

    /**
     * position of class in the model
     */
    private int fPositionInModel;
    
    /**
     * <code>true</code>, if this invariant has explicitly named
     * variables instead of an implicit <code>self</code>.
     */
    private boolean fHasVars;
    
    /**
	 * optional List of variable names
	 */
    private VarDeclList fVars;
    
    /**
     * Flags from generator.
     */
    private boolean loaded;
    private boolean active;
    private boolean negated;
    private boolean checkedByBarrier;
    
    /**
     * If <code>true</code>, the body expression is expanded
     * to <code>className.allInstances()->exists(body)</code> instead of
     * <code>className.allInstances()->forAll(body)</code>.
     */
    private boolean fIsExistential;
    
    /**
     * Constructs a new invariant. The <code>name</code> and <code>vars</code> is optional, i.e., can be <code>null</code>.
     */
    MClassInvariant(String name, List<String> vars, MClassifier cf, IExpression inv, boolean isExistential,
                    IInvariantExpressionFactory exprFactory) throws ExpInvalidException
    {
        super(name);
        
        if (!inv.isBooleanType()) {
        	throw new ExpInvalidException("An invariant must be a boolean expression.");
        }
        this.exprFactory = exprFactory;
        fClassifier = cf;
        fBody = inv;
        fBody.assertBooleanType();
        fVars = new VarDeclList(true);
        loaded = false;
        active = true;
        negated = false;
        fIsExistential = isExistential;

        // parse variables
        if (vars == null || vars.size() == 0)
        {
        	fHasVars = false;
        	VarDecl decl = new VarDecl("self", fClassifier);
        	fVars.add(decl);
        }
        else
        {
        	fHasVars = true;
        	for (String var : vars) {
        		fVars.add(new VarDecl(var, fClassifier));
        	}
        }
        
        calculateExpandedExpression();
    }

    /**
     * Creates a dynamic invariant.
     */
    MClassInvariant(String name, List<String> vars, MClassifier cf, IExpression inv, boolean isExistential,
                    boolean active, boolean negated, IInvariantExpressionFactory exprFactory) throws ExpInvalidException {
    	this(name, vars, cf, inv, isExistential, exprFactory);

		loaded = true;
		this.active = active;
		this.negated = negated;
		
		calculateExpandedExpression();
    }

    public String qualifiedName(){
    	return fClassifier.name() + "::" + name();
    }
    
    /** 
     * Returns the class for which the invariant is specified.
     */
    public MClassifier cls() {
        return fClassifier;
    }

    /** 
     * Returns the expression of the invariant.
     */
    public IExpression bodyExpression() {
        return fBody;
    }

    private void calculateExpandedExpression() {
        // Die Factory baut aus dem Body die global auswertbare Invariante
        fExpanded = exprFactory.buildExpandedInvariant(fBody, fIsExistential);
	}

	/** 
     * Returns the expanded expression of the invariant. This
     * expression requires no context and can be evaluated
     * globally. If {@link #isExistential()} is <code>false</code>, it is enclosed by a <code>forAll</code> expression iterating over
     * all instances of a class. Otherwise, it is enclosed by an <code>exists</code> expression. 
     */
    public IExpression expandedExpression() {
        return fExpanded;
    }

    /**
	 * Returns the invariant expression taking its flags into account. E.g. if
	 * an invariant is negated, a negated expression is returned. This operation
	 * does not handle the flag {@code active}.
	 */
    public IExpression flaggedExpression() {
        Expression invExpr = (Expression) expandedExpression();

        if (negated) {
            try {
                return ExpStdOp.create("not", new Expression[]{ invExpr });
            } catch (ExpInvalidException e) {
                // should not happen as type has been checked before
                throw new RuntimeException("Failed to negate invariant expression", e);
            }
        }

        return invExpr;
    }

    /** 
     * Returns an expression for selecting all instances that violate
     * the invariant.  The expression is generated as
     * <code>C.allInstances->reject(v1 | &lt;inv&gt;)</code> if it has one iteration variable.
     * For two and more variables, an additional <code>C.allInstances()->forAll(v2| &lt;inv&gt;)</code>
     * is introduced, because reject only allows one iteration variable.
     */
    public IExpression getExpressionForViolatingInstances() {
        // Auf der bereits expandierten Invarianten-Expression aufbauen
        return exprFactory.buildViolatingInstancesExpr(fExpanded, negated);
    }

    /** 
     * Returns an expression for selecting all instances that satisfy
     * the invariant.  The expression is generated as
     * <code>C.allInstances->select(v1 | &lt;inv&gt;)</code> if it has one iteration variable.
     * For two and more variables, an additional <code>C.allInstances()->forAll(v2| &lt;inv&gt;)</code>
     * is introduced, because select only allows one iteration variable.
     */
    public IExpression getExpressionForSatisfyingInstances() {
        // Auf der bereits expandierten Invarianten-Expression aufbauen
        return exprFactory.buildSatisfyingInstancesExpr(fExpanded, negated);
    }

    public boolean isExistential()
    {
    	return fIsExistential;
    }
    
    /** 
     * Returns true if the invariant has a variable name declaration.
     */
    public boolean hasVar() {
        return fHasVars;
    }

    /** 
     * Returns the name of the variable. The result is null if no
     * variable was specified.
     */
    public String var() {
        if (!hasVar()) return null;

        if (fVars.size() == 1)
        	return fVars.varDecl(0).name();
        
        StringBuilder result = new StringBuilder(fVars.varDecl(0).name());
        for(int index = 1; index < fVars.size(); index++)
        {
        	result.append(", ").append(fVars.varDecl(index).name());
        }
        
        return result.toString();
    }
    
    /**
	 * Returns the variablelist. The result is {@code null} if no variable was
	 * specified.
	 * 
	 * @see #hasVar()
	 */
    public VarDeclList vars(){
    	return fHasVars ? fVars : null;
    }

	public boolean isLoaded() {
		return loaded;
	}
    
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isNegated() {
		return negated;
	}

	public void setNegated(boolean negated) {
		this.negated = negated;
	}

	/**
	 * <code>true</code> if this invariant is validated by
	 * an automatically placed barrier. So it can be ignored
	 * by the last check.
	 * @return the checkedByBarrier
	 */
	public boolean isCheckedByBarrier() {
		return checkedByBarrier;
	}

	/**
	 * @param checkedByBarrier the checkedByBarrier to set
	 */
	public void setCheckedByBarrier(boolean checkedByBarrier) {
		this.checkedByBarrier = checkedByBarrier;
	}
	
	/**
     * Returns a string representation of this model element.
     */
    @Override
    public String toString() {
        return fClassifier.name() + "::" + name();
    }

    /**
     * Compares just the model element's name.
     */
    @Override
    public int compareTo(MModelElement o) {
        if (o == this )
            return 0;
        if (! (o instanceof MClassInvariant) )
            throw new ClassCastException();
        
        MClassInvariant otherInv = (MClassInvariant) o;
        int clsCmp = cls().compareTo(otherInv.cls());
        
        if (clsCmp == 0)
        	return toString().compareTo(otherInv.toString());
        else
        	return clsCmp;
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == this )
            return true;
        if (obj instanceof MClassInvariant ) {
        	MClassInvariant other = (MClassInvariant)obj;
            return cls().equals(other.cls()) && name().equals(other.name());
        }
        
        return false;
    }

    /**
     * Returns the position in the defined USE-Model.
     */
    public int getPositionInModel() {
        return fPositionInModel;
    }

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(final int position) {
        fPositionInModel = position;
    }

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitClassInvariant(this);
    }
}


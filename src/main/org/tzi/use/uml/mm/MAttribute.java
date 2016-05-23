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

package org.tzi.use.uml.mm;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;

import com.google.common.base.Optional;

/**
 * An Attribute is a model element that is part of a Class.
 *
 * @author  Mark Richters
 * @author  Lars Hamann
 */
public final class MAttribute extends MModelElementImpl implements UseFileLocatable {
    private MClass fOwner;
    private final Type fType;

    private Expression deriveExpression = null;
    
    private Expression initExpression = null;
    
    private int fPositionInModel;

    /**
     * Creates an attribute with given name and type.
     */
    MAttribute(String name, Type type) {
        super(name);
        fType = type;
    }

    void setOwner(MClass owner) {
        fOwner = owner;
    }

    /**
     * Returns the owner class of this attribute.
     */
    public MClass owner() {
        return fOwner;
    }

    public String qualifiedName(){
    	return fOwner.name() + "::" + name();
    }
    
    /**
     * Returns the type of this attribute.
     */
    public Type type() {
        return fType;
    }

    /**
     * <code>true</code> if a derive expression is specified for
     * this attribute.
     * @return
     */
    public boolean isDerived() {
    	return deriveExpression != null;
    }
    
    public boolean hasInitExpression() {
    	return initExpression != null;
    }
    
    /**
     * Sets the derive expression for this attribute.
	 * @param exp The derive expression, can be <code>null</code>.
	 */
	public void setDeriveExpression(Expression exp) {
		this.deriveExpression = exp;
	}
	
	/**
     * Sets the init expression for this attribute.
	 * @param exp The init expression, can be <code>null</code>.
	 */
	public void setInitExpression(Expression exp) {
		this.initExpression = exp;
	}
	
	/**
	 * The defined derive expression, if any.
	 * @return The derive expression or <code>null</code> if none is specified.
	 */
	public Expression getDeriveExpression() {
		return this.deriveExpression;
	}
	
	/**
	 * The defined init expression, if any.
	 * @return The init expression or <code>null</code> if none is specified.
	 */
	public Optional<Expression> getInitExpression() {
		return Optional.fromNullable(this.initExpression);
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
    public void setPositionInModel(int position) {
        fPositionInModel = position;
    }
    
    @Override
    public String toString() {
        return (isDerived() ? "/" : "") + name() + " : " + fType;
    }

    /**
     * Two attribute objects are equal if the have the same owner and the same name
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        if (obj instanceof MAttribute ) {
        	MAttribute other = (MAttribute)obj;
            return fOwner.equals(other.fOwner) && name().equals(other.name());
        }
        
        return false;
    }
    
    /**
     * Process this element with visitor.
     */
    @Override
	public void processWithVisitor(MMVisitor v) {
        v.visitAttribute(this);
    }
}

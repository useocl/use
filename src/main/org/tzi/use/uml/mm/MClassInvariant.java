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

import java.util.Iterator;
import java.util.List;

import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpExists;
import org.tzi.use.uml.ocl.expr.ExpForAll;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.ExpReject;
import org.tzi.use.uml.ocl.expr.ExpSelect;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * A class invariant is a boolean expression that must hold in every
 * system state for each object of a class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MClassInvariant extends MModelElementImpl {
    private VarDeclList fVars;    //  optional List of variable names
    private MClass fClass;  //  context type
    private Expression fBody;   //  boolean expression
    private Expression fExpanded;
    private int fPositionInModel; // position of class in the model
    private String fName; // name of the class
    private boolean fHasVars;
    private boolean fIsExistential;
    
    /**
     * Constructs a new invariant. The name and vars is optional.
     */
    MClassInvariant(String name, List vars, MClass cls, Expression inv, boolean isExistential)
        throws ExpInvalidException
    {
        super(name, "inv");
        fName = name;
        fClass = cls;
        fBody = inv;
        fBody.assertBoolean();
        fVars = new VarDeclList(true);
        fIsExistential = isExistential;
        
        // expand expression
        ObjectType t = TypeFactory.mkObjectType(fClass);
        Expression allInstances = new ExpAllInstances(t);
        if (vars == null || vars.size() == 0)
        {
        	fHasVars = false;
        	VarDecl decl = new VarDecl("self", t);
        	fVars.add(decl);
        }
        else
        {
        	fHasVars = true;
        	Iterator iter = vars.iterator();
        	while(iter.hasNext()) {
        		fVars.add(new VarDecl((String)iter.next(), t));
        	}
        }
        
        if (isExistential)
        {
        	fExpanded = new ExpExists(fVars, allInstances, fBody);
        }
        else
        {
        	fExpanded = new ExpForAll(fVars, allInstances, fBody);
        }
    }


    /** 
     * Returns the class for which the invariant is specified.
     */
    public MClass cls() {
        return fClass;
    }

    /** 
     * Returns the expression of the invariant.
     */
    public Expression bodyExpression() {
        return fBody;
    }

    /** 
     * Returns the expanded expression of the invariant. This
     * expression requires no context and can be evaluated
     * globally. It it enclosed by a forAll expression iterating over
     * all instances of a class.
     */
    public Expression expandedExpression() {
        return fExpanded;
    }

    /** 
     * Returns an expression for selecting all instances that violate
     * the invariant.  The expression is generated as
     * <code>C.allInstances->reject(fVars | <inv>)<code>.
     */
    public Expression getExpressionForViolatingInstances() {
        ObjectType t = TypeFactory.mkObjectType(fClass);
        try {
            Expression allInstances = new ExpAllInstances(t);
            return new ExpReject(fVars, allInstances, fBody);
        } catch (ExpInvalidException ex) {
            throw new RuntimeException("getExpressionForViolatingInstances failed: " +
                                       ex.getMessage());
        }
    }

    /** 
     * Returns an expression for selecting all instances that satisfy
     * the invariant.  The expression is generated as
     * <code>C.allInstances->select(fVars | <inv>)<code>.
     */
    public Expression getExpressionForSatisfyingInstances() {
        ObjectType t = TypeFactory.mkObjectType(fClass);
        try {
            Expression allInstances = new ExpAllInstances(t);
            return new ExpSelect(fVars, allInstances, fBody);
        } catch (ExpInvalidException ex) {
            throw new RuntimeException("getExpressionForSatisfyingInstances failed: " +
                                       ex.getMessage());
        }
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
        
        String result = fVars.varDecl(0).name();
        for(int index = 1; index < fVars.size(); index++)
        {
        	result += ", " + fVars.varDecl(index).name();
        }
        
        return result;
    }

    /**
     * Returns a string representation of this model element.
     */
    public String toString() {
        return fClass.name() + "::" + name();
    }

    /**
     * Compares just the model element's name.
     */
    public int compareTo(Object o) {
        if (o == this )
            return 0;
        if (! (o instanceof MClassInvariant) )
            throw new ClassCastException();
        return toString().compareTo(((MClassInvariant) o).toString());
    }

    /**
     * Returns the name of the corresponding MClass followed by the name of the invariant
     * @return Class name and invariant name as String
     */
    public String getClassAndNameAsString() {
        return fClass.name() + "::" + fName;
    }

    /**
     * Returns the name of the invariant followed by the name of the corresponding MClass
     * @return Invariant name and class name as String
     */
    public String getNameAndClassAsString() {
        return fName + "::" + fClass.name();
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
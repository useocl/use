/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Statement used to create a new {@link MObject}. 
 * 
 * @author Daniel Gent
 * 
 */
public class MNewObjectStatement extends MStatement {
    
    private MClass fObjectClass;
    
    private Expression fObjectName;
    
    /**
     * Constructs a new object creation statement.
     * 
     * @param objectClass The class of the object to create.
     * @param objectName The expression used to derive the name of the new object.
     */
    public MNewObjectStatement(MClass objectClass, Expression objectName) {
        fObjectClass = objectClass;
        fObjectName = objectName;
    }

    /**
     * Constructs a new object creation statement.
     * 
     * @param objectClass The class of the new object to create.
     * @param objectName The name of the new object.
     */
    public MNewObjectStatement(MClass objectClass, String objectName) {
        fObjectClass = objectClass;
        
        if (objectName != null) {
            fObjectName = new ExpConstString(objectName);
        }
    }

    /**
	 * @return the fObjectClass
	 */
	public MClass getObjectClass() {
		return fObjectClass;
	}

	/**
	 * @return the fObjectName
	 */
	public Expression getObjectName() {
		return fObjectName;
	}

	/**
     * Returns the type of the object to create.
     * 
     * @return
     */
    public MClass getObjectType() {
        return fObjectClass;
    }

    @Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result) throws EvaluationFailedException {

    	String objectName;
        if (fObjectName == null) {
            objectName = context.getState().uniqueObjectNameForClass(fObjectClass);
        } else {
            objectName = EvalUtil.evaluateString(context, fObjectName);
        }

        // create new object
        try {
            return new ObjectValue(fObjectClass, context.getSystem().createObject(result, fObjectClass, objectName));
        } catch (MSystemException e) {
            throw new EvaluationFailedException(e);
        }
    }

    @Override
    protected String shellCommand() {
        return "new " + fObjectClass.name()
                + (fObjectName != null ? ("(" + fObjectName + ")") : "");
    }

    @Override
    public String toString() {
        return shellCommand();
    }

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}
}

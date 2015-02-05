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

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * "Compiled" version of a delete statement.
 * @author Fabian Buettner
 * @author Daniel Gent
 *
 */
public class MObjectDestructionStatement extends MStatement {
	/** The expression leading to the objects to delete.
	 *  Must be a single object or a collection of objects.
	 */
	private Expression fToDelete;
	
	/**
	 * Constructs a new destruction statement.
	 * The objects returned by the expression <code>toDelete</code> are
	 * deleted from the system. 
     * <p>The given expression must be either an object type or a collection of object type.<br/>
     *    This is <b>not</b> checked!</p>
	 * @param object
	 */
	public MObjectDestructionStatement(Expression toDelete) {
		fToDelete = toDelete;
	}
	
	/**
	 * Constructs a new destruction statement.
	 * The provided object value will be deleted from the system state.  
     * 
	 * @param object The object value to delete.
	 */
	public MObjectDestructionStatement(ObjectValue object) {
		this(new ExpressionWithValue(object));
	}

	/**
	 * @return the expression leading to the objects to delete
	 */
	public Expression getToDelete() {
		return fToDelete;
	}

	@Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException {
		
		// handle "dynamic" collections (e.g. .allInstances)
		if (fToDelete.type().isKindOfCollection(VoidHandling.INCLUDE_VOID)) {
            Value val = EvalUtil.evaluateExpression(context, fToDelete);
			
			if (val.isUndefined())
				return null;
			
			CollectionValue collection = (CollectionValue)val;
			for (Value object : collection.collection()) {
                MObjectDestructionStatement statement = new MObjectDestructionStatement((ObjectValue)object);
				statement.setSourcePosition(getSourcePosition());
                statement.execute(context, result);
			}
			
		} else {
            MObject object = EvalUtil.evaluateObjectExpression(context, fToDelete);
			
            try {
                context.getSystem().destroyObject(result, object);
            } catch (MSystemException e) {
                throw new EvaluationFailedException(e.getMessage());
			}
		}
		
		return null;
    }
	
	@Override
	protected String shellCommand() {
		return "destroy " + fToDelete;
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

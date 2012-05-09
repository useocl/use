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

// $Id$

package org.tzi.use.uml.sys.soil;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * @author Fabian Buettner
 * @author Daniel Gent
 * 
 */
public class MObjectDestructionStatement extends MStatement {
    /** TODO */
    private Expression fToDelete;

    /**
     * TODO
     * 
     * @param object
     */
    public MObjectDestructionStatement(Expression toDelete) {
        fToDelete = toDelete;
    }

    /**
     * TODO
     * 
     * @param object
     */
    public MObjectDestructionStatement(Value object) {
        this(new ExpressionWithValue(object));
    }

    /**
     * TODO
     * 
     * @return
     */
    public Expression getToDelete() {
        return fToDelete;
    }

    @Override
    public void execute(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException {

        // handle "dynamic" collections (e.g. .allInstances)
        if (fToDelete.type().isCollection(false)) {
            Value val = EvalUtil.evaluateExpression(this, context, result, fToDelete);

            if (val.isUndefined())
                return;

            CollectionValue collection = (CollectionValue) val;
            for (Value object : collection.collection()) {
                MObjectDestructionStatement statement = new MObjectDestructionStatement(object);

                statement.setSourcePosition(getSourcePosition());

                statement.execute(context, result);
            }

        } else {
            MObject object = EvalUtil.evaluateObjectExpression(this, context, result, fToDelete);

            try {
                context.getSystem().destroyObject(result, object);
            } catch (MSystemException e) {
                throw new EvaluationFailedException(this, e.getMessage());
            }
        }
    }

    @Override
    protected String shellCommand() {
        return "destroy " + fToDelete;
    }

    @Override
    public String toString() {
        return shellCommand();
    }
}

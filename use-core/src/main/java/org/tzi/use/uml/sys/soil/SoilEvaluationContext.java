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

import org.tzi.use.output.UserOutput;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.soil.VariableEnvironment;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This context objects comprises everything that is required to execute
 * statements.
 * 
 * @author Fabian Buettner
 * @author Daniel Gent
 *
 */
public class SoilEvaluationContext {
	
	private final MSystem fSystem;

	private final Deque<Expression> fExpressionStack = new ArrayDeque<Expression>();
	
	private boolean fIsUndo = false;
	
	private boolean fIsRedo = false;

	private final UserOutput output;

	/**
	 * Constructs a new evaluation context for the given system.
     *
	 * @param system The system of the context.
	 */
    public SoilEvaluationContext(UserOutput output, MSystem system) {
		fSystem = system;
		this.output = output;
	}

	/**
	 * The system the statement is evaluated on.
	 * @return
	 */
	public MSystem getSystem() {
		return fSystem;
	}

	public UserOutput getOutput() {
		return output;
	}

	/**
	 * The system state the statement is evaluated on. 
     * 
	 * @return
	 */
	public MSystemState getState() {
        return fSystem.state();
	}
	
	/**
	 * The variable environment of the system.
     * 
	 * @return
	 */
	public VariableEnvironment getVarEnv() {
        return fSystem.getVariableEnvironment();
	}
	
	/**
	 * <code>true</code>, if the current context is an undo
	 * of a previously executed statement.
     * 
	 * @return
	 */
	public boolean isUndo() {
		return fIsUndo;
	}
	
	/**
	 * Sets this context to an undo context. 
	 * @param isUndo
	 */
	public void setIsUndo(boolean isUndo) {
		fIsUndo = isUndo;
	}
	
	/**
	 *  <code>true</code>, if the current context is a redo
	 *  of a previously undone statement.
     * 
	 * @return
	 */
	public boolean isRedo() {
		return fIsRedo;
	}
	
	/**
	 * Sets this context to a redo context. 
	 * @param isRedo
	 */
	public void setIsRedo(boolean isRedo) {
		fIsRedo = isRedo;
	}
	
	/**
	 * Pushes <code>expression</code> to the expression stack.
     * 
	 * @param expression The expression to enter.
	 */
	public void enterExpression(Expression expression) {
		fExpressionStack.push(expression);
	}
	
	/**
	 * Pops the last entered expression from the expression stack.
     * 
	 * @return The last entered expression.
	 */
	public Expression exitExpression() {
		return fExpressionStack.pop();
	}
	
	/**
	 * <code>true</code>, if an expression was entered before but not exited.
     * 
	 * @return
	 */
	public boolean isInExpression() {
		return !fExpressionStack.isEmpty();
	}
}

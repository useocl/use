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


import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * Base class for all SOIL statements.
 * MStatement instances provides the methods required for the interpretation of the statement, but do not hold 
 * state information themselves. 
 * @author Fabian Büttner
 * @author Daniel Gent
 */
public abstract class MStatement {

	/**
	 * The source position of the statement (if specified).
	 */
	private SrcPos fSourcePosition;
	
	/** TODO */
	private boolean fIsOperationBody = false;
	
	private static final String SHELL_PREFIX = "!";
	
	/**
	 * Get the position of this statement in the source. 
	 * @return
	 */
	public SrcPos getSourcePosition() {
		return fSourcePosition;
	}
	
	
	/**
     * Set the position of this statement in the source. 
	 * @param sourcePosition
	 */
	public void setSourcePosition(SrcPos sourcePosition) {
		fSourcePosition = sourcePosition;
	}
	
	
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isEmptyStatement() {
		return this == MEmptyStatement.getInstance();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isOperationBody() {
		return fIsOperationBody;
	}


	/**
	 * TODO
	 * @param isOperationBody
	 */
	public void setIsOperationBody(boolean isOperationBody) {
		fIsOperationBody = isOperationBody;
	}
	
	
	/**
	 * Returns the shell command for the statement prefixed by
	 * the shell prefix {@link #SHELL_PREFIX}.
	 * @return The textual form of this statement for the USE shell.
	 */
	public final String getShellCommand() {
		return SHELL_PREFIX + shellCommand();
	}
	
	/**
	 * Returns the shell command of this statement without the shell prefix. 
	 * @return The command text of this statement.
	 */
	protected abstract String shellCommand();
	
	
	@Override
	public abstract String toString();

	/**
	 * TODO
	 * @param indent
	 * @param indentIncr
	 * @return
	 */
	public String toVisitorString(int indent, int indentIncr) {
		
		return toVisitorString(
				new StringBuilder(StringUtil.repeat(" ", indent)), 
				StringUtil.repeat(" ", indentIncr));
		
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	private String toVisitorString(
			StringBuilder indent, 
			String indentIncr) {
		
		StringBuilder result = new StringBuilder();
		
		toVisitorString(indent, indentIncr, result);
		
		return result.toString();
	}
	
	
	/**
	 * TODO
	 * @param indent
	 * @param indentIncrease
	 * @param target
	 */
	protected void toVisitorString(
			StringBuilder indent,
			String indentIncrease,
			StringBuilder target) {
		
		target.append(indent);
		target.append(shellCommand());
	}
	

    /**
     * TODO
     * 
     * @param hasUndoStatement
     * @throws EvaluationFailedException
     */
    public abstract void evaluate(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException;


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
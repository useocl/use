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

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * Base class for all SOIL statements. MStatement instances provides the methods
 * required for the interpretation of the statement, but do not hold state
 * information themselves. 
 * The main method is {@link #execute(SoilEvaluationContext, StatementEvaluationResult)}.
 *
 * @author Fabian Buettner
 * @author Daniel Gent
 */
public abstract class MStatement {

	/**
	 * The source position of the statement (if specified).
	 */
	private SrcPos fSourcePosition;
	
	private static final String SHELL_PREFIX = "!";
	
	/**
     * Get the position of this statement in the source.
	 */
	public SrcPos getSourcePosition() {
		return fSourcePosition;
	}
	
	/**
     * Set the position of this statement in the source.
	 */
	public void setSourcePosition(SrcPos sourcePosition) {
		fSourcePosition = sourcePosition;
	}
	
	/**
     * Is this statement a no-op statement?
	 */
	public boolean isEmptyStatement() {
		return this == MEmptyStatement.getInstance();
	}
	
	/**
     * Execute this statement in the given context. This modifies the system
     * state. The result object captures additional data about the execution of
     * the statement.
     * 
     * @param hasUndoStatement
     * @throws EvaluationFailedException
	 */
    public abstract Value execute(SoilEvaluationContext context, StatementEvaluationResult result) throws EvaluationFailedException;

	/**
     * Returns the shell command for the statement prefixed by the shell prefix
     * {@link #SHELL_PREFIX}.
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
     * Returns the concrete representation of this statement in the concrete syntax.  
     * @param indent Indentation 
     * @param indentIncr Indentation increment (for nested statements).
     * @return The pretty-printed string representation.
	 */
    public String toConcreteSyntax(int indent, int indentIncr) {
		
		StringBuilder result = new StringBuilder();
        toConcreteSyntax(new StringBuilder(StringUtil.repeat(" ", indent)), StringUtil.repeat(" ", indentIncr), result);
		return result.toString();

	}
	
	/**
     * Returns the concrete representation of this statement in the concrete syntax.  
     * @param indent Indentation 
     * @param indentIncr Indentation increment (for nested statements).
     * @param target The result string is appended here
	 */
    protected void toConcreteSyntax(StringBuilder indent, String indentIncrease, StringBuilder target) {
		
		target.append(indent);
		target.append(shellCommand());
	}
    
    public abstract void processWithVisitor(MStatementVisitor v) throws Exception;
	
}
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * Represents a list (sequence) of statements.
 * @author Daniel Gent
 *
 */
public class MSequenceStatement extends MStatement {
	
	/**
	 * The sequence of statements to evaluate
	 */
	private List<MStatement> fStatements;
	
	
	/**
	 * Constructs a new container for statements to
	 * evaluate in a single call.
	 */
	public MSequenceStatement() {
		fStatements = new LinkedList<MStatement>();
	}
	
	
	
	/**
	 * Constructs a new container with the given statements to
	 * execute in a single call
	 * @param statements
	 */
	public MSequenceStatement(List<MStatement> statements) {
		fStatements = statements;
	}
	
	
	/**
	 * Constructs a new container with the given statements to
	 * execute in a single call
	 * @param statements
	 */
	public MSequenceStatement(MStatement... statements) {
		this(Arrays.asList(statements));
	}
	
	
	/**
	 * Returns the number of statements.
	 * @return
	 */
	public int getNumStatements() {
		return fStatements.size();
	}
	
	
	/**
	 * Returns all contained statements
	 * @return
	 */
	public List<MStatement> getStatements() {
		return fStatements;
	}
	
	
	/**
	 * Appends a statement to the sequence.
	 * @param statement
	 */
	public void appendStatement(MStatement statement) {
		fStatements.add(statement);
	}
	
	
	/**
	 * Prepends a statement to the sequence.
	 * @param statement
	 */
	public void prependStatement(MStatement statement) {
		fStatements.add(0, statement);
	}
	
	
	/**
	 * Removes all statements.
	 */
	public void clear() {
		fStatements.clear();
	}
		
	
	/**
	 * True if the sequence does not contain any statement.
	 * @return
	 */
	public boolean isEmpty() {
		return fStatements.isEmpty();
	}
	
	
	/**
	 * Returns a simpler statement if possible, i. e., the empty statement if the sequence is empty
	 * or the single statement if only one statement is included in the sequence,
	 * or this sequence if it cannot be simplified, 
	 * @return
	 */
	public MStatement simplify() {
		
		switch (fStatements.size()) {
		case  0: return MEmptyStatement.getInstance();
		case  1: return fStatements.get(0);
		default: return this;	
		}
	}
	

	@Override
    public Value execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
		for (MStatement statement : fStatements) {
			statement.execute(context, result);
		}
		
		return null;
	}
	
	
	@Override
	protected String shellCommand() {
		StringBuilder result = new StringBuilder();
		List<String> shellCommands = new ArrayList<String>(fStatements.size());
		for (MStatement statement : fStatements) {
			shellCommands.add(statement.shellCommand());
		}
		
		StringUtil.fmtSeq(result, shellCommands, "; ");
		
		return result.toString();
	}
	
	

	@Override
	protected void toConcreteSyntax(
			StringBuilder indent, 
			String indentIncrease,
			StringBuilder target) {
		
		String newLine = "\n";
		
		int numStatements = fStatements.size();
		for (int i = 0; i < numStatements; ++i) {
			MStatement statement = fStatements.get(i);
			statement.toConcreteSyntax(indent, indentIncrease, target);
			target.append(";");
			if (i < (numStatements - 1)) {
				target.append(newLine);
			}
		}
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

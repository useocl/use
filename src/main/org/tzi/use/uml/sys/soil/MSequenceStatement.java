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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MSequenceStatement extends MStatement {
	/** TODO */
	private List<MStatement> fStatements;
	
	
	/**
	 * TODO
	 */
	public MSequenceStatement() {
		fStatements = new ArrayList<MStatement>();
	}
	
	
	/**
	 * TODO
	 * @param initialCapacity
	 */
	public MSequenceStatement(int initialCapacity) {
		fStatements = new ArrayList<MStatement>(initialCapacity);
	}
	
	
	/**
	 * TODO
	 * @param statements
	 */
	public MSequenceStatement(List<MStatement> statements) {
		fStatements = statements;
	}
	
	
	/**
	 * TODO
	 * @param statements
	 */
	public MSequenceStatement(MStatement... statements) {
		this(Arrays.asList(statements));
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public int getNumStatements() {
		return fStatements.size();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public List<MStatement> getStatements() {
		return fStatements;
	}
	
	
	/**
	 * TODO
	 * @param statement
	 */
	public void appendStatement(MStatement statement) {
		fStatements.add(statement);
	}
	
	
	/**
	 * TODO
	 * @param statement
	 */
	public void prependStatement(MStatement statement) {
		fStatements.add(0, statement);
	}
	
	
	/**
	 * TODO
	 */
	public void clear() {
		fStatements.clear();
	}
		
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isEmpty() {
		return fStatements.isEmpty();
	}
	
	
	/**
	 * TODO
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
	protected void evaluate() throws EvaluationFailedException {
		
		for (MStatement statement : fStatements) {
			evaluateSubStatement(statement);
		}
	}
	
	
	@Override
	protected String shellCommand() {
		StringBuilder result = new StringBuilder();
		List<String> shellCommands = new ArrayList<String>(fStatements.size());
		for (MStatement statement : fStatements) {
			shellCommands.add(statement.shellCommand());
		}
		
		StringUtil.addToStringBuilder(result, "; ", shellCommands);
		
		return result.toString();
	}
	
	
	@Override
	public boolean hasSideEffects() {
		for (MStatement statement : fStatements) {
			if (statement.hasSideEffects()) {
				return true;
			}
		}
		return false;
	}


	@Override
	protected void toVisitorString(
			StringBuilder indent, 
			String indentIncrease,
			StringBuilder target) {
		
		String newLine = "\n";
		
		int numStatements = fStatements.size();
		for (int i = 0; i < numStatements; ++i) {
			MStatement statement = fStatements.get(i);
			statement.toVisitorString(indent, indentIncrease, target);
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
}

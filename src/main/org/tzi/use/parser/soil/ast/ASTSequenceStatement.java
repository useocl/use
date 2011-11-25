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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.VariableSet;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTSequenceStatement extends ASTStatement {
	/** TODO */
	private List<ASTStatement> fStatements;
	

	/**
	 * TODO
	 */
	public ASTSequenceStatement() {
		fStatements = new ArrayList<ASTStatement>();
	}
	
	
	/**
	 * TODO
	 * @param initialCapacity
	 */
	public ASTSequenceStatement(int initialCapacity) {
		fStatements = new ArrayList<ASTStatement>(initialCapacity);
	}
	
	
	/**
	 * TODO
	 * @param statements
	 */
	public ASTSequenceStatement(
			List<ASTStatement> statements) {
		
		fStatements = statements;
		addChildStatements(fStatements);
	}
	
	
	/**
	 * TODO
	 * @param statements
	 */
	public ASTSequenceStatement(
			ASTStatement... statements) {
		
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
	public List<ASTStatement> getStatements() {
		return fStatements;
	}
	
	
	/**
	 * TODO
	 * @param statement
	 */
	public void addStatement(ASTStatement statement) {
		fStatements.add(statement);
		addChildStatement(statement);
	}
	
	
	/**
	 * TODO
	 * @param statement
	 * @param sourcePosition
	 */
	public void addStatement(ASTStatement statement, Token sourcePosition) {
		addStatement(statement);
		statement.setSourcePosition(sourcePosition);
	}
	
	
	/**
	 * TODO
	 * @param statement
	 * @param sourcePosition
	 */
	public void addStatement(
			ASTStatement statement, 
			Token sourcePosition,
			String parsedText) {
		
		addStatement(statement);
		statement.setSourcePosition(sourcePosition);
	}
	
	
	@Override
	protected MSequenceStatement generateStatement() throws CompilationFailedException {	
		
		verbosePrintln("\n++++++++++++++++++++++++++++++++++++++");
		verbosePrintln(
				"generating sequence of " + 
				fStatements.size() + 
				" statements\n");
		
		List<MStatement> statements = new ArrayList<MStatement>();
		for (ASTStatement s2 : fStatements) {
			
			statements.add(s2.generateStatement(fContext, fSymtable));
			checkS2Validity(s2);
			
			verbosePrintln("\n-- merging s1;s2 ---------------------");
			verbosePrintln("b=" + fBoundSet);
			verbosePrintln("a=" + fAssignedSet);
			verbosePrintln("-- s2: -------------------------------");
			verbosePrintln("b=" + s2.fBoundSet);
			verbosePrintln("a=" + s2.fAssignedSet);
			
			// bound(s1;s2) = bound(s2) U (bound(s1) pd1 bound(s2))
			fBoundSet.removePolymorphic1(s2.fBoundSet);
			fBoundSet.add(s2.fBoundSet);
			
			// assigned(s1;s2) = assigned(s1) U assigned(s2)
			fAssignedSet.add(s2.fAssignedSet);
			
			verbosePrintln("-- (s1;s2): --------------------------");
			verbosePrintln("b=" + fBoundSet);
			verbosePrintln("a=" + fAssignedSet);
			verbosePrintln("-- merging complete-------------------\n");
		}
		
		verbosePrintln("++++++++++++++++++++++++++++++++++++++");
		
		return new MSequenceStatement(statements);
	}
		
	


	/**
	 * TODO
	 * @param s2
	 * @throws CompilationFailedException
	 */
	private void checkS2Validity(
			ASTStatement s2) throws CompilationFailedException {
	
		if (fBoundSet.isEmpty() || s2.fAssignedSet.isEmpty()) {
			return;
		}
		
		VariableSet boundS1 = fBoundSet;
		VariableSet assignedUnboundS2 = new VariableSet(s2.fAssignedSet); 
		assignedUnboundS2.remove(s2.fBoundSet);	
		
		for (String name : boundS1.getCommonNames(assignedUnboundS2)) {
			Set<Type> assignedTypes = assignedUnboundS2.getTypes(name);
			Set<Type> boundTypes = boundS1.getTypes(name);
			
			for (Type assignedType : assignedTypes) {
				for (Type boundType : boundTypes) {
					if (!assignedType.isSubtypeOf(boundType)) {
						ASTStatement cause = fSymtable.getCause(name);
						throw new CompilationFailedException(
								this,
								"Statement "
										+ StringUtil.inQuotes(cause)
										+ " at line "
										+ cause.getSourcePosition().line()
										+ ", column "
										+ cause.getSourcePosition().column()
										+ " possibly sets variable "
										+ StringUtil.inQuotes(name)
										+ " to type "
										+ StringUtil.inQuotes(boundType)
										+ ". This is prohibited, since this is not a subtype "
										+ "of its current type "
										+ StringUtil.inQuotes(assignedType)
										+ ".");
					}
				}
			}
		}
	}
	
	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		
		target.println(prelude + "[SEQUENCE]");
		
		if (prelude.length() == 0) {
			prelude.append("+-");
		} else {
			prelude.insert(0, "| ");
		}
		for (ASTStatement statement : fStatements) {
			statement.printTree(prelude, target);
		}
		prelude.delete(0, 2);
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTStatement simplify() {
		
		ASTStatement result;
		
		switch (fStatements.size()) {
		case  0: result = new ASTEmptyStatement(); break;
		case  1: result = fStatements.get(0); break;
		default: result = this;
		}
		
		result.setSourcePosition(getSourcePosition());
		
		return result;
	}
	
	
	@Override
	public void flatten() {
		List<ASTStatement> flattenedStatements = 
			new ArrayList<ASTStatement>(fStatements.size());
		
		for (ASTStatement statement : fStatements) {
			if (statement.isEmptyStatement()) {
				continue;
			}
			
			statement.flatten();
				
			if (statement instanceof ASTSequenceStatement) {
				flattenedStatements.addAll(
						((ASTSequenceStatement)statement).fStatements);
				
			} else {
				flattenedStatements.add(statement);
			}	
		}
		
		fStatements = flattenedStatements;
	}
	
	
	@Override
	public String toString() {	
		return fStatements.toString();
	}
}
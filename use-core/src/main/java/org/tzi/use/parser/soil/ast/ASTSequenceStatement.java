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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
 * AST-node representing a sequence of commands.
 * @author Daniel Gent
 */
public class ASTSequenceStatement extends ASTStatement {
	/** The sequence of statements */
	private List<ASTStatement> fStatements;
	

	/**
	 * Constructs a new AST-node.
	 */
	public ASTSequenceStatement(Token start) {
		super(start);
		fStatements = new ArrayList<ASTStatement>();
	}
	
	/**
	 * The number of statements in this sequence.
	 * @return
	 */
	public int getNumStatements() {
		return fStatements.size();
	}
	
	
	/**
	 * Access to the sequence of statements.
	 * @return
	 */
	public List<ASTStatement> getStatements() {
		return fStatements;
	}
	
	/**
	 * Adds a new statement AST to the sequence.
	 * @param statement
	 */
	public void addStatement(
			ASTStatement statement) {
		fStatements.add(statement);
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
	 * Checks if variable assignments in <code>s2</code> are valid w.r.t. 
	 * the current assignments.
	 * @param s2
	 * @throws CompilationFailedException
	 */
	private void checkS2Validity(ASTStatement s2) throws CompilationFailedException {
	
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
					if (!assignedType.conformsTo(boundType)) {
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
	 * Simplifies the sequence statements by returning,
	 * <ul>
	 *   <li>an empty statement if this sequence is empty,</li>
	 *   <li>the contained statement if <code>size() == 1</code> or </li>
	 *   <li><code>this</code> if the sequence contains more than one statement.</li>
	 * </ul>
	 * @return A simplified statement or <code>this</code>.
	 */
	public ASTStatement simplify() {
		
		ASTStatement result;
		
		// Remove empty statements
		for (Iterator<ASTStatement> iter = fStatements.iterator(); iter.hasNext();) {
			ASTStatement s = iter.next();
			if (s == null || s instanceof ASTEmptyStatement) {
				iter.remove();
			}
		}
		
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
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

// $Id: ASTIterationStatement.java 2747 2011-11-25 14:53:17Z lhamann $

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.sys.soil.MBlockStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * An block that may introduce local variables.
 * @author Fabian Buettner
 */
public class ASTBlockStatement extends ASTStatement {
	
	/** The variables that are explicitly declared in this block (using 'declare') */
	private List<ASTVariableDeclaration> fVariableDeclarations;
	
	/** The inner statement of this block */
	private ASTStatement fBody;

	/** Whether explicit variable declarations are required in this (and all inner blocks) */  
	private final boolean fExplicitDeclarations;
	
	
	public ASTBlockStatement(Token sourcePosition, boolean explicitDeclarations) {
		super(sourcePosition);
		this.fExplicitDeclarations = explicitDeclarations;
		this.fVariableDeclarations = new ArrayList<ASTVariableDeclaration>();
	}
	
	public void addVariableDeclaration(ASTVariableDeclaration varDecl) {
		fVariableDeclarations.add(varDecl);
	}
	
	public void setBody(ASTStatement body) {
		fBody = body;
		
	}
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		List<VarDecl> varDecls = new ArrayList<VarDecl>();
		boolean needsExplicit = fExplicitDeclarations || !fVariableDeclarations.isEmpty();
		
		if (needsExplicit) {
			fSymtable.storeState(true);
			
			for (ASTVariableDeclaration astVarDecl : fVariableDeclarations) {
				VarDecl varDecl;
				try {
					varDecl = astVarDecl.gen(this.fContext);
				} catch (SemanticException e) {
					throw new CompilationFailedException(this, e.getMessage());
				}
				if (fSymtable.contains(varDecl.name()))
					throw new CompilationFailedException(this, "Variable " + varDecl.name() + " already declared");
	
				varDecls.add(varDecl);
				fSymtable.setType(varDecl.name(), varDecl.type());
			}
		} else {
			fBoundSet = fBody.bound();
			fAssignedSet = fBody.assigned();
		}
		
		MStatement body = generateStatement(fBody);
		
		if (needsExplicit) {
			fSymtable.restoreState(this);
		}
				
		return new MBlockStatement(varDecls, body);
	}


	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[BLOCK]");
		
		if (prelude.length() == 0) {
			prelude.append("+-");
		} else {
			prelude.insert(0, "| ");
		}
		fBody.printTree(prelude, target);
		prelude.delete(0, 2);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("begin ");
		
		if (!fVariableDeclarations.isEmpty()) {
			sb.append("declare ");
			
			for(Iterator<ASTVariableDeclaration> it = fVariableDeclarations.iterator(); it.hasNext();) {
				ASTVariableDeclaration vd = it.next();
				sb.append(vd.toString());
				if (it.hasNext()) sb.append(",");
			}
			sb.append(";");
		}
		
		sb.append(fBody.toString());
		return sb.toString();
	}

}
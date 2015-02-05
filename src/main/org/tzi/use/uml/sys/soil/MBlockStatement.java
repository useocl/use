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

import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * Statement for a block (a local scope of variables).
 * @author Daniel Gent
 *
 */
public class MBlockStatement extends MStatement {

	
	/** The variables that are explicitly declared in this block (using 'declare') */
	private List<VarDecl> fVariableDeclarations;
	
	/** The inner statement of this block */
	private MStatement fBody;
	
	/**
	 * Constructs a new block statement. 
	 * @param varDecls The declared variables inside this block.
	 * @param body The body of the block.
	 */
	public MBlockStatement(List<VarDecl> varDecls, MStatement body) {
		fVariableDeclarations = varDecls;
		fBody = body;
	}
	
	/**
	 * @return the fVariableDeclarations
	 */
	public List<VarDecl> getVariableDeclarations() {
		return Collections.unmodifiableList(fVariableDeclarations);
	}

	/**
	 * @return the fBody
	 */
	public MStatement getBody() {
		return fBody;
	}

	@Override
    public Value execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
	
		for (VarDecl varDecl : fVariableDeclarations) {
			context.getSystem().assignVariable(result, varDecl.name(), UndefinedValue.instance);
		}
		
		fBody.execute(context, result);
		return null;
	}
	
	
	@Override
	protected String shellCommand() {
		StringBuilder sb = new StringBuilder();
		
		if(fVariableDeclarations.size() > 0){
			sb.append("declare ");
			boolean first = true;
			for(VarDecl var : fVariableDeclarations){
				if(!first){
					sb.append(", ");
				}
				sb.append(var.name());
				sb.append(" : ");
				sb.append(var.type().toString());
				first = false;
			}
			sb.append("; ");
		}
		
		sb.append(fBody.shellCommand());
		return sb.toString();
	}
	
	@Override
	protected void toConcreteSyntax(
			StringBuilder indent, 
			String indentIncrease,
			StringBuilder target) {
		
		if(fVariableDeclarations.size() > 0){
			target.append(indent);
			target.append("declare ");
			boolean first = true;
			for(VarDecl var : fVariableDeclarations){
				if(!first){
					target.append(", ");
				}
				target.append(var.name());
				target.append(" : ");
				target.append(var.type().toString());
				first = false;
			}
			target.append(";\n");
		}
		
		fBody.toConcreteSyntax(indent, indentIncrease, target);
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

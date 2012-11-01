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

// $Id: MIterationStatement.java 2747 2011-11-25 14:53:17Z lhamann $

package org.tzi.use.uml.sys.soil;

import java.util.List;

import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MBlockStatement extends MStatement {

	
	/** The variables that are explicitly declared in this block (using 'declare') */
	private List<VarDecl> fVariableDeclarations;
	
	/** The inner statement of this block */
	private MStatement fBody;
	
	
	/**
	 * TODO
	 * @param variableName
	 * @param range
	 * @param body
	 */
	public MBlockStatement(List<VarDecl> varDecls, MStatement body) {
		fVariableDeclarations = varDecls;
		fBody = body;
	}
	

	@Override
    public void execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
	
		for (VarDecl varDecl : fVariableDeclarations) {
			context.getSystem().assignVariable(result, varDecl.name(), UndefinedValue.instance);
		}
		fBody.execute(context, result);
	}
	
	
	@Override
	protected String shellCommand() {
		//TODO: implement shellCommand()
		return "TODO";
	}
	


	@Override
	protected void toConcreteSyntax(
			StringBuilder indent, 
			String indentIncrease,
			StringBuilder target) {
		//TODO: implement toVisitorString()
	}
	
	 
	@Override
	public String toString() {
		return shellCommand();
	}
}

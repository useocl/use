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
import java.util.List;
import java.util.Map;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;
import org.tzi.use.util.soil.exceptions.compilation.OperationDefinedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTEnterOperationStatement extends ASTStatement {
	/** TODO */
	private ASTExpression fObject;
	/** TODO */
	private String fOperationName;
	/** TODO */
	private List<ASTExpression> fArguments;
	
	
	/**
	 * TODO
	 * @param self
	 * @param operationName
	 * @param arguments
	 */
	public ASTEnterOperationStatement(
			ASTExpression object, 
			String operationName, 
			List<ASTExpression> arguments) {
		
		fObject = object;
		fOperationName = operationName;
		fArguments = arguments;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTExpression getObject() {
		return fObject;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public String getOperationName() {
		return fOperationName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public List<ASTExpression> getArguments() {
		return fArguments;
	}
	
	
	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		Expression object = generateObjectExpression(fObject);
		
		MOperation operation = 
			generateOperation(
					((ObjectType)object.type()).cls(), 
					fOperationName);
		
		if (operation.hasExpression() || operation.hasStatement()) {
			throw new OperationDefinedException(this, operation);
		}
		
		// construct arguments
		Map<String, Expression> arguments = 
			generateOperationArguments(operation, fArguments);
		
		return new MEnterOperationStatement(object, operation, arguments);
	}

	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "ENTER OPERATION");
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("openter ");
		sb.append(fOperationName);
		sb.append("(");
		StringUtil.fmtSeq(sb, fArguments, ", ");
		sb.append(")");
		
		return sb.toString();
	}
}

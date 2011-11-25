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

import java.util.List;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTSimpleType;
import org.tzi.use.parser.ocl.ASTStringLiteral;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueNewLinkObject;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTRValueNewLinkObject extends ASTRValue {
	/** TODO */
	private ASTNewLinkObjectStatement fNewLinkObjectStatement;
	
	
	/**
	 * TODO
	 * @param newLinkObjectStatement
	 */
	public ASTRValueNewLinkObject(
			ASTNewLinkObjectStatement newLinkObjectStatement) {
		
		fNewLinkObjectStatement = newLinkObjectStatement;		
	}
	
	
	/**
	 * TODO
	 * @param associationClassName
	 * @param participants
	 * @param linkObjectName
	 */
	public ASTRValueNewLinkObject(
			ASTSimpleType associationClassName,
			List<ASTRValue> participants,
			List<List<ASTRValue>> qualifierValues,
			ASTExpression linkObjectName) {
		
		this(new ASTNewLinkObjectStatement(
				associationClassName, 
				participants,
				qualifierValues,
				linkObjectName));
	}
	
	
	/**
	 * TODO
	 * @param associationClassName
	 * @param participants
	 * @param linkObjectName
	 */
	public ASTRValueNewLinkObject(
			ASTSimpleType associationClassName,
			List<ASTRValue> participants,
			List<List<ASTRValue>> qualifierValues,
			String linkObjectName) {
		
		this(new ASTNewLinkObjectStatement(
				associationClassName, 
				participants,
				qualifierValues,
				new ASTStringLiteral(linkObjectName)));
	}
	
	
	/**
	 * TODO
	 * @param associationClassName
	 * @param participants
	 */
	public ASTRValueNewLinkObject(
			ASTSimpleType associationClassName, 
			List<ASTRValue> participants,
			List<List<ASTRValue>> qualifierValues) {
		
		this(new ASTNewLinkObjectStatement(
				associationClassName, 
				participants,
				qualifierValues));	
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTNewLinkObjectStatement getNewLinkObjectStatement() {
		return fNewLinkObjectStatement;
	}

	
	@Override
	protected MRValue generate() throws CompilationFailedException {
		
		MStatement subStatement = 
			fParent.generateStatement(fNewLinkObjectStatement);
		
		return new MRValueNewLinkObject((MNewLinkObjectStatement)subStatement);
	}


	@Override
	public String toString() {
		return fNewLinkObjectStatement.toString();
	}
}

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

package org.tzi.use.util.soil.exceptions.compilation;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.parser.soil.ast.ASTStatement;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class CompilationFailedException extends Exception {
	/** TODO */
	private static final long serialVersionUID = 1L;
	/** TODO */
	private ASTStatement fInvalidStatement;
	
	
	/**
	 * TODO
	 * @param statement
	 * @param message
	 */
	public CompilationFailedException(
			ASTStatement statement,
			String message) {
		
		super(message);
		fInvalidStatement = statement;
	}
	
	
	/**
	 * TODO
	 * @param statement
	 * @param message
	 * @param cause
	 */
	public CompilationFailedException(
			ASTStatement statement,
			String message,
			Throwable cause) {
		
		super(message, cause);
		fInvalidStatement = statement;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTStatement getInvalidStatement() {
		return fInvalidStatement;
	}
	
	
	/**
	 * TODO
	 * @param topLevelStatement
	 * @return
	 */
	public String getMessage(ASTStatement topLevelStatement) {
		
		if (fInvalidStatement.isEmptyStatement()) {
			return super.getMessage();
		}
		
		String locationString;
		if (fInvalidStatement.hasSourcePosition()) {
			SrcPos sourcePosition = fInvalidStatement.getSourcePosition();
			
			locationString = 
				sourcePosition.srcName() +
				":" +
				sourcePosition.line() +
				":" +
				sourcePosition.column();
		} else {
			locationString = "<unknown location>";
		}
		
		return locationString + ": " + super.getMessage(); 
	}
}

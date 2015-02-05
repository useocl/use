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

package org.tzi.use.util.soil.exceptions;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.parser.soil.ast.ASTStatement;


/**
 * This exception is used if a soil statement
 * could not be "compiled". 
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class CompilationFailedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/** The source position, if any of the failed statement **/
	private SrcPos sourcePosition;
	
	/**
	 * Constructs a new exception using the AST of the statement failed to be compiled and a message.
	 * @param statement The AST of the failed statement.
	 * @param message A user defined message.
	 */
	public CompilationFailedException(
			ASTStatement statement,
			String message) {
		
		super(message);
		sourcePosition = statement.getSourcePosition();
	}
	
	
	/**
	 * Constructs a new exception using the AST of the statement failed to be 
	 * compiled, a message and the original cause.
	 * @param statement The AST of the failed statement.
	 * @param message A user defined message.
	 */
	public CompilationFailedException(
			ASTStatement statement,
			String message,
			Throwable cause) {
		
		super(message, cause);
		sourcePosition = statement.getSourcePosition();
	}
	
	@Override
	public String getMessage() {
		return getMessage(false);
	}
	
	public String getMessage(boolean includePositionInfomation) {
		if (!includePositionInfomation) return super.getMessage();
		
		String locationString;
		if (sourcePosition != null) {
			locationString = sourcePosition.toString();
		} else {
			locationString = "<unknown location>";
		}
		
		return locationString + super.getMessage();
	}
}

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

package org.tzi.use.util.soil.exceptions.evaluation;

import org.tzi.use.uml.sys.soil.MStatement;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public abstract class EvaluationFailedException extends Exception {
	/** TODO */
	private static final long serialVersionUID = 1L;
	/** TODO */
	private MStatement fFailedStatement;
	
	
	/**
	 * TODO
	 * @param failedStatement
	 * @param message
	 */
	public EvaluationFailedException(
			MStatement failedStatement, 
			String message) {
		
		super(message);
		fFailedStatement = failedStatement;
	}
	
	
	/**
	 * TODO
	 * @param failedStatement
	 * @param message
	 * @param cause
	 */
	public EvaluationFailedException(
			MStatement failedStatement, 
			String message,
			Throwable cause) {
		
		super(message, cause);
		fFailedStatement = failedStatement;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MStatement getFailedStatement() {
		return fFailedStatement;
	}
	
	
	/**
	 * 
	 * @param topLevelStatement
	 * @return
	 */
	public String getMessage(MStatement topLevelStatement) {
		return getMessage();
		/*StringBuilder message = new StringBuilder();
	
		message.append("   Evaluation of statement failed.\n");
		
		message.append("Reason:   ");
		message.append(getMessage());
		message.append("\n");
		
		return message.toString();*/
	}
}

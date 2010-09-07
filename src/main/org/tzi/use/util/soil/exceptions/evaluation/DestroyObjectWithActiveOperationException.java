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

import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;

/**
 * TODO
 * @author Daniel Gent
 *
 */
public class DestroyObjectWithActiveOperationException extends
		EvaluationFailedException {

	/** TODO */
	private static final long serialVersionUID = 1L;

	
	/**
	 * TODO
	 * @param failedStatement
	 * @param message
	 */
	public DestroyObjectWithActiveOperationException(
			MStatement failedStatement, 
			MObject deletee) {
		
		
		super(
				failedStatement, 
				"Object " +
				StringUtil.inQuotes(deletee) +
				" has an active operation and thus cannot be deleted.");
	}
}

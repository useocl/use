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

package org.tzi.use.uml.sys.ppcHandling;

import org.tzi.use.uml.sys.MOperationCall;


/**
 * Base class for pre and postconditin failed exceptions.
 * @author Daniel Gent
 *
 */
public abstract class PPCCheckFailedException extends Exception {
	private static final long serialVersionUID = 1L;
	/** The operation call which failed. */
	private MOperationCall fOperationCall;
	
	/**
	 * Constructor with all needed information. 
	 * @param opCall The operation call which failed
	 */
	public PPCCheckFailedException(MOperationCall opCall) {
		fOperationCall = opCall;
	}
	
	/**
	 * The operation call which failed
	 * @return The operation call which failed
	 */
	public MOperationCall getOperationCall() {
		return fOperationCall;
	}
}

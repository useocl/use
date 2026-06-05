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
import org.tzi.use.uml.sys.MSystem;

/**
 * Interface used by the validation engine to handle
 * pre- and post-conditions.
 * @author Daniel Gent
 * @author Lars Hamann
 *
 */
public interface PPCHandler {
	
	/**
	 * Called if the pre conditions of an operation need to be handled.
	 * @param system
	 * @param operationCall
	 * @throws PreConditionCheckFailedException
	 */
	public abstract void handlePreConditions(
			MSystem system, 
			MOperationCall operationCall) throws PreConditionCheckFailedException;
	
	/**
	 * Called if the post conditions of an operation need to be handled.
	 * @param system
	 * @param operationCall
	 * @throws PostConditionCheckFailedException
	 */
	public abstract void handlePostConditions(
			MSystem system, 
			MOperationCall operationCall) throws PostConditionCheckFailedException;
	
	
	/**
	 * Called after the possible transitions of an operation call
	 * were calculated.
	 * @param system
	 * @param operationCall
	 * @throws PreConditionCheckFailedException
	 */
	public abstract void handleTransitionsPre(
			MSystem system, 
			MOperationCall operationCall) throws PreConditionCheckFailedException;
	
	/**
	 * Called after the transition to execute was calculated.
	 * @param system
	 * @param operationCall
	 * @throws PostConditionCheckFailedException
	 */
	public abstract void handleTransitionsPost(
			MSystem system, 
			MOperationCall operationCall) throws PostConditionCheckFailedException;
}

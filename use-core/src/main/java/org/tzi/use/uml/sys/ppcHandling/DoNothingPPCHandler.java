/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.tzi.use.uml.sys.ppcHandling;

import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;

/**
 * This PPC handler ignores every call.
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class DoNothingPPCHandler implements PPCHandler {
	/** The singleton instance */
	private static final DoNothingPPCHandler fInstance = 
		new DoNothingPPCHandler();

	/** Singleton */
	private DoNothingPPCHandler() {}
	
	
	/**
	 * Returns the singleton instance.
	 * @return
	 */
	public static DoNothingPPCHandler getInstance() {
		return fInstance;
	}
	
	
	@Override
	public void handlePreConditions(
			MSystem system, 
			MOperationCall operationCall) throws PreConditionCheckFailedException {
		
	}


	@Override
	public void handlePostConditions(
			MSystem system,
			MOperationCall operationCall) throws PostConditionCheckFailedException {
		
	}


	@Override
	public void handleTransitionsPre(MSystem system,
			MOperationCall operationCall)
			throws PreConditionCheckFailedException {
				
	}


	@Override
	public void handleTransitionsPost(MSystem system,
			MOperationCall operationCall)
			throws PostConditionCheckFailedException {
				
	}
}

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

package org.tzi.use.uml.sys.soil;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;
import org.tzi.use.util.soil.exceptions.evaluation.ExceptionOccuredException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MObjectRestorationStatement extends MStatement {
	/** TODO */
	private DeleteObjectResult fDeleteObjectResult;
	/** TODO */
	private Map<MObject, List<String>> fUndefinedTopLevelReferences;
	
	
	/**
	 * 
	 * @param deleteObjectResult
	 * @param undefinedTopLevelReferences
	 */
	public MObjectRestorationStatement(
			DeleteObjectResult deleteObjectResult, 
			Map<MObject, List<String>> undefinedTopLevelReferences) {
		
		fDeleteObjectResult = deleteObjectResult;
		fUndefinedTopLevelReferences = undefinedTopLevelReferences;
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		// restore objects
		Set<MObjectState> removedObjectStates = 
			fDeleteObjectResult.getRemovedObjectStates();
		
		for (MObjectState objectState : removedObjectStates) {
            try {
                fState.restoreObject(objectState);  
                fResult.getStateDifference().addNewObject(objectState.object());
            } catch (MSystemException e) {
                throw new ExceptionOccuredException(this, e);
            }
        }
		
		// restore links
		Set<MLink> removedLinks = fDeleteObjectResult.getRemovedLinks();
	
        for (MLink link : removedLinks) {
            fState.insertLink(link);
            fResult.getStateDifference().addNewLink(link);
        }
        
        // restore top level variables
        Set<Entry<MObject, List<String>>> undefinedVariables =
        	fUndefinedTopLevelReferences.entrySet();
        for (Entry<MObject, List<String>> entry :undefinedVariables) {
        	Value value = entry.getKey().value();
        	for (String name : entry.getValue()) {
        		fVarEnv.assign(name, value);
        	}
        }
	}
	
	
	@Override
	protected String shellCommand() {
		return "<object restoration>";
	}
	
	
	@Override
	public boolean hasSideEffects() {
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();
	}


	/* (non-Javadoc)
	 * @see org.tzi.use.uml.sys.soil.MStatement#mayGenerateUnqiueNames()
	 */
	@Override
	public boolean mayGenerateUnqiueNames() {
		return false;
	}
}

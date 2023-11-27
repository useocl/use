/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.api.impl;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.main.Session;
import org.tzi.use.output.InternalUserOutput;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.*;
import org.tzi.use.uml.sys.soil.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This system API implementation uses the internal SOIL
 * implementation to change the system state.
 * It should be used if a normal USE session is running,
 * e.g., if using the API inside a PlugIn.
 * @author Lars Hamann
 *
 */
public class UseSystemApiUndoable extends UseSystemApi {
	public UseSystemApiUndoable(Session session) {
		super(session.system());
	}
	
	public UseSystemApiUndoable(MSystem system) {
		super(system);
	}
	
	public UseSystemApiUndoable(MModel model) {
		super(model);
	}
	
	@Override
	public MObject createObjectEx(MClass objectClass, String objectName)
			throws UseApiException {
		
		StatementEvaluationResult res;
		
		try {
			res = evaluateStatement(
					new MNewObjectStatement(
							objectClass, 
							objectName));
		} catch (MSystemException e) {
			throw new UseApiException("Object creation failed!", e);
		}
		
		return res.getStateDifference().getNewObjects().iterator().next();
	}
	
	@Override
	public void setAttributeValueEx(MObject object, MAttribute attribute, Value value) throws UseApiException {
    	try {
			evaluateStatement(
					new MAttributeAssignmentStatement(
							object, 
							attribute, 
							value));
		} catch (MSystemException e) {
			throw new UseApiException("Assignment of attribute value failed!", e);
		}
    }
	
	@Override
	public MLink createLinkEx(MAssociation association, MObject[] connectedObjects, Value[][] qualifierValues) throws UseApiException {
		List<List<Value>> qualifierValuesList = getQualifierValuesAsList(qualifierValues);
		
		StatementEvaluationResult res;
		try {
			res = evaluateStatement(new MLinkInsertionStatement(association,
					connectedObjects, qualifierValuesList));
		} catch (MSystemException e) {
			throw new UseApiException("Link creation failed!", e);
		}

        return res.getStateDifference().getNewLinks().iterator().next();
	}
	
	@Override
	public MLinkObject createLinkObjectEx(
			MAssociationClass associationClass,
			String newObjectName,
			MObject[] connectedObjects,
			Value[][] qualifierValues) throws UseApiException {
		
		List<List<Value>> qualifierValuesList = getQualifierValuesAsList(qualifierValues);
		
		StatementEvaluationResult res;
		
		try {
			res = evaluateStatement(
					new MNewLinkObjectStatement(
							associationClass, 
							connectedObjects, 
							qualifierValuesList,
							newObjectName));
		} catch (MSystemException e) {
			throw new UseApiException("Link object creation failed!", e);
		}
		
		return (MLinkObject)res.getStateDifference().getNewObjects().iterator().next();
	}

	@Override
	public void deleteLinkEx(
    		MAssociation association,
    		MObject[] connectedObjects,
    		Value[][] qualifierValues) throws UseApiException {

		List<List<MRValue>> qualifierValuesList;
		
		if (qualifierValues == null || qualifierValues.length == 0) {
			qualifierValuesList = Collections.emptyList();
		} else {
			qualifierValuesList = new ArrayList<List<MRValue>>(qualifierValues.length);
            for (Value[] qualifierValue : qualifierValues) {
                if (qualifierValue == null || qualifierValue.length == 0) {
                    qualifierValuesList.add(Collections.<MRValue>emptyList());
                } else {
                    for (int iValue = 0; iValue < qualifierValue.length; ++iValue) {
                        qualifierValuesList.get(iValue).add(new MRValueExpression(qualifierValue[iValue]));
                    }
                }
            }
		}
		    	
    	try {
			evaluateStatement(
					new MLinkDeletionStatement(
							association, 
							connectedObjects, 
							qualifierValuesList));
		} catch (MSystemException e) {
			throw new UseApiException("Link could not be deleted!", e);
		}
    }
	
	@Override
	public void deleteLinkEx(MLink link) throws UseApiException {
		List<List<MRValue>> qualifiers = new ArrayList<List<MRValue>>();
		MAssociation association = link.association();
		int iNumEnds = association.associationEnds().size();
		
		for (int iEnd = 0; iEnd < iNumEnds; ++iEnd) {
			MAssociationEnd end = association.associationEnds().get(iEnd);
			if (end.hasQualifiers()) {
				List<MRValue> qValues = new ArrayList<MRValue>();
				for (int iValue = 0; iValue < end.getQualifiers().size(); ++iValue) {
					qValues.add(new MRValueExpression(link.linkEnd(end).getQualifierValues().get(iValue)));
				}
				qualifiers.add(qValues);
			} else {
				qualifiers.add(Collections.<MRValue>emptyList());
			}
		}
		
		try {
			evaluateStatement(
					new MLinkDeletionStatement(
							link.association(),
							link.linkedObjectsAsArray(),
							qualifiers));
		} catch (MSystemException e) {
			throw new UseApiException("Link could not be deleted!", e);
		}
	}
	
	@Override
	public void deleteObjectEx(MObject object) throws UseApiException {
		try {
			evaluateStatement(
					new MObjectDestructionStatement(
							new ObjectValue(object.cls(), object)));
		} catch (MSystemException e) {
			throw new UseApiException("Object could not be deleted!", e);
		}
	}

	@Override
	public void undo() throws UseApiException, OperationNotSupportedException {
		InternalUserOutput out = new InternalUserOutput();

		try {
			system.undoLastStatement(out);

			if (out.hasOutput()) {
				this.setLastWarningMessage(out.getOutput());
			}

		} catch (MSystemException e) {
			throw new UseApiException("Error during undo of last statement.", e);
		}
	}
	
	@Override
	public void redo() throws UseApiException, OperationNotSupportedException {
		InternalUserOutput out = new InternalUserOutput();

		try {
			system.redoStatement(out);

			if (out.hasOutput()) {
				this.setLastWarningMessage(out.getOutput());
			}
		} catch (MSystemException e) {
			throw new UseApiException("Error during redo.", e);
		}
	}
	
	/**
     * Executes a SOIL statement on the system.
	 *
     * @param statement the <code>MStatement</code> to execute.
     * @throws MSystemException If the execution produces an error.
     */
    private final StatementEvaluationResult evaluateStatement(MStatement statement) throws MSystemException {
		InternalUserOutput out = new InternalUserOutput();
		StatementEvaluationResult result =  system.execute(out, statement);

		if (out.hasOutput()) {
			this.setLastWarningMessage(out.getOutput());
		}

		return result;
    }
}

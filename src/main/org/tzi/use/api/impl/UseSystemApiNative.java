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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.StringUtil;

/**
 * This system API implementation uses the native internal 
 * implementation to change the system state.
 * It should be used if USE is referenced as a library
 * e.g., from another application.
 * @author Lars Hamann
 *
 */
public class UseSystemApiNative extends UseSystemApi {

	public UseSystemApiNative(MModel model) {
		super(model);
	}
	
	public UseSystemApiNative(MSystem system) {
		super(system);
	}

	@Override
	public MObject createObjectEx(MClass objectClass, String objectName)
			throws UseApiException {
		try {
			return system.state().createObject(objectClass, objectName);
		} catch (MSystemException e) {
			throw new UseApiException("Object named "
					+ StringUtil.inQuotes(objectName)
					+ " could not be created!", e);
		}
	}

	@Override
	public void setAttributeValueEx(MObject object, MAttribute attribute,
			Value value) throws UseApiException {
		try {
			object.state(system.state()).setAttributeValue(attribute, value);
        } catch (IllegalArgumentException e) {
            throw new UseApiException("Attribute could not be assigned!", e);
        }
	}

	@Override
	public MLink createLinkEx(MAssociation association,
			MObject[] connectedObjects, Value[][] qualifierValues)
			throws UseApiException {
		MLink newLink;
		List<List<Value>> qualifierValuesList = getQualifierValuesAsList(qualifierValues);
		
		try {
			newLink = system.state().createLink(association,
					Arrays.asList(connectedObjects), qualifierValuesList);
        } catch (MSystemException e) {
            throw new UseApiException("Link could not be created!", e);
        }
		
		return newLink;
	}

	@Override
	public MLinkObject createLinkObjectEx(MAssociationClass associationClass,
			String newObjectName, MObject[] connectedObjects,
			Value[][] qualifierValues) throws UseApiException {
		
		MLinkObject linkObject;
		List<List<Value>> qualifierValuesList = getQualifierValuesAsList(qualifierValues);
		
		try {
			linkObject = system.state().createLinkObject(associationClass,
					newObjectName, Arrays.asList(connectedObjects), qualifierValuesList);
			
        } catch (MSystemException e) {
            throw new UseApiException("Link object could not be created!", e);
        }
		
		return linkObject;
	}

	@Override
	public void deleteObjectEx(MObject object) throws UseApiException {
		system.state().deleteObject(object);
	}

	@Override
	public void deleteLinkEx(MAssociation association,
			MObject[] connectedObjects, Value[][] qualifierValues)
			throws UseApiException {
		List<List<Value>> qualifierValuesList = getQualifierValuesAsList(qualifierValues);
		
		try {
			system.state().deleteLink(association, Arrays.asList(connectedObjects), qualifierValuesList);
		} catch (MSystemException e) {
			throw new UseApiException("Link could not be deleted!", e);
		}
	}

	@Override
	public void deleteLinkEx(MLink link) throws UseApiException {
		
		List<List<Value>> qualifiers = new ArrayList<List<Value>>();
		MAssociation association = link.association();
		int iNumEnds = association.associationEnds().size();
		
		for (int iEnd = 0; iEnd < iNumEnds; ++iEnd) {
			MAssociationEnd end = association.associationEnds().get(iEnd);
			if (end.hasQualifiers()) {
				List<Value> qValues = new ArrayList<Value>();
				for (int iValue = 0; iValue < end.getQualifiers().size(); ++iValue) {
					qValues.add(link.linkEnd(end).getQualifierValues().get(iValue));
				}
			} else {
				qualifiers.add(Collections.<Value>emptyList());
			}
		}
		
		try {
			system.state().deleteLink(link.association(), link.linkedObjects(), qualifiers);
		} catch (MSystemException e) {
			throw new UseApiException("Link could not be deleted!", e);
		}		
	}

	@Override
	public void undo() throws UseApiException, OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void redo() throws UseApiException, OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
}

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

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.collections.Bag;

/**
 * A derived link controller which handles derived
 * association ends. 
 * @author Lars Hamann
 *
 */
public class DerivedLinkControllerDerivedEnd extends DerivedLinkController {

	/**
	 * @param state
	 * @param linkSets
	 */
	public DerivedLinkControllerDerivedEnd(MSystemState state,
			Map<MAssociation, MLinkSet> linkSets) {
		super(state, linkSets);
	}
	
	/**
	 * @param state
	 * @param linkSets
	 * @param derivedLinkController
	 */
	public DerivedLinkControllerDerivedEnd(MSystemState state,
			Map<MAssociation, MLinkSet> linkSets,
			DerivedLinkController derivedLinkController) {
		super(state, linkSets, derivedLinkController);
	}


	@Override
	protected boolean handles(MAssociationEnd end) {
		return end.isDerived();
	}

	@Override
	protected void determineDerivedLinks(Set<MDerivedLink> linksSink, MAssociationEnd associationEnd) {
		
		int numEnds = associationEnd.association().associationEnds().size();
		int dstIndex = associationEnd.association().associationEnds().indexOf(associationEnd);
		
		// Get all objects of the source end (the opposite end).
		List<MAssociationEnd> sourceEnds = associationEnd.getAllOtherAssociationEnds();
		List<MClass> otherClasses = new ArrayList<MClass>();
		int[] endIndices = new int[numEnds -1];
		
		int i = 0;
		for (MAssociationEnd sourceEnd : sourceEnds) {
			otherClasses.add(sourceEnd.cls());
			endIndices[i] = associationEnd.association().associationEnds().indexOf(sourceEnd);
			++i;
		}
		
		Bag<MObject[]> sourceObjects = state.getCrossProductOfInstanceSets(otherClasses);
		
		for (MObject[] objects : sourceObjects) {
			MObject[] linkObjects = new MObject[numEnds];

			List<MObject> linkedObjects;
			try {
				linkedObjects = state.evaluateDeriveExpression(objects, associationEnd);
			} catch (MSystemException e1) {
				Log.error("Derive expression of association end " + StringUtil.inQuotes(associationEnd) + " let to a runtime exception: " + e1.getMessage());
				continue;
			}
			
			for (int index = 0; index < numEnds - 1; ++index) {
				linkObjects[endIndices[index]] = objects[index];
			}
			
			for (MObject targetObject : linkedObjects) {
				linkObjects[dstIndex] = targetObject;
				try {
					MDerivedLink link = new MDerivedLink(
							associationEnd.association(),
							Arrays.asList(linkObjects));
					linksSink.add(link);
				} catch (MSystemException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}

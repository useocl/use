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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.value.Value;

/**
 * A derived link controller which handles
 * association ends marked as union.
 * @author Lars Hamann
 *
 */
public class DerivedLinkControllerUnion extends DerivedLinkController {

	/**
	 * @param state
	 * @param linkSets
	 */
	public DerivedLinkControllerUnion(MSystemState state,
			Map<MAssociation, MLinkSet> linkSets) {
		super(state, linkSets);
	}

	/**
	 * @param state
	 * @param linkSets
	 * @param derivedLinkController
	 */
	public DerivedLinkControllerUnion(MSystemState state,
			Map<MAssociation, MLinkSet> linkSets,
			DerivedLinkController derivedLinkController) {
		super(state, linkSets, derivedLinkController);
	}

	@Override
	protected boolean handles(MAssociationEnd end) {
		return end.isUnion();
	}

	/**
	 * If a subsetting association end is derived, it is
	 * best to use this end to determine
	 * the subset. This at minimum eliminates the inclusion
	 * check of the result (see: <code>Lars Hamann and Martin Gogolla; 
	 * Endogenous Metamodeling Semantics for Structural UML 2 Concepts;
	 * MoDELS 2013</code>).
	 */
	@Override
	protected MAssociationEnd determineBestEnd(MAssociationEnd end) {
		MAssociation assoc = end.association();
		
		for (MAssociation subsettingAssoc : assoc.getSubsettedByClosure()) {
			for (int i = 0; i < assoc.associationEnds().size(); ++i) {
				if (subsettingAssoc.associationEnds().get(i).isDerived()) {
					return assoc.associationEnds().get(i);
				}
			}
		}
		
		return end;
	}
	
	@Override
	protected void determineDerivedLinks(Set<MDerivedLink> linksSink, MAssociationEnd associationEnd) {
		MAssociationEnd srcEnd = associationEnd.getAllOtherAssociationEnds().get(0);
		List<MObject> connected;
		
		MObject[] linkObjects = new MObject[2];
		int iSrcEnd = associationEnd.association().associationEnds().indexOf(srcEnd);
		int iTrgEnd = associationEnd.association().associationEnds().indexOf(associationEnd);
		
		for (MObject objSource : state.objectsOfClassAndSubClasses(srcEnd.cls())) {
			linkObjects[iSrcEnd] = objSource;
			connected = state.getNavigableObjects(objSource,
					associationEnd.association(), iSrcEnd, iTrgEnd,
					Collections.<Value> emptyList(), true, false);
			
			for (MObject conObject : connected) {
				linkObjects[iTrgEnd] = conObject;
				try {
					linksSink.add(new MDerivedLink(associationEnd.association(), Arrays.asList(linkObjects)));
				} catch (MSystemException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}

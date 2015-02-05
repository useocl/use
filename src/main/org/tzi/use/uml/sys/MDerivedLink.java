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

import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.value.Value;

/**
 * This class saves information about a derived link.
 * @author Lars Hamann
 *
 */
class MDerivedLink extends MLinkImpl {
	/**
     * Creates a new derived link for the given association.
     *
     * @param assoc The association to create the link for.
     * @param objects The list of connected objects in the same order as the association ends.
     * @exception MSystemException objects do not conform to the
     *            association ends.
     */
	MDerivedLink(MAssociation assoc, List<MObject> objects) throws MSystemException {
		super(assoc, objects, Collections.<List<Value>>emptyList());
	}
	
	@Override
	public final boolean isVirtual() {
		return true;
	}
}

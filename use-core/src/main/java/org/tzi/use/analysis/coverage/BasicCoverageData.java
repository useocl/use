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

package org.tzi.use.analysis.coverage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.tzi.use.uml.mm.*;

/**
 * Basic coverage data, i. e., sets of model elements.
 * @author Lars Hamann
 *
 */
public class BasicCoverageData {
	private Set<MClassifier> coveredClasses = new HashSet<MClassifier>();
	private Set<MAttribute> coveredAttributes = new HashSet<MAttribute>();
	private Set<MOperation> coveredOperations = new HashSet<MOperation>();
	private Set<MAssociation> coveredAssociations = new HashSet<MAssociation>();
	/**
	 * @return the coveredClasses
	 */
	public Set<MClassifier> getCoveredClasses() {
		return coveredClasses;
	}
	/**
	 * @return the coveredAttributes
	 */
	public Set<MAttribute> getCoveredAttributes() {
		return coveredAttributes;
	}
	/**
	 * @return the coveredOperations
	 */
	public Set<MOperation> getCoveredOperations() {
		return coveredOperations;
	}
	/**
	 * @return the coveredAssociations
	 */
	public Set<MAssociation> getCoveredAssociations() {
		return coveredAssociations;
	}
	
	/**
	 * Returns <code>true</code> if this coverage data and the provided coverage
	 * data have no common elements.
	 * @param data
	 * @return
	 */
	public boolean disjoint(BasicCoverageData data) {
		return
				Collections.disjoint(this.coveredClasses, data.coveredClasses) &&
				Collections.disjoint(this.coveredAttributes, data.coveredAttributes) &&
				Collections.disjoint(this.coveredAssociations, data.coveredAssociations);
	}
}

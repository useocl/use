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
package org.tzi.use.uml.sys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.util.soil.StateDifference;

/**
 * This class controls the "virtual" links inside a system state.
 * Virtual links are links which are derived by an OCL expression.
 * We currently assume that:
 * <ul>
 *   <li>Only one end is derived.</li>
 *   <li>No qualifiers can be specified for a derived association</li>
 * </ul>
 * @author Lars Hamann
 *
 */
public abstract class DerivedLinkController implements DerivedValueController {

	protected final MSystemState state;
	
	private Set<MAssociationEnd> derivedAssociationEnds;
		
	private Set<MDerivedLink> derivedLinks;
	
	private Map<MAssociation, MLinkSet> linkSets;
	
	public DerivedLinkController(MSystemState state, Map<MAssociation, MLinkSet> linkSets) {
		this.state = state;
		this.linkSets = linkSets;
		this.derivedLinks = Collections.emptySet();
	}

	/**
	 * Copy constructor
	 * @param derivedLinkController
	 */
	public DerivedLinkController(MSystemState state, Map<MAssociation, MLinkSet> linkSets, DerivedLinkController derivedLinkController) {
		this.state = state;
		this.linkSets = linkSets;
		this.derivedAssociationEnds = new HashSet<MAssociationEnd>(derivedLinkController.derivedAssociationEnds);
		this.derivedLinks = new HashSet<MDerivedLink>(derivedLinkController.derivedLinks);
	}

	/**
	 * Initializes the derived link controller.
	 */
	public void initState() {
		determineDerivedAssociations();
		derivedLinks = determineDerivedLinks();
		for (MDerivedLink link : derivedLinks) {
			linkSets.get(link.association()).add(link);
		}
	}
	
	/**
	 * Calculates all derived links.
	 */
	private Set<MDerivedLink> determineDerivedLinks() {
		Set<MDerivedLink> links = new HashSet<MDerivedLink>(derivedLinks.size());
		
		for (MAssociationEnd associationEnd : derivedAssociationEnds) {			
			determineDerivedLinks(links, associationEnd);
		}
		
		return links;
	}

	/**
	 * Calculates all derived links for a single derived association end.
	 * @param associationEnd
	 * @return
	 */
	protected abstract void determineDerivedLinks(Set<MDerivedLink> linksSink, MAssociationEnd associationEnd);
	
	/**
	 * Saves all derived associations in the system for faster
	 * access.
	 */
	private void determineDerivedAssociations() {
		derivedAssociationEnds = new HashSet<MAssociationEnd>();
		for (MAssociation association : state.system().model().associations()) {
			for (MAssociationEnd end : association.associationEnds()) {
				if (handles(end)) {
					end = determineBestEnd(end);
					derivedAssociationEnds.add(end);
					break;
				}
			}
		}
	}

	/**
	 * Subclasses can override this method to change the 
	 * association end the concrete derived link controller
	 * uses to calculate the derived links.
	 *  
	 * @param end The end selected by {@link #determineDerivedAssociations()}.
	 * @return A possible more suitable end of the same association, that should be
	 *         used to calculate the derived links.
	 * @see DerivedLinkControllerUnion#determineBestEnd(MAssociationEnd)        
	 */
	protected MAssociationEnd determineBestEnd(MAssociationEnd end) {
		return end;
	}

	/**
	 * Used by {@link #determineDerivedAssociations()} to determine,
	 * if an association end is handled by a concrete {@link DerivedLinkController}.
	 * For example, the {@link DerivedLinkControllerUnion} handles ends, that
	 * are marked as <code>{union}</code>.
	 * 
	 * @param end The end to query
	 * @return <code>true</code>, if the concrete controller handles this end.
	 */
	protected abstract boolean handles(MAssociationEnd end);
	
	@Override
	public void updateState() {
		// This is a rather naive implementation
		// We get all currently derived links and compare them to the previously
		// calculated ones
		Set<MDerivedLink> allDerivedLinks = determineDerivedLinks();
		
		// Get the new links
		for (MDerivedLink link : allDerivedLinks) {
			MLinkSet linkSet = linkSets.get(link.association());
			
			if (!linkSet.contains(link)) {
				linkSet.add(link);
			}
		}
		
		// safe the new derived links
		derivedLinks = allDerivedLinks;
	}
	
	@Override
	public final void updateState(StateDifference diff) {
		// This is a rather naive implementation
		// We get all currently derived links and compare them to the previously
		// calculated ones
		Set<MDerivedLink> allDerivedLinks = determineDerivedLinks();
		
		// Get the new links
		for (MDerivedLink link : allDerivedLinks) {
			if (!derivedLinks.contains(link)) {
				linkSets.get(link.association()).add(link);
				diff.addNewLink(link);
				state.system().fireLinkInserted(link);
			} else {
				// remove from original set, so we get all deleted links at the end
				derivedLinks.remove(link);
			}
		}
		
		for (MDerivedLink link : derivedLinks) {
			linkSets.get(link.association()).remove(link);
			diff.addDeletedLink(link);
			state.system().fireLinkDeleted(link);
		}
		
		// safe the new derived links
		derivedLinks = allDerivedLinks;
	}
}

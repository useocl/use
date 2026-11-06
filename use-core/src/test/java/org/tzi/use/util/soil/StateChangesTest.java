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

package org.tzi.use.util.soil;


import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;


/**
 * Test cases for various methods of {@code StateChanges}
 * 
 * @author Daniel Gent
 * @see StateDifference
 */
public class StateChangesTest extends TestCase {
	/** the test subject */
	private StateDifference fSC;
	/** an arbitrary object */
	private MObject fObject;
	/** an arbitrary link */
	private MLink fLink;
	/** an arbitrary link object */
	private MLinkObject fLinkObject;
	
	
	/**
	 * builds the fixture consisting of the {@code StateChanges} object 
	 * + the objects to insert
	 */
	@Override
	@Before
	public void setUp() {
		
		fSC = new StateDifference();
		MModel model = TestModelUtil.getInstance().createComplexModel();
		MClass person = model.getClass("Person");
		MClass company = model.getClass("Company");
		MAssociation isBoss = model.getAssociation("isBoss");
		MAssociationClass job = model.getAssociationClass("Job");
		
		MSystem system = new MSystem(model);
		MSystemState state = system.state();
		try {
			fObject = state.createObject(person, "P");
			MObject c = state.createObject(company, "C");
			fLink = state.createLink(isBoss, Arrays.asList(fObject, fObject), null);
			fLinkObject = state.createLinkObject(job, "J", Arrays.asList(fObject, c), null);
		} catch (MSystemException e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * tests clear, isEmpty
	 * <p>
	 * - initially the object is empty<br>
	 * - invoking any {@code add} method on an empty {@code StateChanges} 
	 *   results in it being not empty anymore<br>
	 * - invoking {@code clear} results in the {@code StateChanges} being 
	 *   empty<br>
	 */
	@Test
	public void testClearIsEmpty() {
		assertTrue(fSC.isEmpty());
		fSC.addNewObject(fObject);
		assertFalse(fSC.isEmpty());
		fSC.clear();
		assertTrue(fSC.isEmpty());
		fSC.addModifiedObject(fObject);
		assertFalse(fSC.isEmpty());
		fSC.clear();
		assertTrue(fSC.isEmpty());
		fSC.addDeletedObject(fObject);
		assertFalse(fSC.isEmpty());
		fSC.clear();
		assertTrue(fSC.isEmpty());
		fSC.addNewLink(fLink);
		assertFalse(fSC.isEmpty());
		fSC.clear();
		assertTrue(fSC.isEmpty());
		fSC.addDeletedLink(fLink);
		assertFalse(fSC.isEmpty());
		fSC.clear();
		assertTrue(fSC.isEmpty());
	}
	
	
	/**
	 * tests addNewObject, addModifiedObject, addDeletedObject with a 
	 * non-link-object object
	 * <p>
	 * - after invoking any one method, the supplied object is either
	 *   in exactly one or in none of the new-, modified- or deleted sets<br>
	 * - two consecutive invocations of different {@code add...Object} methods
	 *   with the same object lead to certain outcomes<br>
	 */
	@Test
	public void testAddObject() {
		// adding objects to an empty StateChanges object
		fSC.addNewObject(fObject);
		assertTrue(isOnlyNew(fObject));
		fSC.clear();
		fSC.addModifiedObject(fObject);
		assertTrue(isOnlyModified(fObject));
		fSC.clear();
		fSC.addDeletedObject(fObject);
		assertTrue(isOnlyDeleted(fObject));
		fSC.clear();
		
		// invocations of the add...Object methods in the context of
		// previous add...Object calls
		
		// new, modified = new
		fSC.addNewObject(fObject);
		fSC.addModifiedObject(fObject);
		assertTrue(isOnlyNew(fObject));
		fSC.clear();
		
		// new, deleted = empty
		fSC.addNewObject(fObject);
		fSC.addDeletedObject(fObject);
		assertTrue(fSC.isEmpty());
		fSC.clear();
		
		// modified, new = new
		// hypothetical case, shouldn't be possible to achieve
		fSC.addModifiedObject(fObject);
		fSC.addNewObject(fObject);
		assertTrue(isOnlyNew(fObject));
		fSC.clear();
		
		// modified, deleted = deleted
		fSC.addModifiedObject(fObject);
		fSC.addDeletedObject(fObject);
		assertTrue(isOnlyDeleted(fObject));
		fSC.clear();
		
		// deleted, new = modified
		fSC.addDeletedObject(fObject);
		fSC.addNewObject(fObject);
		assertTrue(isOnlyModified(fObject));
		fSC.clear();
		
		// deleted, modified = modified
		// hypothetical case, shouldn't be possible to achieve
		fSC.addDeletedObject(fObject);
		fSC.addModifiedObject(fObject);
		assertTrue(isOnlyModified(fObject));
		fSC.clear();
	}
	
	
	/**
	 * tests addNewLink, addDeletedLink with a non-link-object link
	 * <p>
	 * - after invoking one of the methods, the link is either in the set of 
	 *   new links or deleted links or in neither of those sets, but not in 
	 *   both<br>
	 * - adding a new link which was previously deleted leads to that link
	 *   being in neither the set of new links nor the set of deleted links<br>
	 * - the same holds true for adding a deleted link which was previously 
	 *   new<br>
	 */
	@Test
	public void testAddLink() {
		fSC.addNewLink(fLink);
		assertTrue(fSC.getNewLinks().contains(fLink));
		fSC.clear();
		fSC.addDeletedLink(fLink);
		assertTrue(fSC.getDeletedLinks().contains(fLink));
		fSC.clear();
		
		// new, deleted = empty
		fSC.addNewLink(fLink);
		fSC.addDeletedLink(fLink);
		assertFalse(fSC.getNewLinks().contains(fLink));
		assertFalse(fSC.getDeletedLinks().contains(fLink));
		fSC.clear();
		
		// deleted, new = empty
		fSC.addDeletedLink(fLink);
		fSC.addNewLink(fLink);
		assertFalse(fSC.getNewLinks().contains(fLink));
		assertFalse(fSC.getDeletedLinks().contains(fLink));
		fSC.clear();
	}
	
	
	/**
	 * tests addNew(Link)(Object), addDeleted(Link)(Object)
	 * <p>
	 * - invoking any of the {@code addNew...} or {@code addDeleted...} methods 
	 *   with a link object results in the link object being treated as an 
	 *   object and a link, i.E. it doesn't matter which version is used<br>
	 */
	@Test
	public void testAddLinkObject() {
		fSC.addNewLinkObject(fLinkObject);
		assertTrue(fSC.getNewObjects().contains(fLinkObject));
		assertTrue(fSC.getNewLinks().contains(fLinkObject));
		fSC.clear();
		
		fSC.addDeletedLinkObject(fLinkObject);
		assertTrue(fSC.getDeletedObjects().contains(fLinkObject));
		assertTrue(fSC.getDeletedLinks().contains(fLinkObject));		
		fSC.clear();
		
		fSC.addNewObject(fLinkObject);
		assertTrue(fSC.getNewObjects().contains(fLinkObject));
		assertTrue(fSC.getNewLinks().contains(fLinkObject));
		fSC.clear();
		
		fSC.addDeletedObject(fLinkObject);
		assertTrue(fSC.getDeletedObjects().contains(fLinkObject));
		assertTrue(fSC.getDeletedLinks().contains(fLinkObject));
		fSC.clear();
		
		fSC.addNewLink(fLinkObject);
		assertTrue(fSC.getNewObjects().contains(fLinkObject));
		assertTrue(fSC.getNewLinks().contains(fLinkObject));
		fSC.clear();
		
		fSC.addDeletedLink(fLinkObject);
		assertTrue(fSC.getDeletedObjects().contains(fLinkObject));
		assertTrue(fSC.getDeletedLinks().contains(fLinkObject));
		fSC.clear();
	}
	
	
	/**
	 * returns true if object is a member of the newObjects set, and not member
	 * of the other sets
	 * 
	 * @param object the object to test
	 * @return true if {@code object} is only in the set of new objects
	 */
	private boolean isOnlyNew(MObject object) {
		return 
		    fSC.getNewObjects().contains(object) 
		&& !fSC.getModifiedObjects().contains(object)
		&& !fSC.getDeletedObjects().contains(object);
	}
	
	
	/**
	 * returns true if object is a member of the modifiedObjects set, and not 
	 * member of the other sets
	 * 
	 * @param object the object to test
	 * @return true if {@code object} is only in the set of modified objects
	 */
	private boolean isOnlyModified(MObject object) {
		return 
	       !fSC.getNewObjects().contains(object) 
	    &&  fSC.getModifiedObjects().contains(object)
	    && !fSC.getDeletedObjects().contains(object);
	}

	
	/**
	 * returns true if object is a member of the deletedObjects set, and not 
	 * member of the other sets
	 * 
	 * @param object the object to test
	 * @return true if {@code object} is only in the set of deleted objects
	 */
	private boolean isOnlyDeleted(MObject object) {
		return 
	       !fSC.getNewObjects().contains(object) 
	    && !fSC.getModifiedObjects().contains(object)
	    &&  fSC.getDeletedObjects().contains(object);
	}
}

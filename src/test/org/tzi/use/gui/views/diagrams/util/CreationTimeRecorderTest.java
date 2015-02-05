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

package org.tzi.use.gui.views.diagrams.util;

import java.util.List;

import junit.framework.TestCase;

import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.CreationTimeRecorder;
import org.tzi.use.gui.views.diagrams.behavior.communicationdiagram.MMessage;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;

/**
 * Tests the message recorder
 * @author Lars Hamann
 *
 */
public class CreationTimeRecorderTest extends TestCase {
	/**
     * Creates a model with two classes and an association class. It
     * creates instances of those as well.
     *
     * @return returns the actual System.
     */
    private MSystem createModelWithObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocClass();
            
            UseSystemApi api = UseSystemApi.create(model, true);
            
            // creation of an object (p1) of the class Person
            api.createObjects("Person", "p1");
            
            // creation of an object (c1) of the class Company
            api.createObjects("Company", "c1");
        

            // creation of an link object (j1) of the association class Job
            api.createLinkObject(
            		"Job", 
            		"j1", 
            		new String[] {"p1", "c1"});
            
            return api.getSystem();
        } catch ( UseApiException e ) {
            throw ( new Error( e ) );
        }
    }
    
	public void testTime() {
		MSystem system = createModelWithObject();
		List<Event> events = system.getAllEvents();
		
		assertEquals(3, events.size());
		
		CreationTimeRecorder rec = new CreationTimeRecorder();
		for (int i = 0; i < events.size(); ++i) {
			rec.addMessage(new MMessage(events.get(i)));
			assertEquals(i+1, rec.getTime());
		}
		
		UseSystemApi api = UseSystemApi.create(system, true);
		MObject p1 = api.getObject("p1");
		assertEquals(0, rec.getLastCreationTime(p1));
		
		MObject c1 = api.getObject("c1");
		assertEquals(1, rec.getLastCreationTime(c1));
		
		MObject j1 = api.getObject("j1");
		assertEquals(2, rec.getLastCreationTime(j1));
		
		try {
			api.deleteObject("p1");
		} catch (UseApiException e) {
			fail(e.getMessage());
		}
		
		events = system.getAllEvents();
		assertEquals(5, events.size());
		
		assertTrue("Expected LinkDeletedEvent", events.get(4) instanceof LinkDeletedEvent);
		rec.addMessage(new MMessage(events.get(4)));
				
		assertEquals(2, rec.getLastCreationTime(j1));
		assertEquals(2, rec.getLastCreationTime(j1, 3));
		assertEquals(2, rec.getLastCreationTime(j1, 2));
	}
}

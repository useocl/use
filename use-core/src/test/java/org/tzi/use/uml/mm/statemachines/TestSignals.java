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

package org.tzi.use.uml.mm.statemachines;

import junit.framework.TestCase;

import org.junit.Test;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;

/**
 * Tests for UML signals
 * @author Lars Hamann
 *
 */
public class TestSignals extends TestCase {
	
	@Test
	public void testGeneration() {
		MModel model = TestModelUtil.getInstance().createModelWithClasses();
		UseModelApi api = new UseModelApi(model);
		
		MSignal sEmployed;
		try {
			sEmployed = api.createSignal("Employed", false);
			api.createAttributeEx(sEmployed, "company", "Company");
		} catch (UseApiException e1) {
			fail("Signal creation failed!");
			return;
		}
		
		assertEquals(1, sEmployed.getAttributes().size());
		
		try {
			api.createAttributeEx(sEmployed, "company", "Company");
			fail("Invalid duplicate attribute for signal not covered");
		} catch (UseApiException e) { }
		
		assertEquals(1, sEmployed.getAttributes().size());
		
		MSignal sEmployedSub;
		try {
			sEmployedSub = api.createSignal("EmployedSub", false);
			api.createGeneralizationEx(sEmployedSub, sEmployed);
		} catch (UseApiException e1) {
			fail("Signal inheritance creation failed!");
			return;
		}
		
		MClass subClass;
		try {
			subClass = api.createClass("EmployedSubSub", false);
		} catch (UseApiException e) {
			fail("Class creation failed");
			return;
		}
		
		ModelFactory fac = new ModelFactory();
		MGeneralization gen = fac.createGeneralization(subClass, sEmployedSub);
		try {
			model.addGeneralization(gen);
			fail("Invalid inheritance realtion between MSignal and MClass not handled!");
		} catch (MInvalidModelException e) {
			// fine
		}
	}
}

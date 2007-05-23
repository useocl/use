/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */
package org.tzi.use.uml.sys;

import java.util.Arrays;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;

import junit.framework.TestCase;

/**
 * @author green
 */
public class MSystemStateTest extends TestCase {

    public void testGetCrossProductOfInstanceSets() {
        ModelFactory factory = new ModelFactory();
        MModel model = factory.createModel("test");
        MClass c1 = factory.createClass("C1", false);
        MClass c2 = factory.createClass("C2", false);
        MClass c3 = factory.createClass("C3", false);
        
        try {
            model.addClass(c1);
            model.addClass(c2);
            model.addClass(c3);
        } catch (MInvalidModelException e1) {
            throw new RuntimeException(e1);
        }
        
        MSystem system = new MSystem(model);
        MSystemState state = system.state();
        
        try {
            state.createObject(c1,"o1_1");
            state.createObject(c2,"o2_1");
            state.createObject(c2,"o2_2");
            state.createObject(c3,"o3_1");
            state.createObject(c3,"o3_2");
            state.createObject(c3,"o3_3");
            
            assertEquals(2, state.getCrossProductOfInstanceSets(
                    Arrays.asList(new MClass[]{c1,c2})).size());
            assertEquals(3, state.getCrossProductOfInstanceSets(
                    Arrays.asList(new MClass[]{c1,c3})).size());
            assertEquals(6, state.getCrossProductOfInstanceSets(
                    Arrays.asList(new MClass[]{c2,c3})).size());
        } catch (MSystemException e) {
            throw new RuntimeException(e);
        }
    }

}

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

// $Id$

package org.tzi.use.uml.mm;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;

/**
 * The class <code>ModelCreationTest</code> try to instanciate Objects
 * and Links of all models in <code>TestModelUtil</code>.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class ModelCreationTest extends TestCase {

    /**
     * Tests, if the method <code>createEmptyModel</code> really creates
     * an empty USE-model.
     */
    public void testCreateEmptyModel() {
        MModel model = TestModelUtil.getInstance().createEmptyModel();
        assertTrue( model.classes().isEmpty() );
    }

    /**
     * This method instanciate Objects of the classes, which are created in the
     * method <code>createModelWithClasses</code>.
     */
    public void testCreateModelWithClasses() {
        try {
            MModel model = TestModelUtil.getInstance().createModelWithClasses();
            MSystem system = new MSystem(model);
            
            MClass personClass = model.getClass("Person");
            MClass companyClass = model.getClass("Company");
            
            system.evaluateStatement(
            		new MNewObjectStatement(
            				personClass, "p1"));
            
            system.evaluateStatement(
            		new MNewObjectStatement(
            				personClass, "p2"));
            
            system.evaluateStatement(
            		new MNewObjectStatement(
            				companyClass, "c1"));
            
            assertEquals( system.state().objectByName( "p1" ).name(), "p1" );
            assertEquals( system.state().objectByName( "p2" ).name(), "p2" );
            assertEquals( system.state().objectByName( "c1" ).name(), "c1" );
        } catch (MSystemException e) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Create links and objects of the associations and classes
     * in <code>TestModelUtil</code>.
     */
    public void testCreateModelWithClassAndAssocs() {
        try {
            MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
            MSystem system = new MSystem( model );
            
            MClass personClass = model.getClass("Person");
            MClass companyClass = model.getClass("Company");
            
            system.evaluateStatement(
            		new MNewObjectStatement(
            				personClass, "p1"));
            
            system.evaluateStatement(
            		new MNewObjectStatement(
            				personClass, "p2"));
            
            system.evaluateStatement(
            		new MNewObjectStatement(
            				companyClass, "c1"));

            
            List<MRValue> participants = new ArrayList<MRValue>(2);
            participants.add(
            		new MRValueExpression(system.state().objectByName("p1")));
            participants.add(
            		new MRValueExpression(system.state().objectByName("c1")));
           
            system.evaluateStatement(
            		new MLinkInsertionStatement(
            				model.getAssociation("Job"), 
            				participants));

            participants.clear();
            participants.add(
            		new MRValueExpression(system.state().objectByName("p1")));
            participants.add(
            		new MRValueExpression(system.state().objectByName("p2")));

            system.evaluateStatement(
            		new MLinkInsertionStatement(
            				model.getAssociation("isBoss"), 
            				participants));
            
            assertEquals( system.state().objectByName( "p1" ).name(), "p1" );
            assertEquals( system.state().objectByName( "p2" ).name(), "p2" );
            assertEquals( system.state().objectByName( "c1" ).name(), "c1" );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }
}


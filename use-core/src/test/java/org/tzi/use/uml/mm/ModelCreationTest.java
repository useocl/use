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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;

/**
 * The class <code>ModelCreationTest</code> tries to instantiate Objects
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
            
            system.execute(
            		new MNewObjectStatement(
            				personClass, "p1"));
            
            system.execute(
            		new MNewObjectStatement(
            				personClass, "p2"));
            
            system.execute(
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
            
            system.execute(
            		new MNewObjectStatement(
            				personClass, "p1"));
            
            system.execute(
            		new MNewObjectStatement(
            				personClass, "p2"));
            
            system.execute(
            		new MNewObjectStatement(
            				companyClass, "c1"));

            
            List<MRValue> participants = new ArrayList<MRValue>(2);
            participants.add(
            		new MRValueExpression(system.state().objectByName("p1")));
            participants.add(
            		new MRValueExpression(system.state().objectByName("c1")));
           
            system.execute(
            		new MLinkInsertionStatement(
            				model.getAssociation("Job"), 
            				participants, null));

            participants.clear();
            participants.add(
            		new MRValueExpression(system.state().objectByName("p1")));
            participants.add(
            		new MRValueExpression(system.state().objectByName("p2")));

            system.execute(
            		new MLinkInsertionStatement(
            				model.getAssociation("isBoss"), 
            				participants, null));
            
            assertEquals( system.state().objectByName( "p1" ).name(), "p1" );
            assertEquals( system.state().objectByName( "p2" ).name(), "p2" );
            assertEquals( system.state().objectByName( "c1" ).name(), "c1" );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }
    
    /**
     * Create links and objects of the qualified association and classes
     * in <code>TestModelUtil</code>.
     */
    public void testCreateModelWithClassAndQualifiedAssoc() {
        try {
            MModel model = TestModelUtil.getInstance().createModelWithClassAndQualifiedAssoc();
            MSystem system = new MSystem( model );
            
            MClass personClass = model.getClass("Person");
            MClass bankClass = model.getClass("Bank");
            
            system.execute(
            		new MNewObjectStatement(
            				personClass, "p1"));
            
            system.execute(
            		new MNewObjectStatement(
            				personClass, "p2"));
            
            system.execute(
            		new MNewObjectStatement(
            				bankClass, "b1"));

            MObject bank1 = system.state().objectByName( "b1" );
            MObject person1 = system.state().objectByName( "p1" );
            MObject person2 = system.state().objectByName( "p2" );
            
            assertEquals( person1.name(), "p1" );
            assertEquals( person2.name(), "p2" );
            assertEquals( bank1.name(), "b1" );
            
            // Build insertion statement equivalent to "insert (b1['123456'], p1) into Account"
            List<MRValue> participants = new ArrayList<MRValue>(2);
            participants.add(new MRValueExpression(bank1));
            participants.add(new MRValueExpression(person1));
           
            List<List<MRValue>> qualifierValues = new ArrayList<List<MRValue>>(2);            
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("123456"))));
            qualifierValues.add(null);
            
            system.execute(
            		new MLinkInsertionStatement(
            				model.getAssociation("Account"), 
            				participants,
            				qualifierValues));
            
            // Build erroneous insertion statement equivalent to "insert (b1, p1['123456']) into Account"           
            qualifierValues.clear();            
            qualifierValues.add(null);
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("123456"))));
            
            try {
	            system.execute(
	            		new MLinkInsertionStatement(
	            				model.getAssociation("Account"), 
	            				participants,
	            				qualifierValues));
	            fail("Invalid link creation not handled!");
            } catch (MSystemException e) {}
            
            // Build erroneous insertion statement equivalent to "insert (b1['123456'], p1['123456']) into Account"           
            qualifierValues.clear();            
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("123456"))));
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("123456"))));
            
            try {
	            system.execute(
	            		new MLinkInsertionStatement(
	            				model.getAssociation("Account"), 
	            				participants,
	            				qualifierValues));
	            fail("Invalid link creation not handled!");
            } catch (MSystemException e) {}
            
            // Build erroneous insertion statement equivalent to "insert (b1, p1) into Account"           
            qualifierValues.clear();
            try {
	            system.execute(
	            		new MLinkInsertionStatement(
	            				model.getAssociation("Account"), 
	            				participants,
	            				qualifierValues));
	            fail("Invalid link creation not handled!");
            } catch (MSystemException e) {}
            
            // Build erroneous insertion statement "Link already exists"           
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("123456"))));
            qualifierValues.add(null);
            try {
	            system.execute(
	            		new MLinkInsertionStatement(
	            				model.getAssociation("Account"), 
	            				participants,
	            				qualifierValues));
	            fail("Invalid link creation not handled!");
            } catch (MSystemException e) {}
            
            // Build valid insertion statement equivalent to "insert (b1['123455'], p1) into Account" -> Second account for p1           
            qualifierValues.clear();            
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("123455"))));
            qualifierValues.add(null);
            
	        system.execute(
            		new MLinkInsertionStatement(
            				model.getAssociation("Account"), 
            				participants,
            				qualifierValues));
            
	        // Insert person2 with accountnr 654321 into association
	        participants.clear();
	        participants.add(
            		new MRValueExpression(bank1));
            participants.add(
            		new MRValueExpression(person2));
            qualifierValues.clear();            
            qualifierValues.add(Arrays.asList((MRValue)new MRValueExpression(new StringValue("654321"))));
            qualifierValues.add(null);
            
            system.execute(
            		new MLinkInsertionStatement(
            				model.getAssociation("Account"), 
            				participants,
            				qualifierValues));
            
            MAssociation account = model.getAssociation("Account");
            MAssociationEnd endBank = account.getAssociationEnd(bankClass, "bank");
            MAssociationEnd endAccount = account.getAssociationEnd(personClass, "account");
            
            // Is bank1 linked to p1 with accountNr 123456?
            List<MObject> linkedObjects = system.state().getNavigableObjects(bank1, endBank, endAccount, Arrays.asList((Value)new StringValue("123456")));
            assertEquals(1, linkedObjects.size());
            assertEquals(person1.name(), linkedObjects.get(0).name());
            
            // Is bank1 linked to p1 with accountNr 123455?
            linkedObjects = system.state().getNavigableObjects(bank1, endBank, endAccount, Arrays.asList((Value)new StringValue("123455")));
            assertEquals(1, linkedObjects.size());
            assertEquals(person1.name(), linkedObjects.get(0).name());
            
            // Is bank1 linked to p2 with accountNr 654321?
            linkedObjects = system.state().getNavigableObjects(bank1, endBank, endAccount, Arrays.asList((Value)new StringValue("654321")));
            assertEquals(1, linkedObjects.size());
            assertEquals(person2.name(), linkedObjects.get(0).name());
            
            // Is bank1 linked to three persons?
            linkedObjects = system.state().getNavigableObjects(bank1, endBank, endAccount, Collections.<Value>emptyList());
            assertEquals(3, linkedObjects.size());
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }
}


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

package org.tzi.use.uml.sys;

import org.tzi.use.SystemManipulator;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.TestModelUtil;
import org.tzi.use.uml.ocl.expr.ExpConstString;


/**
 * ObjectCreation is needed for creating instances of a specific
 * model. This class is only needed for the tests.
 *
 * @version $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class ObjectCreation {
    private static ObjectCreation instance = null;

    private ObjectCreation() {
    }

    public static ObjectCreation getInstance() {
        if ( instance == null ) {
            instance = new ObjectCreation();
        }
        return instance;
    }

    /**
     * Creates a model with two classes and an association class. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjects() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocs();
            MSystem system = new MSystem( model );
            
            SystemManipulator systemManipulator = new SystemManipulator(system);
            
            // creation of an object (p1) of the class Person
            systemManipulator.createObjects("Person", "p1");
            
            // creation of an object (c1) of the class Company
            systemManipulator.createObjects("Company", "c1");
            
            // creation of a link between p1 and c1 of an association
            systemManipulator.insertLink("Job", "p1", "c1");
            
            return system;
        } catch ( Exception e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an association class. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithManyObjects() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocs2();
            MSystem system = new MSystem( model );
            
            SystemManipulator systemManipulator = new SystemManipulator(system);
            
            // creation of an object (p1) of the class Person
            systemManipulator.createObjects("Person", "p1");
            
            // creation of four objects (c1-c4) of the class Company
            systemManipulator.createObjects(
            		"Company", 
            		"c1", "c2", "c3", "c4");
             
            // creation of a link between p1 and c1 of an association
            systemManipulator.insertLink("Job", "p1", "c1");
            systemManipulator.insertLink("Job", "p1", "c2");
            systemManipulator.insertLink("Job", "p1", "c3");
            systemManipulator.insertLink("Job", "p1", "c4");
            
            // set an attribute value in c1
            systemManipulator.setAttribute("c1", "name", new ExpConstString("IBM"));
                   
            return system;
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an association class. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsAndLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndAssocClass();
            MSystem system = new MSystem( model );
            
            SystemManipulator systemManipulator = new SystemManipulator(system);
            
            // creation of an object (p1) of the class Person
            systemManipulator.createObjects("Person", "p1");
            
            // creation of an object (c1) of the class Company
            systemManipulator.createObjects("Company", "c1");
            
            // creation of a link object (j1) of class Job between p1 and c1
            systemManipulator.createLinkObject(
            		"Job", 
            		"j1",
            		"p1", "c1");
            
            // set an attribute value in c1
            systemManipulator.setAttribute("c1", "name", new ExpConstString("IBM"));
            
            return system;
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates an instance of a model with one class and one association class. 
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsOfSameClassAndLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithOneClassAndOneAssocClass();
            MSystem system = new MSystem( model );
            
            SystemManipulator systemManipulator = new SystemManipulator(system);
            
            // creation of an objects (p1,p2) of the class Person
            systemManipulator.createObjects("Person", "p1", "p2");
            
            // creation of a link object (j1) of class Job between p1 and p2
            systemManipulator.createLinkObject(
            		"Job", 
            		"j1", 
            		"p1", "p2");
            
            return system;
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }

    /**
     * Creates a model with two classes and an association class. It creates
     * instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsAndTenaryLinkObject() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createModelWithClassAndTenaryAssocClass();
            MSystem system = new MSystem( model );
            
            SystemManipulator systemManipulator = new SystemManipulator(system);
            
            // creation of an object (p1) of the class Person
            systemManipulator.createObjects("Person", "p1");
            
            // creation of an object (c1) of the class Company
            systemManipulator.createObjects("Company", "c1");
            
            // creation of an object (s1) of the class Salary
            systemManipulator.createObjects("Salary", "s1");
            
            // creation of a link object (j1) of class Job between p1, s1 and c1
            systemManipulator.createLinkObject(
            		"Job",
            		"j1", 
            		"p1", "c1", "s1");

            // set an attribute value in c1
            systemManipulator.setAttribute("c1", "name", new ExpConstString("IBM"));

            return system;
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }


    /**
     * Creates a model with two classes, an association class and an association.
     * It creates instances of those as well.
     *
     * @return returns the actual System.
     */
    public MSystem createModelWithObjectsAndLinkObject2() {
        try {
            // creation of the system
            MModel model = TestModelUtil.getInstance()
                    .createComplexModel();
            MSystem system = new MSystem( model );
            
            SystemManipulator systemManipulator = new SystemManipulator(system);
            
            // creation of two objects (p1, p2) of the class Person
            systemManipulator.createObjects("Person", "p1", "p2");
            
            // creation of a link between p1 and p2 (p1 is boss of p2)
            systemManipulator.insertLink("isBoss", "p1", "p2");
                 
            // creation of two objects (c1, c2) of the class Company
            systemManipulator.createObjects("Company", "c1", "c2");
            
            // creation of a link object (j1) of class Job between p1 and c1
            systemManipulator.createLinkObject(
            		"Job", 
            		"j1",
            		"p1", "c1");
            
            // creation of a link object (j2) of class Job between p2 and c1
            systemManipulator.createLinkObject(
            		"Job", 
            		"j2",
            		"p2", "c1");                      
            
            // set an attribute value in c1
            systemManipulator.setAttribute("c1", "name", new ExpConstString("IBM"));
            
            // set an attribute value in c2
            systemManipulator.setAttribute("c2", "name", new ExpConstString("SUN"));

            return system;
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }
}

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
import java.util.Iterator;
import java.util.List;

import junit.framework.*;

import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateObjects;
import org.tzi.use.uml.sys.MCmdInsertLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;

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
            MSystem system = new MSystem( model );

            List names = new ArrayList();
            names.add( "p1" );
            names.add( "p2" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmdCreateObjects cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            names.clear();
            names.add( "c1" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            assertEquals( system.state().objectByName( "p1" ).name(), "p1" );
            assertEquals( system.state().objectByName( "p2" ).name(), "p2" );
            assertEquals( system.state().objectByName( "c1" ).name(), "c1" );
        } catch ( MSystemException e ) {
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

            List names = new ArrayList();
            names.add( "p1" );
            names.add( "p2" );
            ObjectType type = TypeFactory.mkObjectType( model.getClass( "Person" ) );
            MCmd cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            names.clear();
            names.add( "c1" );
            type = TypeFactory.mkObjectType( model.getClass( "Company" ) );
            cmd = new MCmdCreateObjects( system.state(), names, type );
            system.executeCmd( cmd );

            names.clear();
            names.add( "p1" );
            names.add( "c1" );
            Expression[] exprs = new Expression[names.size()];
            Iterator it = names.iterator();
            int i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            cmd = new MCmdInsertLink( system.state(), exprs,
                                      model.getAssociation( "Job" ) );
            system.executeCmd( cmd );

            names.clear();
            names.add( "p1" );
            names.add( "p2" );
            exprs = new Expression[names.size()];
            it = names.iterator();
            i = 0;
            while (it.hasNext() ) {
                MObject obj =  system.state().objectByName( (String) it.next() ); 
                exprs[i++] = new ExpVariable( obj.name(), obj.type() );
            }
            cmd = new MCmdInsertLink( system.state(), exprs,
                                      model.getAssociation( "isBoss" ) );
            system.executeCmd( cmd );


            assertEquals( system.state().objectByName( "p1" ).name(), "p1" );
            assertEquals( system.state().objectByName( "p2" ).name(), "p2" );
            assertEquals( system.state().objectByName( "c1" ).name(), "c1" );
        } catch ( MSystemException e ) {
            throw ( new Error( e ) );
        }
    }


}


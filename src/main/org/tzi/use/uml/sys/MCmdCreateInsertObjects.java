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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;


/**
 * A command for creating objects of an associationclass and binding them to
 * identifiers.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public final class MCmdCreateInsertObjects extends MCmd {
    private MSystemState fSystemState;
    private String fVarNameCreate;  // (String) variable names
    private List fVarNamesInsert;   // (String) variable names
    private MAssociationClass fAssocClass;
    private ObjectType fType;

    // undo information
    private MLinkObject fLinkObject;

    /**
     * Creates a command for creating an instance of an associationclass and
     * binding them to identifiers which are given as a list of names.
     */
    public MCmdCreateInsertObjects( MSystemState systemState, String nameCreate,
                                    MAssociationClass assocClass, List namesInsert ) {
        super( true );
        fSystemState = systemState;
        fVarNameCreate = nameCreate;
        fAssocClass = assocClass;
        fVarNamesInsert = namesInsert;
        fType = TypeFactory.mkObjectType( fAssocClass );
    }

    /**
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void execute() throws CommandFailedException {

        // specifies if the object already exists in the systemstate
        VarBindings varBindings = fSystemState.system().varBindings();
        if ( varBindings.getValue( fVarNameCreate ) != null ) {
            throw new CommandFailedException( "Object `" + fVarNameCreate +
                                              "' already exists." );
        }

        // create link for given objects (association class)
        // map list of variable names to list of objects
        List objects = new ArrayList( fVarNamesInsert.size() );
        Iterator it = fVarNamesInsert.iterator();
        while ( it.hasNext() ) {
            String varname = ( String ) it.next();
            Value value = varBindings.getValue( varname );
            if ( value == null )
                throw new CommandFailedException( "Unbound variable `" +
                                                  varname + "'." );
            if ( !value.type().isObjectType() )
                throw new CommandFailedException( "Expected variable of object " +
                                                  "type, found type `" + value.type() + "'." );
            MObject obj = ( ( ObjectValue ) value ).value();
            objects.add( obj );
        }

        // specifies if there is already an existing linkobject of this
        // association class between the objects
        if ( fSystemState.hasLinkBetweenObjects( fAssocClass, (MObject[])objects.toArray(new MObject[0]) ) ) {
            throw new CommandFailedException( "Cannot create two linkobjects of the same type"
                                              + " between one set of objects!" );
        }

        // create linkobject of specified type (associationclass)
        try {
            fLinkObject = fSystemState.createLinkObject( fAssocClass, fVarNameCreate, objects );
            // bind variable to object
            varBindings.push( fVarNameCreate, new ObjectValue( fType, fLinkObject ) );
        } catch ( MSystemException ex ) {
            throw new CommandFailedException( ex.getMessage() );
        }
    }


    /**
     * Undo effect of command.
     *
     * @exception CannotUndoException if the command cannot be undone or
     *                                has not been executed before.
     */
    public void undo() throws CannotUndoException {
        // the CommandProcessor should prevent us from being called
        // without a successful prior execute, just be safe here
        if ( fLinkObject == null )
            throw new CannotUndoException( "no undo information" );

        VarBindings varBindings = fSystemState.system().varBindings();
        fSystemState.deleteObject( fLinkObject );
        varBindings.remove( fVarNameCreate );
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges( StateChangeEvent sce, boolean undoChanges ) {
        if ( fLinkObject == null ) {
            throw new IllegalStateException( "command not executed" );
        }

        if ( undoChanges ) {
            sce.addDeletedObject( fLinkObject );
            sce.addDeletedLink( fLinkObject );
        } else {
            sce.addNewObject( fLinkObject );
            sce.addNewLink( fLinkObject );
        }
    }

    /**
     * Returns a short name of command, e.g. 'Create object foo : ...' for
     * display in an undo menu item.
     */
    public String name() {
        return "Create object "
                + fVarNameCreate
                + " : " + fType
                + " (associationclass)";
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.
     */
    public String getUSEcmd() {
        return "!create "
                + fVarNameCreate + ":"
                + fType + " between ("
                + StringUtil.fmtSeq( fVarNamesInsert.iterator(), "," )
                + ")";
    }

    /**
     * Returns a general name of command, e.g. 'Create instance of an associationclass'.
     */
    public String toString() {
        return "Create instance of an associationclass";
    }
}

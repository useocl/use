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

import java.util.*;

import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for destroying objects.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MCmdDestroyObjects extends MCmd {
    private MSystemState fSystemState;
    private Expression[] fObjectExprs;
    
    // undo information
    private List fObjectStates; // (MObjectState)
    private Set fRemovedLinks;  // (MLink)
    private Set fRemovedObjects; // (MObject)

    private List fObjects;
    
    /**
     * Creates a command for destroying objects whose names are given as
     * a list of expressions.
     */
    public MCmdDestroyObjects(MSystemState systemState, Expression[] exprs) {
        super(true);
        fSystemState = systemState;
        fObjectExprs = exprs;
        fObjects = new ArrayList();
    }
   
    private void destroyOne( VarBindings varBindings, MObject obj ) 
        throws CommandFailedException 
    {
//        ObjectValue oval = (ObjectValue) v;
//        MObject obj = oval.value();
        MObjectState objState = obj.state(fSystemState);
        if ( objState == null ) {
            return;
//            throw new CommandFailedException("Object `" + obj.name() +
//                                             "' does not exist anymore.");
        }
        
        //An object can only be destroyed, if there are no open operations 
        //which have been started by this object
        List operationCalls = fSystemState.system().callStack();
        for(int i=0; i<operationCalls.size(); i++){
            MOperationCall opCall = (MOperationCall)operationCalls.get(i);
            MObject object = opCall.targetObject();
            if(obj==object)
                throw new CommandFailedException("Object " + object.name() + " has open Operations.");
        }
            
        
        // delete object
        Set[] removed = fSystemState.deleteObject(obj);
        
        // store undo information
        fRemovedObjects.addAll( removed[MSystemState.REMOVED_OBJECTS] );
        fObjectStates.addAll( removed[MSystemState.REMOVED_OBJECTSTATES] );
        fRemovedLinks.addAll( removed[MSystemState.REMOVED_LINKS] );
        
        Iterator it = fRemovedObjects.iterator();
        fObjects.add(obj);
        while ( it.hasNext() ) {
            String name = ( ( MObject ) it.next() ).name();
            // remove variable binding
            varBindings.remove( name );
        }
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void execute() throws CommandFailedException {
        fObjectStates = new ArrayList();
        fRemovedLinks = new HashSet();
        fRemovedObjects = new HashSet();
        Set objects = new HashSet();
        
        Evaluator evaluator = new Evaluator();
        VarBindings varBindings = fSystemState.system().varBindings();
        Iterator objExprsIter = Arrays.asList(fObjectExprs).iterator();
        while( objExprsIter.hasNext() ) { 
            Expression expr = (Expression) objExprsIter.next();
            Value v = evaluator.eval(expr, fSystemState, varBindings);
            if (v.isObject() ) {
                MObject obj = ((ObjectValue) v).value();
                objects.add( obj );
            } else if (v.isCollection() ) {
                CollectionValue coll = (CollectionValue) v;
                Iterator elemIter = coll.iterator();
                while (elemIter.hasNext() ) {
                    Value elem = (Value) elemIter.next();
                    // additional check
                    if (! elem.isObject() ) {
                        throw new CommandFailedException("Element in collection argument " +
                                                         "of destroy command " + 
                                                         "does not evaluate to an object, found `" + 
                                                         elem.toStringWithType() + "'.");
                    }
                    MObject obj = ((ObjectValue) elem).value();
                    objects.add( obj );
                }
            } else
                throw new CommandFailedException("Argument of destroy command " + 
                                                 "does not evaluate to an object, found `" + 
                                                 v.toStringWithType() + "'.");
        }
        Iterator it = objects.iterator();
        while ( it.hasNext() ) {
            MObject obj = (MObject) it.next();
            destroyOne( varBindings, obj );
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
        if (fObjectStates == null )
            throw new CannotUndoException("no undo information");

        // recreate objects
        VarBindings varBindings = fSystemState.system().varBindings();
        Iterator objStateIter = fObjectStates.iterator();
        while (objStateIter.hasNext() ) {
            MObjectState objState = (MObjectState) objStateIter.next();
            try {
                fSystemState.restoreObject(objState);
                MObject obj = objState.object();
                varBindings.push(obj.name(), 
                                 new ObjectValue(TypeFactory.mkObjectType(obj.cls()), 
                                                 obj));
            } catch (MSystemException ex) {
                throw new CannotUndoException(ex.getMessage());
            }
        }

        // restore links
        Iterator linkIter = fRemovedLinks.iterator();
        while (linkIter.hasNext() ) {
            MLink link = (MLink) linkIter.next();
            fSystemState.insertLink(link);
        }
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
        if (fObjectStates == null )
            throw new IllegalStateException("command not executed");
    
        Iterator objStateIter = fRemovedObjects.iterator();
        while ( objStateIter.hasNext() ) {
            MObject obj = ( MObject ) objStateIter.next();
            if ( undoChanges ) {
                if ( obj instanceof MLinkObject ) {
                    sce.addNewLink( ( MLinkObject ) obj );
                }
                sce.addNewObject( obj );
            } else {
                if ( obj instanceof MLinkObject ) {
                    sce.addDeletedLink( ( MLinkObject ) obj );
                }
                sce.addDeletedObject( obj );
            }

        }

        Iterator linkIter = fRemovedLinks.iterator();
        while (linkIter.hasNext() ) {
            MLink link = (MLink) linkIter.next();
            if ( !( link instanceof MLinkObject ) ) {
                if ( undoChanges ) {
                    sce.addNewLink( link );
                } else {
                    sce.addDeletedLink( link );
                }
            }    
        }
    }

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public String name() {
        return "Destroy object(s) " + StringUtil.fmtSeq( fObjectExprs, "," );
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
        return "!destroy " + StringUtil.fmtSeq( fObjectExprs, "," );
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Destroy object";
    }
    
    public List getObjects() throws CommandFailedException{
        return fObjects;
    }
}

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
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
    private List<MObjectState> fObjectStates;
    private Set<MLink> fRemovedLinks;
    private Set<MObject> fRemovedObjects;

    private List<MObject> fObjects;
    
    /**
     * Creates a command for destroying objects whose names are given as
     * a list of expressions.
     */
    public MCmdDestroyObjects(MSystemState systemState, Expression[] exprs) {
    	this(null, systemState, exprs);
    }
    
    /**
     * Creates a command for destroying objects whose names are given as
     * a list of expressions.
     */
    public MCmdDestroyObjects(SrcPos pos, MSystemState systemState, Expression[] exprs) {
        super(pos, true);
        fSystemState = systemState;
        fObjectExprs = exprs;
        fObjects = new ArrayList<MObject>();
    }
   
    private void destroyOne( VarBindings varBindings, MObject obj ) 
        throws CommandFailedException 
    {
        MObjectState objState = obj.state(fSystemState);
        
        if ( objState == null ) {
            return;
        }
        
        // An object can only be destroyed, if there are no open operations 
        // which have been started by this object
        List<MOperationCall> operationCalls = fSystemState.system().callStack();
        for(int i=0; i<operationCalls.size(); i++){
            MOperationCall opCall = (MOperationCall)operationCalls.get(i);
            MObject object = opCall.targetObject();
            if(obj==object)
                throw new CommandFailedException("Object " + object.name() + " has open Operations.");
        }
        
        // delete object
        DeleteObjectResult result = fSystemState.deleteObject(obj);
        
        // store undo information
        fRemovedObjects.addAll( result.getRemovedObjects() );
        fObjectStates.addAll( result.getRemovedObjectStates() );
        fRemovedLinks.addAll( result.getRemovedLinks() );
        
        fObjects.add(obj);
        for (MObject o : fRemovedObjects) {
            String name = o.name();
            // remove variable binding
            varBindings.remove( name );
        }
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void doExecute() throws CommandFailedException {
        fObjectStates = new ArrayList<MObjectState>();
        fRemovedLinks = new HashSet<MLink>();
        fRemovedObjects = new HashSet<MObject>();
        Set<MObject> objects = new HashSet<MObject>();
        
        Evaluator evaluator = new Evaluator();
        VarBindings varBindings = fSystemState.system().topLevelBindings();

        for (Expression expr : fObjectExprs) {
            Value v = evaluator.eval(expr, fSystemState, varBindings);
            if (v.isObject() ) {
                MObject obj = ((ObjectValue) v).value();
                objects.add( obj );
            } else if (v.isCollection() ) {
                CollectionValue coll = (CollectionValue) v;
                Iterator<Value> elemIter = coll.iterator();
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
        
        for (MObject obj : objects) {
        	// Here varBindings is used because modifying topLevelBindings has no effect
            destroyOne( fSystemState.system().varBindings(), obj );
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
        
        for (MObjectState objState : fObjectStates) {
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
        for (MLink link : fRemovedLinks) {
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
    
        for (MObject obj : fRemovedObjects) {
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
        
        for (MLink link : fRemovedLinks) {
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
        return new StringBuilder("!destroy ").append(StringUtil.fmtSeq( fObjectExprs, "," )).toString();
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Destroy object";
    }
    
    public List<MObject> getObjects() throws CommandFailedException{
        return fObjects;
    }
}

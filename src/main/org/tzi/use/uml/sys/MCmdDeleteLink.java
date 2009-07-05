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
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for deleting association links.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MCmdDeleteLink extends MCmd {
    private MSystemState fSystemState;
    private Expression[] fObjectExprs;
    private MAssociation fAssociation;
    
    // undo information
    private List<MObjectState> fObjectStates;
    private Set<MLink> fRemovedLinks;
    private Set<MObject> fRemovedObjects;
    
    protected MObject[] fObjects;
    
    /**
     * Creates a command for deleting a link.
     */
    public MCmdDeleteLink(MSystemState systemState,
                          Expression[] exprs, 
                          MAssociation assoc) {
        super(true);
        fSystemState = systemState;
        fObjectExprs = exprs;
        fAssociation = assoc;
        fObjects = new MObject[exprs.length];
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
        
        VarBindings varBindings = fSystemState.system().topLevelBindings();
        List<MAssociationEnd> assocEnds = fAssociation.associationEnds();

        // map expression list to list of objects 
        List<MObject> objects = new ArrayList<MObject>(fObjectExprs.length);
        
        for (int i = 0; i < fObjectExprs.length; i++) {
            MAssociationEnd aend = (MAssociationEnd) assocEnds.get(i);
            Evaluator evaluator = new Evaluator();
            // evaluate in scope local to operation
            Value v = evaluator.eval(fObjectExprs[i], fSystemState, varBindings);
            boolean ok = false;
            
            if (v.isDefined() && (v instanceof ObjectValue) ) {
                ObjectValue oval = (ObjectValue) v;
                MObject obj = oval.value();
                objects.add(obj);
                fObjects[i]=obj;
                ok = obj.cls().isSubClassOf(aend.cls());
            }
            
            if (! ok )
                throw new CommandFailedException("Argument #" + (i+1) +
                                                 " of delete command does not evaluate to an object of class `" + 
                                                 aend.cls().name() + "', found `" +
                                                 v.toStringWithType() + "'.");
        }
        try {            
            // delete link
            DeleteObjectResult result = fSystemState.deleteLink(fAssociation, objects);
            
            // store undo information
            fRemovedObjects.addAll( result.getRemovedObjects() );
            fObjectStates.addAll( result.getRemovedObjectStates() );
            fRemovedLinks.addAll( result.getRemovedLinks() );
            
            
        } catch (MSystemException ex) {
            throw new CommandFailedException(ex.getMessage());
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
              
        if ( fRemovedLinks == null )
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
        return "Delete link from " + fAssociation.name();
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
        return "!delete (" + 
            StringUtil.fmtSeq(fObjectExprs, ",") + 
            ") from " + fAssociation.name();
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Delete link";
    }
    
    public MAssociation getAssociation(){
        return fAssociation;
    }

    public MObject[] getObjects(){
        return fObjects;
    }
}

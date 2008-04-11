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

$Id$

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for creating association links. A new association link is
 * created and entered into the current system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MCmdInsertLink extends MCmd {
    private MSystemState fSystemState;
    private Expression[] fObjectExprs;
    private MAssociation fAssociation;
    private MObject[] fObjects;

    // undo information
    private MLink fNewLink;

    /**
     * Creates a command for adding a link.
     */
    public MCmdInsertLink(MSystemState systemState, 
                          Expression[] exprs, 
                          MAssociation assoc) {
        super(true);
        fSystemState = systemState;
        fObjectExprs = exprs;
        fObjects = new MObjectImpl[fObjectExprs.length];
        fAssociation = assoc;
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void execute() throws CommandFailedException {
        VarBindings varBindings = fSystemState.system().topLevelBindings();
        List assocEnds = fAssociation.associationEnds();

        // map expression list to list of objects 
        List objects = new ArrayList(fObjectExprs.length);
        for (int i = 0; i < fObjectExprs.length; i++) {
            MAssociationEnd aend = (MAssociationEnd) assocEnds.get(i);
            Evaluator evaluator = new Evaluator();
            Value v = evaluator.eval(fObjectExprs[i], fSystemState, varBindings);
            boolean ok = false;
            if (v.isDefined() && (v instanceof ObjectValue) ) {
                ObjectValue oval = (ObjectValue) v;
                MObject obj = oval.value();
                MObjectState objState = obj.state(fSystemState);
                if (objState == null )
                    throw new CommandFailedException("Object `" + obj.name() + 
                                                     "' does not exist anymore.");
                objects.add(obj);
                fObjects[i]=obj;
                ok = obj.cls().isSubClassOf(aend.cls());
            }
            if (! ok )
                throw new CommandFailedException("Argument #" + (i+1) +
                                                 " of insert command does not evaluate to an object of class `" + 
                                                 aend.cls().name() + "', found `" +
                                                 v.toStringWithType() + "'.");
        }
        try { 
            fNewLink = fSystemState.createLink(fAssociation, objects);
            if ( fNewLink instanceof MLinkObject ) {
                MLinkObject obj = (MLinkObject) fNewLink;
                // Object name has to be pushed to varbindings. topLevelBindings is an 
                // independent List, where changes have no effects. 
                fSystemState.system().varBindings().push( obj.name(), new ObjectValue( obj.type(), obj ) );
            }
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
        if (fNewLink == null )
            throw new CannotUndoException("no undo information");

        fSystemState.deleteLink(fNewLink);
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
        if (fNewLink == null )
            throw new IllegalStateException("command not executed");

        if (undoChanges ) {
            if ( fNewLink instanceof MLinkObject ) {
                sce.addDeletedObject( ( MLinkObject ) fNewLink );
            }
            sce.addDeletedLink(fNewLink);
        } else {
            if ( fNewLink instanceof MLinkObject ) {
                sce.addNewObject( ( MLinkObject ) fNewLink );
            }
            sce.addNewLink(fNewLink);
        }
    }
    

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public String name() {
        return "Insert link into " + fAssociation.name();
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
        return "!insert (" + 
            StringUtil.fmtSeq(fObjectExprs, ",") + 
            ") into " + fAssociation.name();
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Insert link";
    }
    
    public MAssociation getAssociation(){
        return fAssociation;
    }

    public MObject[] getObjects(){
        
        return fObjects;
    }

    public String[] getRoleNames(){
        String[] roleNames = new String[fObjectExprs.length];
        List assocEnds = fAssociation.associationEnds();

        for (int i = 0; i < fObjectExprs.length; i++) {
            MAssociationEnd aend = (MAssociationEnd) assocEnds.get(i);
            roleNames[i] = aend.name();
        }
        return roleNames;

    }
}

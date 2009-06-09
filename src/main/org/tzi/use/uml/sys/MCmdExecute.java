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

import org.tzi.use.config.Options;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;
import java.util.*;

/**
 * Invoke an operation in non-side effect free context.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      green
 */
public final class MCmdExecute extends MCmd {
    private MSystemState fSystemState;
    private Expression fExp;

    MSystemState fPreState;

    public MCmdExecute(MSystemState systemState, Expression exp) {
        super(true);
        fSystemState = systemState;
        fExp = exp;
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void doExecute() throws CommandFailedException {
        if (!Options.extensionActionLanguage) {
            throw new CommandFailedException("Executable operations are experimental. \n" 
                    + "They must be enabled using -XenableActionLanguage");
        }
        fPreState = new MSystemState( "pre", fSystemState );
        EvalContext ctx = new EvalContext(fSystemState, fSystemState, fSystemState.system().varBindings(), null);

        Value v = fExp.eval(ctx);
        if (v != null) {
            System.out.println("-> " + v.toStringWithType());
        }
    }

    /**
     * Undo effect of command.
     *
     * @exception CannotUndoException if the command cannot be undone or 
     *                                has not been executed before.
     */
    public void undo() throws CannotUndoException {
        throw new CannotUndoException();
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
        HashSet deletedObjects = new HashSet(fPreState.allObjects());
        deletedObjects.removeAll(fSystemState.allObjects());
        for (Iterator it=deletedObjects.iterator(); it.hasNext();) {
            sce.addDeletedObject((MObject)it.next());
        }
        
        HashSet createdObjects = new HashSet(fSystemState.allObjects());
        createdObjects.removeAll(fPreState.allObjects());
        for (Iterator it=createdObjects.iterator(); it.hasNext();) {
            sce.addNewObject((MObject)it.next());
        }

        HashSet deletedLinks = new HashSet(fPreState.allLinks());
        deletedLinks.removeAll(fSystemState.allLinks());
        for (Iterator it=deletedLinks.iterator(); it.hasNext();) {
            sce.addDeletedLink((MLink)it.next());
        }

        HashSet createdLinks = new HashSet(fSystemState.allLinks());
        createdLinks.removeAll(fPreState.allLinks());
        for (Iterator it=createdLinks.iterator(); it.hasNext();) {
            sce.addNewLink((MLink)it.next());
        }
        
    }

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public String name() {
        return "exec " + fExp.toString();
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
        return "exec " + fExp.toString();
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Evaluate non-context free expression";
    }
}

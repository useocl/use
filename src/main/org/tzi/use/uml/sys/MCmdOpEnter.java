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

import java.io.PrintWriter;

import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for entering an operation.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters
 */
public final class MCmdOpEnter extends MCmd {
    private MSystemState fSystemState;

    private MOperationCall fOpCall;

    private boolean fSuccessful;

    public MCmdOpEnter(MSystemState systemState, MOperationCall opCall) {
        super(true);
        fSystemState = systemState;
        fOpCall = opCall;
        fSuccessful = false;
    }

    public MOperationCall operationCall() {
        return fOpCall;
    }

    public boolean successful() {
        return fSuccessful;
    }

    /**
     * Executes command and stores undo information.
     * 
     * @exception CommandFailedException
     *                if the command failed.
     */
    public void execute() throws CommandFailedException {
        fSuccessful = fSystemState.system().enterOperation(fOpCall,
                new PrintWriter(Log.out()));
        // fSystemState.system().enterOperation(fOpCall, new
        // PrintWriter(Log.out()));
    }

    /**
     * Undo effect of command.
     * 
     * @exception CannotUndoException
     *                if the command cannot be undone or has not been executed
     *                before.
     */
    public void undo() throws CannotUndoException {
        // the CommandProcessor should prevent us from being called
        // without a successful prior execute, just be safe here
        MSystem system = fSystemState.system();
        if (system.activeOperation() != fOpCall)
            throw new CannotUndoException("no undo information");

        system.popOperation();
        system.setCurrentState(fSystemState);
    }

    /**
     * Fill a StateChangeEvent with information about this command's effect.
     * 
     * @param undoChanges
     *            get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
        // if (fObjects == null )
        // throw new IllegalStateException("command not executed");

    }

    /**
     * Returns a short name of command, e.g. 'Create class foo' for display in
     * an undo menu item.
     */
    public String name() {
        MOperation op = fOpCall.operation();
        return "Enter operation " + op.cls().name() + "::" + op.signature();
    }

    /**
     * Returns a string that can be read and executed by the USE shell achieving
     * the same effect of this command.
     */
    public String getUSEcmd() {
        MOperation op = fOpCall.operation();
        return "!openter " + fOpCall.target() + " " + op.name() + "("
                + StringUtil.fmtSeq(fOpCall.argExprs(), ",") + ")";
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Enter operation";
    }
}

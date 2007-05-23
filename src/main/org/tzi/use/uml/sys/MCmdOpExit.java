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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.sys;

import java.io.PrintWriter;

import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.Log;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for exiting an operation.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MCmdOpExit extends MCmd {
    private MSystemState fSystemState;
    private Expression fResultExpr; // may be null

    // undo information
    private MOperationCall fOpCall;
    private MSystemState fSystemStateBeforeExit;

    public MCmdOpExit(MSystemState systemState, Expression resultExpr) {
        super(true);
        fSystemState = systemState;
        fResultExpr = resultExpr;
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void execute() throws CommandFailedException {
        Value result = null;
        MSystem system = fSystemState.system();
        if (fResultExpr != null ) {
            Evaluator evaluator = new Evaluator();
            result = evaluator.eval(fResultExpr, fSystemState, 
                                    system.topLevelBindings());
        }
        try {
            fOpCall = system.activeOperation();
            fSystemStateBeforeExit = system.state();
            system.exitOperation(result, new PrintWriter(Log.out()));
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
        if (fOpCall == null )
            throw new CannotUndoException("no undo information");

        MSystem system = fSystemState.system();
        system.pushOperation(fOpCall);
        system.setCurrentState(fSystemStateBeforeExit);
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
        if (fOpCall == null )
            throw new IllegalStateException("command not executed");
    }

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public String name() {
        return "Exit last operation";
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
        return "!opexit" + ( fResultExpr == null ? "" : " " + fResultExpr );
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Exit operation";
    }
    
    public MOperationCall operationCall(){
        return fOpCall;
    }
}

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

import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.cmd.CannotUndoException;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A command for binding a top-level variable.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MCmdLet extends MCmd {
    private MSystemState fSystemState;
    private String fVar;
    private Type fType; // may be null
    private Expression fExpr;

    public MCmdLet(MSystemState systemState, 
                   String var, Type type, Expression expr) {
        super(true);
        fSystemState = systemState;
        fVar = var;
        fType = type;
        fExpr = expr;
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public void execute() throws CommandFailedException {
        MSystem system = fSystemState.system();

        // evaluate expression
        Evaluator evaluator = new Evaluator();
        Value v = evaluator.eval(fExpr, fSystemState, 
                                 system.topLevelBindings());

        // bind variable to object
        system.addVarBindingToCurrentScope(fVar, v);
    }

    /**
     * Undo effect of command.
     *
     * @exception CannotUndoException if the command cannot be undone or 
     *                                has not been executed before.
     */
    public void undo() throws CannotUndoException {
        MSystem system = fSystemState.system();
        system.removeLastVarBindingFromCurrentScope();
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public void getChanges(StateChangeEvent sce, boolean undoChanges) {
    }

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public String name() {
        return "Bind variable " + fVar;
    }

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public String getUSEcmd() {
        return "!let " + fVar + " : " +
            ( fType == null ? fExpr.type() : fType ) +
            " = " + fExpr;
    }

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public String toString() {
        return "Bind variable";
    }
}

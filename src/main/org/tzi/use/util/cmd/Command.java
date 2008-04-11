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

package org.tzi.use.util.cmd;

/** 
 * Abstract base class of all commands.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public abstract class Command {
    private boolean fCanUndo;

    protected Command(boolean canUndo) {
        fCanUndo = canUndo;
    }

    /** 
     * Executes command and stores undo information.
     *
     * @exception CommandFailedException if the command failed.
     */
    public abstract void execute() throws CommandFailedException;

    /**
     * Undo effect of executing a command.
     *
     * @exception CannotUndoException if the command cannot be undone or 
     *                                has not been executed before.
     */
    public abstract void undo() throws CannotUndoException;

    /**
     * Returns a short name of command, e.g. 'Create class foo' for
     * display in an undo menu item.  
     */
    public abstract String name();

    /**
     * Returns a general name of command, e.g. 'Create Class'.
     */
    public abstract String toString();

    /**
     * Returns true, if command can be undone.
     */
    public final boolean canUndo() {
        return fCanUndo; 
    }
}

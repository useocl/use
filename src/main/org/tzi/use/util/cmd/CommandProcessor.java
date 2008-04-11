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

package org.tzi.use.util.cmd;

import java.util.List;
import java.util.ArrayList;

/** 
 * A CommandProcessor executes commands, stores them on a stack and
 * allows to undo commands.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class CommandProcessor {
    private List fCommands; // (Command)

    public CommandProcessor() {
        fCommands = new ArrayList();
    }

    /** 
     * Execute and remember command.
     *
     * @exception CommandFailedException if the command failed.
     */
    public synchronized void execute(Command cmd) 
        throws CommandFailedException 
    {
        cmd.execute();
        if (cmd.canUndo() )
            fCommands.add(cmd);
        else // have to clear all predecessors
            fCommands.clear();
    }

    /** 
     * Returns true if there is at least one command to undo.
     */
    public boolean hasUndoableCmd() {
        return ! fCommands.isEmpty();
    }

    /** 
     * Returns the next command that can be undone.
     *
     * @return next undoable cmd or null, if there is no command for undo.
     */
    public Command nextUndoableCmd() {
        if (fCommands.isEmpty() )
            return null;
        else
            return (Command) fCommands.get(fCommands.size() - 1);
    }

    /**
     * Undo effect of command.
     *
     * @exception CannotUndoException if the command cannot be undone or 
     *                                has not been executed before.
     */
    public Command undoLast() 
        throws CannotUndoException
    {
        if (fCommands.isEmpty() )
            throw new CannotUndoException("no command to undo");
        int last = fCommands.size() - 1;
        Command cmd = (Command) fCommands.get(last);
        cmd.undo();
        fCommands.remove(last);
        return cmd;
    }

    /** 
     * Returns a list of executed commands. The list should not be
     * modified.
     *
     * @return List(Command)
     */
    public synchronized List commands() {
        return new ArrayList(fCommands);
    }
}

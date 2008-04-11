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

import org.tzi.use.util.cmd.Command;

/**
 * Base class for system manipulation commands.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public abstract class MCmd extends Command {
    protected MCmd(boolean canUndo) {
        super(canUndo);
    }

    /**
     * Fill a StateChangeEvent with information about this command's
     * effect.  
     *
     * @param undoChanges get information about undo action of command.
     */
    public abstract void getChanges(StateChangeEvent sce, boolean undoChanges);

    /**
     * Returns a string that can be read and executed by the USE shell
     * achieving the same effect of this command.  
     */
    public abstract String getUSEcmd();
}

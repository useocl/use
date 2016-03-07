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

/*
 * Created on 07.07.2005
 */
package org.tzi.use.main.shell;

import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import org.tzi.use.util.Log;
import org.tzi.use.util.input.Readline;

/**
 * @author green
 */
public class ReadlineStack {
    /**
     * Stack of input methods for reading user input.
     */
    private Stack<Readline> fReadlineStack;

    public ReadlineStack() {
        fReadlineStack = new Stack<Readline>();
    }
    
    public synchronized void closeAll() {
        for (Iterator<Readline> it = fReadlineStack.iterator(); it.hasNext();) {
            Readline rl = it.next();
            try {
                rl.close();
            } catch (IOException e) {
                Log.error(e);
            }
            it.remove();
        }
    }

    public synchronized Readline getCurrentReadline() {
        return fReadlineStack.peek();
    }
    
    public synchronized boolean popCurrentReadline() {
        try {
    		getCurrentReadline().close();
        } catch (IOException ex) {
            Log.error(ex.getMessage());
        }
        fReadlineStack.pop();
        return fReadlineStack.isEmpty();
    }

    /**
     * @param readline
     */
    public synchronized void push(Readline readline) {
        fReadlineStack.push(readline);
    }
    
    public synchronized boolean hasReadline(){
    	return !fReadlineStack.empty();
    }
}

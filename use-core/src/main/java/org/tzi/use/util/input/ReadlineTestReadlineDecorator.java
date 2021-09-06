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
package org.tzi.use.util.input;

import java.io.IOException;

/**
 * @author green
 */
public class ReadlineTestReadlineDecorator implements Readline {

    private static int fBalance = 0;
    
    private static synchronized void incBalance() {
        ++fBalance;
    }
    
    private static synchronized void decBalance() {
        --fBalance;
    }
    
    public static synchronized int getBalance() {
        return fBalance;
    }
    
    private Readline fDecoratedReadline;
    
    public ReadlineTestReadlineDecorator(Readline rl) {
        fDecoratedReadline = rl;
        incBalance();
    }
    
    public String readline(String prompt) throws IOException {
        return fDecoratedReadline.readline(prompt);
    }

    public void usingHistory() {
        fDecoratedReadline.usingHistory();

    }

    public void readHistory(String filename) throws IOException {
        fDecoratedReadline.readHistory(filename);

    }

    public void writeHistory(String filename) throws IOException {
        fDecoratedReadline.writeHistory(filename);

    }

    public void close() throws IOException {
        fDecoratedReadline.close();
        decBalance();
    }

    public boolean doEcho() {
        return fDecoratedReadline.doEcho();
    }
}

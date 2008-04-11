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

package org.tzi.use.gui.util;

import java.io.IOException;
import java.io.Writer;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;


/** 
 * A Writer directing its ouptut into a JTextComponent.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class TextComponentWriter extends Writer {
    private JTextComponent fTextComp;

    public TextComponentWriter(JTextComponent textComp) {
        fTextComp = textComp;
    }

    /**
     * Writes a portion of an array of characters.
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        final String s = new String(cbuf, off, len);

        // be safe here: we might be called from outside the
        // event-dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    String oldText = fTextComp.getText();
                    fTextComp.setText(oldText + s);
                    //fTextComp.repaint();
                }
            });
    }

    /**
     * Flushes the stream.
     */
    public void flush() throws IOException {
    }

    /**
     * Closes the stream, flushing it first.
     */
    public void close() throws IOException {
    }
}

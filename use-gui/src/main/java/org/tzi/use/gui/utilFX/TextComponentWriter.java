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

package org.tzi.use.gui.utilFX;

import java.io.IOException;
import java.io.Writer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

/**
 * A Writer directing its ouptut into a JavaFX TextInputControl (e.g., TextArea or TextField).
 * 
 * @author  Akif Aydin
 */
public class TextComponentWriter extends Writer {
    private final TextInputControl fTextControl;

    public TextComponentWriter(TextInputControl textControl) {
        this.fTextControl = textControl;
    }

    /**
     * Writes a portion of an array of characters.
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        final String s = new String(cbuf, off, len);

        // Ensure thread safety by running on JavaFX Application Thread
        Platform.runLater(()-> {
            String oldText = fTextControl.getText();
            fTextControl.setText(oldText + s);
            // Auto-scroll to bottom
            if (fTextControl instanceof TextArea) {
                fTextControl.positionCaret(fTextControl.getText().length());
            }
        });
    }

    /**
     * Flushes the stream.
     */
    public void flush() throws IOException {
        // No operation needed for TextInputControl
    }

    /**
     * Closes the stream, flushing it first.
     */
    public void close() throws IOException {
        // No operation needed for TextInputControl
    }
}

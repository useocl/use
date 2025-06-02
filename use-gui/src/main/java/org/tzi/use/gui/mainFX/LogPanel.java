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

package org.tzi.use.gui.mainFX;

import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.Writer;

/**
 * A Log panel with a scrollable text area.
 * 
 * @author  Akif Aydin [old:Mark Richters]
 */
@SuppressWarnings("serial")
public class LogPanel extends Writer {
    private final TextArea fTextLog;
    private final ContextMenu fPopupMenu; // context menu on right mouse click

    public LogPanel(TextArea fTextLog) {

        this.fTextLog = fTextLog;

        fTextLog.setEditable(false);

        // create the popup menu
        fPopupMenu = new ContextMenu();

        // Füge die "Clear" Option hinzu
        MenuItem clearItem = new MenuItem("Clear");
        clearItem.setOnAction(event -> fTextLog.clear());
        fPopupMenu.getItems().addAll(clearItem);
        fTextLog.setContextMenu(fPopupMenu);

    }

    public TextArea getTextComponent() {
        return fTextLog;
    }

    /**
     * Clears the log.
     */
    public void clear() {
        fTextLog.setText(null);
    }

    @Override
    public void write(char[] cbuf, int off, int len) {
        String text = new String(cbuf, off, len);
        // Ensure UI updates are done on the JavaFX Application Thread
        Platform.runLater(() -> fTextLog.appendText(text));
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}

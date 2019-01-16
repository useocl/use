/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views.selection.objectselection;

import org.tzi.use.uml.sys.MObject;

import java.util.Set;

/**
 * @author Carsten Schlobohm
 */
public interface DataHolder {
    /**
     * repaint the diagram
     */
    void repaint();

    /**
     * Show all objects.
     */
    void showAll();

    /**
     * Hide all objects.
     */
    void hideAll();

    /**
     * Hide a set of objects.
     * @param objects to show
     */
    void hideObjects(Set<MObject> objects);

    /**
     * Hide a single object
     * @param object to hide
     */
    void hideObject(MObject object);

    /**
     * Show a set of objects.
     * @param objects to hide
     */
    void showObjects(Set<MObject> objects);

    /**
     * Show a single object
     * @param object to show
     */
    void showObject(MObject object);

    /**
     *
     * @param repaint is repaint necessary
     */
    void invalidateContent(boolean repaint);
}

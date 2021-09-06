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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;

/**
 * Because use objects and links don't consider the
 * creation time we need an additional class for the
 * model element keys.
 *
 * @author Lars Hamann
 *
 */
public class ElementKey {
    private final Object element;

    private final int timestamp;

    public ElementKey(Object element, int timestamp) {
        if (!(element instanceof MLink) && !(element instanceof MObject))
            throw new IllegalArgumentException("Key element must be an MLink or MObject");

        this.element = element;
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return element.hashCode() * timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        ElementKey key = (ElementKey)obj;
        return this.timestamp == key.timestamp
                && this.element.equals(key.element);
    }
}

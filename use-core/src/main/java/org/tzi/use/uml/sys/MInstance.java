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

package org.tzi.use.uml.sys;

import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.ocl.value.InstanceValue;

/**
 * This is an instance of a classifier. It does not have different states.
 * This class allows stateless instances of data types.
 *
 * @author Stefan Schoon
 */
public interface MInstance {
    /**
     * Returns a name for this instance.
     */
    String name();

    /**
     * Returns the classifier of this instance.
     */
    MClassifier cls();

    /**
     * returns the value for this instance
     * @return the value for this instance
     */
    InstanceValue value();

    /**
     * Returns the state of an instance.
     */
    MInstanceState state(MSystemState state);

    boolean exists(MSystemState mSystemState);
}

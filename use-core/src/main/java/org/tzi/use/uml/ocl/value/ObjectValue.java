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

package org.tzi.use.uml.ocl.value;

import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.sys.MObject;

/**
 * A value referring to an object.
 *
 * @author  Mark Richters
 */
public final class ObjectValue extends InstanceValue {

    private final MObject fInstance;

    public ObjectValue(MClassifier t, MObject obj) {
        super(t, obj);
        fInstance = obj;
    }

    @Override
    public boolean isObject() {
    	return true;
    }

    @Override
    public MObject value() {
        return fInstance;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj instanceof ObjectValue)
            return fInstance.equals(((ObjectValue) obj).fInstance);

        return false;
    }

    public int hashCode() {
        return fInstance.hashCode();
    }

    public int compareTo(Value o) {
        if (o == this)
            return 0;
        if (o.isUndefined())
            return +1;
        if (!o.isObject())
            return toString().compareTo(o.toString());

        return fInstance.name().compareTo(((ObjectValue) o).fInstance.name());
    }
}

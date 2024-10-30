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

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MInstance;

/**
 * A value referring to an instance.
 *
 * @author Stefan Schoon
 */
public abstract class InstanceValue extends Value {

    protected final MInstance fInstance;

    protected InstanceValue(Type t, MInstance instance) {
        super(t);
        this.fInstance = instance;
    }

    public MInstance value() {
        return fInstance;
    }

    /**
     * Returns the <em>actual</em> type of the object.
     */
    public Type getRuntimeType() {
        return fInstance.cls();
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append(fInstance.name());
    }
}

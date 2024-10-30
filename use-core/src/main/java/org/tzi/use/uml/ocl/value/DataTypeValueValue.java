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
import org.tzi.use.util.collections.CollectionComparator;

import java.util.Map;
import java.util.Objects;

/**
 * A value referring to a data type.
 *
 * @author Stefan Schoon
 */
public final class DataTypeValueValue extends InstanceValue {
    private final Map<String, Value> values;

    public DataTypeValueValue(Type t, MInstance instance, Map<String, Value> values) {
        super(t, instance);
        this.values = values;
    }

    public Map<String, Value> getValues() {
        return values;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(super.type()).append(values.toString());
        return sb;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        DataTypeValueValue that = (DataTypeValueValue) obj;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    public int compareTo(Value o) {
        if (o == this)
            return 0;
        if (o instanceof UndefinedValue)
            return +1;
        if (!(o instanceof InstanceValue))
            // Use textual representation to compare
            return this.toStringWithType().compareTo(o.toStringWithType());

        DataTypeValueValue other = (DataTypeValueValue) o;
        return new CollectionComparator<Value>().compare(((DataTypeValueValue) o).values.values(), other.values.values());
    }
}

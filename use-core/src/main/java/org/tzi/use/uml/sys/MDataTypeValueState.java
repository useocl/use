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

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;
import org.tzi.use.util.StringUtil;

import java.util.*;

/**
 * A {@link MDataTypeValueState} is the state of a {@link MDataTypeValue} (without a system state).
 *
 * @author Stefan Schoon
 */
public final class MDataTypeValueState implements MInstanceState {
    /**
     * Slots holding a value for each attribute.
     */
    private final Map<MAttribute, Value> fAttrSlots;

    /**
     * owner instance
     */
    private final MInstance fInstance;

    public MDataTypeValueState(MDataTypeValue instance) {
        fInstance = instance;

        List<MAttribute> atts = instance.cls().allAttributes();
        // initialize attribute slots with instance values
        fAttrSlots = new IdentityHashMap<MAttribute, Value>(atts.size());

        for (MAttribute attr : atts) {
            fAttrSlots.put(attr, instance.value().getValues().get(attr.name()));
        }
    }

    /**
     * Copy constructor.
     */
    public MDataTypeValueState(MDataTypeValueState x) {
        this.fAttrSlots = new IdentityHashMap<MAttribute, Value>(x.fAttrSlots);
        this.fInstance = x.fInstance;
    }

    /**
     * Returns the instance linked to this state.
     */
    public MInstance object() {
        return fInstance;
    }

    /**
     * Returns the value of the specified attribute.
     *
     * @exception IllegalArgumentException attr is not part of this instance.
     */
    public Value attributeValue(MAttribute attr) {
        Value val = fAttrSlots.get(attr);

        if (val == null)
            throw new IllegalArgumentException("Attribute " + StringUtil.inQuotes(attr) + " does not exist in instance "
                    + StringUtil.inQuotes(fInstance.name()) + ".");

        return val;
    }

    /**
     * Returns the value of the specified attribute.
     *
     * @exception IllegalArgumentException attributeName is not a name of an attribute of this instance.
     */
    public Value attributeValue(String attributeName) {
        MAttribute attribute = fInstance.cls().attribute(attributeName, true);
        return attributeValue(attribute);
    }

    /**
     * Returns a map with attribute/value pairs. The returned map is unmodifiable.
     *
     * @return Map(MAttribute, Value)
     */
    public Map<MAttribute, Value> attributeValueMap() {
        return Collections.unmodifiableMap(fAttrSlots);
    }

    @Override
    public Set<MProtocolStateMachineInstance> getProtocolStateMachinesInstances() {
        return new HashSet<>();
    }
}

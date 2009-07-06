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

package org.tzi.use.util;

import java.util.Map;
import java.util.HashMap;

/**
 * A generator for creating unique names. Given a name a it produces
 * a1,a2,... each time a new name is requested.
 *
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class UniqueNameGenerator {
    private Map<String, MutableInteger> fNameMap;

    // We don't want to allocate a new Integer object each time we
    // have to increment the value in a map.
    class MutableInteger {
        int fInt = 1;
    }

    public UniqueNameGenerator() {
        fNameMap = new HashMap<String, MutableInteger>();
    }
    
    /**
     * Creates a new name by appending <code>name</code> with a
     * number. Numbering starts with 1. Subsequent calls will
     * increment this number.
     */
    public String generate(String name) {
        MutableInteger i = fNameMap.get(name);
        if (i == null ) {
            i = new MutableInteger();
            fNameMap.put(name, i);
        } else
            i.fInt++;
        return name + String.valueOf(i.fInt);
    }
}

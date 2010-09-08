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

package org.tzi.use.uml.mm;

import java.util.HashMap;
import java.util.Map;


/**
 * Base class for all model elements.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

abstract class MModelElementImpl implements MModelElement {
    private String fName;
    private static Map<String, MutableInteger> fNameMap = new HashMap<String, MutableInteger>();
    private int hashCode;
    
    // We don't want to allocate a new Integer object each time we
    // have to increment the value in a map.
    class MutableInteger {
        int fInt = 1;
    }

    protected MModelElementImpl(String name) {
        if (name == null || name.length() == 0 )
            throw new IllegalArgumentException("Modelelement without name");
        fName = name;
        hashCode = fName.hashCode();
    }
    
    /**
     * Creates a new model element with optional name. If the name is
     * null or empty a new name starting with <code>prefix</code> will
     * be generated. Note that the generated names will be unique but
     * they may still clash with some user defined name.
     */
    protected MModelElementImpl(String name, String prefix) {
        if (name == null || name.length() == 0 ) {
            MutableInteger i = fNameMap.get(prefix);
            if (i == null ) {
                i = new MutableInteger();
                fNameMap.put(prefix, i);
            } else
                i.fInt++;
            name = prefix + String.valueOf(i.fInt);
        }
        fName = name;
        hashCode = fName.hashCode();
    }
    
    /**
     * Returns the name of this model element.
     */
    public String name() {
        return fName;
    }

    /**
     * Process this element with visitor.
     */
    public abstract void processWithVisitor(MMVisitor v);

    public int hashCode() { 
        return hashCode;
    }

    /**
     * The default method defines model elements to be equal if their
     * names are equal.
     */
    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        if (obj instanceof MModelElement )
            return fName.equals(((MModelElement) obj).name());
        return false;
    }

    /**
     * Compares just the model element's name.
     */
    public int compareTo(MModelElement o) {
        if (o == this )
            return 0;
        if (! (o instanceof MModelElement) )
            throw new ClassCastException();
        return fName.compareTo(((MModelElement) o).name());
    }

    /**
     * Returns the name of this model element.
     */
    public String toString() {
        return fName;
    }
}

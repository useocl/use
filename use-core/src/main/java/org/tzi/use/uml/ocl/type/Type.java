/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.uml.ocl.type;

import org.tzi.use.util.BufferedToString;
import org.tzi.use.uml.api.IType;

/**
 * Low-level OCL Type. Implements the API-level IType; this interface only adds
 * ocl-specific helpers not required by the mm layer.
 */
public interface Type extends IType, BufferedToString {

    /**
     * Return a short printable name (e.g. 'Set(...)').
     */
    String shortName();

    /**
     * Qualified name for model-created types.
     */
    String qualifiedName();

    /**
     * ocl-specific overload returning an ocl Type as common supertype.
     */
    Type getLeastCommonSupertype(Type other);

    /**
     * Bridge for API-level IType.getLeastCommonSupertype.
     */
    @Override
    default IType getLeastCommonSupertype(IType other) {
        if (other instanceof Type) {
            return getLeastCommonSupertype((Type) other);
        }
        return null;
    }
}

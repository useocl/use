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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.uml.sys;

import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;

/**
 * A link is an instance of an association.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public interface MLink {
    /**
     * Returns the association describing this link.
     */
    MAssociation association();

    /** 
     * Returns all link ends of this link.
     *
     * @return Set(MLinkEnd)
     */
    Set linkEnds();

    /**
     * Returns the set of objects participating in this link.
     *
     * @return Set(MObject).
     */
    Set linkedObjects();

    /**
     * Returns the set of objects participating in this link.
     *
     * @return Set(MObject).
     */
    MObject[] linkedObjectsAsArray();

    /** 
     * Returns the link end for the given association end.
     */
    MLinkEnd linkEnd(MAssociationEnd aend);
}

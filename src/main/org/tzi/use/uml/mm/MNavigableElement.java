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

import java.util.List;
import java.util.Set;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;

/**
 * MNavigableElement instances represent model elements which can be
 * used for navigation in OCL.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public interface MNavigableElement {

    public MClass cls();

    public Type getType( Type sourceObjectType, MNavigableElement src );

    public MAssociation association();

    public String nameAsRolename();

    boolean isUnion();
    
    /**
     * Returns true, if this navigable element is derived.
     * That is it has a derive expression.
     * @return
     */
    boolean isDerived();
    
    /**
     * Returns the derive expression of this navigable end if any
     * @return
     */
    Expression getDeriveExpression();
    	
    void setUnion(boolean newValue);
    
    Set<MAssociationEnd> getSubsettingEnds();
    
    Set<MAssociationEnd> getRedefiningEnds();
    
    public List<MAssociationEnd> getAllOtherAssociationEnds();
    
    public boolean equals( Object obj );

    public int hashCode();

}

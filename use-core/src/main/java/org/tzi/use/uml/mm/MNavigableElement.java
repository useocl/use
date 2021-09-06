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
import org.tzi.use.uml.ocl.expr.VarDecl;
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

    /**
     * Returns the <code>Type</code> of the navigation from <code>sourceObjectType</code> via
     * the association end <code>src</code> to this end.
     * 
     * <code>src</code> is required because redefined ends
     * or the navigation over an qualified association without qualifier values
     * can change the type of the navigation.
     * 
     * let C be the class at dstEnd, then the result type is:
     * <li> <code>C</code> if the multiplicity is max. one, this is binary association and qualifier values are provided for a qualified association
     * <li> <code>OrderedSet(C)</code> if this association end is marked as {ordered} and qualifier values are provided for a qualified association
     * <li> <code>Set(C)</code> if the multiplicity is greater than 1 qualifier values are provided for a qualified association
     * <li> <code>Bag(C)</code> if <code>src</code> has qualifiers, but no qualifier values are provided
     * <li> <code>Sequence(C)</code> if this association end is marked as {ordered} and <code>src</code> has qualifiers, but no qualifier values are provided
     * 
     * @param sourceObjectType The concrete <code>Type</code> the navigation starts from. 
     * @param src The source end the navigation starts from.
     * @param qualifiedAccess Boolean value whether qualifier values are used or not. 
     * @return The result <code>Type</code> of a navigation with the specified parameters.
     */
    public Type getType( Type sourceObjectType, MNavigableElement src, boolean qualifiedAccess );

    public MAssociation association();

    /**
	 * Returns the role name for elements which are reachable
	 * by navigation. For association ends, this is the name,
	 * whereas for classes this is the name starting with a lower case
	 * letter.
	 * TODO: This changed in OCL 2.3 to returning the name as is.
	 */
    public String nameAsRolename();

    void setUnion(boolean newValue);
    
    boolean isUnion();
    
    /**
     * Returns true, if this navigable element is derived.
     * That is it has a derive expression.
     * @return
     */
    boolean isDerived();
    
    /**
	 * Returns the expression to calcualte the derived value
	 * of this association end if any.
	 * @return Expression to calculate the derived value if present otherwise <code>null</code>
	 */
    Expression getDeriveExpression();
        
    Set<MAssociationEnd> getSubsettingEnds();
    
    Set<MAssociationEnd> getRedefiningEnds();
    
    /**
	 * The immutable list of qualifier for this navigable end.
	 * @return The <code>List</code> of all defined qualifiers or an empty list.
	 */
    public List<VarDecl> getQualifiers();
    
    /**
	 * True if there are any qualifiers defined for this association end.
	 * @return A <code>Boolean</code> value indicating if this end has defined qualifiers.
	 */
    public boolean hasQualifiers();
    
    public List<MAssociationEnd> getAllOtherAssociationEnds();
    
    public boolean isCollection();
    
    public boolean equals( Object obj );

    public int hashCode();

}

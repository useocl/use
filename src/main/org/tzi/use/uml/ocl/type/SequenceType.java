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

package org.tzi.use.uml.ocl.type;

import java.util.List;

/**
 * The OCL Sequence type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetType
 * @see     BagType
 */
public final class SequenceType extends CollectionType {

    public SequenceType(Type elemType) {
        super(elemType);
    }
    
    @Override
	public boolean isInstantiableCollection() {
    	return true;
    }

    @Override
	public String shortName() {
        if (elemType().isCollection(true) )
            return "Sequence(...)";
        else 
            return "Sequence(" + elemType() + ")";
    }

    @Override
	public boolean isTrueCollection() {
    	return false;
    }
    
    @Override
	public boolean isTrueSequence() {
    	return true;
    }
    
    @Override
	public boolean isSequence() {
    	return true;
    }
    
    @Override
	public Type getLeastCommonSupertype(Type type)
    {
    	if (!type.isCollection(false))
    		return null;
    	
    	if (type.isVoidType())
    		return this;
    	
    	CollectionType cType = (CollectionType)type;
    	Type commonElementType = this.elemType().getLeastCommonSupertype(cType.elemType());
    	
    	if (commonElementType == null) 
    		return null;
    	
    	if (type.isSequence())
    		return TypeFactory.mkSequence(commonElementType);
    	else
    		return TypeFactory.mkCollection(commonElementType);
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    @Override
	public boolean isSubtypeOf(Type t) {
        if (! t.isTrueCollection() && ! t.isTrueSequence() )
            return false;

        CollectionType t2 = (CollectionType) t;
        if (elemType().isSubtypeOf(t2.elemType()) )
            return true;
        return false;
    }

    /* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.type.CollectionType#initOrderedSuperTypes(java.util.List)
	 */
    /**
     * If this collection has type Sequence(T) 
     * all types Sequence(T') and Collection(T') where T' <= T are
     * added to the list of all supertypes.
     */
	@Override
	protected void getOrderedSuperTypes(List<Type> allSupertypes) {
		for (Type t : elemType().allSupertypesOrdered()) {
			allSupertypes.add(TypeFactory.mkSequence(t));
		}

		super.getOrderedSuperTypes(allSupertypes);
	}

	@Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Sequence(");
        elemType().toString(sb);
        return sb.append(")");
    }
}

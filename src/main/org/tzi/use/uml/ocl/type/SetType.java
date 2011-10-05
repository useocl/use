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
 * The OCL Set type.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 * @see     SetType
 */
public final class SetType extends CollectionType {

    public SetType(Type elemType) {
        super(elemType);
    }

    @Override
	public String shortName() {
        if (elemType().isCollection(true) )
            return "Set(...)";
        else 
            return "Set(" + elemType() + ")";
    }

    @Override
	public boolean isTrueCollection() {
    	return false;
    }
    
    @Override
	public boolean isSet() {
    	return true;
    }
    
    @Override
	public boolean isTrueSet() {
    	return true;
    }
    
    @Override
	public boolean isInstantiableCollection() {
    	return true;
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    @Override
	public boolean isSubtypeOf(Type t) {
        if (! t.isTrueCollection() && ! t.isTrueSet() )
            return false;

        CollectionType t2 = (CollectionType) t;
        if (elemType().isSubtypeOf(t2.elemType()) )
            return true;
        
        return false;
    }

    /* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.type.CollectionType#initOrderedSuperTypes(java.util.List)
	 */
	@Override
	protected void getOrderedSuperTypes(List<Type> allSupertypes) {
        for(Type t : elemType().allSupertypesOrdered()) {
        	allSupertypes.add(TypeFactory.mkSet(t));
        }
        
		super.getOrderedSuperTypes(allSupertypes);
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
    	
    	if (type.isSet())
    		return TypeFactory.mkSet(commonElementType);
    	else
    		return TypeFactory.mkCollection(commonElementType);
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append("Set(");
        elemType().toString(sb);
        return sb.append(")");
    }
}

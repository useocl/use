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

package org.tzi.use.uml.sys;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * A link end keeps a reference to an object as defined by an
 * association end.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MLinkEnd {
    /**
     * The type of the link end
     */
	private final MAssociationEnd fAssociationEnd;
	
	/**
	 * The linked object
	 */
    private final MObject fObject;

    /**
     * Possible defined qualifier values.
     * Maybe <code>null</code>.
     */
    private final Value[] qualifierValues;
    
    /**
     * Saved hash value for performance reasons
     */
    private final int hashCode;

    /**
     * Constructs a new link end. 
     * @param aend
     * @param obj
     * @param qualifierValues The <code>Value</code>s for the qualifiers. Can be null 
     * @exception MSystemException object does not conform to the
     *            association end or the provided qualifier values do not match.
     */
    public MLinkEnd(MAssociationEnd aend, MObject obj, List<Value> qualifierValues) 
        throws MSystemException 
    {
        // make sure objects match the expected type of the
        // association end
        if (! obj.cls().isSubClassOf(aend.cls()) )
            throw new MSystemException("Object `" + obj.name() + 
                                       "' is of class `" + obj.cls() +
                                       "', but association end `" + aend + 
                                       "' can only hold objects of class `" +
                                       aend.cls() + 
                                       "' or its subclasses.");
        
        // Makes life easier...
        if (qualifierValues == null)
        	qualifierValues = Collections.emptyList();
        
        if (aend.getQualifiers().size() < qualifierValues.size()) {
        	throw new MSystemException("To many qualifer values!");
        } else if (aend.getQualifiers().size() > qualifierValues.size()) {
        	throw new MSystemException("Insufficient qualifer values!");
        }
        
        for (int index = 0; index < qualifierValues.size(); ++index) {
        	Value value = qualifierValues.get(index);
        	Type expectedType = aend.getQualifiers().get(index).type();
        	
        	if (!value.type().conformsTo(expectedType))
        		throw new MSystemException(
        			"Type of qualifier value (" + StringUtil.inQuotes(value.toStringWithType()) + 
        			") does not conform to expected qualifier type (" + StringUtil.inQuotes(expectedType.toString()) + ")!");
        }
        
        this.fAssociationEnd = aend;
        this.fObject = obj;
        this.qualifierValues = qualifierValues.toArray(new Value[qualifierValues.size()]);
        // Note: We use the hash code calculation of the provided list for the qualifier values
        this.hashCode = fAssociationEnd.hashCode() + 19 * fObject.hashCode() + 23 * qualifierValues.hashCode();
    }

    /**
     * Returns the association end describing this link end.
     */
    public MAssociationEnd associationEnd() {
        return fAssociationEnd;
    }

    /**
     * Returns the connected object.
     */
    public MObject object() {
        return fObject;
    }

    public int hashCode() { 
        return hashCode;
    }

    /**
     * Two link ends are equal iff they connect the same object,
     * have the same type and if the qualifier values (if present)
     * are equal.
     */
    public boolean equals(Object obj) { 
        if (obj == this )
            return true;
        
        if (obj instanceof MLinkEnd ) {
        	MLinkEnd oEnd = (MLinkEnd) obj;
        	if (this.hashCode != oEnd.hashCode)
        		return false;
        	
            return fAssociationEnd.equals(oEnd.fAssociationEnd)
                && fObject.equals(oEnd.fObject)
                && Arrays.equals(qualifierValues, oEnd.qualifierValues);
        }
        
        return false;
    }

    public String toString() {
		return fAssociationEnd
				+ ":"
				+ fObject
				+ (qualifierValues.length > 0 ? "["
						+ StringUtil.fmtSeq(qualifierValues, ",") + "]" : "");
    }

	/**
	 * Checks if the provided qualifier values match the
	 * qualifier values at this end.
	 * @param qualifiers The <code>List</code> of the qualifier values to check. May be <code>null</code>
	 * @return True, if the qualifiers (if any) match
	 */
	public boolean qualifierValuesEqual(List<Value> qualifiers) {
		return qualifierValuesEqual(qualifiers.toArray(new Value[0]));
	}

	/**
	 * Checks if the provided qualifier values match the
	 * qualifier values at this end.
	 * @param qualifiers The <code>Array</code> of the qualifier values to check. May be <code>null</code>
	 * @return True, if the qualifiers (if any) match
	 */
	public boolean qualifierValuesEqual(Value[] qualifiers) {
		return Arrays.equals(qualifierValues, qualifiers);
	}
	
	/**
	 * The unmodifiable List of the qualifier values of this end.
	 * @return The <code>List</code> of all qualifier values.
	 */
	public List<Value> getQualifierValues() {
		return Arrays.asList(qualifierValues);
	}

	/**
	 * @return
	 */
	public boolean hasQualifiers() {
		return fAssociationEnd.hasQualifiers();
	}
}

/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.gen.assl.statics;

import java.util.List;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.ObjectType;

/**
 * Matcher for a try on an attribute
 * @author Lars Hamann
 *
 */
public class GMatcherTry_Attribute implements IGInstructionMatcher {

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.IGInstructionMatcher#createIfMatches(java.util.List, org.tzi.use.uml.mm.MModel)
	 */
	@Override
	public GInstruction createIfMatches(List<Object> param, MModel model) {
		if (param.size() != 3)
            return null;
        
		if (!(param.get(0) instanceof GOCLExpression)) {
			return null;
		}
		
		if (!(param.get(1) instanceof String)) {
			return null;
		}
		
		if (!(param.get(2) instanceof GOCLExpression)) {
			return null;
		}
		
		if (!((GOCLExpression)param.get(2)).type().isSequence()) {
			return null;
		}
		
		GOCLExpression range = (GOCLExpression)param.get(0);
		String attributeName = (String)param.get(1); 
								
		if (!range.type().isCollection(true) && 
			!((CollectionType)range.type()).elemType().isObjectType())
				return null;
		
		ObjectType oType = (ObjectType)((CollectionType)range.type()).elemType();
		MClass cls = oType.cls();
		MAttribute attribute = cls.attribute(attributeName, true);
		
		if (attribute == null)
			return null;
		
		GOCLExpression values = (GOCLExpression) param.get(2);
		
        return new GInstrTry_Attribute( range, attribute, values);
	}

		
	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.IGInstructionMatcher#name()
	 */
	@Override
	public String name() {
		return "Try";
	}

}

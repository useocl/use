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
import org.tzi.use.uml.ocl.type.Type.VoidHandling;

/**
 * Matcher for a try on an attribute
 * @author Lars Hamann
 *
 */
public class GMatcherTry_Attribute implements IGInstructionMatcher {

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
		
		if (!((GOCLExpression)param.get(2)).type().isTypeOfSequence()) {
			return null;
		}
		
		GOCLExpression range = (GOCLExpression)param.get(0);
		String attributeName = (String)param.get(1); 
								
		if (!range.type().isKindOfCollection(VoidHandling.EXCLUDE_VOID) && 
			!((CollectionType)range.type()).elemType().isTypeOfClass())
				return null;
		
		MClass cls = (MClass)((CollectionType)range.type()).elemType();
		MAttribute attribute = cls.attribute(attributeName, true);
		
		if (attribute == null)
			return null;
		
		GOCLExpression values = (GOCLExpression) param.get(2);
		
        return new GInstrTry_Attribute( range, attribute, values);
	}
		
	@Override
	public String name() {
		return "Try";
	}

}

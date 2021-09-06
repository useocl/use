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

package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.EnumValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

import com.google.common.collect.Multimap;

/**
 * Standard operations on enumerations
 * @author Lars Hamann
 *
 */
class StandardOperationsEnum {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
		OpGeneric.registerOperation(new Op_toString(), opmap);
	}	
}

/* toString : Enum -> String */
final class Op_toString extends OpGeneric {
	public String name() {
		return "toString";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 1 && params[0].isTypeOfEnum())
			return TypeFactory.mkString();
		else
			return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		Value res;
		if (args[0].isUndefined())
			res = UndefinedValue.instance;
		else {
			// get object
			EnumValue enumVal = (EnumValue) args[0];
			res = new StringValue(enumVal.value());
		}
		return res;
	}
}

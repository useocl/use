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

package org.tzi.use.uml.sys.soil.library;

import org.tzi.use.uml.ocl.expr.operations.OpGeneric;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.soil.SoilEvaluationContext;

/**
 * Interface for SOIL library operations.
 * Similar to {@link OpGeneric}.
 * @author Lars Hamann
 *
 */
public interface LibraryOperation {

	/**
	 * Returns <code>true</code>, if the types of the arguments
	 * conform to the operation.  
	 * @param argTypes
	 * @return
	 */
	boolean matches(Type[] argTypes);
	
	/**
	 * The name of the operation.
	 * @return
	 */
	String getName();

	/**
	 * The return type of the operation.
	 * Void if no return type.
	 * @see TypeFactory
	 * @return The return type of the operation. 
	 */
	Type getReturnType();

	/**
	 * Executes the operation using the given <code>context</code> and
	 * the provided argument values.
	 * @param arguments
	 * @return
	 */
	Value execute(SoilEvaluationContext context, Value[] arguments);
}

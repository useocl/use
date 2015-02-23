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

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;


/**
 * selectByType as introduced in OCL 2.4
 * @author Lars Hamann
 *
 */
public class ExpSelectByType extends ExpSelectByKind {

	/**
	 * @param source
	 * @param t
	 * @throws SemanticException 
	 */
	public ExpSelectByType(Expression source, Type t) throws SemanticException {
		super(source, t);
	}

	@Override
	protected boolean includeElement(Value v) {
		return v.getRuntimeType().equals(type().elemType());
	}

	@Override
	public String name() {
		return "selectByType";
	}

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitExpSelectByType(this);
	}

	
}

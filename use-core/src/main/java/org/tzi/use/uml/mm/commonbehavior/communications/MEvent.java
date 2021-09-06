/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.uml.mm.commonbehavior.communications;

import org.tzi.use.parser.ExprContext;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.mm.MNamedElement;

/**
 * An event is the specification of some occurrence that may potentially trigger
 * effects by an object.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * @author Lars Hamann
 * 
 */
public abstract class MEvent implements MNamedElement {

	protected String name;
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNamedElement#name()
	 */
	@Override
	public String name() {
		return name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param vars
	 * @param exprContext
	 * @param isPre
	 * @throws SemanticException 
	 */
	public void buildEnvironment(Symtable vars, ExprContext exprContext,
			boolean isPre) throws SemanticException {
	}

	
}

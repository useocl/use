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
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElement;
import org.tzi.use.uml.mm.MOperation;

/**
 * A trigger relates an event to a behavior that may affect an instance of the classifier.
 * [<a href="http://www.omg.org/spec/UML/">UML Superstructure 2.4.1</a>]
 * @author Lars Hamann
 *
 */
public class MTrigger implements MNamedElement {

	/**
	 * The event that causes the trigger.
	 */
	private final MEvent event;

	protected MTrigger(MEvent event) {
		this.event = event;
	}
	
	/**
	 * @return the event
	 */
	public MEvent getEvent() {
		return event;
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.mm.MNamedElement#name()
	 */
	@Override
	public String name() {
		return event.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return event.toString();
	}

	/**
	 * @param vars
	 * @param exprContext
	 * @param isPre
	 * @throws SemanticException 
	 */
	public void buildEnvironment(Symtable vars, ExprContext exprContext, boolean isPre) throws SemanticException {
		event.buildEnvironment(vars, exprContext, isPre);
	}
	
	/**
	 * @param text
	 * @return
	 */
	public static MTrigger create(String text, MClass cls) {
		if (text.equals("create")) {
			return new MTrigger(new MEventCreate(cls));
		}
		
		return null;
	}

	/**
	 * @param op
	 * @param currentClass
	 * @return
	 */
	public static MTrigger create(MOperation op) {
		return new MTrigger(new MCallEvent(op));
	}
}

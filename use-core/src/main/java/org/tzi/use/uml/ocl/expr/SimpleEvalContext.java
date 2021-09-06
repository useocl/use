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

import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;

/**
 * TODO
 * @author lhamann
 *
 */
public final class SimpleEvalContext extends EvalContext {

	/**
	 * @param preState
	 * @param postState
	 * @param globalBindings
	 * @param evalLog
	 * @param evalLogIndent
	 */
	public SimpleEvalContext(MSystemState preState, MSystemState postState, VarBindings globalBindings) {
		super(preState, postState, globalBindings, null, "");
	}

	@Override
	final void enter(Expression expr) { }

	@Override
	final void exit(Expression expr, Value result) { }
}

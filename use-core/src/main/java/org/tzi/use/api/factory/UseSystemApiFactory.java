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
package org.tzi.use.api.factory;

import org.tzi.use.api.UseSystemApi;
import org.tzi.use.api.impl.UseSystemApiNative;
import org.tzi.use.api.impl.UseSystemApiUndoable;
import org.tzi.use.main.Session;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.sys.MSystem;

/**
 * Factory for creating {@link UseSystemApi} instances.
 * <p>
 * The factory lives in its own package (separate from {@code api.impl}) so that
 * the module only needs to export this factory package — implementation classes
 * in {@code api.impl} stay encapsulated and are not part of the module's public
 * surface. The dependency chain {@code factory -> impl -> api} remains
 * unidirectional, so no cycle is introduced.
 * </p>
 *
 * @author Lars Hamann
 * @see UseSystemApi
 */
public final class UseSystemApiFactory {

	private UseSystemApiFactory() {
		// prevent instantiation
	}

	/**
	 * Creates a new system API for the given session.
	 * The returned API implementation is designed to be used
	 * inside a running USE application session.
	 * @param session The session to create a new system API for.
	 * @return A new UseSystemApi instance with an empty system state to manipulate.
	 */
	public static UseSystemApi create(Session session) {
		return new UseSystemApiUndoable(session);
	}

	/**
	 * Creates a new system API for the given system.
	 * @param system The system to encapsulate with the API.
	 * @param enableUndo Whether the API should generate undo statements
	 * @return A new UseSystemApi instance with the system state encapsulated to manipulate it.
	 */
	public static UseSystemApi create(MSystem system, boolean enableUndo) {
		if (enableUndo)
			return new UseSystemApiUndoable(system);
		else
			return new UseSystemApiNative(system);
	}

	/**
	 * Creates a new system API for the given model.
	 * @param model The model to create a new system API for.
	 * @param enableUndo Whether the API should generate undo statements
	 * @return A new UseSystemApi instance with an empty system state to manipulate.
	 */
	public static UseSystemApi create(MModel model, boolean enableUndo) {
		if (enableUndo)
			return new UseSystemApiUndoable(model);
		else
			return new UseSystemApiNative(model);
	}
}

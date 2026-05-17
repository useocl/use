/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2026 University of Bremen
 *
 * Licensed under the GNU General Public License, version 2.
 */

package org.tzi.use.uml.mm.instance;

/**
 * Marker interface for a runtime model state.
 *
 * <p>Defined here so that {@link MInstance} and {@link MObject} can
 * reference "some snapshot of the running system" without dragging in
 * the concrete {@code sys.MSystemState} (which would create a
 * {@code mm → sys} back-edge). Concrete implementations live in the
 * sys layer and add the broader query surface on top of this marker.
 */
public interface IModelState {
}

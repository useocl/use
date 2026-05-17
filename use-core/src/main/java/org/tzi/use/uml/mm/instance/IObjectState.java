/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2026 University of Bremen
 *
 * Licensed under the GNU General Public License, version 2.
 */

package org.tzi.use.uml.mm.instance;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.values.Value;

/**
 * Object-level extension of {@link MInstanceState}: adds the
 * mutation and state-machine query methods that the model layer
 * (mm.expr, mm.values, mm.extension) needs without dragging in
 * the sys layer. Concrete implementation: {@code sys.MObjectState}.
 */
public interface IObjectState extends MInstanceState {

    /**
     * Sets an attribute value.
     */
    void setAttributeValue(MAttribute attr, Value value);

    /**
     * Returns true if this object is in the given state machine state.
     */
    boolean isInState(MState state);
}

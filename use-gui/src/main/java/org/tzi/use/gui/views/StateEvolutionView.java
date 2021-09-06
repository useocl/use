/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views;

import java.awt.Color;

import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.tags.SystemStructureChangedEvent;

import com.google.common.eventbus.Subscribe;

/** 
 * A LineChartView showing the evolution of objects and links over time.
 *
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class StateEvolutionView extends LineChartView implements View {
    private MSystem fSystem;

    public StateEvolutionView(MSystem system) {
        super(50, 2, new Color[] { Color.blue, Color.red });
        fSystem = system;
        fSystem.getEventBus().register(this);
        update();
    }

    private void update() {
        MSystemState systemState = fSystem.state();
        int[] values = { systemState.allObjects().size(), 
                         systemState.allLinks().size() };
        addValues(values);
    }

    @Subscribe
    public void onStructureChanged(SystemStructureChangedEvent e) {
        update();
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
    }
}

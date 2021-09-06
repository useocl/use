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
import java.util.Arrays;
import java.util.Collection;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;

import com.google.common.eventbus.Subscribe;

/** 
 * A BarChartView showing the number of objects in the current system
 * state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class ObjectCountView extends BarChartView implements View {
    private MSystem fSystem;
    private MClass[] fClasses;
    private int[] fValues;

    public ObjectCountView(MSystem system) {
        super("Class", "# Objects", Color.blue);
        fSystem = system;
        Collection<MClass> classes = fSystem.model().classes();
        fClasses = classes.toArray(new MClass[0]);
        Arrays.sort(fClasses);
        setNames(fClasses);
        fValues = new int[fClasses.length];
        fSystem.getEventBus().register(this);
        update();
    }

    private void update() {
        MSystemState systemState = fSystem.state();
        for (int i = 0; i < fClasses.length; i++) {
            fValues[i] = systemState.objectsOfClass(fClasses[i]).size();
        }
        setValues(fValues);
    }

    @Subscribe
    public void onObjectCreated(ObjectCreatedEvent e) {
    	update();
    }
    
    @Subscribe
    public void onObjectDestroyed(ObjectDestroyedEvent e) {
    	update();
    }
    
    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
    }
}
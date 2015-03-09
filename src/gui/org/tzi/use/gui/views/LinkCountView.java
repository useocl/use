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

package org.tzi.use.gui.views;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;

import com.google.common.eventbus.Subscribe;

/** 
 * A BarChartView showing the number of links in the current system
 * state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
@SuppressWarnings("serial")
public class LinkCountView extends BarChartView implements View {
    private MSystem fSystem;
    private MAssociation[] fAssociations;

    public LinkCountView(MSystem system) {
        super("Association", "# Links", Color.red);
        fSystem = system;
        Collection<MAssociation> associations = fSystem.model().associations();
        fAssociations = associations.toArray(new MAssociation[associations.size()]);
        Arrays.sort(fAssociations);
        setNames(fAssociations);
        fSystem.registerRequiresAllDerivedValues();
        fSystem.getEventBus().register(this);
        update();
    }

    private void update() {
        int[] values = new int[fAssociations.length];
        MSystemState systemState = fSystem.state();
        for (int i = 0; i < fAssociations.length; i++) {
            values[i] = systemState.linksOfAssociation(fAssociations[i]).size();
        }
        setValues(values);
    }

    @Subscribe
    public void stateChanged(LinkInsertedEvent e) {
    	update();
    }
    
    @Subscribe
    public void stateChanged(LinkDeletedEvent e) {
    	update();
    }
    
    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
        fSystem.unregisterRequiresAllDerivedValues();
    }
}

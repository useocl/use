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

package org.tzi.use.gui.util;

import java.awt.*;

/** 
 * A helper class for adding components to a container with a
 * GridBagLayout.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class GridBagHelper {
    private Container fContainer;
    private GridBagConstraints fConstraints;
    private GridBagLayout fGridBag;

    /**
     * Creates a GridBagHelper for the specified container. A new
     * GridBagLayout manager is set to manage the container. 
     */
    public GridBagHelper(Container con) {
        fContainer = con;
        fConstraints = new GridBagConstraints();
        fGridBag = new GridBagLayout();
        fContainer.setLayout(fGridBag);
    }

    /**
     * Adds a component with the specified constraints to the
     * container. 
     */
    public void add(Component comp, 
                    int gridx, int gridy,
                    int gridwidth, int gridheight,
                    double weightx, double weighty,
                    int fill) {
        fConstraints.gridx = gridx;
        fConstraints.gridy = gridy;
        fConstraints.gridwidth = gridwidth;
        fConstraints.gridheight = gridheight;
        fConstraints.weightx = weightx;
        fConstraints.weighty = weighty;
        fConstraints.fill = fill;
        fGridBag.setConstraints(comp, fConstraints);
        fContainer.add(comp);
    }
}

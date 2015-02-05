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


package org.tzi.use.gui.views.diagrams.util;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;

@SuppressWarnings("serial")
public class GUI extends JFrame {

    public static void main(final String[] argv) {
        //Create Window and make it visible
        final JFrame frame = new JFrame("DrawTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
        final Graphics graphic = mainPanel.getGraphics();

        try {
            DirectedEdgeFactory.createInheritance(100, 100, 200, 100).draw(graphic);
            DirectedEdgeFactory.createAggregation(100, 100, 200, 0, false).draw(graphic);
            DirectedEdgeFactory.createAssociation(100, 100, 100, 0, false).draw(graphic);
            DirectedEdgeFactory.createComposition(100, 100, 0, 0, false).draw(graphic);
            DirectedEdgeFactory.createDependency(100, 100, 0, 100).draw(graphic);
            DirectedEdgeFactory.createImplementation(100, 100, 0, 200).draw(graphic);
            DirectedEdgeFactory.createInheritance(100, 100, 100, 200).draw(graphic);
            DirectedEdgeFactory.createAggregation(100, 100, 200, 200, false).draw(graphic);
            DirectedEdgeFactory.createDependency(300, 100, 400, 100, 50, 10).draw(graphic);
            DirectedEdgeFactory.createImplementation(300, 100, 400, 0, 5, 5).draw(graphic);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

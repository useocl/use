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

// $Id$

package org.tzi.use.gui.views.diagrams;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * A panel that displays the current selection box when selecting multiple nodes
 * by surrounding them with the mouse.
 * 
 * @author Frank Hilken
 */
public class SelectionBox extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final Point startPoint;
	
	public SelectionBox(Point p) {
		startPoint = p;
		setBounds(p.x, p.y, 0, 0);
		setOpaque(false);
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color oldColor = g.getColor();
        g.setColor(new Color(192, 192, 192, 50));
        g.fillRect(1, 1, Math.max(0, this.getWidth()-2), Math.max(0, this.getHeight()-2));
        
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, Math.max(0, this.getWidth()-1), Math.max(0, this.getHeight()-1));
        g.setColor(oldColor);
    }
    
    public void updateForCursorPosition(Point p){
    	Rectangle rectangle = new Rectangle(startPoint);
    	rectangle.add(p);
    	setBounds(rectangle);
    }
}
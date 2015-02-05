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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;

/** 
 * This panel produces a bar chart view. A list of names is displayed
 * in a column. For each name an integer value determines the relative
 * length of a bar in a second column. The columns have headers at
 * their top. While the names are fixed after initialization, their
 * associated values may change over time.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class BarChartView extends JPanel {
    private String fHeader1;    // for name column
    private String fHeader2;    // for bar column
    private Color fBarColor;    // color of bar rectangles
    private Object[] fNames;    // names in left column
    private int fMaxNameWidth;  // max. width of names
    private int[] fValues;  // values associated to names
    private int fMaxValue;  // max. value
    private int fFontSize;
    private Font fFont;

    /**
     * Creates a BarChartView with given headers and bar color.
     */
    public BarChartView(String header1, String header2, Color barColor) {
        fHeader1 = header1;
        fHeader2 = header2;
        fBarColor = barColor;
        fMaxValue = 1;
        fFont = Font.getFont("use.gui.userFont", getFont());
        fFontSize = fFont.getSize();
        setBackground(Color.white);
        setLayout(null);
        setMinimumSize(new Dimension(100, 50));
    }

    private Dimension calcPreferredSize() {
        FontMetrics fm = getFontMetrics(fFont);
        fMaxNameWidth = fm.stringWidth(fHeader1);
        for (int i = 0; i < fNames.length; i++)
            fMaxNameWidth = Math.max(fMaxNameWidth, 
                                     fm.stringWidth(fNames[i].toString()));
        return new Dimension(10 + fMaxNameWidth + 10 + 100 + 10, 
                             fFontSize + 2 + fNames.length * (fFontSize + 2) + 4);
    }

    /**
     * Sets new values to be displayed.
     */
    public void setNames(Object[] names) {
        fNames = names;
        fValues = new int[names.length];
        setPreferredSize(calcPreferredSize());
    }

    /**
     * Sets new values to be displayed.
     */
    public synchronized void setValues(int[] values) {
        if (values.length != fNames.length )
            throw new IllegalArgumentException("values.length != fNames.length");
        fMaxValue = Integer.MIN_VALUE;
        fValues = values;
        for (int i = 0; i < fValues.length; i++)
            if (fValues[i] > fMaxValue )
                fMaxValue = fValues[i];
        if (fMaxValue == 0 )
            fMaxValue = 1;
        repaint();
    }

    /**
     * Draws the bar chart.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
		// copy values to prevent racing condition when the values are updated
		// while the component is painted
        int[] values;
        int maxValue;
        synchronized (this) {
			values = this.fValues;
			maxValue = this.fMaxValue;
		}
        
        Font oldFont = g.getFont();
        g.setFont(fFont);
        
        // respect borders
        Insets insets = getInsets();
        Rectangle r = new Rectangle(getSize());
        r.x += insets.left;
        r.y += insets.top;
        r.width -= insets.left + insets.right;
        r.height -= insets.top + insets.bottom;
	
        // write headers
        g.setColor(Color.lightGray);

        // background rectangle of header
        g.fill3DRect(r.x, r.y, r.width, fFontSize + 2, true);
        int x = r.x + 10; 
        int y = r.y + fFontSize;

        // vertical line between columns
        g.drawLine(x + fMaxNameWidth + 5, r.y + 1, 
                   x + fMaxNameWidth + 5, r.y + r.height);

        // header text
        g.setColor(Color.black);
        g.drawString(fHeader1, x, y);
        g.drawString(fHeader2, x + fMaxNameWidth + 10, y);

        // left column
        y += fFontSize + 4;
        for (int i = 0; i < fNames.length; i++) {
            g.drawString(fNames[i].toString(), x, y);
            y += fFontSize + 2;
        }

        // right column
        x = r.x + 10 + fMaxNameWidth + 10;
        y = r.y + 2 * fFontSize + 4;
        for (int i = 0; i < values.length; i++) {
            // draw bar
            g.setColor(new Color(fBarColor.getRed() * (i+1) / values.length,
                                 fBarColor.getGreen() * (i+1) / values.length,
                                 fBarColor.getBlue() * (i+1) / values.length));
            
            // +1 because the bar can not be 0 wide
            int width = (100 * values[i] / maxValue) +1;
            g.fill3DRect(x, y - fFontSize,
                         width, fFontSize, true);

            // draw number right from bar
        	g.setColor(Color.black);
            g.drawString(Integer.toString(values[i]), x+width+2, y - 2);
            y += fFontSize + 2;
        }
        g.setFont(oldFont);
    }
}

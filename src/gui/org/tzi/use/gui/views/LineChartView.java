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

import java.awt.*;
import javax.swing.*;

/** 
 * This panel produces a line chart view. 
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class LineChartView extends JPanel {
    private int[][] fValues;    // y-values
    private int fRangeX;    // max. number of samples on x-axis
    private int fNumLines;  // number of lines drawn
    private Color[] fColors;    // color of each line
    private int fRangeCurrentX; // 0 <= fCurrentX < fMaxX 
    private int fMaxY = 0;  // max. y value of all samples
    private int fTopY = 10; // fMaxY <= fTopY
    
    private Font fFont;

    /**
     * Creates a LineChartView.
     *
     * @param xres     number of points on x-axis
     * @param numLines number of lines to be drawn
     * @param colors   color for each line
     */
    public LineChartView(int xres, int numLines, Color[] colors) {
        fRangeX = xres;
        fNumLines = numLines;
        fValues = new int[fRangeX][fNumLines];
        fColors = colors;

        fFont = Font.getFont("use.gui.userFont", getFont());
        setBackground(Color.white);
        setLayout(null);
        setMinimumSize(new Dimension(50, 50));
        setPreferredSize(new Dimension(200, 100));
    }

    /**
     * Adds a set of new values to be displayed.
     */
    public void addValues(int[] values) {
        if (values.length != fNumLines )
            throw new IllegalArgumentException("values.length != fNumLines");

        if (fRangeCurrentX < fRangeX - 1)
            fRangeCurrentX++;
        else {
            // move range
            for (int i = 1; i < fRangeX; i++)
                fValues[i - 1] = fValues[i];
            fValues[fRangeX - 1] = new int[fNumLines];
        } 

        // copy values to current end of range
        for (int i = 0; i < values.length; i++) {
            int v = values[i];
            fValues[fRangeCurrentX][i] = v;
            if (v > fMaxY ) 
                fMaxY = v;
        }
    
        if (fMaxY > 10 )
            fTopY = ((fMaxY / 100) + 1 ) * 100;
        //System.out.println("fMaxY = " + fMaxY + " fTopY = " + fTopY);

        // update view
        repaint();
    }

    /**
     * Draws the panel.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font oldFont = g.getFont();
        g.setFont(fFont);

        //      Log.trace(this, getBounds().toString());
        //      Log.trace(this, getInsets().toString());
    
        // respect borders
        Insets insets = getInsets();
        Rectangle r = getBounds();
        r.x += insets.left;
        r.y += insets.top;
        r.width -= insets.left + insets.right;
        r.height -= insets.top + insets.bottom;

        // System.out.println("paintComponent" + count++);


        int h = r.height - 10;
        int w = r.width - 10;

        g.setColor(Color.lightGray);
        // y-axis
        g.drawLine(r.x + 10, r.y, r.x + 10, r.y + h);

        // x-axis
        g.drawLine(r.x + 10, r.y + h, r.x + r.width, r.y + h);

        g.setColor(Color.black);
        g.drawString(Integer.toString(fTopY), r.x, r.y + 10);

        // draw lines
        for (int i = 0; i < fNumLines; i++) {
            g.setColor(fColors[i]);
            // first point in origin
            int y1 = r.y + h;
            int x1 = r.x + 10;
            for (int t = 0; t <= fRangeCurrentX; t++) {
                int x2 = r.x + 10 + 
                    (int) Math.round((double) t * w / (fRangeX - 1));
                int y2 = r.y + 
                    (int) Math.round(h - ((double) h * fValues[t][i] / fTopY));
                if (t > 0 )
                    g.drawLine(x1, y1, x2, y2);
                x1 = x2;
                y1 = y2;
            }
        }
        // restore font
        g.setFont(oldFont);
    }
}

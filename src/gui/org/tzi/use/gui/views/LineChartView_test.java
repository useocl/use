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
import java.util.Random;
import javax.swing.*;

/** 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class LineChartView_test {

    public static void main(String args[]) {
        JFrame f = new JFrame("LineChartView_test");
        Color[] colors = { Color.red, Color.blue };
        LineChartView lcv = new LineChartView(50, 2, colors);

        // Layout the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(lcv), BorderLayout.CENTER);
        f.setContentPane(contentPane);
        f.pack();
        f.setVisible(true);

        int[] values = new int[2];
        Random rand = new Random();
        try {
            for (int n = 0; n < 1000; n++) {
                Thread.sleep(100);
                for (int i = 0; i < values.length; i++) {
                    values[i] = rand.nextInt(100);
                    System.out.println("n = " + n + " value = " + values[i]);
                }
                lcv.addValues(values);
            }
        } catch (InterruptedException ex) { }
    }
}

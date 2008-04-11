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
public class BarChartView_test {

    public static void main(String args[]) {
        JFrame f = new JFrame("BarChartView_test");
        String[] names = { "Person", "Employee" , "Company", "ClassWithLongName",
                           "A", "B", "C" };
        BarChartView bcv = new BarChartView("Class", "Count", Color.blue);
        bcv.setNames(names);

        // Layout the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(bcv), BorderLayout.CENTER);
        f.setContentPane(contentPane);
        f.pack();
        f.setVisible(true);

        //      int[] values = { 10, 3, 8, 22, 14, 5, 1 };
        //      bcv.setValues(values);
        int[] values = new int[7];
        Random rand = new Random();
        try {
            for (int n = 0; n < 10; n++) {
                Thread.sleep(500);
                for (int i = 0; i < values.length; i++) {
                    values[i] = rand.nextInt(30);
                    System.out.println(values[i]);
                }
                bcv.setValues(values);
            }
        } catch (InterruptedException ex) { }
    }
}

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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Carsten Schlobohm
 */
public class WindowHelper {
    /**
     * Creates a custom JPanel which calculates its preferred size
     * with the help of the inserted border text
     * @param extraSpace allows padding
     * @return a new JPanel
     */
    public static JPanel customJPanel(final int extraSpace) {
        return new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize()
            {
                Dimension preferredSize = super.getPreferredSize();

                Border border = getBorder();
                int borderWidth = 0;

                if (border instanceof TitledBorder)
                {
                    Insets insets = getInsets();
                    TitledBorder titledBorder = (TitledBorder)border;
                    borderWidth = titledBorder.getMinimumSize(this).width + insets.left + insets.right;
                }

                int preferredWidth = Math.max(preferredSize.width, borderWidth);

                return new Dimension(preferredWidth + extraSpace, preferredSize.height);
            }
        };
    }

    /**
     * Creates a custom textArea with the look of a JLabel
     * @param text of the Item
     * @return a new JTextArea
     */
    public static JTextArea customTextArea(final String text) {
        JTextArea helpText = new JTextArea(text);
        helpText.setBackground(null);
        helpText.setEditable(false);
        helpText.setBorder(null);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setFocusable(false);
        helpText.setOpaque(false);
        helpText.setFont(UIManager.getFont("Label.font"));
        return helpText;
    }
}

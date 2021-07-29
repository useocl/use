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

import jnr.ffi.annotations.In;
import org.tzi.use.gui.util.GridBagHelper;

import javax.swing.*;
import java.awt.*;
/**
 * @author Carsten Schlobohm
 *
 * A class for a custom JPanel for the selection of
 * options for the Show related objects... option
 */
public class ShowRelatedObjectsPanel {

    /**
     * Some panels.
     */
    private JPanel selectionSettings;

    private Container container;

    private JComboBox<Integer> searchCycles;

    private JRadioButton showLinksRadio, showLinkObjectsRadio,
            showEdgesFromLinkObjectsRadio, showFunctionParameterRadio;


    /**
     * @param showAssociations show hidden links of associations if they are relevant
     * @param showAssociationClasses show hidden links association classes if they are relevant
     * @param showObjectsFromAssociation show hidden objects of an visible links
     * @param showObjectsAsParam show hidden objects which are used as function param
     * @param searchCyclesValue cycles
     */
    public ShowRelatedObjectsPanel(boolean showAssociations,
                                   boolean showAssociationClasses,
                                   boolean showObjectsFromAssociation,
                                   boolean showObjectsAsParam,
                                   int searchCyclesValue,
                                   String helpText) {

        selectionSettings = WindowHelper.customJPanel(0);
        GridBagLayout llLayout = new GridBagLayout();
        selectionSettings.setLayout(llLayout);
        JTextArea helpTextArea = WindowHelper.customTextArea(helpText);


        showLinksRadio = new JRadioButton("Show hidden links (association) of visible objects");
        showLinkObjectsRadio = new JRadioButton("Show hidden links (association class) of visible objects");
        showEdgesFromLinkObjectsRadio = new JRadioButton("Show hidden partners of visible links");
        showFunctionParameterRadio = new JRadioButton("Show hidden objects that appear as parameters in messages");

        this.searchCycles = new JComboBox<>();
        searchCycles.setBorder(BorderFactory.createTitledBorder("Search cycles"));

        for (int i = 1; i <= 10; i++) {
            searchCycles.addItem(i);
        }
        searchCycles.setSelectedIndex(0);

        GridBagHelper oclHelper = new GridBagHelper(selectionSettings);

        oclHelper.add(helpTextArea, 0, 0, 1, 2, 0.0, 4.0, GridBagConstraints.HORIZONTAL);
        oclHelper.add(showLinksRadio, 0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
        oclHelper.add(showLinkObjectsRadio, 0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
        oclHelper.add(showEdgesFromLinkObjectsRadio, 0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
        oclHelper.add(showFunctionParameterRadio, 0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);
        oclHelper.add(searchCycles, 0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.HORIZONTAL);

        showLinksRadio.setSelected(showAssociations);
        showLinkObjectsRadio.setSelected(showAssociationClasses);
        showEdgesFromLinkObjectsRadio.setSelected(showObjectsFromAssociation);
        showFunctionParameterRadio.setSelected(showObjectsAsParam);
        searchCycles.setSelectedItem(searchCyclesValue);
    }

    public JPanel getPanel() {
        return selectionSettings;
    }

    public boolean isShowAssociations() {
        return showLinksRadio.isSelected();
    }

    public boolean isShowAssociationClasses() {
        return showLinkObjectsRadio.isSelected();
    }

    public boolean isShowObjectsFromAssociation() {
        return showEdgesFromLinkObjectsRadio.isSelected();
    }

    public boolean isShowObjectsAsParam() {
        return showFunctionParameterRadio.isSelected();
    }

    public int searchCycles() {
        return searchCycles.getSelectedIndex() + 1;
    }
}

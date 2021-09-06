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

package org.tzi.use.gui.views.diagrams.event;

import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.StateMachineOrder;
import org.tzi.use.uml.mm.MModelElement;

/**
 * @author gutsche
 *
 */
public class ModelBrowserMouseHandling implements MouseListener {

    private static final String ALPHABETIC = "in alphabetic order";
    private static final String ALPHABETIC_BY_CLASS = "in alphabetic order - class name first";
    private static final String ALPHABETIC_BY_INV_NAME = "in alphabetic order - invariant name first";
    private static final String USE = "in USE file order";
    private static final String ALPHABETIC_BY_OP = "in alphabetic order - operation name first";
    private static final String ALPHABETIC_BY_COND_NAME = "in alphabetic order - condition name first";
    private static final String ALPHABETIC_BY_COND_TYPE= "in alphabetic order - pre conditions first";
    
    private ModelBrowserSorting fMBS = ModelBrowserSorting.getInstance();
    private ModelBrowser fMB;
    
    public ModelBrowserMouseHandling( ModelBrowser mb ) {
        fMB = mb;
        fHighlightElements = new HashMap<MModelElement, Boolean>();
    }
    
    /**
     * Determinds if the popupMenu is shown and if so, than
     * it shows it right away, too.
     */
    private boolean maybeShowPopup(MouseEvent e) {

        if (!e.isPopupTrigger()) {
            return false;
        }

        // create the popup menu
        JPopupMenu popupMenu; // context menu on right mouse click
        popupMenu = new JPopupMenu();      


        //////////////////////////////////////////////////////////
        // items for classes
        //////////////////////////////////////////////////////////

        JMenu subMenuCls = new JMenu("sort classes");
        popupMenu.add(subMenuCls);

        final JRadioButtonMenuItem alphabeticCls = new JRadioButtonMenuItem(ALPHABETIC);
        alphabeticCls.setSelected(ModelBrowserSorting.CLS_ALPHABETIC == fMBS.clsOrder);
        alphabeticCls.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC)) {
                    fMBS.clsOrder = ModelBrowserSorting.CLS_ALPHABETIC;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuCls.add(alphabeticCls);

        final JRadioButtonMenuItem useOrderCls = new JRadioButtonMenuItem(USE);
        useOrderCls.setSelected(ModelBrowserSorting.CLS_USE_ORDER == fMBS.clsOrder);
        useOrderCls.addItemListener(new ItemListener() {
            public void itemStateChanged(final ItemEvent ev) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.clsOrder = ModelBrowserSorting.CLS_USE_ORDER;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuCls.add(useOrderCls);
        
        // submenu for attributes
        
        JMenu subMenuAttr = new JMenu( "sort attributes" );
        subMenuCls.add( subMenuAttr );
        
        final JRadioButtonMenuItem alphabeticAttr = new JRadioButtonMenuItem( ALPHABETIC );
        alphabeticAttr.setSelected( ModelBrowserSorting.ATTR_ALPHABETIC == fMBS.attrOrder );
        alphabeticAttr.addItemListener( new ItemListener() {
            public void itemStateChanged( final ItemEvent ev ) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals( ALPHABETIC )) {
                    fMBS.attrOrder = ModelBrowserSorting.ATTR_ALPHABETIC;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuAttr.add( alphabeticAttr );
        
        final JRadioButtonMenuItem useOrderAttr = new JRadioButtonMenuItem( USE );
        useOrderAttr.setSelected( ModelBrowserSorting.ATTR_USE_ORDER == fMBS.attrOrder );
        useOrderAttr.addItemListener( new ItemListener() {
            public void itemStateChanged( final ItemEvent ev ) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.attrOrder = ModelBrowserSorting.ATTR_USE_ORDER;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuAttr.add( useOrderAttr );
        
        // submenu for operations
        
        JMenu subMenuOpr = new JMenu( "sort operations" );
        subMenuCls.add( subMenuOpr );
        
        final JRadioButtonMenuItem alphabeticOpr = new JRadioButtonMenuItem( ALPHABETIC );
        alphabeticOpr.setSelected( ModelBrowserSorting.OPR_ALPHABETIC == fMBS.oprOrder );
        alphabeticOpr.addItemListener( new ItemListener() {
            public void itemStateChanged( final ItemEvent ev ) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals( ALPHABETIC )) {
                    fMBS.oprOrder = ModelBrowserSorting.OPR_ALPHABETIC;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuOpr.add( alphabeticOpr );
        
        final JRadioButtonMenuItem useOrderOpr = new JRadioButtonMenuItem( USE );
        useOrderOpr.setSelected( ModelBrowserSorting.OPR_USE_ORDER == fMBS.oprOrder );
        useOrderOpr.addItemListener( new ItemListener() {
            public void itemStateChanged( final ItemEvent ev ) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.oprOrder = ModelBrowserSorting.OPR_USE_ORDER;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuOpr.add( useOrderOpr );

        JMenu subMenuStateM = new JMenu( "sort state machines" );
        subMenuCls.add( subMenuStateM );
        
        final JRadioButtonMenuItem alphabeticSms = new JRadioButtonMenuItem( ALPHABETIC );
        alphabeticSms.setSelected( fMBS.stateMachineOrder == StateMachineOrder.ALPHABETIC );
        alphabeticSms.addItemListener( new ItemListener() {
            public void itemStateChanged( final ItemEvent ev ) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals( ALPHABETIC )) {
                    fMBS.stateMachineOrder = StateMachineOrder.ALPHABETIC;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuStateM.add( alphabeticSms );
        
        final JRadioButtonMenuItem useOrderSms = new JRadioButtonMenuItem( USE );
        useOrderSms.setSelected( fMBS.stateMachineOrder == StateMachineOrder.USE );
        useOrderSms.addItemListener( new ItemListener() {
            public void itemStateChanged( final ItemEvent ev ) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.stateMachineOrder = StateMachineOrder.USE;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuStateM.add( useOrderSms );
        
        //////////////////////////////////////////////////////////
        // items for associations
        //////////////////////////////////////////////////////////

        final JMenu subMenuAssoc = new JMenu("sort associations");
        popupMenu.add(subMenuAssoc);

        final JRadioButtonMenuItem alphabeticAssoc = new JRadioButtonMenuItem(ALPHABETIC);
        alphabeticAssoc.setSelected(ModelBrowserSorting.ASSOC_ALPHABETIC == fMBS.assocOrder);
        alphabeticAssoc.addItemListener(new ItemListener() {
            public void itemStateChanged(final ItemEvent ev) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC)) {
                    fMBS.assocOrder = ModelBrowserSorting.ASSOC_ALPHABETIC;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuAssoc.add(alphabeticAssoc);

        final JRadioButtonMenuItem useOrderAssoc = new JRadioButtonMenuItem(USE);
        useOrderAssoc.setSelected(ModelBrowserSorting.ASSOC_USE_ORDER == fMBS.assocOrder);
        useOrderAssoc.addItemListener(new ItemListener() {
            public void itemStateChanged(final ItemEvent ev) {
                final JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.assocOrder = ModelBrowserSorting.ASSOC_USE_ORDER;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuAssoc.add(useOrderAssoc);

        //////////////////////////////////////////////////////////
        // items for invariants
        //////////////////////////////////////////////////////////

        JMenu subMenuInv = new JMenu("sort invariants");
        popupMenu.add(subMenuInv);

        final JRadioButtonMenuItem alphabeticInvByClass = new JRadioButtonMenuItem(ALPHABETIC_BY_CLASS);
        alphabeticInvByClass.setSelected(ModelBrowserSorting.INV_ALPHABETIC_BY_CLASS == fMBS.invOrder);
        alphabeticInvByClass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC_BY_CLASS)) {
                    fMBS.invOrder = ModelBrowserSorting.INV_ALPHABETIC_BY_CLASS;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuInv.add(alphabeticInvByClass);

        final JRadioButtonMenuItem alphabeticInvByInvName = new JRadioButtonMenuItem(ALPHABETIC_BY_INV_NAME);
        alphabeticInvByInvName.setSelected(ModelBrowserSorting.INV_ALPHABETIC_INV_NAME == fMBS.invOrder);
        alphabeticInvByInvName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC_BY_INV_NAME)) {
                    fMBS.invOrder = ModelBrowserSorting.INV_ALPHABETIC_INV_NAME;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuInv.add(alphabeticInvByInvName);

        final JRadioButtonMenuItem useOrderInv = new JRadioButtonMenuItem(USE);
        useOrderInv.setSelected(ModelBrowserSorting.INV_USE_ORDER == fMBS.invOrder);
        useOrderInv.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.invOrder = ModelBrowserSorting.INV_USE_ORDER;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuInv.add(useOrderInv);

        //////////////////////////////////////////////////////////
        // items for pre-/postconditions
        //////////////////////////////////////////////////////////

        final JMenu subMenuCond = new JMenu("sort pre-/postconditions");
        popupMenu.add(subMenuCond);


        final JRadioButtonMenuItem alphabeticCondByOpName = new JRadioButtonMenuItem(ALPHABETIC_BY_OP);
        alphabeticCondByOpName.setSelected(ModelBrowserSorting.COND_ALPHABETIC_BY_OPERATION == fMBS.condOrder);
        alphabeticCondByOpName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC_BY_OP)) {
                    fMBS.condOrder = ModelBrowserSorting.COND_ALPHABETIC_BY_OPERATION;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuCond.add(alphabeticCondByOpName);

        final JRadioButtonMenuItem alphabeticCondByCondName = new JRadioButtonMenuItem(ALPHABETIC_BY_COND_NAME);
        alphabeticCondByCondName.setSelected(ModelBrowserSorting.COND_ALPHABETIC_BY_NAME == fMBS.condOrder);
        alphabeticCondByCondName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC_BY_COND_NAME)) {
                    fMBS.condOrder = ModelBrowserSorting.COND_ALPHABETIC_BY_NAME;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuCond.add(alphabeticCondByCondName);

        final JRadioButtonMenuItem alphabeticCondByCondType = new JRadioButtonMenuItem(ALPHABETIC_BY_COND_TYPE);
        alphabeticCondByCondType.setSelected(ModelBrowserSorting.COND_ALPHABETIC_BY_PRE == fMBS.condOrder);
        alphabeticCondByCondType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(ALPHABETIC_BY_COND_TYPE)) {
                    fMBS.condOrder = ModelBrowserSorting.COND_ALPHABETIC_BY_PRE;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuCond.add(alphabeticCondByCondType);  
        
        final JRadioButtonMenuItem useOrderCond = new JRadioButtonMenuItem(USE);
        useOrderCond.setSelected(ModelBrowserSorting.COND_USE_ORDER == fMBS.condOrder);
        useOrderCond.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                JRadioButtonMenuItem j = (JRadioButtonMenuItem) ev.getItem();
                if (j.getText().equals(USE)) {
                    fMBS.condOrder = ModelBrowserSorting.COND_USE_ORDER;
                }
                fMBS.fireStateChanged();
            }
        });
        subMenuCond.add(useOrderCond);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
        return true;
    }
    
    
    private Rectangle fRectangle;
    private MModelElement fElem;
    private Map<MModelElement, Boolean> fHighlightElements;
    
    public void setSelectedNodeRectangle( Rectangle rec ) {
        fRectangle = rec;
    }
    
    public void setSelectedModelElement( MModelElement elem ) {
        fElem = elem;
    }
    
    private void tryToFireStateChangeEvent( MouseEvent e ) {
        if ( e.getModifiersEx() == InputEvent.BUTTON2_DOWN_MASK ) {
            if ( fRectangle != null && fRectangle.contains( e.getPoint() ) ) {
                boolean highlight = false;
                if ( fHighlightElements.containsKey( fElem ) ) {
                    highlight = 
                        fHighlightElements.get( fElem ).booleanValue();
                    if ( highlight ) {
                        highlight = false;
                    } else {
                        highlight = true;
                    }
                    fHighlightElements.put( fElem, Boolean.valueOf( highlight ) );
                } else {
                    highlight = true;
                    fHighlightElements.put( fElem, Boolean.valueOf( highlight ) );
                }
                fMB.fireStateChanged( fElem, highlight );
            }
                
        }
    }
    
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseClicked(MouseEvent e) {
        tryToFireStateChangeEvent( e );
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }
}
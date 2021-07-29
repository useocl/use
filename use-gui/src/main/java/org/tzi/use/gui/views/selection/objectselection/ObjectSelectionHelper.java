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

package org.tzi.use.gui.views.selection.objectselection;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.sys.MObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author Carsten Schlobohm
 */
public class ObjectSelectionHelper {

    public static JMenu getSubMenuShowOrHide(final DataHolder dataHolder,
                                      final TreeSet<MObject> objectTreeSet,
                                      final boolean show,
                                      final boolean hideAll) {
        final String title = (show) ? "Show object" : "Hide object";
        final String allTitle = (show) ? "Show all hidden objects" : "Hide all objects";
        final String hideOrShowTitle = (show) ? "Show " : "Hide ";

        JMenu subMenu = new JMenu(title);
        final JMenuItem allObjectMenu = new JMenuItem(allTitle);
        allObjectMenu.setEnabled((show) ? !objectTreeSet.isEmpty() : hideAll);
        allObjectMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (show)  {
                    dataHolder.showAll();
                    dataHolder.repaint();
                } else {
                    dataHolder.hideAll();
                    dataHolder.repaint();
                }

            }
        });

        subMenu.add(allObjectMenu);
        subMenu.addSeparator();

        Map<MClass, JMenu> classMenus = new HashMap<MClass, JMenu>();
        for (final MObject obj : objectTreeSet) {
            final MClass cls = obj.cls();

            if (!classMenus.containsKey(cls)) {
                JMenu subm = new JMenu("Class " + cls.name());
                classMenus.put(cls, subm);

                // Add show all
                JMenuItem allMenu = new JMenuItem(hideOrShowTitle + "all");
                allMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (show) {
                            dataHolder.showObjects(getObjectsForMClass(cls, objectTreeSet));
                            dataHolder.repaint();
                        } else {
                            dataHolder.hideObjects(getObjectsForMClass(cls, objectTreeSet));
                            dataHolder.repaint();
                        }

                    }
                });
                subm.add(allMenu);
                subm.addSeparator();
            }

            JMenu parent = classMenus.get(cls);

            final JMenuItem objectMenu = new JMenuItem(hideOrShowTitle + obj.name());
            objectMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    if (show) {
                        dataHolder.showObject(obj);
                        dataHolder.repaint();
                    } else {
                        dataHolder.hideObject(obj);
                        dataHolder.repaint();
                    }

                }
            });
            parent.add(objectMenu);
        }

        List<MClass> classes = new ArrayList<MClass>(classMenus.keySet());
        Collections.sort(classes, new MNamedElementComparator());

        for (MClass cls : classes) {
            subMenu.add(classMenus.get(cls));
        }

        return subMenu;
    }

    private static Set<MObject> getObjectsForMClass(final MClass cls, final Collection<MObject> objects) {
        final Set<MObject> filtered = new HashSet<MObject>();

        for (MObject object : objects) {
            if (object.cls().equals(cls)) {
                filtered.add(object);
            }
        }
        return filtered;
    }
}

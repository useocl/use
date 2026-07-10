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

package org.tzi.use.gui.views.diagrams;

import javax.swing.*;
import java.util.*;

/**
 * Assembles a {@link JPopupMenu} from named, ordered sections.
 *
 * <p>Callers add items to sections by name. {@link #build()} produces the
 * final menu, inserting a separator between every pair of non-empty adjacent
 * sections. Items within a section are added in insertion order. A
 * {@link JSeparator} item inside a section produces an in-section separator.</p>
 *
 * <p>Sections are assembled in declaration order:
 * {@value #SEC_SELECTION} → {@value #SEC_VISIBILITY} → {@value #SEC_ACTIONS} →
 * {@value #SEC_OPTIONS} → {@value #SEC_RENDERING} → {@value #SEC_LAYOUT} →
 * {@value #SEC_EXTRAS}.</p>
 */
public class PopupMenuBuilder {

    /** Context-sensitive actions: hide/crop selection, show hidden, destroy object, etc. */
    public static final String SEC_SELECTION  = "selection";

    /** Named-element show/hide submenus (hide class X, show link Y, etc.). */
    public static final String SEC_VISIBILITY = "visibility";

    /** Diagram-specific action launchers: PSM viewer, create/delete link, etc. */
    public static final String SEC_ACTIONS    = "actions";

    /** Toggle checkboxes for diagram-wide display options. */
    public static final String SEC_OPTIONS    = "options";

    /** Rendering controls: align elements, anti-aliasing, grid, grayscale. */
    public static final String SEC_RENDERING  = "rendering";

    /** Layout controls: auto-layout, layout algorithms, reset/load/save layout, select all. */
    public static final String SEC_LAYOUT     = "layout";

    /** Diagram-specific trailing items: coverage, export, comm-diagram extras, etc. */
    public static final String SEC_EXTRAS     = "extras";

    private static final List<String> SECTION_ORDER = Arrays.asList(
            SEC_SELECTION, SEC_VISIBILITY, SEC_ACTIONS,
            SEC_OPTIONS, SEC_RENDERING, SEC_LAYOUT, SEC_EXTRAS);

    private final LinkedHashMap<String, List<JComponent>> sections = new LinkedHashMap<>();

    public PopupMenuBuilder() {
        for (String id : SECTION_ORDER) {
            sections.put(id, new ArrayList<>());
        }
    }

    /**
     * Adds {@code item} to the named section.
     *
     * @throws IllegalArgumentException if {@code section} is not a known section id
     */
    public void add(String section, JComponent item) {
        List<JComponent> items = sections.get(section);
        if (items == null) {
            throw new IllegalArgumentException("Unknown popup menu section: " + section);
        }
        items.add(item);
    }

    /**
     * Adds an in-section separator to the named section.
     */
    public void addSeparator(String section) {
        add(section, new JPopupMenu.Separator());
    }

    /**
     * Builds and returns the assembled {@link JPopupMenu}.
     * Non-empty sections are separated by a menu separator; empty sections are skipped.
     */
    public JPopupMenu build() {
        JPopupMenu menu = new JPopupMenu();
        boolean addedAny = false;
        for (List<JComponent> items : sections.values()) {
            if (items.isEmpty()) continue;
            if (addedAny) menu.addSeparator();
            for (JComponent item : items) {
                if (item instanceof JSeparator) {
                    menu.addSeparator();
                } else {
                    menu.add(item);
                }
            }
            addedAny = true;
        }
        return menu;
    }
}

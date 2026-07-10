package org.tzi.use.gui.views.selection;

/**
 * Shared scroll-menu configuration constants for selection menu builders.
 * Implemented by {@link org.tzi.use.gui.views.selection.classselection.ClassSelection}
 * and {@link org.tzi.use.gui.views.selection.objectselection.ObjectSelection}.
 */
public interface ISelectionMenuProvider {
    int SCROLLER_VISIBLE_ITEMS = 20;
    int SCROLLER_INTERVAL_MS   = 125;
    int SCROLLER_TOP_FIXED     = 0;
    int SCROLLER_BOTTOM_FIXED  = 0;
}

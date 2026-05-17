package org.tzi.use.gui.main.runtime;

import javax.swing.Action;

/**
 * Consumer-side view of a plugin action proxy.
 *
 * <p>Exposes only what the application's main window needs to interact with
 * a plugin action: the standard Swing {@link Action} API (so the action can
 * be invoked / its icon read) plus {@link #calculateEnabled()} for refreshing
 * the enabled state on session changes.</p>
 *
 * <p>Lives in {@code gui.main} (the consumer slice) on purpose: the concrete
 * implementation in {@code gui.plugin.PluginActionProxy} implements this
 * interface, producing a one-way {@code plugin → main} edge. Without this
 * interface, fields like {@code MainWindow.pluginActions} would have to
 * reference the concrete impl type, creating a {@code main ↔ plugin} cycle.</p>
 */
public interface IPluginActionProxy extends Action {

    /**
     * Recalculate this action's enabled state, e.g. when the application's
     * session changes.
     */
    void calculateEnabled();
}

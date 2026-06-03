package org.tzi.use.gui.views.diagrams;

import org.tzi.use.gui.views.diagrams.framework.DiagramType;

import javafx.embed.swing.SwingNode;

/**
 * Lets Swing-side diagram code request a JavaFX-hosted window without
 * importing the FX MainWindow directly.
 *
 * <p>The FX MainWindow installs itself as the {@link #INSTANCE} on
 * startup; Swing action callers in {@code gui.views.diagrams.selection.*}
 * invoke {@link #createNewWindow} through this interface instead of
 * statically referencing {@code org.tzi.use.gui.mainFX.MainWindow}, which
 * would create a {@code views → mainFX} back-edge.</p>
 *
 * <p>If no FX MainWindow is active (e.g. Swing-only run), {@link #INSTANCE}
 * is {@code null} and callers fall back to a no-op or Swing-equivalent
 * path.</p>
 */
public interface IFXWindowHost {

    /** Globally installed instance, or {@code null} if FX is not active. */
    java.util.concurrent.atomic.AtomicReference<IFXWindowHost> INSTANCE =
            new java.util.concurrent.atomic.AtomicReference<>();

    /**
     * Open a JavaFX window hosting the given Swing component.
     *
     * @param title   window title
     * @param node    Swing component wrapped for FX display
     * @param type    diagram type the window represents
     */
    void createNewWindow(String title, SwingNode node, DiagramType type);

    /**
     * Returns the application session (as Object so this SPI is free of
     * upstream package dependencies). Callers downcast to
     * {@code org.tzi.use.main.Session} where the concrete type is needed.
     */
    Object getSession();
}

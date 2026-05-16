package org.tzi.use.gui.main.runtime;

/**
 * SPI-facing handle to the application's main window.
 * <p>
 * Lives in the {@code gui.main.runtime} package so that runtime plugin
 * interfaces ({@link IPluginActionExtensionPoint}) can refer to a main
 * window without depending on the concrete
 * {@code org.tzi.use.gui.main.MainWindow} class in the parent package.
 * {@code MainWindow} implements this interface, so plugins continue to
 * receive the same object instance.
 * </p>
 *
 * <p>
 * Intentionally a marker interface: no main-window methods are currently
 * part of the plugin SPI surface. Callers may downcast to the concrete
 * {@code MainWindow} type if they need window-specific behaviour, with
 * the usual caveats.
 * </p>
 */
public interface IMainWindow {
}

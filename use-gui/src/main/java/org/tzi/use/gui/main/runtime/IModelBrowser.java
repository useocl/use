package org.tzi.use.gui.main.runtime;

/**
 * SPI-facing handle to the application's model-browser tree.
 * <p>
 * Lives in the {@code gui.main.runtime} package so that runtime plugin
 * interfaces ({@link IPluginMMVisitor}, {@link IPluginMModelExtensionPoint})
 * can refer to a model browser without depending on the concrete
 * {@code org.tzi.use.gui.views.diagrams.ModelBrowser} class in the parent package.
 * {@code ModelBrowser} implements this interface, so plugins continue to
 * receive the same object instance.
 * </p>
 *
 * <p>
 * Intentionally a marker interface: no browser methods are currently part
 * of the plugin SPI surface. Callers may downcast to the concrete
 * {@code ModelBrowser} type if they need browser-specific behaviour, with
 * the usual caveats.
 * </p>
 */
public interface IModelBrowser {
}

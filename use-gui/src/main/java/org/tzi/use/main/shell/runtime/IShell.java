package org.tzi.use.main.shell.runtime;

/**
 * SPI-facing handle to the application's Shell.
 * <p>
 * Lives in the {@code shell.runtime} package so that runtime plugin interfaces
 * ({@link IPluginShellCmd}, {@link IPluginShellExtensionPoint}) can refer to a
 * shell without depending on the concrete {@code Shell} class in the parent
 * package. The concrete {@code org.tzi.use.main.shell.Shell} implements this
 * interface, so plugins continue to receive the same object instance.
 * </p>
 *
 * <p>
 * Intentionally a marker interface: no shell methods are currently part of the
 * plugin SPI surface. Callers may downcast to a concrete shell type if they
 * need shell-specific behaviour, with the usual caveats.
 * </p>
 */
public interface IShell {
}

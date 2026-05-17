package org.tzi.use.runtime.spi;

/**
 * This interface provides the Plugin Shell Command behaviour implemented by the
 * Plugin. - This interface should be implemented by any Plugin providing Shell
 * Commands.
 *
 * <p>The parameter is typed as {@link Object} so this SPI does not depend on
 * the {@code org.tzi.use.main.shell.runtime.IPluginShellCmd} interface (which
 * lives in the application layer). Implementations downcast to the concrete
 * type they know.</p>
 *
 * @author Roman Asendorf
 */
public interface IPluginShellCmdDelegate {

	/**
	 * Method to execute the Plugin's Shell Command with the given Plugin Shell
	 * Command Proxy.
	 *
	 * @param pluginCommand
	 *            The Plugin Shell Command Proxy (downcast to the concrete type
	 *            the implementation expects).
	 */
	public void performCommand(Object pluginCommand);

}

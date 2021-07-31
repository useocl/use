package org.tzi.use.runtime.shell;

import org.tzi.use.main.shell.runtime.IPluginShellCmd;

/**
 * This interface provides the Plugin Shell Command behaviour implemented by the
 * Plugin. - This interface should be implemented by any Plugin providing Shell
 * Commands.
 * 
 * @author Roman Asendorf
 */
public interface IPluginShellCmdDelegate {

	/**
	 * Method to execute the Plugin's Shell Command with the given Plugin Shell
	 * Command Proxy.
	 * 
	 * @param pluginCommand
	 *            The Plugin Shell Command Proxy
	 */
	public void performCommand(IPluginShellCmd pluginCommand);

}

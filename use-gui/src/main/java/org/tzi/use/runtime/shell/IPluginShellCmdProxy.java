package org.tzi.use.runtime.shell;

/**
 * This interface provides the Plugin Shell Command Proxy's behaviour. - This
 * interface should only be used internally
 * 
 * @author Roman Asendorf
 */
public interface IPluginShellCmdProxy {

	/**
	 * Method to execute the Plugin Shell Command.
	 * 
	 * @param currKey
	 *            The Plugin Shell Command as key
	 * @param substring
	 *            The Plugin Shell Command arguments
	 */
	public void executeCmd(String currKey, String substring);
}

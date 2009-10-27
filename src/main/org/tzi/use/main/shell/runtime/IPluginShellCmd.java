package org.tzi.use.main.shell.runtime;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;

/**
 * Interface for Shell Command Proxies - this interface should only be used
 * internally
 * 
 * @author Roman Asendorf
 */

public interface IPluginShellCmd {

	/**
	 * Method to execute a Shell Command with it's given arguments
	 * 
	 * @param cmd
	 *            The Shell Command's name
	 * @param cmdArguments
	 *            The Shell Command's arguments
	 */
	public void executeCmd(String cmd, String cmdArguments);

	/**
	 * Method to get the Shell Command's name
	 * 
	 * @return The Shell Command's name
	 */
	public String getCmd();

	/**
	 * Method to get the Session Object
	 * 
	 * @return The Session object
	 */
	public Session getSession();

	/**
	 * Method to get the Shell Object
	 * 
	 * @return The Shell object
	 */
	public Shell getShell();

	/**
	 * Method to get the Shell Command's arguments
	 * 
	 * @return The Shell Command's arguments
	 */
	public String getCmdArguments();
}

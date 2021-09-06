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
	public void executeCmd(String cmd, String cmdArguments, String[] argList);

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
	 * Method to get the Shell Command's arguments as a plain {@code String}.
	 * 
	 * @return The Shell Command's arguments
	 * @see #getCmdArgumentList()
	 */
	public String getCmdArguments();
	
	/**
	 * Method to get the Shell Command's arguments as a split
	 * {@code String array}. The splitting handles single quoted, double quoted
	 * and arguments without quotes.
	 * 
	 * @return The Shell Command's arguments
	 * @see #getCmdArguments()
	 */
	public String[] getCmdArgumentList();
}

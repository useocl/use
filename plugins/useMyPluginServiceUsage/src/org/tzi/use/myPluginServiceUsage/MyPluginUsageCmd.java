package org.tzi.use.myPluginServiceUsage;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.main.shell.runtime.IPluginShellCmd;
import org.tzi.use.runtime.shell.IPluginShellCmdDelegate;

/**
 * Example Shell Command Plugin class. When running, the main class of this
 * Plugin will be run automatically so the Service of this plugin will be
 * executed.
 * 
 * @author Roman Asendorf
 * 
 */
public class MyPluginUsageCmd implements IPluginShellCmdDelegate {

	private String fArguments;
	private Shell fShell;
	private Session fSession;
	private String fCmd;

	// Command(s) to recognize
	private final String CMD_EXAMPLE_SERVICE = "runService";

	/**
	 * Method to excute the command, called by the Plugin Shell Command Proxy in
	 * the application.
	 */
	public void performCommand(IPluginShellCmd pluginCmd) {
		this.fArguments = pluginCmd.getCmdArguments();
		this.fShell = pluginCmd.getShell();
		this.fSession = pluginCmd.getSession();
		this.fCmd = pluginCmd.getCmd();
		if (this.fCmd.startsWith(CMD_EXAMPLE_SERVICE)
				|| this.fCmd.equals(CMD_EXAMPLE_SERVICE)) {
			System.out.println("Performing [" + this.fCmd + "], ["
					+ this.fArguments.toString() + "]");

		}
	}

}

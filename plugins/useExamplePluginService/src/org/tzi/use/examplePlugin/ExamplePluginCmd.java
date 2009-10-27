package org.tzi.use.examplePlugin;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.main.shell.runtime.IPluginShellCmd;
import org.tzi.use.runtime.shell.IPluginShellCmdDelegate;

/**
 * Example Plugin Shell Command class providing to commands.
 * 
 * @author Roman Asendorf
 * 
 */
public class ExamplePluginCmd implements IPluginShellCmdDelegate {

	private String fArguments;
	private Shell fShell;
	private Session fSession;
	private String fCmd;

	// Command(s) to recognize
	private final String CMD_DISPLAY = "display";
	private final String CMD_EXAMPLE_SERVICE = "exampleService";

	/**
	 * Method to excute the command, called by the Plugin Shell Command Proxy in
	 * the application.
	 */
	public void performCommand(IPluginShellCmd pluginCmd) {
		this.fArguments = pluginCmd.getCmdArguments();
		this.fShell = pluginCmd.getShell();
		this.fSession = pluginCmd.getSession();
		this.fCmd = pluginCmd.getCmd();
		if (this.fCmd.startsWith(CMD_DISPLAY) || this.fCmd.equals(CMD_DISPLAY)) {
			// display was entered
			System.out.println("Performing the first [" + this.fCmd + "], ["
					+ this.fArguments.toString() + "]");
		} else if (this.fCmd.startsWith(CMD_EXAMPLE_SERVICE)
				|| this.fCmd.equals(CMD_EXAMPLE_SERVICE)) {
			// exampleService was entered
			System.out.println("Performing the second [" + this.fCmd + "], ["
					+ this.fArguments.toString() + "]");

		}
	}

}

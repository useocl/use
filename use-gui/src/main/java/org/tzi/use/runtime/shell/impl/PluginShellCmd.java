package org.tzi.use.runtime.shell.impl;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.main.shell.runtime.IPluginShellCmd;
import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.impl.PluginRuntime;
import org.tzi.use.runtime.shell.IPluginShellCmdDelegate;
import org.tzi.use.runtime.shell.IPluginShellCmdDescriptor;
import org.tzi.use.util.Log;

/**
 * This class provides the abstract behaviour for the Plugin Shell Command
 * Proxy.
 * 
 * @author Roman Asendorf
 */
public abstract class PluginShellCmd implements IPluginShellCmd {

	private IPluginShellCmdDelegate pluginCmdDelegate;
	private final IPluginShellCmdDescriptor pluginCmdDescriptor;
	private final Session fSession;
	private final Shell fShell;
	private String cmd;
	private String cmdArguments;
	private String[] cmdArgumentList;

	/**
	 * Constructor to create a Plugin Shell Command Proxy with the given Plugin
	 * Shell Command Descriptor.
	 * 
	 * @param pluginCmdDescriptor
	 *            The Plugin Shell Command Descriptor
	 * @param session
	 *            The application's Session object
	 * @param shell
	 *            The application's Shell object
	 */
	public PluginShellCmd(IPluginShellCmdDescriptor pluginCmdDescriptor,
			Session session, Shell shell) {
		this.pluginCmdDescriptor = pluginCmdDescriptor;
		this.fSession = session;
		this.fShell = shell;
	}

	private IPluginShellCmdDelegate createCmdDelegate() {
		IPlugin thePlugin = this.pluginCmdDescriptor.getParent()
				.getPluginClass();
		if (thePlugin == null) {
			Log
					.debug("No main plugin class found! Running cmd delegate directly.");
		} else {
			try {
				IPluginRuntime pluginRuntime = PluginRuntime.getInstance();
				thePlugin.run(pluginRuntime);
			} catch (Exception e) {
				Log.error("The plugin [" + thePlugin.getName()
						+ "] could not be started! " + e);
			}
		}
		this.pluginCmdDelegate = this.pluginCmdDescriptor.getCmdClass();
		return this.pluginCmdDelegate;
	}

	public void executeCmd(String cmd, String cmdArguments, String[] argList) {
		this.cmd = cmd;
		this.cmdArguments = cmdArguments;
		this.cmdArgumentList = argList;
		
		if (this.pluginCmdDelegate == null) {
			Log.debug("Plugin not started yet, starting now...");
			this.pluginCmdDelegate = createCmdDelegate();
			if (this.pluginCmdDelegate == null) {
				Log.error("Did not get a valid delegate for cmd ["
						+ this.pluginCmdDescriptor.getPluginCmdModel()
								.getCmdClass() + "]");
				return;
			}
		}
		this.pluginCmdDelegate.performCommand(this);
	}

	public String getCmd() {
		return this.cmd;
	}

	public String getCmdArguments() {
		return this.cmdArguments;
	}
	
	public String[] getCmdArgumentList() {
		return this.cmdArgumentList;
	}

	public IPluginShellCmdDescriptor getDescriptor() {
		return this.pluginCmdDescriptor;
	}

	public Session getSession() {
		return this.fSession;
	}

	public Shell getShell() {
		return this.fShell;
	}
}

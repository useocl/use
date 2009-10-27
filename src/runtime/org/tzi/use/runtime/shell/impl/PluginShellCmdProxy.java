package org.tzi.use.runtime.shell.impl;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.runtime.shell.IPluginShellCmdDescriptor;

/**
 * This class provides the implementation for the Plugin Shell Command Proxy.
 * 
 * @author Roman Asendorf
 */

public class PluginShellCmdProxy extends PluginShellCmd {

	/**
	 * Constructor to create a plugin Shell Command Proxy in the application.
	 * 
	 * @param pluginCmdDescriptor
	 *            The Plugin Shell Command Descriptor object
	 * @param session
	 *            The application's Session object
	 * @param shell
	 *            the application's Shell object
	 */
	public PluginShellCmdProxy(IPluginShellCmdDescriptor pluginCmdDescriptor,
			Session session, Shell shell) {
		super(pluginCmdDescriptor, session, shell);

	}

}

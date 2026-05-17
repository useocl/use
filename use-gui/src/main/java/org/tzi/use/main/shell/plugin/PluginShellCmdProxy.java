package org.tzi.use.main.shell.plugin;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.runtime.IShell;
import org.tzi.use.runtime.spi.IPluginShellCmdDescriptor;

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
			Session session, IShell shell) {
		super(pluginCmdDescriptor, session, shell);

	}

}

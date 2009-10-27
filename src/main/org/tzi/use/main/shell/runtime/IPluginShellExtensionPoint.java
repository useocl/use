package org.tzi.use.main.shell.runtime;

import java.util.Map;

import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IDescriptor;
import org.tzi.use.main.runtime.IExtensionPoint;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.runtime.shell.impl.PluginShellCmdProxy;

/**
 * This interface provides the Shell Command Extension Point. The implementation
 * of this interface is located in the Plugin Framework - this interface should
 * only be used internally
 * 
 * @author Roman Asendorf
 */

public interface IPluginShellExtensionPoint extends IExtensionPoint {

	/**
	 * Method to register Shell Commands from PluginDescriptor
	 * 
	 * @param pluginDescriptor
	 */
	void registerCmds(IDescriptor pluginDescriptor);

	/**
	 * 
	 * Method to create the Shell Command Proxies in the application
	 * 
	 * @param session
	 *            The Session object
	 * @param shell
	 *            The Shell object
	 * @return Map of created Shell Command Proxies
	 */
	Map<Map<String, String>, PluginShellCmdProxy> createPluginCmds(Session session, Shell shell);

}

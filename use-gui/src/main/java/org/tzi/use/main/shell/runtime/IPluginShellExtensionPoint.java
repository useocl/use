package org.tzi.use.main.shell.runtime;

import java.util.List;

import org.tzi.use.main.Session;
import org.tzi.use.runtime.spi.IDescriptor;
import org.tzi.use.runtime.spi.IExtensionPoint;

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
	 * @return List of created Shell Command Containers
	 */
	List<IPluginShellCmdContainer> createPluginCmds(Session session, IShell shell);

}

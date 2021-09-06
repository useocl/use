package org.tzi.use.runtime.shell;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginShellCmdModel;

/**
 * This interface provides the Plugin Shell Command Descriptor's behaviour. -
 * This interface should only be used internally
 * 
 * @author Roman Asendorf
 */
public interface IPluginShellCmdDescriptor {

	/**
	 * Method to get the Plugin's Shell Command class
	 * 
	 * @return the Plugin's Shell Command class
	 */
	public IPluginShellCmdDelegate getCmdClass();

	/**
	 * Method to get the Plugin Descriptor.
	 * 
	 * @return The Plugin Descriptor
	 */
	public IPluginDescriptor getParent();

	/**
	 * Method to get the Plugin Shell Command Model object.
	 * 
	 * @return The Plugin Shell Command Model object
	 */
	public PluginShellCmdModel getPluginCmdModel();
}

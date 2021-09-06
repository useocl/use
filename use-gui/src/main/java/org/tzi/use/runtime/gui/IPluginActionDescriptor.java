package org.tzi.use.runtime.gui;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginActionModel;

/**
 * This interface provides the Plugin Action Descriptor's behaviour. - This
 * interface should only be used internally
 * 
 * @author Roman Asendorf
 */
public interface IPluginActionDescriptor {

	/**
	 * Method to get the Plugin's Action class
	 * 
	 * @return the Plugin's Action class
	 */
	public IPluginActionDelegate getActionClass();

	/**
	 * Method to get the Plugin Descriptor.
	 * 
	 * @return The Plugin Descriptor
	 */
	public IPluginDescriptor getParent();

	/**
	 * Method to get the Plugin Action's Model object
	 * 
	 * @return The Plugin Action's Model
	 */
	public PluginActionModel getPluginActionModel();
}

package org.tzi.use.runtime.service;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginServiceModel;

/**
 * This interface provides the Plugin Service Descriptor's behaviour. - This
 * interface should only be used internally
 * 
 * @author Roman Asendorf
 */
public interface IPluginServiceDescriptor {

	/**
	 * Method to get the Plugin Descriptor.
	 * 
	 * @return The Plugin Descriptor
	 */
	public IPluginDescriptor getParent();

	/**
	 * Method to get the Plugin Service Model object.
	 * 
	 * @return The Plugin Service Model object
	 */
	public PluginServiceModel getPluginServiceModel();

	/**
	 * Method to get the Service class.
	 * 
	 * @return The Service class
	 */
	public IPluginService getServiceClass();
}

package org.tzi.use.runtime.util;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginServiceModel;
import org.tzi.use.runtime.service.IPluginServiceDescriptor;
import org.tzi.use.runtime.service.impl.PluginServiceDescriptor;
import org.tzi.use.util.Log;

/**
 * The Service Registry.
 * 
 * @author Roman Asendorf
 */
public class ServiceRegistry {

	private static ServiceRegistry instance = new ServiceRegistry();

	/**
	 * Method returning the Singleton instance of the ServiceRegistry
	 * 
	 * @return The ServiceRegistry instance
	 */
	public static ServiceRegistry getInstance() {
		return instance;
	}

	/**
	 * Private default Constructor.
	 */
	private ServiceRegistry() {
	}

	/**
	 * Method to register the Plugin Services from the given Plugin Descriptor
	 * provided by a Plugin.
	 * 
	 * @param currentPluginDescriptor
	 *            The Plugin Descriptor
	 * @param pluginServiceModel
	 *            The Plugin Service Model
	 * @return The plugin Service Descriptor
	 */
	public IPluginServiceDescriptor registerPluginService(
			IPluginDescriptor currentPluginDescriptor,
			PluginServiceModel pluginServiceModel) {

		if (currentPluginDescriptor == null) {
			Log.error("No PluginDescriptor given.");
			return null;
		}
		if (pluginServiceModel == null) {
			Log.error("No PluginServiceModul given.");
			return null;
		}
		
		Log.debug("Registering service class: ["
				+ pluginServiceModel.getServiceClass() + "]");

		IPluginServiceDescriptor currentServiceDescriptor = new PluginServiceDescriptor(
				pluginServiceModel, currentPluginDescriptor);

		return currentServiceDescriptor;
	}
}

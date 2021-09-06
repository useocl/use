package org.tzi.use.runtime;

import java.net.URL;
import java.util.Map;

import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.runtime.service.IPluginService;
import org.tzi.use.runtime.service.IPluginServiceDescriptor;

/**
 * This interface provides the Plugin Runtime's behaviour. It extends the common
 * IRuntime behaviour. - This interface is also needed to give Plugin startup
 * classes access to the Plugin Framework's functionality.
 * 
 * @author Roman Asendorf
 */
public interface IPluginRuntime extends IRuntime {

	/**
	 * Method to get all registered Plugins
	 * 
	 * @return A Map of Plugins
	 */
	public Map<String, IPluginDescriptor> getPlugins();

	/**
	 * Method to get a registered plugin Service by it's name
	 * 
	 * @param pluginServiceClassName
	 *            The Service's name
	 * @return The Plugin Service
	 */
	public IPluginService getService(String pluginServiceClassName);

	/**
	 * Method to get all registered Plugin Services
	 * 
	 * @return A Map of registered Plugin Services
	 */
	public Map<String, IPluginServiceDescriptor> getServices();

	/**
	 * Method to register a Plugin by the given filename in the location path
	 * 
	 * @param pluginFilename
	 *            the Plugin's filename
	 * @param pluginURL
	 *            the Plugin's location path as URL
	 */
	public void registerPlugin(String pluginFilename, URL pluginURL);

	/**
	 * Method to register all Services by PluginDescriptor provided by a Plugin.
	 * 
	 * @param currentPluginDescriptor
	 *            The PluginDescriptor
	 */
	public void registerServices(IPluginDescriptor currentPluginDescriptor);

}

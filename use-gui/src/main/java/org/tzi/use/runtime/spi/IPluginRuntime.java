package org.tzi.use.runtime.spi;

import java.net.URL;
import java.util.Map;

import org.tzi.use.runtime.spi.IExtensionPoint;
import org.tzi.use.runtime.spi.IRuntime;
import org.tzi.use.runtime.spi.IPluginService;
import org.tzi.use.runtime.spi.IPluginServiceDescriptor;

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

	/**
	 * Method to register an extension point under a symbolic name. The bootstrap
	 * wires the host-specific extension-point singletons in at startup so the
	 * runtime impl does not need a static reference to them.
	 *
	 * @param name  the symbolic key used by {@link #getExtensionPoint(String)}
	 * @param point the extension-point singleton to register
	 */
	public void registerExtensionPoint(String name, IExtensionPoint point);

}

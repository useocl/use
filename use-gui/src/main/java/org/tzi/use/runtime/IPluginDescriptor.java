package org.tzi.use.runtime;

import java.net.URL;

import org.tzi.use.main.runtime.IDescriptor;
import org.tzi.use.runtime.model.PluginModel;

/**
 * This interface provides the Plugin Descriptor's behaviour. - This interface
 * should only be used internally
 * 
 * @author Roman Asendorf
 */
public interface IPluginDescriptor extends IDescriptor {

	/**
	 * Method to get the Plugin's startup class
	 * 
	 * @return The Plugin class
	 */
	public IPlugin getPluginClass();

	/**
	 * Method to get the Plugin's ClassLoader
	 * 
	 * @return The Plugin's ClassLoader
	 */
	public IPluginClassLoader getPluginClassLoader();

	/**
	 * Method to get the Plugin's path as URL.
	 * 
	 * @return The Plugin's path
	 */
	public URL getPluginLocation();

	/**
	 * Method to get the Plugin's Model object
	 * 
	 * @return The Plugin's Model
	 */
	public PluginModel getPluginModel();
}

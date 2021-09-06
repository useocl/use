package org.tzi.use.runtime.util;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.impl.PluginActionDescriptor;
import org.tzi.use.runtime.model.PluginActionModel;
import org.tzi.use.util.Log;

/**
 * The Action Registry.
 * 
 * @author Roman Asendorf
 */
public class ActionRegistry {

	private static ActionRegistry instance = new ActionRegistry();

	/**
	 * Method returning the Singleton instance of the ActionRegistry
	 * 
	 * @return The ActionRegistry instance
	 */
	public static ActionRegistry getInstance() {
		return instance;
	}

	/**
	 * Private default Constructor.
	 */
	private ActionRegistry() {
	}

	/**
	 * Method to register the Plugin Actions from the given Plugin Descriptor
	 * provided by a Plugin.
	 * 
	 * @param currentPluginDescriptor
	 *            The Plugin Descriptor
	 * @param pluginActionModel
	 *            The Plugin Action Model
	 * @return The Plugin Action Descriptor
	 */
	public PluginActionDescriptor registerPluginAction(
			IPluginDescriptor currentPluginDescriptor,
			PluginActionModel pluginActionModel) {

		if (currentPluginDescriptor == null) {
			Log.error("No PluginDescriptor given.");
			return null;
		}
		if (pluginActionModel == null) {
			Log.error("No PluginActionModul given.");
			return null;
		}

		Log.debug("Registering action class: ["
				+ pluginActionModel.getActionClass() + "]");

		PluginActionDescriptor currentActionDescriptor = new PluginActionDescriptor(
				pluginActionModel, currentPluginDescriptor
						.getPluginClassLoader(), currentPluginDescriptor);

		return currentActionDescriptor;
	}
}

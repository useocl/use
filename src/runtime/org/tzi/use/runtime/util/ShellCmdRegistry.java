package org.tzi.use.runtime.util;

import java.net.URL;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginShellCmdModel;
import org.tzi.use.runtime.shell.IPluginShellCmdDescriptor;
import org.tzi.use.runtime.shell.impl.PluginShellCmdDescriptor;
import org.tzi.use.util.Log;

/**
 * The Shell Command Registry.
 * 
 * @author Roman Asendorf
 */

public class ShellCmdRegistry {
	private static ShellCmdRegistry instance = new ShellCmdRegistry();

	/**
	 * Method returning the Singleton instance of the ShellCmdRegistry
	 * 
	 * @return The ShellCmdRegistry instance
	 */
	public static ShellCmdRegistry getInstance() {
		return instance;
	}

	/**
	 * Private default Constructor.
	 */
	private ShellCmdRegistry() {
	}

	/**
	 * Method to register the Plugin Shell Commands from the given Plugin
	 * Descriptor provided by a Plugin.
	 * 
	 * @param currentPluginDescriptor
	 *            The Plugin Descriptor
	 * @param pluginCmdModel
	 *            The Plugin Descriptor Model
	 * @return
	 */
	public IPluginShellCmdDescriptor registerPluginCmd(
			IPluginDescriptor currentPluginDescriptor,
			PluginShellCmdModel pluginCmdModel) {

		if (currentPluginDescriptor == null) {
			Log.error("No PluginDescriptor given.");
			return null;
		}
		if (pluginCmdModel == null) {
			Log.error("No PluginCmdModul given.");
			return null;
		}
		URL pluginLocation = currentPluginDescriptor.getPluginLocation();

		Log.debug("Registering command class: [" + pluginCmdModel.getCmdClass()
				+ "], command [" + pluginCmdModel.getShellCmd() + "]");

		IPluginShellCmdDescriptor currentCmdDescriptor = new PluginShellCmdDescriptor(
				pluginCmdModel, pluginLocation, currentPluginDescriptor);

		return currentCmdDescriptor;
	}
}

package org.tzi.use.runtime.shell.impl;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.tzi.use.runtime.IPluginClassLoader;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginShellCmdModel;
import org.tzi.use.runtime.shell.IPluginShellCmdDelegate;
import org.tzi.use.runtime.shell.IPluginShellCmdDescriptor;
import org.tzi.use.util.Log;

/**
 * This class provides the implementation of the Plugin Shell Command
 * Descriptor.
 * 
 * @author Roman Asendorf
 */
public class PluginShellCmdDescriptor implements IPluginShellCmdDescriptor {

	private PluginShellCmdModel pluginCmdModel = null;

	private IPluginShellCmdDelegate pluginCmd = null;

	private final IPluginDescriptor parent;

	/**
	 * Constructor to create a Plugin Shell Command Descriptor with the given
	 * Plugin Command Model.
	 * 
	 * @param pluginCmdModel
	 *            the Plugin Command Model.
	 * @param pluginLocation
	 *            The Plugin location path.
	 * @param parent
	 *            The Plugin Descriptor
	 */
	public PluginShellCmdDescriptor(PluginShellCmdModel pluginCmdModel,
			URL pluginLocation, IPluginDescriptor parent) {

		this.pluginCmdModel = pluginCmdModel;
		this.parent = parent;
	}

	public IPluginShellCmdDelegate getCmdClass() {
		if (this.pluginCmd == null) {
			String className = this.pluginCmdModel.getCmdClass();
			IPluginClassLoader currentClassLoader = this.parent
					.getPluginClassLoader();

			Log.debug("[" + this.pluginCmdModel.getId() + "]" + ", " + "["
					+ currentClassLoader.toString() + "]");
			try {
				this.pluginCmd = (IPluginShellCmdDelegate) currentClassLoader
						.loadClass(className).getDeclaredConstructor().newInstance();
			} catch (ClassNotFoundException cnfe) {
				Log.error("No command class [" + className + "]: ", cnfe);
			} catch (InstantiationException ie) {
				Log
						.error("Could not instantiate class [" + className
								+ "]", ie);
			} catch (IllegalAccessException iae) {
				Log.error("Could not access class [" + className + "]", iae);
			} catch(InvocationTargetException ite) {
				Log.error("InvocationTargetException [" + className + "]: ", ite);
			} catch(NoSuchMethodException nsme) {
				Log.error("Method not found for [" + className + "]: ", nsme);
			}
			if (this.pluginCmd == null) {
				Log.error("Could not get class [" + className + "]");
			}
		}
		return this.pluginCmd;
	}

	public IPluginDescriptor getParent() {
		return this.parent;
	}

	public PluginShellCmdModel getPluginCmdModel() {
		return this.pluginCmdModel;
	}
}

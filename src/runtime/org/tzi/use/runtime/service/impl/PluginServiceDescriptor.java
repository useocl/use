package org.tzi.use.runtime.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.tzi.use.runtime.IPluginClassLoader;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginServiceModel;
import org.tzi.use.runtime.service.IPluginService;
import org.tzi.use.runtime.service.IPluginServiceDescriptor;
import org.tzi.use.util.Log;

/**
 * The Service Descriptor Class.
 * 
 * @author Roman Asendorf
 */
public class PluginServiceDescriptor implements IPluginServiceDescriptor {

	private PluginServiceModel pluginServiceModel = null;

	private IPluginService pluginService = null;

	private final IPluginDescriptor parent;

	/**
	 * Constructor creating a Service Descriptor with the given Plugin Service
	 * Model
	 * 
	 * @param pluginServiceModel
	 *            The Plugin Service Model
	 * @param parent
	 *            the Plugin Descriptor
	 */
	public PluginServiceDescriptor(PluginServiceModel pluginServiceModel,
			IPluginDescriptor parent) {

		this.pluginServiceModel = pluginServiceModel;
		this.parent = parent;
	}

	public IPluginDescriptor getParent() {
		return this.parent;
	}

	public PluginServiceModel getPluginServiceModel() {
		return this.pluginServiceModel;
	}

	public IPluginService getServiceClass() {
		if (this.pluginService == null) {
			String className = this.pluginServiceModel.getServiceClass();
			IPluginClassLoader currentClassLoader = parent
					.getPluginClassLoader();

			Log.debug("[" + this.pluginServiceModel.getId() + "]" + ", " + "["
					+ currentClassLoader.toString() + "]");
			try {
				this.pluginService = (IPluginService) currentClassLoader
						.loadClass(className).getDeclaredConstructor().newInstance();
			} catch (ClassNotFoundException cnfe) {
				Log.error("No service class [" + className + "]: ", cnfe);
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
			if (this.pluginService == null) {
				Log.error("Could not get class [" + className + "]");
			}
		}
		return this.pluginService;
	}
}

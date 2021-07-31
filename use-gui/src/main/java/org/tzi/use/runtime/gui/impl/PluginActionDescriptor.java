package org.tzi.use.runtime.gui.impl;

import java.lang.reflect.InvocationTargetException;

import org.tzi.use.runtime.IPluginClassLoader;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.tzi.use.runtime.gui.IPluginActionDescriptor;
import org.tzi.use.runtime.model.PluginActionModel;
import org.tzi.use.util.Log;

/**
 * This class provides the implementation of the Plugin Action Descriptor.
 * 
 * @author Roman Asendorf
 */
public class PluginActionDescriptor implements IPluginActionDescriptor {

	private PluginActionModel pluginActionModel;

	private IPluginActionDelegate pluginAction;

	private IPluginClassLoader pluginClassLoader;

	private IPluginDescriptor parent;

	/**
	 * Constructor to create a Plugin Action Descriptor with the given Plugin
	 * Action Model and Plugin ClassLoader.
	 * 
	 * @param pluginActionModel
	 *            The Plugin Action Model object
	 * @param pluginClassLoader
	 *            The Plugin ClassLoader
	 * @param parent
	 *            The Plugin Action's parent Window object
	 */
	public PluginActionDescriptor(PluginActionModel pluginActionModel,
			IPluginClassLoader pluginClassLoader, IPluginDescriptor parent) {
		this.pluginActionModel = pluginActionModel;
		this.pluginClassLoader = pluginClassLoader;
		this.parent = parent;
	}

	public IPluginActionDelegate getActionClass() {
		if (this.pluginAction == null) {
			String className = this.pluginActionModel.getActionClass();

			Log.debug("Action class name [" + className + "]");

			try {
				Class<?> actionClass = getPluginClassLoader().loadClass(className);
				Log.debug("PluginActionDescriptor loading class ["
						+ this.pluginActionModel.getId() + "]" + ", " + "["
						+ actionClass.getClassLoader().toString() + "]");

				this.pluginAction = (IPluginActionDelegate) actionClass
						.getDeclaredConstructor().newInstance();
			} catch (ClassNotFoundException cnfe) {
				Log.error("No action class [" + className + "]: ", cnfe);
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
			if (this.pluginAction == null) {
				Log.error("PAD, Could not get class [" + className + "]");
			}
		}
		return this.pluginAction;
	}

	public IPluginDescriptor getParent() {
		return this.parent;
	}

	public PluginActionModel getPluginActionModel() {
		return this.pluginActionModel;
	}

	private IPluginClassLoader getPluginClassLoader() {
		return this.pluginClassLoader;
	}
}

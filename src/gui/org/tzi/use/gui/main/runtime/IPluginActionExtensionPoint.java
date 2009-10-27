package org.tzi.use.gui.main.runtime;

import java.util.Map;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IDescriptor;
import org.tzi.use.main.runtime.IExtensionPoint;
import org.tzi.use.runtime.gui.impl.PluginActionProxy;

/**
 * This interface provides the behaviour of the Plugin Action Extension Point.
 * 
 * @author Roman Asendorf
 */
public interface IPluginActionExtensionPoint extends IExtensionPoint {

	/**
	 * Method to register Plugin Actions from the given Plugin Descriptor
	 * 
	 * param pluginDescriptor The Plugin Descriptor object
	 */
	void registerActions(IDescriptor pluginDescriptor);

	/**
	 * Method to create Plugin Action Proxies for the current application's
	 * state.
	 * 
	 * @param session
	 *            The aplication's Session object
	 * @param mainWindow
	 *            The application's MainWindow object
	 * @return
	 */
	Map<Map<String, String>, PluginActionProxy> createPluginActions(Session session, MainWindow mainWindow);

}

package org.tzi.use.runtime.gui.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;
import org.tzi.use.runtime.gui.IPluginActionDescriptor;
import org.tzi.use.runtime.model.PluginActionModel;

/**
 * This class provides the Plugin Action Proxy Factory to create Plugin Action
 * Proxies.
 * 
 * @author Roman Asendorf
 */

public class PluginActionFactory {

	private static PluginActionFactory instance = new PluginActionFactory();

	/**
	 * Method returning the Singleton instance of the PluginActionFactory
	 * 
	 * @return The PluginActionFactory instance
	 */
	public static PluginActionFactory getInstance() {
		return instance;
	}

	/**
	 * Private default constructor
	 */
	private PluginActionFactory() {
	}

	/**
	 * Method to create the Plugin Action Proxies.
	 * 
	 * @param actions
	 *            The Plugin Action Descriptors
	 * @param session
	 *            The application's Session object
	 * @param mainWindow
	 *            The application's MainWindow object
	 * @return A Map of Plugin Action Proxies
	 */
	public Map<Map<String, String>, PluginActionProxy> createPluginActions(Vector<IPluginActionDescriptor> actions, Session session,
			MainWindow mainWindow) {

		Map<Map<String, String>, PluginActionProxy> actionsMap = new HashMap<Map<String, String>, PluginActionProxy>();

		for (IPluginActionDescriptor currentActionDescriptor : actions) {
			Map<String, String> currentActionDescMap = new HashMap<String, String>();
			
			PluginActionModel currentActionModel = currentActionDescriptor.getPluginActionModel();
			
			currentActionDescMap.put("menu", currentActionModel.getMenu());
			currentActionDescMap
					.put("tooltip", currentActionModel.getTooltip());
			currentActionDescMap.put("menuitem", currentActionModel
					.getMenuitem());
			actionsMap.put(currentActionDescMap, new PluginActionProxy(
					currentActionDescriptor, session, mainWindow));
		}
		
		return actionsMap;
	}
}

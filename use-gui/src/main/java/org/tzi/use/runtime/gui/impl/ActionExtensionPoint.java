package org.tzi.use.runtime.gui.impl;

import java.util.Map;
import java.util.Vector;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.runtime.IPluginActionExtensionPoint;
import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IDescriptor;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.gui.IPluginActionDescriptor;
import org.tzi.use.runtime.model.PluginActionModel;
import org.tzi.use.runtime.util.ActionRegistry;

/**
 * This class provides the implementation of the Action Extension Point.The
 * referenced interface should be located in the application elsewhere.
 * 
 * @author Roman Asendorf
 */
public class ActionExtensionPoint implements IPluginActionExtensionPoint {

	private static ActionExtensionPoint instance = new ActionExtensionPoint();

	/**
	 * Method returning the Singleton instance of the ActionExtensionPoint
	 * 
	 * @return The ActionExtensionPoint instance
	 */
	public static IPluginActionExtensionPoint getInstance() {
		return instance;
	}

	/**
	 * Private default Constructor.
	 */
	private ActionExtensionPoint() {
	}

	private Vector<IPluginActionDescriptor> registeredActions;

	public Map<Map<String, String>, PluginActionProxy> createPluginActions(Session session, MainWindow mainWindow) {
		PluginActionFactory actionFactory = PluginActionFactory.getInstance();
		return actionFactory.createPluginActions(getPluginActions(), session,
				mainWindow);
	}

	private Vector<IPluginActionDescriptor> getPluginActions() {
		if (this.registeredActions == null) {
			this.registeredActions = new Vector<IPluginActionDescriptor>();
		}
		return this.registeredActions;
	}

	private void registerAction(IPluginActionDescriptor pluginActionDescriptor) {
		getPluginActions().add(pluginActionDescriptor);
	}

	public void registerActions(IDescriptor pluginDescriptor) {
		ActionRegistry actionRegistry = ActionRegistry.getInstance();

		IPluginDescriptor currentPluginDescriptor = (IPluginDescriptor) pluginDescriptor;

		Vector<PluginActionModel> pluginActions = currentPluginDescriptor.getPluginModel().getActions();

		for (int cntPluginActions = 0; cntPluginActions < pluginActions.size();) {
			PluginActionModel currentPluginActionModel = pluginActions.get(cntPluginActions);
			IPluginActionDescriptor currentPluginActionDescriptor = actionRegistry
					.registerPluginAction(currentPluginDescriptor,
							currentPluginActionModel);

			registerAction(currentPluginActionDescriptor);
			cntPluginActions++;
		}
	}
}

package org.tzi.use.runtime.gui;

/**
 * This interface provides the Plugin Action's behaviour implemented by the
 * Plugin. - This interface should be implemented by any Plugin providing
 * Actions.
 * 
 * @author Roman Asendorf
 */
public interface IPluginActionDelegate {

	/**
	 * Method to execute the Plugin's Action with the given Plugin Action.
	 * 
	 * @param pluginAction
	 *            The Plugin Action Proxy
	 */
	void performAction(IPluginAction pluginAction);

	/**
	 * PluginActionDelegates can override the behavior when their invoking user interfaces
	 * should be enabled.
	 *
	 * @param pluginAction
	 * 			The plugins Action Proxy
	 * @return <code>true</code>, if user interface elements for invoking this action should be enabled.
	 */
	default boolean shouldBeEnabled(IPluginAction pluginAction) {
		return pluginAction.getSession().hasSystem();
	};
}

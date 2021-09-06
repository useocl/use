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
	public void performAction(IPluginAction pluginAction);
}

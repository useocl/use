package org.tzi.use.runtime.spi;

/**
 * This interface provides the Plugin Action's behaviour implemented by the
 * Plugin. - This interface should be implemented by any Plugin providing
 * Actions.
 * 
 * @author Roman Asendorf
 * @author Lars Hamann
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
	 * {@link IPluginActionDelegate}s can override the behavior when their invoking user interfaces
	 * should be enabled.
	 * The default behaviour is to disable any action if no system (model) is loaded.
	 *
	 * @param pluginAction
	 * 			The plugins action with information about the session.
	 * @return <code>true</code>, if user interface elements for invoking this action should be enabled.
	 */
	default boolean shouldBeEnabled(IPluginAction pluginAction) {
		// Session is exposed as Object in IPluginAction to keep the SPI free of
		// upstream package dependencies. Downcast to call hasSystem() reflectively.
		Object session = pluginAction.getSession();
		try {
			return (Boolean) session.getClass().getMethod("hasSystem").invoke(session);
		} catch (ReflectiveOperationException e) {
			return false;
		}
	}
}

package org.tzi.use.runtime;



/**
 * This interface provides the basic startup class behaviour for the Plugin's
 * startup classes. - This interface should be implemented by a Plugin class if
 * the Plugin needs startup behaviour.
 * 
 * @author Roman Asendorf
 */
public interface IPlugin {

	/**
	 * Method to get the Plugin's name
	 * 
	 * @return The Plugin's name
	 */
	String getName();

	/**
	 * Method to execute the startup Class of the Plugin in the actual Plugin
	 * Runtime Environment.
	 * 
	 * @param pluginRuntime
	 *            The Plugin Runtime object
	 * @throws Exception
	 *             Throws errors if the Plugin's startup call fails.
	 * 
	 */
	void run(IPluginRuntime pluginRuntime) throws Exception;
}

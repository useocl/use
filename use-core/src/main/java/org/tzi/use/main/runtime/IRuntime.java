package org.tzi.use.main.runtime;

/**
 * Base interface for the Plugin Runtime to connect the Plugin Framework with
 * the application - this interface should only be used internally
 * 
 * @author Roman Asendorf
 */

public interface IRuntime {

	/**
	 * Method to get a Extension Point by name
	 * 
	 * @param extensionPoint
	 *            The Extension Point's name
	 * @return The Extension Point
	 */
	IExtensionPoint getExtensionPoint(String extensionPoint);

}

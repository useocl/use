package org.tzi.use.runtime;

import java.io.IOException;

/**
 * This interface provides a common Plugin ClassLoader behaviour for Plugin
 * ClassLoaders. - This interface should only be used internally
 * 
 * @author Roman Asendorf
 */

public interface IPluginClassLoader {

	/**
	 * Method to get the Plugin's Startup Class name
	 * 
	 * @return The Plugin's startup class name
	 * @throws IOException
	 *             Throws error if the file access fails.
	 */
	String getMainClassName() throws IOException;

	/**
	 * Method to load a specific class from the Plugin
	 * 
	 * @param className
	 *            The class name
	 * @return The class
	 * @throws ClassNotFoundException
	 *             Throws error if the class could not be found and loaded.
	 */
	Class<?> loadClass(String className) throws ClassNotFoundException;

}

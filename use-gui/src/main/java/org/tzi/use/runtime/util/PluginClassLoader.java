package org.tzi.use.runtime.util;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.Attributes;

import org.tzi.use.runtime.IPluginClassLoader;

/**
 * This class provides the Plugin ClassLoader.
 * 
 * @author Roman Asendorf
 * @author (Modified for Java 9 by Andreas Kaestner)
 */
public class PluginClassLoader implements IPluginClassLoader {
	/** This URLClassLoader can load all current plugin classes */
	private static URLClassLoader classLoader;
	/** The current URL of this specific plugin */
	private final URL url;

	/**
	 * Constructor creating a Plugin ClassLoader with the given location path as
	 * URL.
	 * 
	 * @param location
	 *            Plugin location path.
	 */
	public PluginClassLoader(URL location) {
		if (classLoader == null) {
			URL[] firstURL = { location };
			classLoader = new URLClassLoader(firstURL);
		} else {
			URL[] previousURLs = classLoader.getURLs();
			if (!Arrays.asList(previousURLs).contains(location)) {
				URL[] newURLs = Arrays.copyOf(previousURLs, previousURLs.length + 1);
				newURLs[newURLs.length - 1] = location;
				classLoader = new URLClassLoader(newURLs);
			}
		}
		this.url = location;
	}

	/**
	 * Method returing the Plugin ClassLoder instance.
	 * 
	 * @return The Plugin ClassLoader instance.
	 */
	public PluginClassLoader getClassLoader() {
		return this;
	}

	public String getMainClassName() throws IOException {
		URL url = new URL("jar:" + this.url + "!/");
		JarURLConnection uc = (JarURLConnection) url.openConnection();
		Attributes attr = uc.getMainAttributes();
		return attr != null ? attr.getValue(Attributes.Name.MAIN_CLASS) : null;
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return PluginClassLoader.classLoader.loadClass(className);
	}
}

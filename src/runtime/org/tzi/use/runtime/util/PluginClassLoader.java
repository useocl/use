package org.tzi.use.runtime.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;

import org.tzi.use.runtime.IPluginClassLoader;
import org.tzi.use.util.Log;

/**
 * This class provides the Plugin ClassLoader.
 * 
 * @author Roman Asendorf
 */
public class PluginClassLoader implements IPluginClassLoader {

	static class ClassLoaderUtil {

		private final static Class<?>[] parameters = new Class[] { URL.class };

		public static void addURL(URL url) throws IOException {

			URL urls[] = PluginClassLoader.classLoader.getURLs();
			for (int i = 0; i < urls.length; i++) {
				if (urls[i].toString().equalsIgnoreCase(url.toString())) {
					Log.debug("URL " + url + " is already in the CLASSPATH");
					return;
				}
			}
			Class<?> sysclass = URLClassLoader.class;
			try {
				Method method = sysclass
						.getDeclaredMethod("addURL", parameters);
				method.setAccessible(true);
				method.invoke(PluginClassLoader.classLoader,
						new Object[] { url });
			} catch (Throwable t) {
				t.printStackTrace();
				throw new IOException(
						"Error, could not add URL to system classloader");
			}
		}
	}

	static URLClassLoader classLoader;

	static URL[] addURLsToClassLoader(URL location) {
		URL url = null;

		try {
			ClassLoaderUtil.addURL(location);
			url = new URL("jar:" + location + "!/");
			Log.debug("Adding url to classLoader [" + url.toString() + "]");
		} catch (IOException ioe) {
			Log.error("Plugin path [" + url + "] could not be added.", ioe);
		}
		URL[] urls = new URL[] { url };
		return urls;
	}

	private final URL url;

	/**
	 * Constructor creating a Plugin ClassLoader with the given location path as
	 * URL.
	 * 
	 * @param location
	 *            Plugin location path.
	 */
	public PluginClassLoader(URL location) {
		PluginClassLoader.classLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		addURLsToClassLoader(location);
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

package org.tzi.use.runtime.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarFile;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.impl.PluginDescriptor;
import org.tzi.use.runtime.model.PluginModel;
import org.tzi.use.util.Log;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * The Plugin Registry class registers the Plugins. It
 * 
 * @author Roman Asendorf
 */
public class PluginRegistry {

	private static PluginRegistry instance = new PluginRegistry();

	/**
	 * Method returning the Singleton instance of the PluginRegistry
	 * 
	 * @return The PluginRegistry instance
	 */
	public static PluginRegistry getInstance() {
		return instance;
	}

	/**
	 * Private default constructor
	 */
	private PluginRegistry() {
	}

	private final String PLUGINXML = "useplugin.xml";

	private IPluginDescriptor createPluginDescriptor(PluginModel pluginModel,
			URL location) {
		IPluginDescriptor pluginDescriptor = null;
		try {
			pluginDescriptor = new PluginDescriptor(pluginModel, location);
		} finally {
			Log.debug("Finally we have a plugin desciptor or not.");
		}
		return pluginDescriptor;
	}

	private PluginModel parseConfigFile(URL location) {

		PluginModel pluginModel = null;
		try {
			URL configFile = new URL("jar", "", location + "!/");
			String jarFilePath = location.getPath();
			Log.debug("Creating jarfile path: [" + jarFilePath + "]");
			JarFile jarFile = new JarFile(jarFilePath);

			InputStream inputStream = jarFile.getInputStream(jarFile
					.getEntry(this.PLUGINXML));
			try {
				InputSource inputSource = new InputSource(inputStream);
				inputSource.setSystemId(configFile.getFile());
				Log.debug("Creating plugin for: " + location);
				pluginModel = new PluginParser().parsePlugin(inputSource);
			} finally {
				Log.debug("Closing stream in any case.");
				inputStream.close();
				jarFile.close();
			}
		} catch (MalformedURLException mfue) {
			Log.error("Error creating config file location URL: ", mfue);
		} catch (SAXParseException se) {
			Log.error("Error while parsing plugin config file: ", se);
		} catch (IOException ioe) {
			Log.error("No such plugin config file: 	", ioe);
		} catch (Exception e) {
			Log.error("An unexpected error accured: ", e);
		}
		return pluginModel;
	}

	/**
	 * Method to register a Plugin in the given plugin location path.
	 * 
	 * @param location
	 *            The plugin location path
	 * @return The Plugin Descriptor of the registered Plugin
	 */
	public IPluginDescriptor registerPlugin(URL location) {

		if (location == null) {
			Log.error("No URL given");
			return null;
		}
		PluginModel pluginModel = parseConfigFile(location);
		if (pluginModel == null) {
			Log.error("No plugin at given URL [" + location + "] found.");
			return null;
		}
		Log.debug("Plugin [" + pluginModel.getName() + "] found.");

		IPluginDescriptor pluginDescriptor = createPluginDescriptor(
				pluginModel, location);
		if (pluginDescriptor == null) {
			Log.error("Could not create a PluginDescriptor for plugin ["
					+ pluginModel.getName() + "]");
			return null;
		}
		Log.debug("PluginDescriptor created.");

		return pluginDescriptor;
	}
}

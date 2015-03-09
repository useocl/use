package org.tzi.use.runtime.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;

import javax.xml.parsers.ParserConfigurationException;

import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.impl.PluginDescriptor;
import org.tzi.use.runtime.model.PluginModel;
import org.tzi.use.util.Log;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The Plugin Registry class registers the Plugins. It
 * 
 * @author Roman Asendorf
 */
public class PluginRegistry {

	private static final PluginRegistry instance = new PluginRegistry();

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

	private static final String PLUGINXML = "useplugin.xml";

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
		File pluginFile = new File(location.getFile());
		
		try (JarFile jarFile = new JarFile(pluginFile); InputStream inputStream = jarFile.getInputStream(jarFile.getEntry(PLUGINXML))){
			Log.debug("Creating jarfile path: [" + pluginFile + "]");
			
			InputSource inputSource = new InputSource(inputStream);
			Log.debug("Creating plugin for: " + pluginFile);
			pluginModel = new PluginParser().parsePlugin(inputSource);
		} catch (SAXException | ParserConfigurationException se) {
			Log.error("Error while parsing plugin config file: ", se);
		} catch (IOException ioe) {
			Log.error("No such plugin config file: 	", ioe);
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

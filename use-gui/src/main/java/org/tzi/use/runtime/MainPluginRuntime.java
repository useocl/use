package org.tzi.use.runtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tzi.use.gui.main.runtime.IPluginActionExtensionPoint;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.main.shell.runtime.IPluginShellExtensionPoint;
import org.tzi.use.output.HighlightAs;
import org.tzi.use.output.UserOutput;
import org.tzi.use.runtime.impl.PluginRuntime;
import org.tzi.use.util.StringUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * This is the Plugin Runtime's main class. It will be called from the USE main
 * class if the Plugin Framework is enabled.
 * 
 * @author Roman Asendorf
 */
public class MainPluginRuntime {

	private static final Logger log = LogManager.getLogger(MainPluginRuntime.class.getName());

	/**
	 * Jar filename Filter
	 */
	public static class JarFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".jar"));
		}
	}

	/**
	 * Method to get all filename with jar-extension in the given location.
	 * 
	 * @param location
	 *            The Plugin location path
	 * @return Array of jar-filenames
	 */
	private static String[] getJarFileNames(Path pluginDirURL, UserOutput out) {

		String[] fileNames = null;
		File pluginDir = pluginDirURL.toFile();
		
		log.debug("Searching for plugins in: [" + pluginDirURL.toString()+ "]");
		log.debug("Plugin path validity: [" + pluginDir.exists() + "]");
		
		JarFilter jarFilter = new JarFilter();
		fileNames = pluginDir.list(jarFilter);
		
		if (fileNames == null) {
			out.printWarn("Could not read plugin directory ");
			out.printHighlightedWarn(pluginDir.toString(), HighlightAs.FILE);
			out.printlnWarn(".");
			return new String[0];
		}
		
		StringBuilder verboseMsg = new StringBuilder("Plugin filename(s) [");
		StringUtil.fmtSeq(verboseMsg, fileNames, ",");
		verboseMsg.append("]");
		out.printlnVerbose(verboseMsg.toString());
		
		return fileNames;
	}

	/**
	 * Method to startup the Plugin Runtime using the given location path.
	 * 
	 * @param pluginDirURL
	 *            The Plugin location path
	 * @return The Plugin Runtime object
	 */
	public static IRuntime run(Path pluginDirURL, UserOutput out) {

		String[] pluginFileNames;
		pluginFileNames = getJarFileNames(pluginDirURL, out);
		log.debug("Counted " + pluginFileNames.length + " files in directory");

		IPluginRuntime pluginRuntime = PluginRuntime.getInstance();
		for (int cntFiles = 0; cntFiles < pluginFileNames.length;) {
			String pluginFilename = pluginFileNames[cntFiles];
			log.debug("Current plugin filename [" + pluginFilename + "]");
			try {
				pluginRuntime.registerPlugin(pluginFilename, pluginDirURL.toUri().toURL());
			} catch (MalformedURLException e) {
				out.printlnWarn("Could not convert filepath " + StringUtil.inQuotes(pluginDirURL) + ". Skipping plugin.");
				continue;
			}
			log.debug("ClassLoader of runtime ["
					+ Thread.currentThread().getContextClassLoader().toString()
					+ "]");
			cntFiles++;
		}

		IPluginActionExtensionPoint actionExtensionPoint = (IPluginActionExtensionPoint) pluginRuntime
				.getExtensionPoint("action");

		IPluginShellExtensionPoint shellExtensionPoint = (IPluginShellExtensionPoint) pluginRuntime
				.getExtensionPoint("shell");

		log.debug("Registered [" + pluginRuntime.getPlugins().size() + "] plugins");
		
		for (IPluginDescriptor currentPluginDescriptor : pluginRuntime.getPlugins().values()) {
			log.debug("Main: Registering services");
			pluginRuntime.registerServices(currentPluginDescriptor);
			log.debug("Main: Registering actions");
			actionExtensionPoint.registerActions(currentPluginDescriptor);
			log.debug("Main: Registering commands");
			shellExtensionPoint.registerCmds(currentPluginDescriptor);
		}
		return pluginRuntime;
	}
}

package org.tzi.use.runtime.shell.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.runtime.model.PluginShellCmdModel;
import org.tzi.use.runtime.shell.IPluginShellCmdDescriptor;

/**
 * This class provides the Plugin Shell Command Proxy Factory to create Plugin
 * Shell Command Proxies.
 * 
 * @author Roman Asendorf
 */

public class PluginShellCmdFactory {

	private static PluginShellCmdFactory instance = new PluginShellCmdFactory();

	/**
	 * Method returning the Singleton instance of the PluginShellCmdFactory
	 * 
	 * @return The PluginShellCmdFactory instance
	 */
	public static PluginShellCmdFactory getInstance() {
		return instance;
	}

	/**
	 * Private default constructor
	 */
	private PluginShellCmdFactory() {
	}

	/**
	 * Method to create the Plugin Shell Command Proxies.
	 * 
	 * @param cmds
	 *            The Plugin Shell Command Descriptors
	 * @param session
	 *            The application's Session object
	 * @param shell
	 *            The application's Shell object
	 * @return A Map of Plugin Shell Command Proxies
	 */
	public Map<Map<String, String>, PluginShellCmdProxy> createPluginCmds(Vector<IPluginShellCmdDescriptor> cmds, Session session, Shell shell) {

		Map<Map<String, String>, PluginShellCmdProxy> cmdsMap = new HashMap<Map<String, String>, PluginShellCmdProxy>();
		
		for (IPluginShellCmdDescriptor currentCmdDescriptor : cmds) {
			Map<String, String> currentCmdDescMap = new HashMap<String, String>();
			
			PluginShellCmdModel currentCmdModel = currentCmdDescriptor
					.getPluginCmdModel();
			currentCmdDescMap.put("cmd", currentCmdModel.getShellCmd());
			currentCmdDescMap.put("help", currentCmdModel.getCmdHelp());
			cmdsMap.put(currentCmdDescMap, new PluginShellCmdProxy(
					currentCmdDescriptor, session, shell));
		}
		return cmdsMap;
	}

}

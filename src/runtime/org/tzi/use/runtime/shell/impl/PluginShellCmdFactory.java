package org.tzi.use.runtime.shell.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
	 * @return A sorted list of Plugin Shell Command Containers
	 */
	public List<PluginShellCmdContainer> createPluginCmds(Vector<IPluginShellCmdDescriptor> cmds, Session session, Shell shell) {

		List<PluginShellCmdContainer> cmdList = new ArrayList<PluginShellCmdContainer>(cmds.size());
		
		for (IPluginShellCmdDescriptor currentCmdDescriptor : cmds) {
			PluginShellCmdModel currentCmdModel = currentCmdDescriptor.getPluginCmdModel();
			cmdList.add(new PluginShellCmdContainer(currentCmdModel.getShellCmd(), currentCmdModel.getAlias(), currentCmdModel.getCmdHelp(),
					new PluginShellCmdProxy(currentCmdDescriptor, session, shell)));
		}
		
		/*
		 * Sort the list so longer entries are at the front. This is to prevent
		 * shorter commands from hiding longer commands by matching a suffix of
		 * the other commands.
		 * E.g. 'command' hides 'commandOther' if it is matched first.
		 */
		Collections.sort(cmdList, new Comparator<PluginShellCmdContainer>() {
			@Override
			public int compare(PluginShellCmdContainer o1, PluginShellCmdContainer o2) {
				return o2.cmd.length() - o1.cmd.length();
			}
		});
		
		return cmdList;
	}
	
	public static class PluginShellCmdContainer {
		private final String cmd;
		private final String alias;
		private final String help;
		private final PluginShellCmdProxy proxy;
		
		private PluginShellCmdContainer(String cmd, String alias, String help, PluginShellCmdProxy proxy){
			this.cmd = cmd;
			this.alias = alias;
			this.help = help;
			this.proxy = proxy;
		}

		public String getCmd() {
			return cmd;
		}

		public String getAlias() {
			return alias;
		}
		
		public String getHelp() {
			return help;
		}

		public PluginShellCmdProxy getProxy() {
			return proxy;
		}
	}
	
}

package org.tzi.use.runtime.shell.impl;

import java.util.List;
import java.util.Vector;

import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IDescriptor;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.main.shell.runtime.IPluginShellExtensionPoint;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.model.PluginShellCmdModel;
import org.tzi.use.runtime.shell.IPluginShellCmdDescriptor;
import org.tzi.use.runtime.shell.impl.PluginShellCmdFactory.PluginShellCmdContainer;
import org.tzi.use.runtime.util.ShellCmdRegistry;
import org.tzi.use.util.Log;

/**
 * This class provides the implementation of the Shell Command Extension Point.
 * The referenced interface should be located in the application elsewhere.
 * 
 * @author Roman Asendorf
 */
public class ShellExtensionPoint implements IPluginShellExtensionPoint {

	private static ShellExtensionPoint instance = new ShellExtensionPoint();

	/**
	 * Method returning the Singleton instance of the ShellExtensionPoint
	 * 
	 * @return The ShellExtensionPoint instance
	 */
	public static IPluginShellExtensionPoint getInstance() {
		return instance;
	}

	/**
	 * Private default Constructor.
	 */
	private ShellExtensionPoint() {
	}

	private Vector<IPluginShellCmdDescriptor> registeredCmds;

	public List<PluginShellCmdContainer> createPluginCmds(Session session, Shell shell) {
		PluginShellCmdFactory cmdFactory = PluginShellCmdFactory.getInstance();
		return cmdFactory.createPluginCmds(getRegisteredCmds(), session, shell);
	}

	public Vector<IPluginShellCmdDescriptor> getRegisteredCmds() {
		if (this.registeredCmds == null) {
			this.registeredCmds = new Vector<IPluginShellCmdDescriptor>();
		}
		return this.registeredCmds;
	}

	public void registerCmd(IPluginShellCmdDescriptor pluginCmdDescriptor) {
		Log.debug("Registering cmd ["
				+ pluginCmdDescriptor.getPluginCmdModel().getShellCmd() + "]");
		getRegisteredCmds().add(pluginCmdDescriptor);
	}

	public void registerCmds(IDescriptor pluginDescriptor) {
		ShellCmdRegistry cmdRegistry = ShellCmdRegistry.getInstance();

		IPluginDescriptor currentPluginDescriptor = (IPluginDescriptor) pluginDescriptor;
		Vector<PluginShellCmdModel> pluginCmds = currentPluginDescriptor.getPluginModel().getCommands();

		for (int cntPluginCmds = 0; cntPluginCmds < pluginCmds.size();) {
			PluginShellCmdModel currentPluginCmdModel = pluginCmds.get(cntPluginCmds);
			IPluginShellCmdDescriptor currentPluginCmdDescriptor = cmdRegistry
					.registerPluginCmd(currentPluginDescriptor,
							currentPluginCmdModel);

			registerCmd(currentPluginCmdDescriptor);
			cntPluginCmds++;
		}
	}

}

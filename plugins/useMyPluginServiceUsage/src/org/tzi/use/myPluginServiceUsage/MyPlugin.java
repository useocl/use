package org.tzi.use.myPluginServiceUsage;

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.impl.Plugin;
import org.tzi.use.runtime.service.IPluginService;
import org.tzi.use.util.Log;

/**
 * 
 * Example Class for Running own and other Plugin Services
 * 
 * @author Roman Asendorf
 * 
 */
public class MyPlugin extends Plugin implements IPlugin {

	final protected String PLUGIN_ID = "My Plugin";

	private MyPluginService service;

	public String getName() {
		return this.PLUGIN_ID;
	}

	public MyPluginService getService() {
		return this.service;
	}

	/**
	 * Performing startup action
	 */
	public void run(IPluginRuntime pluginRuntime) throws Exception {
		try {
			// Initializing own service
			this.service = (org.tzi.use.myPluginServiceUsage.MyPluginService) pluginRuntime
					.getService("org.tzi.use.myPluginServiceUsage.MyPluginService");
			System.out.println("Loaded MyPluginService");
			System.out.println("Service returned: ["
					+ this.service.executeService(this.PLUGIN_ID) + "]");

			// Initializing other service
			IPluginService examplePluginService = pluginRuntime
					.getService("org.tzi.use.examplePlugin.ExamplePluginService");
			System.out.println("Performing ExamplePluginService "
					+ examplePluginService.getClass().getName());
			System.out
					.println("Service returned: ["
							+ examplePluginService
									.executeService(this.PLUGIN_ID) + "]");

		} catch (Exception e) {
			Log.error(e);
		}
	}
}

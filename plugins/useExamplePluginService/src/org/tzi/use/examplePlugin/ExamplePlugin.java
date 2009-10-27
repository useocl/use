package org.tzi.use.examplePlugin;

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.impl.Plugin;
import org.tzi.use.util.Log;

/**
 * Example Plugin Start class running the Plugin's own Service on Startup.
 * 
 * @author Roman Asendorf
 * 
 */
public class ExamplePlugin extends Plugin implements IPlugin {

	final protected String PLUGIN_ID = "Example Plugin";

	private ExamplePluginService service;

	public String getName() {
		return this.PLUGIN_ID;
	}

	public ExamplePluginService getService() {
		return this.service;
	}

	public void run(IPluginRuntime pluginRuntime) throws Exception {
		try {
			// Initializing own Plugin Service
			this.service = (org.tzi.use.examplePlugin.ExamplePluginService) pluginRuntime
					.getService("org.tzi.use.examplePlugin.ExamplePluginService");
			System.out.println("Loaded ExamplePluginService");
		} catch (Exception e) {
			Log.error(e);
		}
	}
}

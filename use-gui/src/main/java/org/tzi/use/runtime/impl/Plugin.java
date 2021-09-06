package org.tzi.use.runtime.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginDescriptor;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.util.Log;

/**
 * Abstract Plugin base that provides additional helper functions.
 * @author Roman Asendorf
 * @author Lars Hamann
 */
public abstract class Plugin implements IPlugin {

	/**
	 * Implementors should not override this method,
	 * instead {@link #doRun(IPluginRuntime)} should be overridden.<br>
	 * This method is not declared as final for backward compatibility. 
	 */
	@Override
	public void run(IPluginRuntime pluginRuntime) throws Exception {
		doRun(pluginRuntime);
	}
	
	/**
	 * Hook method which allows implementors to react on the plugin start.
	 * @param pluginRuntime
	 */
	protected void doRun(IPluginRuntime pluginRuntime) { }

	/**
	 * Opens the specified resource as a stream.
	 * The resource <code>name</code> is relative
	 * @param name
	 * @return
	 */
	public InputStream getResourceAsStream(String name) {
		URL resourceUrl = null;
		resourceUrl = getResource(name);
		
		if (resourceUrl == null) return null;
		
		InputStream res = null;
		
		try {
			res = new FileInputStream(resourceUrl.toString());
		} catch (FileNotFoundException e) {
			Log.error("File '" + resourceUrl.toString() + "' not found!");
		}
		
		return res;
	}
	
	/**
	 * Opens the specified resource as a stream.
	 * The resource <code>name</code> is relative
	 * @param name
	 * @return
	 */
	public URL getResource(String name) {
		URL resourceUrl = null;
		
		IPluginDescriptor desc = PluginRuntime.getInstance().getPlugins().get(this.getName());
		
		String urlString = "jar:" + desc.getPluginLocation() + "!/" + name;
		
		try {	
			resourceUrl = new URL(urlString);
		} catch (MalformedURLException e) {
			Log.error("The URL to the image ["
					+ urlString
					+ "] was malformed!");
			return null;
		}
		
		return resourceUrl;
	}
}

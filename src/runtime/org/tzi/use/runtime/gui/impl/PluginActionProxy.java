package org.tzi.use.runtime.gui.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.tzi.use.config.Options;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;
import org.tzi.use.runtime.gui.IPluginActionDescriptor;
import org.tzi.use.util.Log;

/**
 * This Plugin Action Proxy class.
 * 
 * @author Roman Asendorf
 */
public class PluginActionProxy extends PluginAction {

	/**
	 * Serial for this class.
	 */
	private static final long serialVersionUID = -8929195916819522778L;

	private static String createActionName(
			IPluginActionDescriptor pluginActionDescriptor) {
		return pluginActionDescriptor.getPluginActionModel().getMenuitem();
	}

	private static ImageIcon createImageIcon(
			IPluginActionDescriptor pluginActionDescriptor) {
		String fallBackImage = Options.getIconPath("New.gif").toString();
		URL imageUrl = null;
		String urlString = "jar:"
			+ pluginActionDescriptor.getParent().getPluginLocation()
			+ "!/" // + System.getProperty("file.separator")
			+ pluginActionDescriptor.getPluginActionModel().getIcon();
		
		try {	
			imageUrl = new URL(urlString);
		} catch (MalformedURLException e) {
			Log.error("The URL to the image ["
					+ urlString
					+ "] was malformed!");
		}
		if (imageUrl == null) {
			Log.debug("Creating fallBack ImageIcon [" + fallBackImage + "]");
			return new ImageIcon(fallBackImage);
		}
		try {
			imageUrl.getContent();
		} catch (IOException ioe) {
			Log.error("Could not find image at the given URL [" + imageUrl
					+ "] .");
			Log.debug("Creating fallBack ImageIcon [" + fallBackImage + "]");
			return new ImageIcon(fallBackImage);
		}

		Log.debug("Creating ImageIcon [" + imageUrl + "]");
		return new ImageIcon(imageUrl);
	}

	/**
	 * Constructor creating a Plugin Action Proxy from the given Plugin Action
	 * Descriptor
	 * 
	 * @param pluginActionDescriptor
	 * @param session
	 *            The Session object
	 * @param parent
	 *            The MainWindow object as parent
	 */
	public PluginActionProxy(IPluginActionDescriptor pluginActionDescriptor,
			Session session, MainWindow parent) {

		super(pluginActionDescriptor, session, parent,
				createActionName(pluginActionDescriptor),
				createImageIcon(pluginActionDescriptor));
	}
}

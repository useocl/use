package org.tzi.use.runtime.model;

import java.util.Vector;

/**
 * The Plugin Model providing all Plugin meta information in the Application.
 * 
 * @author Roman Asendorf
 */
public class PluginModel {

	private String id = null;
	private String name = null;
	private String version = null;
	private String pluginClass = null;
	private String publisher = null;

	private Vector<PluginActionModel> actions = null;
	private Vector<PluginServiceModel> services = null;
	private Vector<PluginShellCmdModel> commands = null;

	/**
	 * Method to get the Plugin Action information
	 * 
	 * @return a Vector of Plugin Action Descriptors
	 */
	public Vector<PluginActionModel> getActions() {
		if (this.actions == null) {
			this.actions = new Vector<PluginActionModel>();
		}
		return this.actions;
	}

	/**
	 * Method to get the Plugin Shell Command information
	 * 
	 * @return a Vector of Plugin Shell Command Descriptors
	 */
	public Vector<PluginShellCmdModel> getCommands() {
		if (this.commands == null) {
			this.commands = new Vector<PluginShellCmdModel>();
		}
		return this.commands;
	}

	/**
	 * Method to get the Plugin's id.
	 * 
	 * @return The Plugin's id.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Method to get the Plugin's name.
	 * 
	 * @return The Plugin's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method to get the Plugin's class name
	 * 
	 * @return The Plugin's class name
	 */
	public String getPluginClass() {
		return this.pluginClass;
	}

	/**
	 * Method to get the Plugin's publisher name
	 * 
	 * @return The Plugin's publisher name
	 */
	public String getPublisher() {
		return this.publisher;
	}

	/**
	 * Method to get the Plugin's
	 * 
	 * @return A Vector of Plugin Service Descriptors
	 */
	public Vector<PluginServiceModel> getServices() {
		if (this.services == null) {
			this.services = new Vector<PluginServiceModel>();
		}
		return services;
	}

	/**
	 * Method to get the Plugin's Version
	 * 
	 * @return The Plugin's version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Method to set the Plugin's id.
	 * 
	 * @param id
	 *            The Plugin's id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Method to set the Plugin's name.
	 * 
	 * @param name
	 *            The Plugin's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to set the Plugin's Class name.
	 * 
	 * @param pluginClass
	 *            The Plugin's Class name
	 */
	public void setPluginClass(String pluginClass) {
		this.pluginClass = pluginClass;
	}

	/**
	 * Method to set the Plugin's publisher name.
	 * 
	 * @param publisher
	 *            The Plugin's publisher name
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Method to set the Plugin's Version.
	 * 
	 * @param version
	 *            The Plugin's Version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}

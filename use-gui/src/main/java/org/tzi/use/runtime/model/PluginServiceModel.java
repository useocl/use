package org.tzi.use.runtime.model;

/**
 * The Plugin Service Model providing all Plugin Service meta information.
 * 
 * @author Roman Asendorf
 */
public class PluginServiceModel {

	private String id = null;
	private String name = null;
	private String serviceClass = null;

	/**
	 * Method to get the Plugin Service id.
	 * 
	 * @return The Plugin Service id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method to get the Plugin Service name.
	 * 
	 * @return The Plugin Service name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to get the Plugin Service class name.
	 * 
	 * @return The Plugin Service class name
	 */
	public String getServiceClass() {
		return serviceClass;
	}

	/**
	 * Method to set the Plugin Service id.
	 * 
	 * @param id
	 *            The Plugin Service id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Method to set the Plugin Service name.
	 * 
	 * @param name
	 *            The Plugin Service name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to set the Plugin Service class name.
	 * 
	 * @param serviceClass
	 *            The Plugin Service class name
	 */
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
}

package org.tzi.use.runtime.service;

/**
 * This interface provides the behavioru for Plugin Services. This interface
 * should be implemented by any Plugin Class providing a Plugin Service.
 * 
 * @author Roman Asendorf
 */
public interface IPluginService {

	/**
	 * Method to run the Plugin's service.
	 * 
	 * @param object
	 *            general Object to provide arguments to the Service
	 * @return general return Object
	 * 
	 */
	public Object executeService(Object object);

}

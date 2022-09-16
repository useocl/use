package org.tzi.use.runtime.gui;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;

import java.awt.event.ActionEvent;

/**
 * This interface provides the Plugin Action Proxy's behaviour. - This interface
 * should only be used internally
 * 
 * @author Roman Asendorf
 */
public interface IPluginAction {

	/**
	 * Method to execute the Plugin Action
	 * 
	 * @param event
	 *            The ActionEvent
	 */
	void actionPerformed(ActionEvent event);

	/**
	 * Method to get the application's Session object
	 * 
	 * @return The Session object
	 */
	Session getSession();

	/**
	 * Method to get the application's MainWindow object
	 * 
	 * @return The MainWindow object
	 */
	MainWindow getParent();
}

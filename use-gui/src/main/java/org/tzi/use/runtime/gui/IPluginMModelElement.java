package org.tzi.use.runtime.gui;

import org.tzi.use.gui.main.runtime.IPluginMMVisitor;
import org.tzi.use.uml.mm.MModelElement;

/**
 * This interface provides the Plugin MModelElement's behaviour. - This
 * interface should be implemented by any Plugin providing a MModelElement.
 * 
 * @author Roman Asendorf
 */

public interface IPluginMModelElement extends MModelElement {

	/**
	 * Method to print PluginMModelElement's information with the given
	 * PluginMMVisitor.
	 * 
	 * @param pluginMMVisitor
	 *            the PluginMMVisitor object
	 */
	void displayInfo(IPluginMMVisitor pluginMMVisitor);
}

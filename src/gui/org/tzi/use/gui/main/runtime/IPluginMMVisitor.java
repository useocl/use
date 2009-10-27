package org.tzi.use.gui.main.runtime;

import java.io.PrintWriter;

import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModelElement;

/**
 * This interface extends the the MMVisitor's behaviour to process
 * MModelElements provided by Plugins.
 * 
 * @author Roman Asendorf
 */

public interface IPluginMMVisitor extends MMVisitor {

	/**
	 * Method processing the MModelElements not especially processed by the
	 * MMVisitor.
	 * 
	 * @param e
	 *            The MModelElement object.
	 */
	void visitMModelElement(MModelElement e);

	/**
	 * Method to get the ModelBrowser.
	 * 
	 * @return The ModelBrowser object.
	 */
	public ModelBrowser modelBrowser();

	/**
	 * Method to get the PrintWriter.
	 * 
	 * @return The PrintWriter object to write into.
	 */
	public PrintWriter getPrintWriter();
}

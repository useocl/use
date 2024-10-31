package org.tzi.use.gui.mainFX.runtime;

import org.tzi.use.gui.mainFX.ModelBrowserFX;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModelElement;

import java.io.PrintWriter;

/**
 * This interface extends the the MMVisitor's behaviour to process
 * MModelElements provided by Plugins.
 * 
 * @author Roman Asendorf
 */

public interface IPluginMMVisitorFX extends MMVisitor {

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
	public ModelBrowserFX modelBrowser();

	/**
	 * Method to get the PrintWriter.
	 * 
	 * @return The PrintWriter object to write into.
	 */
	public PrintWriter getPrintWriter();
}

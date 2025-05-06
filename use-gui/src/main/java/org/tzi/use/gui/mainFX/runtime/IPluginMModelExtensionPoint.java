package org.tzi.use.gui.mainFX.runtime;

import org.tzi.use.gui.mainFX.ModelBrowser;
import org.tzi.use.main.runtime.IExtensionPoint;

import java.io.PrintWriter;

/**
 * This interface provides the behaviour of the Plugin MModel Extension Point.
 * 
 * @author Roman Asendorf
 */

public interface IPluginMModelExtensionPoint extends IExtensionPoint {

	/**
	 * Method to create a MMPrintVisitor
	 * 
	 * @param printWriter
	 *            The PrintWriter object
	 * @param modelBrowser
	 *            The ModelBrowser object
	 * @return The MMPrintVisitor instance.
	 */
	IPluginMMVisitor createMMPrintVisitorFX(PrintWriter printWriter,
											ModelBrowser modelBrowser);

	/**
	 * Method to create a MMHTMLPrintVisitor
	 * 
	 * @param printWriter
	 *            The PrintWriter object
	 * @param modelBrowser
	 *            The ModelBrowser object
	 * @return The MMHTMLPrintVisitor instance.
	 */
	IPluginMMVisitor createMMHTMLPrintVisitorFX(PrintWriter printWriter,
												ModelBrowser modelBrowser);

}

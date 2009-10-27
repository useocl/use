package org.tzi.use.gui.main.runtime;

import java.io.PrintWriter;

import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.main.runtime.IExtensionPoint;

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
	IPluginMMVisitor createMMPrintVisitor(PrintWriter printWriter,
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
	IPluginMMVisitor createMMHTMLPrintVisitor(PrintWriter printWriter,
			ModelBrowser modelBrowser);

}

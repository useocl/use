package org.tzi.use.runtime.gui.impl;

import java.io.PrintWriter;

import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.runtime.IPluginMMVisitor;
import org.tzi.use.gui.main.runtime.IPluginMModelExtensionPoint;

/**
 * This class provides the implementation of the MModel Extension Point. The
 * referenced interface should be locatd in the application elsewhere.
 * 
 * @author Roman Asendorf
 */

public class MModelExtensionPoint implements IPluginMModelExtensionPoint {

	private static IPluginMModelExtensionPoint instance = new MModelExtensionPoint();

	/**
	 * Method returning the Singleton instance of the PluginActionFactory
	 * 
	 * @return The PluginActionFactory instance
	 */
	public static IPluginMModelExtensionPoint getInstance() {
		return instance;
	}

	/**
	 * Private default Constructor.
	 */
	private MModelExtensionPoint() {
	}

	public IPluginMMVisitor createMMPrintVisitor(PrintWriter printWriter,
			ModelBrowser modelBrowser) {

		return new PluginMMPrintVisitor(printWriter, modelBrowser);
	}

	public IPluginMMVisitor createMMHTMLPrintVisitor(PrintWriter printWriter,
			ModelBrowser modelBrowser) {
		return new PluginMMHTMLPrintVisitor(printWriter, modelBrowser);
	}

}

package org.tzi.use.runtime.guiFX.impl;

import org.tzi.use.gui.mainFX.runtime.IPluginMMVisitor;
import org.tzi.use.gui.mainFX.runtime.IPluginMModelExtensionPoint;
import org.tzi.use.gui.mainFX.ModelBrowser;

import java.io.PrintWriter;

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

	@Override
	public IPluginMMVisitor createMMPrintVisitorFX(PrintWriter printWriter, ModelBrowser modelBrowser) {
		return new PluginMMPrintVisitor(printWriter, modelBrowser);
	}

	@Override
	public IPluginMMVisitor createMMHTMLPrintVisitorFX(PrintWriter printWriter, ModelBrowser modelBrowser) {
		return new PluginMMHTMLPrintVisitor(printWriter, modelBrowser);
	}
}

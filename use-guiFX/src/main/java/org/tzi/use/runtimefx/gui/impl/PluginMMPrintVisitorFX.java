package org.tzi.use.runtimefx.gui.impl;

import org.tzi.use.gui.mainFX.ModelBrowserFX;
import org.tzi.use.gui.mainFX.runtime.IPluginMMVisitorFX;
import org.tzi.use.runtimefx.gui.IPluginMModelElementFX;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.util.Log;

import java.io.PrintWriter;

/**
 * The PluginMMPrintVisitor extends the MMPrintVisitor class behaviour. It is
 * nessecary to process PluginMModelElements for presentation.
 * 
 * @author Roman Asendorf
 */

public class PluginMMPrintVisitorFX extends MMPrintVisitor implements
		IPluginMMVisitorFX {

	private ModelBrowserFX modelBrowser;

	/**
	 * Constructor creating the PluginMMPrintVisitor with the given PrintWriter
	 * and ModelBrowser
	 *
	 * @param out
	 *            The PrintWriter object to write into.
	 * @param modelBrowser
	 *            The ModelBrowser object
	 */
	public PluginMMPrintVisitorFX(PrintWriter out, ModelBrowserFX modelBrowser) {
		super(out);
		setModelBrowser(modelBrowser);
	}

	private void setModelBrowser(ModelBrowserFX modelBrowser) {
		this.modelBrowser = modelBrowser;

	}

	public ModelBrowserFX modelBrowser() {
		return this.modelBrowser;
	}

	public void visitMModelElement(MModelElement e) {

		Log.debug("testing instance of MModelElement");
		if (e instanceof IPluginMModelElementFX) {
			Log.debug("Casting to IPluginMModelElement");
			IPluginMModelElementFX ie = (IPluginMModelElementFX) e;
			Log.debug("Calling method displayInfo in plugin MModelElement");
			ie.displayInfo(this);
		}
	}

	public PrintWriter getPrintWriter() {
		return this.fOut;
	}
}

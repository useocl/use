package org.tzi.use.runtime.gui.impl;

import java.io.PrintWriter;

import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.runtime.IPluginMMVisitor;
import org.tzi.use.runtime.gui.IPluginMModelElement;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.util.Log;

/**
 * The PluginMMPrintVisitor extends the MMPrintVisitor class behaviour. It is
 * nessecary to process PluginMModelElements for presentation.
 * 
 * @author Roman Asendorf
 */

public class PluginMMPrintVisitor extends MMPrintVisitor implements
		IPluginMMVisitor {

	private ModelBrowser modelBrowser;

	/**
	 * Constructor creating the PluginMMPrintVisitor with the given PrintWriter
	 * and ModelBrowser
	 * 
	 * @param out
	 *            The PrintWriter object to write into.
	 * @param modelBrowser
	 *            The ModelBrowser object
	 */
	public PluginMMPrintVisitor(PrintWriter out, ModelBrowser modelBrowser) {
		super(out);
		setModelBrowser(modelBrowser);
	}

	private void setModelBrowser(ModelBrowser modelBrowser) {
		this.modelBrowser = modelBrowser;

	}

	public ModelBrowser modelBrowser() {
		return this.modelBrowser;
	}

	public void visitMModelElement(MModelElement e) {

		Log.debug("testing instance of MModelElement");
		if (e instanceof IPluginMModelElement) {
			Log.debug("Casting to IPluginMModelElement");
			IPluginMModelElement ie = (IPluginMModelElement) e;
			Log.debug("Calling method displayInfo in plugin MModelElement");
			ie.displayInfo(this);
		}
	}

	public PrintWriter getPrintWriter() {
		return this.fOut;
	}
}

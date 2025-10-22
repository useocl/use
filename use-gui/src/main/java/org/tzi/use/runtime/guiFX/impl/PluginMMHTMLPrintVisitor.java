package org.tzi.use.runtime.guiFX.impl;

import org.tzi.use.gui.mainFX.runtime.IPluginMMVisitor;
import org.tzi.use.gui.mainFX.ModelBrowser;
import org.tzi.use.gui.utilFX.MMHTMLPrintVisitor;
import org.tzi.use.runtime.guiFX.IPluginMModelElement;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.util.Log;

import java.io.PrintWriter;

/**
 * The PluginMMHTMLPrintVisitor extends the MMHTMLPrintVisitor class behaviour.
 * It is nessecary to process PluginMModelElements for presentation.
 * 
 * @author Roman Asendorf
 */

public class PluginMMHTMLPrintVisitor extends MMHTMLPrintVisitor implements
		IPluginMMVisitor {

	private ModelBrowser modelBrowser;

	/**
	 * Constructor creating the PluginMMHTMLPrintVisitor with the given
	 * PrintWriter and ModelBrowser
	 *
	 * @param out
	 *            The PrintWriter object to write into.
	 * @param modelBrowser
	 *            The ModelBrowser object
	 */
	public PluginMMHTMLPrintVisitor(PrintWriter out, ModelBrowser modelBrowser) {
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

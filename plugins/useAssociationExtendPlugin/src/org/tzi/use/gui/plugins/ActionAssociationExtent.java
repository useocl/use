package org.tzi.use.gui.plugins;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.main.Session;
import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;

/**
 * This is the AssociationExtent Plugin Action class. It provides the Action
 * which will be performed if the corresponding Plugin Action Delegate in the
 * application is called.
 * 
 * @author Roman Asendorf
 * @author Frank Hilken
 */
public class ActionAssociationExtent implements IPluginActionDelegate {

	/**
	 * Default constructor
	 */
	public ActionAssociationExtent() {
	}

	/**
	 * This is the Action Method called from the Action Proxy
	 */
	public void performAction(IPluginAction pluginAction) {
		if(!pluginAction.getSession().hasSystem()){
			JOptionPane.showMessageDialog(
							pluginAction.getParent(),
							"No model loaded. Please load a model first.",
							"No Model", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Getting Session object from Proxy
		Session session = pluginAction.getSession();
		// Getting MainWindow object from Proxy
		final MainWindow mainWindow = pluginAction.getParent();
		// Creating Association Extent View
		AssociationExtentView aev = new AssociationExtentView(mainWindow, session.system());
		ViewFrame f = new ViewFrame("Association Extent", aev, "ClassExtentView.gif");
		
		f.addInternalFrameListener(new InternalFrameAdapter() {
        	@Override
			public void internalFrameActivated(InternalFrameEvent ev) {
				mainWindow.statusBar().showTmpMessage("Use right mouse button to select association.");
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent ev) {
				mainWindow.statusBar().clearMessage();
			}
		});
		
		JComponent c = (JComponent) f.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(aev, BorderLayout.CENTER);
		// Adding View to the MainWindow
		mainWindow.addNewViewFrame(f);
	}
}

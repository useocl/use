package org.tzi.use.gui.plugins;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

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
 * 
 * @author Roman Asendorf
 * 
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
		// Getting Session object from Proxy
		Session fSession = pluginAction.getSession();
		// Getting MainWindow object from Proxy
		MainWindow fMainWindow = pluginAction.getParent();
		// Creating Association Extent View
		AssociationExtentView aev = new AssociationExtentView(fMainWindow,
				fSession.system());
		aev.setVisible(true);
		ViewFrame f = new ViewFrame("Association Extent", aev,
				"ClassExtentView.gif");
		JComponent c = (JComponent) f.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new JScrollPane(aev), BorderLayout.CENTER);
		// Adding View to the MainWindow
		fMainWindow.addNewViewFrame(f);
	}
}

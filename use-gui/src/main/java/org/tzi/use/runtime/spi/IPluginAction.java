package org.tzi.use.runtime.spi;

import java.awt.event.ActionEvent;

/**
 * This interface provides the Plugin Action Proxy's behaviour. - This interface
 * should only be used internally.
 *
 * <p>{@code getSession()} and {@code getParent()} return {@link Object} because
 * the SPI deliberately does not depend on the concrete application types
 * (e.g. {@code org.tzi.use.main.Session}, {@code org.tzi.use.gui.views.diagrams.MainWindow}).
 * Plugin code that already lives in the application is expected to cast the
 * returned object to the concrete type it knows.</p>
 *
 * @author Roman Asendorf
 */
public interface IPluginAction {

	/**
	 * Method to execute the Plugin Action
	 *
	 * @param event
	 *            The ActionEvent
	 */
	void actionPerformed(ActionEvent event);

	/**
	 * Returns the application's Session object (typed as Object to keep the SPI
	 * free of upstream package dependencies). Callers downcast to
	 * {@code org.tzi.use.main.Session} where the concrete type is needed.
	 */
	Object getSession();

	/**
	 * Returns the application's main window (typed as Object to keep the SPI
	 * free of upstream package dependencies). Callers downcast to
	 * {@code org.tzi.use.gui.views.diagrams.MainWindow} where the concrete type is needed.
	 */
	Object getParent();
}

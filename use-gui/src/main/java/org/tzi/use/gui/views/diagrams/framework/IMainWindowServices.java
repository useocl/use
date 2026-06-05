/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.tzi.use.gui.views.diagrams.framework;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.tzi.use.gui.main.View;
import org.tzi.use.gui.util.StatusBar;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.instance.MObject;

/**
 * The host services a diagram view needs from the application main window.
 * <p>
 * Lives in the foundation {@code framework} slice so that diagram type slices
 * (class/object/state-machine/sequence/communication diagrams and their
 * selection helpers) depend on this abstraction instead of the concrete
 * {@code MainWindow} (which sits at the top of the diagram package). The methods
 * are filled in compiler-driven from actual usage; {@code MainWindow} implements
 * this interface.
 */
public interface IMainWindowServices {

	/** Globally installed main window (set by MainWindow), so diagram code can reach
	 *  host services without a static reference to the concrete MainWindow (root). */
	AtomicReference<IMainWindowServices> INSTANCE = new AtomicReference<>();

	/** Whether the application is running under the JavaFX shell. */
	AtomicBoolean JAVA_FX_MODE = new AtomicBoolean(false);

	ModelBrowser getModelBrowser();

	View getSelectedView();

	PrintWriter logWriter();

	StatusBar statusBar();

	void addNewViewFrame(ViewFrame f);

	/** @return the created state-machine diagram view (typed Object to keep this
	 *  foundation interface independent of the statemachine slice). */
	Object showStateMachineView(MStateMachine sm);

	void showStateMachineView(MProtocolStateMachine sm, MObject instance);

	ObjectPropertiesView showObjectPropertiesView();

	void createObject(String clsName);

	/** @return the open object-diagram views (typed {@code List<?>} to keep this
	 *  foundation interface independent of the objectdiagram slice). */
	java.util.List<?> getObjectDiagrams();

}

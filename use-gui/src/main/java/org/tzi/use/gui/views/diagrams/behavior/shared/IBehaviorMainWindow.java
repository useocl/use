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

package org.tzi.use.gui.views.diagrams.behavior.shared;

import org.tzi.use.gui.views.diagrams.framework.IMainWindowServices;

/**
 * Behavior-diagram extension of {@link IMainWindowServices}: the two main-window
 * factory operations whose parameter ({@link VisibleDataManager}) lives in the
 * behavior slice, so they cannot sit on the foundation interface without creating
 * a {@code framework -> behavior} edge. {@code MainWindow} implements this; the
 * sequence/communication diagrams reach it by narrowing {@code getMainWindow()}.
 */
public interface IBehaviorMainWindow extends IMainWindowServices {

	void createSequenceDiagram(VisibleDataManager visibleDataManager);

	void createCommunicationDiagram(VisibleDataManager visibleDataManager);

}

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

package org.tzi.use.gui.viewsFX;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;


/** 
 * Views with JavaFX print/export facility implement this interface.
 * 
 * @author Akif Aydin
 */
public interface PrintableView {

	/**
	 * Prints the view using JavaFX's PrinterJob and layout.
	 *
	 * @param layout JavaFX PageLayout
	 * @param job    JavaFX PrinterJob
	 */
	void printView(PageLayout layout, PrinterJob job);

	/**
	 * @return the width of the content
	 */
	double getContentWidth();

	/**
	 * @return the height of the content
	 */
	double getContentHeight();
}

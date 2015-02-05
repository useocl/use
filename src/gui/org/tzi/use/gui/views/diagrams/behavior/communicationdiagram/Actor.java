/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

// $Id$

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

/**
 * This class represents data of the actor who works with USE in the
 * communication diagram.
 * 
 * @author Quang Dung Nguyen
 * 
 */
public class Actor {

	private String name;

	public Actor() {
		this.name = "Actor";
	}

	public Actor(String name) {
		this.name = name;
	}

	public void setUserName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return name;
	}

}

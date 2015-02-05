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

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;

/**
 * This class represents node of objects in communication diagrams
 * 
 * @author Quang Dung Nguyen
 * 
 */
public class ObjectBoxNode extends BaseNode implements ObjectNodeActivity {

	private final MObject obj;
	private final String createdStamp;

	public ObjectBoxNode(MObject obj, String sequenceNumber, CommunicationDiagramView parent, DiagramOptions opt) {
		super(parent, opt);
		this.obj = obj;
		this.createdStamp = sequenceNumber;

		MClass cls = obj.cls();
		label = obj.name() + ":" + cls.name();
		initLabel(label);
	}

	public MObject object() {
		return obj;
	}

	public MClass cls() {
		return obj.cls();
	}

	@Override
	public String name() {
		return obj.name() + "." + this.createdStamp;
	}

	@Override
	public String getTextForMenu() {
		return "object " + obj.name();
	}
	
	@Override
	protected String getStoreType() {
		return "Object Node";
	}

	@Override
	public String toString() {
		return "ObjectBoxNode[" + object().name() + "]";
	}

}

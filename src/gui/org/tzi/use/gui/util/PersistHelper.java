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

package org.tzi.use.gui.util;

import org.w3c.dom.Element;

/**
 * Provides easy to use XML methods
 * @author lhamann
 *
 */
public class PersistHelper {
	/**
	 * Appends a child element to <code>parent</code> with the
	 * given name and a text element with the value given by <code>value</code>
	 * @param tagName
	 * @parem value
	 * @return
	 */
	public static Element appendChild(Element parent, String tagName, String value) {
		Element e = parent.getOwnerDocument().createElement(tagName);
        e.appendChild(parent.getOwnerDocument().createTextNode(value));
        parent.appendChild(e);
        return e;
	}
	
	public static String getElementStringValue(Element parent, String childName) {
		return parent.getElementsByTagName(childName).item(0).getChildNodes().item(0).getNodeValue();
	}
	
	public static boolean getElementBooleanValue(Element parent, String childName) {
		return Boolean.valueOf(parent.getElementsByTagName(childName).item(0).getChildNodes().item(0).getNodeValue());
	}
	
	public static double getElementDoubleValue(Element parent, String childName) {
		return Double.valueOf(parent.getElementsByTagName(childName).item(0).getChildNodes().item(0).getNodeValue());
	}
}

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

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.tzi.use.util.Log;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Provides easy to use XML methods
 * @author Lars Hamann
 */
public class PersistHelper {
	
	XPathFactory factory = XPathFactory.newInstance();
	
	public PersistHelper() { }
	
	/**
	 * Appends a child element to <code>parent</code> with the
	 * given name and a text element with the value given by <code>value</code>
	 * @param tagName
	 * @param value
	 * @return
	 */
	public Element appendChild(Element parent, String tagName, String value) {
		Element e = parent.getOwnerDocument().createElement(tagName);
        e.appendChild(parent.getOwnerDocument().createTextNode(value));
        parent.appendChild(e);
        return e;
	}
	
	public String getElementStringValue(Element parent, String childName) {
		NodeList elems = parent.getElementsByTagName(childName);
		if (elems.getLength() > 0)
			return elems.item(0).getTextContent();
		else
			return null;
	}
	
	public boolean getElementBooleanValue(Element parent, String childName) {
		return Boolean.valueOf(getElementStringValue(parent, childName));
	}
	
	public double getElementDoubleValue(Element parent, String childName) {
		return Double.valueOf(getElementStringValue(parent, childName));
	}
	
	public int getElementIntegerValue(Element parent, String childName) {
		return Integer.valueOf(getElementStringValue(parent, childName));
	}
	
	public NodeList getChildElementsByTagName( Element parent, String childName) {
		return (NodeList)evaluateXPathSave(parent, "./" + childName, XPathConstants.NODESET);
	}
	
	public Element getElementByExpression( Element currentElement, String xpathExpr ) {
		return (Element)evaluateXPathSave(currentElement, xpathExpr, XPathConstants.NODE);
	}
	
	public Object evaluateXPathSave( Element currentElement, String xpathExpr, QName resultType) {
		XPath xpath = factory.newXPath();
		try {
			return xpath.evaluate(xpathExpr, currentElement, resultType);
		} catch (XPathExpressionException e) {
			Log.error("Invalid XPath expression: " + xpathExpr);
			return null;
		}
	}
}

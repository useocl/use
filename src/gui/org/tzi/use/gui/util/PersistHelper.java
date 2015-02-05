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

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

/**
 * Provides easy to use XML methods
 * @author Lars Hamann
 */
public class PersistHelper {
	
	final VTDGen vg;
	final VTDNav vn;
	
	final PrintWriter log;
	
	protected Map<String, PlaceableNode> allNodes;
	
	public PersistHelper(PrintWriter log) {
		vg = null;
		vn = null;
		this.log = log;
	}
	
	
	/**
	 * Sets up a new helper for reading a document
	 * @param file
	 */
	public PersistHelper(Path fileToRead, PrintWriter log) {
		vg = new VTDGen();
		vg.parseFile(fileToRead.toAbsolutePath().toString(), false);
		vn = vg.getNav();
		this.log = log;
	}
	
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
	
	/**
	 * Appends a child element to <code>parent</code> with the
	 * given name.
	 * @param tagName
	 * @return
	 */
	public Element createChild(Element parent, String tagName) {
		Element e = parent.getOwnerDocument().createElement(tagName);
        parent.appendChild(e);
        return e;
	}
	
	
	public VTDNav getNav() {
		return vn;
	}
	
	public boolean toFirstChild(String childName) {
		try {
			return vn.toElement(VTDNav.FIRST_CHILD, childName);
		} catch (NavException e) {
			log.println(e.getMessage());
			return false;
		}
	}
	
	public boolean toNextSibling(String childName) {
		try {
			return vn.toElement(VTDNav.NEXT_SIBLING, childName);
		} catch (NavException e) {
			log.println(e.getMessage());
			return false;
		}
	}
	
	public boolean hasAttribute(String attrName) {
		try {
			return vn.hasAttr(attrName);
		} catch (NavException e) {
			log.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * @param attrName
	 * @return
	 */
	public String getAttributeValue(String attrName) {
		try {
			int t = vn.getAttrVal(attrName);
			if (t != -1)
				return vn.toNormalizedString(t);
		} catch (NavException e) {
			log.println(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Extracts the string value of the current element
	 * @return
	 */
	public String getElementStringValue() {
		int t = vn.getText(); // get the index of the text (char data or CDATA)
        if (t != -1) {
        	try {
				return vn.toNormalizedString(t);
			} catch (NavException e) {
				return null;
			}
        }
        return null;
	}
	/**
	 * Retrieves the text of the child element with the name <code>childName</code>.
	 * @param parent
	 * @param childName
	 * @return The string value of the child (empty string of no text) or <code>null</code> if child does not exists.
	 */
	public String getElementStringValue(String childName) {
		String res = "";
		
		try {
			if (vn.toElement(VTDNav.FIRST_CHILD, childName)) {
				int t = vn.getText(); // get the index of the text (char data or CDATA)
		        if (t != -1) {
		        	res = vn.toRawString(t);
		        }
		        vn.toElement(VTDNav.PARENT);
			} else {
				return null;
			}
		} catch (NavException e) {
			log.println(e.getMessage());
			return null;
		}
		
		return res;
	}
	
	public boolean getElementBooleanValue(String childName) {
		return Boolean.valueOf(getElementStringValue(childName));
	}
	
	public double getElementDoubleValue(String childName) {
		return Double.valueOf(getElementStringValue(childName));
	}
	
	public int getElementIntegerValue(String childName) {
		return Integer.valueOf(getElementStringValue(childName));
	}
	
	public NodeList getChildElementsByTagName(String childName) {
		return null;
		
		//return (NodeList)evaluateXPathSave(parent, "./" + childName, XPathConstants.NODESET);
	}
	
	public Element getElementByExpression( String xpathExpr ) {
		return null;
		// return (Element)evaluateXPathSave(currentElement, xpathExpr, XPathConstants.NODE);
	}
		
		/*
		
		
		ap.
		int count = 1;
		while (ap.evalXPath() != -1) {
		    System.out.println("Inside Entry: " + count);

		    //move to n1 child
		    vn.toElement(VTDNav.FIRST_CHILD, "n1");
		    System.out.println("\tn1: " + vn.toNormalizedString(vn.getText()));

		    //move to n2 child
		    vn.toElement(VTDNav.NEXT_SIBLING, "n2");
		    System.out.println("\tn2: " + vn.toNormalizedString(vn.getText()));

		    //move back to parent
		    vn.toElement(VTDNav.PARENT);
		    count++;
		}
		
		XPath xpath = factory.newXPath();
		try {
			return xpath.evaluate(xpathExpr, currentElement, resultType);
		} catch (XPathExpressionException e) {
			Log.error("Invalid XPath expression: " + xpathExpr);
			return null;
		}
		*/

	/**
	 * 
	 */
	public void toParent() {
		try {
			vn.toElement(VTDNav.PARENT);
		} catch (NavException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public PrintWriter getLog() {
		return log;
	}

	/**
	 * @param collectAllNodes
	 */
	public void setAllNodes(Map<String, PlaceableNode> allNodes) {
		this.allNodes = allNodes;
	}
	
	public Map<String,PlaceableNode> getAllNodes() {
		return this.allNodes;
	}
	
}

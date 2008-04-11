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

// $Id$

package org.tzi.use.gui.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Abstract class as a content handler. Every parsed content will have
 * its own content handler.
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Fabian Gutsche 
 */
public abstract class ContentHandler extends DefaultHandler {

    public abstract void startElement( String nsURI, String localName,
                                       String qName, Attributes atts )
    throws SAXException;
    
    /**
     * This method is called to read the text within a xml-tag.
     */
    public abstract void characters( char[] ch, int start, int length )
    throws SAXException;
    
    /**
     * This method is called when the xml-tag ends. The value of 
     * tag is saved.
     */
    public abstract void endElement( String nsURI, String ln, String qName )
    throws SAXException;
}

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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.xmlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.tzi.use.config.Options;
import org.tzi.use.gui.views.diagrams.LayoutInfos;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//import com.sun.org.apache.xerces.internal.parsers.SAXParser;

import org.apache.xerces.parsers.SAXParser;


/**
 * Provieds access to the complete XML parser. 
 * 
 * @version     $ProjectVersion: 0.393 $
 * @author      Fabian Gutsche 
 */
public class XMLParserAccessImpl implements XMLParserAccess {
    private LayoutInfos fLayoutInfos;
    
    public XMLParserAccessImpl() {
    }
    
    public XMLParserAccessImpl( LayoutInfos layoutInfos ) {
        fLayoutInfos = layoutInfos;
    }
    
    /**
     * Loads a layout of a given diagram from an XML file.
     */
    public void loadXMLFile( File file, boolean hide ) {
        try {
            ContentHandler contentHandler = new LayoutContentHandler( fLayoutInfos,
                                                                      hide );
            InputStreamReader ins = 
                new InputStreamReader( new FileInputStream( file ),
                                       Options.ENCODING );
            InputSource inSource = new InputSource( ins );
            SAXParser saxParser = new SAXParser();
            saxParser.setContentHandler( contentHandler );
            saxParser.parse( inSource );
            
        } catch ( FileNotFoundException ex ) {
            ex.printStackTrace();
        } catch ( SAXException ex ) {
            ex.printStackTrace();
            System.out.println( "Error occured: Please check your XML-File:\n"
                                + ex.getMessage() );
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Loads a layout of a given diagram from an XML String.
     */
    public void loadXMLString( String xml, boolean hide ) {
        try {
            ContentHandler contentHandler = new LayoutContentHandler( fLayoutInfos,
                                                                      hide );
            String completeXML = "<?xml version=\"1.0\" encoding=\""
                                 + Options.ENCODING+"\"?>\n\n"
                                 + LayoutTags.OPEN_TAG
                                 + xml
                                 + LayoutTags.CLOSE_TAG;
            InputSource inSource = new InputSource( new StringReader( completeXML ) );
            SAXParser saxParser = new SAXParser();
            saxParser.setContentHandler( contentHandler );
            saxParser.parse( inSource );
            
        } catch ( FileNotFoundException ex ) {
            ex.printStackTrace();
        } catch ( SAXException ex ) {
            ex.printStackTrace();
            System.out.println( "Error occured: Please check your XML-File.\n"
                                + ex.getMessage() );
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the layout of a given diagram into an XML file.
     */
    public void saveXMLFile( File file, String xml ) {
        String openTag = 
            "<?xml version=\"1.0\" encoding=\""
            +Options.ENCODING+"\"?>\n\n"; 
        String closeTag = "";
        
        
        openTag += LayoutTags.OPEN_TAG;
        closeTag = LayoutTags.CLOSE_TAG;
        
        File orgFile = file;
        File backupFile = new File( file.getAbsolutePath() + ".bak");
        if ( backupFile.exists() ) {
            backupFile.delete();
        }
        orgFile.renameTo( new File( file.getAbsolutePath() + ".bak" ) );
        
        try {
            OutputStreamWriter osw = 
                new OutputStreamWriter( new FileOutputStream( file ),
                                        Options.ENCODING );
            
            osw.write( openTag, 0, openTag.length() );

            osw.write( xml, 0, xml.length() );
            osw.write( closeTag, 0, closeTag.length() );
            
            osw.flush();
            osw.close();
        } catch ( FileNotFoundException ex ) {
            ex.printStackTrace();
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
        
    }

}

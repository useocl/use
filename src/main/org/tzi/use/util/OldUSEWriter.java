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

package org.tzi.use.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.tzi.use.config.Options;
import org.tzi.use.main.shell.Shell;

/**
 * Needs to be used as output stream in USE. This class prints every
 * output into the normal System.out print stream and into a protocol
 * file.
 */
public class OldUSEWriter extends PrintStream {
    private static OldUSEWriter fPrintWriter;
      
    private String nl = Options.LINE_SEPARATOR;
    
    /**
     * Protocol buffer.
     */
    private StringWriter fProtocolWriter;
    
    /**
     * List of all prompts used during execution. For
     * examples reading to cmd-Files...
     */
    private List fPromptList;
    
    /**
     * Actual prompt which is used to intend the lines.
     */
    private String fActualPrompt = "";
    
    /**
     * Characters when a line break should occure.
     */
    private final int LINE_BREAK = 72;

    /**
     * Maximal ident of a prompt. If the prompt is longer than
     * this number, than everything after the prompt will be
     * in the next line and has an indent of 
     * <code>Shell.PROMPT.length()</code>.
     */
    private final int MAX_PROMPT_INDENT = 52;
    
    /**
     * Just one instance. Initializes the FileWriter, to which the
     * protocol file is written to.
     */
    private OldUSEWriter() {
        super( System.out );
        fProtocolWriter = new StringWriter();
        fPromptList = new ArrayList();
        fPromptList.add( Shell.PROMPT );
        fPromptList.add( Shell.CONTINUE_PROMPT );
    }
    
    /**
     * Returns the only existing instance of this PrintStream.
     */
    public static OldUSEWriter getInstance() {
        if ( fPrintWriter == null ) {
            fPrintWriter = new OldUSEWriter();
        }
        return fPrintWriter;
    }
 
    public void setCmdReadPrompt( String prompt ) {
        if ( prompt != null ) {
            fPromptList.add( prompt );
        }
    }
    
    /**
     * Write a portion of a String. And write this into the protocol
     * file as well.
     */
    public void write( byte[] buf, int off, int len ) {
        super.write( buf, off, len );
        writeBuffer( new String( buf ), off, len );
    }
    
    /**
     * Writes a line just into the protocol file.
     */
    public void protocol( String line ) {
        printBuffer( line );
        printlnBuffer();
    }

    /**
     * Writes the line separator to the protocol.
     */
    private void printlnBuffer() {
        fProtocolWriter.write( System.getProperty("line.separator") );
        fProtocolWriter.flush();
    }
    
    /**
     * Writes a string into the protocol.
     */
    private void printBuffer( String line ) {
        fProtocolWriter.write( line, 0, line.length() );
        fProtocolWriter.flush();
    } 

    /**
     * Write a portion of a String into the protocol.
     */
    private void writeBuffer( String line, int buf, int len ) {
        line = line.substring( buf, len );
        fProtocolWriter.write( line, 0, line.length() );
        fProtocolWriter.flush();
    } 
    
    /**
     * Writes protocol file to the harddrive.
     */
    public void writeProtocolFile( File file ) {
        // if file name does not end with '.protocol' append it to the end
        if ( !file.getName().endsWith(".protocol") ) {
            file = new File( file.getAbsolutePath() + ".protocol" );
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter( file ) );
            writer.write( formatProtocolBuffer() );
            writer.flush();
            writer.close();
            fActualPrompt = "";
        } catch ( IOException ex ) {
            System.out.println( "Error: " + ex.getMessage() );
            //ex.printStackTrace();
        }
    }
    
    /**
     * Formats the protocol buffer in the wanted format.
     * @return A String representing the protocol buffer in
     *         the wanted format.
     */
    private String formatProtocolBuffer() {
        String protocol = "";
        String buffer = fProtocolWriter.toString();
        StringTokenizer st = new StringTokenizer( buffer, "\n" +nl );
        
        while ( st.hasMoreTokens() ) {
            protocol += formatLineForProtocol( st.nextToken() );
            protocol += nl;
        }
        
        return protocol;
    }
    
    /**
     * Gives the line the right layout for the protocol file.
     *
     * @param line Line to layout.
     * @return Layouted line.
     */
    private String formatLineForProtocol( String line ) {
        Iterator it = fPromptList.iterator();
        String prompt = "";
        
        while ( it.hasNext() ) {
            prompt = (String) it.next();
            
            if ( line.startsWith( prompt ) 
                 && prompt.equals( Shell.CONTINUE_PROMPT ) ) {
                line = spaces( Shell.PROMPT.length()-1 ) + line;
                fActualPrompt = Shell.CONTINUE_PROMPT;
            } else if ( line.startsWith( "->" ) ) {
                line = line.substring( 2 ).trim();
            } else if ( line.startsWith( prompt ) ) {
                fActualPrompt = prompt;
                return doLineBreak( line, "", prompt );
            }
        }
    
        if ( fActualPrompt.length() >= MAX_PROMPT_INDENT ) {
            line = spaces( Shell.PROMPT.length() ) + line;    
        } else {
            line = spaces( fActualPrompt.length() ) + line;
        }
        return doLineBreak( line, "", fActualPrompt );
    }

    /**
     * Returns <code>x</code> spaces within a string.
     */
    private String spaces( int x ) {
        String spaces = "";
        for ( int i=0; i<x; i++ ) {
            spaces += " ";
        }
        return spaces;
    }
    
    /**
     * Is the line longer than 72 characters, than a line break will
     * be done at the most possible point bevor the 72nd character.
     */
    private String doLineBreak( String line, String oldLine, String prompt ) {
        StringTokenizer st; 
        String token = "";
        String newLine = "";
        
        line = introduceSpacesBehindComma( line );
        
        st = new StringTokenizer( line, "\n"+nl, true );        
        while ( st.hasMoreTokens() ) {
            token = st.nextToken();

            if ( token.length() > LINE_BREAK ) {
                newLine += separateLine( token, oldLine, prompt );
            } else {
                newLine += token;
            }
        }
        return newLine;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * Is the line longer than 72 characters, than a line break will
     * be done at the most possible point bevor the 72nd character.
     */
    private String separateLine( String line, String oldLine, String prompt ) {
        StringTokenizer st;
        String token;
        String newLine;
        
        if ( ! isSeparateable( line.trim() ) ) {
            return line;
        }
        
        if ( line.length() > LINE_BREAK ) {
            st = new StringTokenizer( line, ", ->", true );
            token = "";
            newLine = "";
            
            while ( st.hasMoreTokens() ) {
                token = st.nextToken();
                
                // check if there is '->' as string. if there is use 
                // this as line break as well.
                if ( token.equals( "-" ) || token.equals( ">" ) ) {
                    if ( st.hasMoreTokens() ) {
                        String nextToken = st.nextToken();
                        if ( token.equals( "-" ) && nextToken.equals( ">" ) ) {
                            // set token to the original wanted token.
                            token = "->";
                            // nextToken is not the arrowhead '>' than add both
                            // token and nextToken to the newLine. Continue,
                            // because at this point there should be no line break.
                        } else {
                            newLine += token + nextToken;
                            continue;
                        }
                    }
                }
                
                // newLine + token longer than LINE_BREAK, than make a line break
                // behind the new line and check if the rest needs to have 
                // another line break.
                if ( newLine.length() + token.length() > LINE_BREAK ) {
                    String nextLine = "";
                    int index = 0;
                    
                    if ( newLine.trim().equals( prompt.trim() ) ) {
                        newLine += token;
                        continue;
                    } else if ( prompt.length() >= MAX_PROMPT_INDENT ) {
                        // if the prompt is longer than the MAX_PROMPT_INDENT
                        // indent the next line with a standart indext of 
                        // the length of the USE-shell prompt.
                        index = newLine.length() + token.length();
                        nextLine = spaces( Shell.PROMPT.length() ) 
                                   + (token + line.substring( index )).trim();
                        return newLine + nl 
                               + separateLine( nextLine, line, prompt );
                    } else if ( token.equals( "," ) ) {
                        // is the token a comma, than put it to the new line.
                        // we do not want to have a comma as first character of 
                        // a line
                        newLine += token;
                        index = newLine.length();
                        nextLine = spaces( prompt.length() ) 
                                   + line.substring( index ).trim();
                        return newLine + nl 
                               + separateLine( nextLine, line, prompt );
                    } else {
                        index = newLine.length() + token.length();
                        nextLine = spaces( prompt.length() )
                                   + (token + line.substring( index )).trim();
                        return newLine + nl 
                               + separateLine( nextLine, line, prompt );
                    }
                } else {
                    // if the line is still not longer than LINE_BREAK add
                    // the token to the new line.
                    newLine += token;
                }
            }
            return newLine;
        }

        return line;        
    }

    /**
     * Checkes if a given line can be separated.
     * @param line Line to be checked.
     * @return <code>true</code> if the line can be separated, 
     *         otherwise <code>false</code>.
     */
    private boolean isSeparateable( String line ) {
        StringTokenizer st = new StringTokenizer( line, ", ->", true );
        String token = "";
        while ( st.hasMoreTokens() ) {
            token = st.nextToken();
            if ( token.equals( "-" ) || token.equals( ">" ) ) {
                if ( st.hasMoreTokens() ) {
                    String nextToken = st.nextToken();
                    if ( token.equals( "-" ) && nextToken.equals( ">" ) ) {
                        return true;
                    } 
                } 
            }
            
            if ( token.equals( " " ) || token.equals( "," ) ) {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Places a space behind every comma. 
     */
    private String introduceSpacesBehindComma( String line ) {
        StringTokenizer st = new StringTokenizer( line, ", ", true );
        String token = "";
        String newLine = "";
        boolean lastTokenWasComma = false;
        
        while ( st.hasMoreTokens() ) {
            token = st.nextToken();
            
            if ( token.equals( "," ) ) {
                lastTokenWasComma = true;
                newLine += ", ";
            } else if ( lastTokenWasComma && token.equals( " " ) ) {
                // ignore space, because we don't want to have two
                // spaces behind a comma
                lastTokenWasComma = false;
            } else if ( lastTokenWasComma && !token.equals( " " ) ) {
                newLine += token;
                lastTokenWasComma = false;
            } else {
                newLine += token;
                lastTokenWasComma = false;
            }
        }
        return newLine;            
    }
    
    
}

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

package org.tzi.use.parser;

import org.antlr.runtime.Token;

/** 
 * Exception thrown during code generation in AST walking.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class SemanticException extends Exception {
	/**
	 * To get rid of the warning...
	 */
	private static final long serialVersionUID = 1L;
	
    private final SrcPos fSrcPos;

    public SemanticException(String message) {
    	super(message);
    	fSrcPos = null;
    }
    
    /** 
     * Construct exception with information about source position.
     */
    public SemanticException(SrcPos pos, String message) {
        super(message);
        fSrcPos = pos;
    }
    
    public SemanticException(SrcPos pos, Exception ex) {
        this(pos, ex.getMessage());
    }
    
    /** 
     * Construct exception with information about source position
     * given by an error token.
     */
    public SemanticException(Token token, String message) {
        super(message);
        fSrcPos = new SrcPos(token);
    }
    
    public SemanticException(Token token, Exception ex) {
        this(token, ex.getMessage());
    }

    
    /**
     * Returns the source informations and the description of the exception 
     */
    @Override
    public String getMessage() {
        return (fSrcPos == null ? "" : fSrcPos.toString()) + super.getMessage();
    }
    
    /**
     * Returns only the exception message without source information.
     * @return description of the exception 
     */
    public String getShortMessage() {
        return super.getMessage();
    }
}

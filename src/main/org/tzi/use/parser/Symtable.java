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

$Id$

package org.tzi.use.parser;

import java.util.HashMap;
import java.util.Stack;

import org.tzi.use.config.Options;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;

/**
 * Symbol table for variable declarations with nested scopes.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class Symtable {

    /**
     * A Symtable entry has a name, a type and optionally information
     * about the position in the source.
     */
    class Entry {
        String fName;
        Type fType;
        SrcPos fSrcPos;     // may be null

        Entry(String name, Type t, SrcPos pos) {
            fName = name;
            fType = t;
            fSrcPos = pos;
        }
    }

    /**
     * The stack of scopes.
     */
    private Stack fScopes;  // (Map(String name -> Entry))
    
    /**
     * An optional set of externally defined global variable bindings.
     */
    private VarBindings fGlobalBindings; // may be null

    public Symtable() {
        fScopes = new Stack();
        enterScope();
    }

    public Symtable(VarBindings globalBindings) {
        fScopes = new Stack();
        fGlobalBindings = globalBindings;
        enterScope();
    }

    /** 
     * Creates a new scope level.
     */
    public void enterScope() {
        fScopes.push(new HashMap());
        // System.out.println("--- enterScope");
    }
    
    /** 
     * Drops one scope level.
     */
    public void exitScope() {
        fScopes.pop();
        // System.out.println("--- exitScope");
    }

    /** 
     * Adds a new entry to the current scope. The argument pos may be null.
     *
     * @throws SemanticException name to be added already exists.
     */
    public void add( String name, Type type, SrcPos pos )
            throws SemanticException {
        HashMap names = (HashMap) fScopes.peek();
        Entry e = (Entry) names.get( name );
        if ( e != null ) {
            String msg = "Redefinition of `" + name + "'.";
            if ( e.fSrcPos != null )
                msg += Options.LINE_SEPARATOR + e.fSrcPos + "`" + name
                        + "' previously declared here.";
            throw new SemanticException( pos, msg );
        }
        names.put( name, new Entry( name, type, pos ) );
        // System.out.println("--- add: " + varName + " : " + type);
    }
    
    
 /*   public void add(String name, Type type, SrcPos pos) 
    throws SemanticException 
    {
        HashMap names = (HashMap) fScopes.peek();
        Entry e = (Entry) names.get(name);
        
//        if ( e != null ) {
//            if ( name.equals( "self" ) ) {
//                buildErrorString( name, e, pos );
//            } else {
//                try { 
//                    buildErrorString( name, e, pos );
//                } catch ( SemanticException ex ) {
//                    System.out.println( ex.getMessage() );
//                    System.exit(1);
//                    //throw new Error( ex.getMessage() );
//                }
//            }
//        }
        
        if (e != null ) {
            try {
                String msg = "Redefinition of `" + name + "'.";
                if (e.fSrcPos != null )
                    msg += Options.LINE_SEPARATOR + e.fSrcPos + 
                    "`" + name + "' previously declared here.";
                throw new SemanticException(pos, msg);
            } catch ( SemanticException ex ) {
                ex.getMessage();
            }
        }
        names.put(name, new Entry(name, type, pos));
        // System.out.println("--- add: " + name + " : " + type);
    }
   */ 
//    /**
//     * Builds the error string for the SemanticException. This method is 
//     * neccessarry because if name equals 'self' than the exception is 
//     * just written into an error PrintWriter in Context.java. Ohterwiese
//     * the exception will be printet in the calling method.
//     * 
//     * @throws SemanticException name to be added already exists.
//     */
//    private String buildErrorString( String name, Entry e, SrcPos pos ) 
//    throws SemanticException {
//        String msg = "Redefinition of `" + name + "'.";
//        if (e.fSrcPos != null )
//            msg += Options.LINE_SEPARATOR + e.fSrcPos + 
//            "`" + name + "' previously declared here.";
//        throw new SemanticException(pos, msg);
//    }
//    
    public void add(MyToken token, Type type) 
        throws SemanticException 
    {
        this.add(token.getText(), type, new SrcPos(token));
    }


    /** 
     * Looks up a name and returns its type. 
     *
     * @return null if variable not found.
     */
    public Type lookup(String name) {
        // System.out.println("--- lookup: " + name);
        for (int i = fScopes.size() - 1; i >= 0; i--) {
            HashMap names = (HashMap) fScopes.elementAt(i);
            Entry e = (Entry) names.get(name);
            if (e != null )
                return e.fType;
        }

        if (fGlobalBindings != null ) {
            Value v = fGlobalBindings.getValue(name);
            if (v != null )
                return v.type();
        }

        return null;
    }
}

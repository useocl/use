/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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

package org.tzi.use.gen.assl.statics;

import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @see org.tzi.use.gen.assl.statics
 * @author  Joern Bohling
 */
public class GProcedure {
    private String fName;
    private List fParameterDecls; // VarDecl
    private List fLocalDecls; // VarDecl
    private GInstructionList fInstructionList;

    public GProcedure(String name) {
        fName = name;
        fParameterDecls = new ArrayList();
        fLocalDecls = new ArrayList();
        fInstructionList = new GInstructionList();
    }

    public String name() {
        return fName;
    }

    public void addParameterDecl( VarDecl parameterDecl ) {
        fParameterDecls.add(parameterDecl);
    }
    
    public void addLocalDecl( VarDecl localDecl ) {
        fLocalDecls.add(localDecl);
    }

    public void addInstruction( GInstruction instruction ) {
        fInstructionList.add(instruction);
    }

    public List parameterDecls() {
        return fParameterDecls;
    }
    
    public List localDecls() {
        return fLocalDecls;
    }
    
    public GInstructionList instructionList() {
        return fInstructionList;
    }

    private List parameterTypes() {
        Iterator it = parameterDecls().iterator();
        ArrayList types = new ArrayList();
        while (it.hasNext())
            types.add( ((VarDecl) it.next()).type() );
        return types;
    }

    public List signature() {
        List types = new ArrayList(parameterTypes());
        types.add(0, name());
        return types;
    }

    private String signatureString() {
        return "procedure " + name() + "(" + StringUtil.fmtSeq(parameterTypes().iterator(), ",") + ")";
    }
    
    public String toString() {
        return signatureString();
    }
}




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

package org.tzi.use.parser.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTCmdList extends AST {
    private List fCmdList;  // (ASTCmd)

    public ASTCmdList() {
        fCmdList = new ArrayList();
    }

    public void add(ASTCmd cmd) {
        fCmdList.add(cmd);
    }

    public List gen(Context ctx) 
        throws SemanticException
    {
        // map list of ASTCmd to list of Cmd
        List cmdList = new ArrayList(fCmdList.size());
        Iterator it = fCmdList.iterator();
        while (it.hasNext() ) {
            ASTCmd cmd = (ASTCmd) it.next();
            cmdList.add(cmd.gen(ctx));
        }
        return cmdList;
    }
}

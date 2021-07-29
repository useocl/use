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

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.uml.mm.MMultiplicity;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTMultiplicity extends AST {
    private Token fStartToken; // for error position
    private List<ASTMultiplicityRange> fRanges;

    public ASTMultiplicity(Token t) {
        fStartToken = t;
        fRanges = new ArrayList<ASTMultiplicityRange>();
    }

    public void addRange(ASTMultiplicityRange mr) {
        fRanges.add(mr);
    }

    public MMultiplicity gen(Context ctx) {
        MMultiplicity mult = ctx.modelFactory().createMultiplicity();

        try {
            for (ASTMultiplicityRange mr : fRanges) {
                mult.addRange(mr.fLow, mr.fHigh);
            }
        } catch (IllegalArgumentException ex) {
            ctx.reportError(fStartToken, ex);
        }
        return mult;
    }

    public String toString() {
        return "FIXME";
    }
}

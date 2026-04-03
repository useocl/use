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

package org.tzi.use.uml.sys;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;

/**
 * Functional interface for compiling invariant definitions from an input stream.
 * This decouples MSystem from the parser's ASSLCompiler.
 *
 * @author Trong-Nghia Be
 */
@FunctionalInterface
public interface InvariantCompiler {
    /**
     * Compiles invariant definitions from an input stream.
     *
     * @param model the model to compile invariants for
     * @param in the input stream containing invariant definitions
     * @param inputName the name of the input source (for error messages)
     * @param out writer for output/error messages
     * @return a collection of compiled class invariants, or null on failure
     */
    Collection<MClassInvariant> compileInvariants(MModel model, InputStream in, String inputName, PrintWriter out);
}

/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2024 Mark Richters, University of Bremen
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

package org.tzi.use.uml.mm;

/**
 * Marker interface for the body of an {@link MOperation}.
 * Concrete implementations live in {@code org.tzi.use.uml.mm.sys.soil}
 * (e.g. {@code MStatement}); this interface exposes only what the
 * model layer needs so that {@code mm} does not depend on {@code sys}.
 */
public interface IStatement {
    String toConcreteSyntax(int indent, int indentIncr);
}

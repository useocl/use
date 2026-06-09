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

package org.tzi.use.util;

import java.util.EnumMap;
import java.util.Map;

/**
 * A {@link UserOutput} that collects all output in memory. Useful for tests and
 * for capturing output before forwarding it somewhere else.
 *
 * @author Claude
 */
public class CollectingUserOutput implements UserOutput {

    private final StringBuilder all = new StringBuilder();
    private final Map<OutputLevel, StringBuilder> byLevel = new EnumMap<>(OutputLevel.class);

    @Override
    public void print(OutputLevel level, String message) {
        all.append(message);
        byLevel.computeIfAbsent(level, l -> new StringBuilder()).append(message);
    }

    /** All collected output across every level, in the order it was produced. */
    public String getOutput() {
        return all.toString();
    }

    /** The collected output for a single {@code level}. */
    public String getOutput(OutputLevel level) {
        StringBuilder sb = byLevel.get(level);
        return sb == null ? "" : sb.toString();
    }
}

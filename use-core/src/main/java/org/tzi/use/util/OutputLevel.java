/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2026 University of Bremen & University of Applied Sciences Hamburg
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

/**
 * The level of a piece of {@link UserOutput} meant for the USE user.
 *
 * <p>This is intentionally separate from developer-facing logging (see issue
 * #60): it describes <em>what kind</em> of user output is produced, so that
 * different output targets can render or route it accordingly (e.g. errors and
 * warnings to an error stream, warnings highlighted, ...).</p>
 *
 * @author Cansin Yildiz
 * @author Claude
 */
public enum OutputLevel {
    /** Regular output that is the result the user asked for. */
    NORMAL,
    /** Additional, informational output. */
    INFO,
    /** A warning the user should be aware of. */
    WARN,
    /** An error reported to the user. */
    ERROR,
    /** Fine-grained trace output for following what USE is doing. */
    TRACE
}

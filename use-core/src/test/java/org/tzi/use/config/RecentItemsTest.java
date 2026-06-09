/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2025 University of Bremen & University of Applied Sciences Hamburg
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

package org.tzi.use.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that recent items are not recorded while USE runs in test mode.
 *
 * <p>When integration tests run, the files they open must not be added to the
 * user's recent files list, otherwise they would push out the user's own
 * entries (issue #50). This is controlled by {@link Options#testMode}.</p>
 *
 */
public class RecentItemsTest {

    /** An isolated preferences node, kept separate from the real recent files. */
    private Preferences node;

    @BeforeEach
    public void setUp() throws BackingStoreException {
        Options.resetOptions();
        node = Preferences.userRoot().node("/org/tzi/use/test/recentitems");
        node.clear();
    }

    @AfterEach
    public void tearDown() throws BackingStoreException {
        node.clear();
        Options.resetOptions();
    }

    @Test
    public void pushIsIgnoredInTestMode() {
        RecentItems items = new RecentItems(5, node);
        assertTrue(items.isEmpty(), "precondition: no recent items");

        Options.testMode = true;
        items.push("/tmp/integration-test-file.use");

        assertTrue(items.isEmpty(),
                "In test mode, opening files must not be recorded as recent items.");
    }

    @Test
    public void pushRecordsWhenNotInTestMode() {
        RecentItems items = new RecentItems(5, node);

        Options.testMode = false;
        items.push("/tmp/user-file.use");

        assertEquals(1, items.size(),
                "Outside test mode, recent items must still be recorded as before.");
        assertEquals("/tmp/user-file.use", items.peek());
    }
}

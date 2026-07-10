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

package org.tzi.use.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the configurable directories for plugins and OCL extensions
 * (issue #20).
 *
 * <p>By default both directories are derived from the USE home directory
 * ({@code -H}): plugins live in {@code <home>/lib/plugins} and OCL extensions
 * in {@code <home>/oclextensions}. These tests verify that both defaults are
 * applied and that each directory can be overridden independently with a
 * startup argument ({@code -pluginDir=} and {@code -extensionsDir=}).</p>
 *
 * @author Cansin Yildiz
 * @author Claude
 */
public class OptionsDirectoryArgsTest {

    private Properties savedSystemProperties;

    @BeforeEach
    public void setUp() {
        // processArgs() augments the global system properties; remember them
        // so we can restore them and keep the test isolated.
        savedSystemProperties = (Properties) System.getProperties().clone();
        Options.resetOptions();
    }

    @AfterEach
    public void tearDown() {
        Options.resetOptions();
        System.setProperties(savedSystemProperties);
    }

    private Path home() {
        return Paths.get(System.getProperty("java.io.tmpdir"), "use-home-under-test");
    }

    @Test
    public void pluginDirDefaultsToHomeLibPlugins() {
        Path home = home();
        Options.processArgs(new String[] { "-H=" + home, "-c" });
        assertEquals(home.resolve("lib").resolve("plugins"), Options.pluginDir,
                "plugin directory must default to <home>/lib/plugins");
    }

    @Test
    public void extensionsDirDefaultsToHomeOclextensions() {
        Path home = home();
        Options.processArgs(new String[] { "-H=" + home, "-c" });
        assertEquals(home.resolve("oclextensions"), Options.oclExtensionsDir,
                "OCL extensions directory must default to <home>/oclextensions");
    }

    @Test
    public void pluginDirCanBeOverridden() {
        Path home = home();
        Path custom = Paths.get(System.getProperty("java.io.tmpdir"), "custom-plugins");
        Options.processArgs(new String[] { "-H=" + home, "-pluginDir=" + custom, "-c" });
        assertEquals(custom, Options.pluginDir,
                "-pluginDir must override the plugin directory");
        assertEquals(home.resolve("oclextensions"), Options.oclExtensionsDir,
                "overriding the plugin directory must not affect the extensions directory");
    }

    @Test
    public void extensionsDirCanBeOverridden() {
        Path home = home();
        Path custom = Paths.get(System.getProperty("java.io.tmpdir"), "custom-extensions");
        Options.processArgs(new String[] { "-H=" + home, "-extensionsDir=" + custom, "-c" });
        assertEquals(custom, Options.oclExtensionsDir,
                "-extensionsDir must override the OCL extensions directory");
        assertEquals(home.resolve("lib").resolve("plugins"), Options.pluginDir,
                "overriding the extensions directory must not affect the plugin directory");
    }

    @Test
    public void bothDirectoriesCanBeOverriddenIndependentlyOfHome() {
        Path home = home();
        Path customPlugins = Paths.get(System.getProperty("java.io.tmpdir"), "p");
        Path customExtensions = Paths.get(System.getProperty("java.io.tmpdir"), "e");
        Options.processArgs(new String[] {
                "-H=" + home,
                "-pluginDir=" + customPlugins,
                "-extensionsDir=" + customExtensions,
                "-c" });
        assertEquals(customPlugins, Options.pluginDir);
        assertEquals(customExtensions, Options.oclExtensionsDir);
    }
}

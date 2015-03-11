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

package org.tzi.use.main.shell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.tzi.use.config.Options;
import org.tzi.use.util.Log;

/**
 * Singleton helper class for the command line help. The actual help texts are
 * taken from a property file.
 * 
 * The property is organized in keys. For each key, three entries must exist in
 * the property file.
 *
 * Example: help.net.syntax=net help.net=Read commands from socket
 * help.net.detail=Read commands from a socket. Socket number is 1777.
 * 
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick </a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche </a>
 * @author green
 */
public class HelpForCmd {

    private static HelpForCmd INSTANCE;

    private static final String INDENT = "  ";

    private static final String HELP_PROPERTY_FILE = "help.properties";

    private ResourceBundle resource;

    private HelpForCmd() {
    	Path path = Options.homeDir.resolve("etc").resolve(HELP_PROPERTY_FILE);
        try (InputStream fis = Files.newInputStream(path)) {
            resource = new PropertyResourceBundle(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HelpForCmd getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HelpForCmd();
        }
        return INSTANCE;
    }

    /**
     * Prints a detailed help for the command/concept identified by key.
	 * 
	 * @param key
	 *            The help key
     */
    private void printDetailedHelpByKey(String key) {
        try {
            String shortStr = resource.getString(key);
            String syntax = resource.getString(key+".syntax");
            String detail = resource.getString(key+".detail");
            detail = detail.replaceAll("\\n",Options.LINE_SEPARATOR + "  ");
            System.out.println("SYNTAX");
            System.out.println(INDENT + syntax);
            System.out.println();
            System.out.println("SYNOPSIS");
            System.out.println(INDENT + shortStr);
            System.out.println();
            System.out.println("DESCRIPTION");
            System.out.println(INDENT + detail);
            System.out.println();
        } catch (MissingResourceException e) {
            Log.error("help missing for " + key);
        }
    }

    /**
     * Prints a one line help for the command/concept identified by key.
	 * 
	 * @param key
	 *            The help key
     */
    private void printOneLineHelpByKey(String key) {
        try {
            String shortStr = resource.getString(key);
            String syntax = resource.getString(key+".syntax");
            System.out.println(fillUp(syntax,30) + shortStr);
        } catch (MissingResourceException e) {
            Log.error("help missing for " + key);
        }
    }
    
    /**
     * Prints a header (used in the help overview).
	 * 
     * @param header
     */
    private void printHeader(String header) {
        int length = 72;
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<(length-header.length())/2; i++) {
            buf.append("=");
        }
        buf.append(header);
        for (int i=0; i<(length-header.length())/2; i++) {
            buf.append("=");
        }
        if (buf.length() != length)
            buf.append("=");
        System.out.println(buf);
    }
    
    // helper function, fills a string with spaces
    private String fillUp(String str, int length) {
        StringBuffer buf = new StringBuffer(str);
        buf.append("  ");
        while(buf.length() < length-2) {
            buf.append(" ");
        }
        return buf.toString();
    }
    
    /**
     * Print help for a command. If cmd is null, a help overview is printed.
	 * 
     * @param cmd
     */
    public void printHelp(String cmd) {
        cmd = cmd.trim();
        if (cmd.length() == 0) {
            printHelpOverview();
        } else if (cmd.startsWith("??")) {
            printDetailedHelpByKey("help.eval.verbose");
        } else if (cmd.startsWith("?")) {
            printDetailedHelpByKey("help.eval");
        } else if (cmd.startsWith(":")) {
            printDetailedHelpByKey("help.statictype");
        } else if (cmd.startsWith("!") && cmd.indexOf("CMD") > -1) {
            printDetailedHelpByKey("help.cmd");
        } else if (cmd.equals("!create") || cmd.equals("create")) {
            printDetailedHelpByKey("help.create");
        } else if (cmd.equals("!destroy") || cmd.equals("destroy")) {
            printDetailedHelpByKey("help.destroy");
        } else if (cmd.equals("!insert") || cmd.equals("insert")) {
            printDetailedHelpByKey("help.insert");
        } else if (cmd.equals("!delete") || cmd.equals("delete")) {
            printDetailedHelpByKey("help.delete");
        } else if (cmd.equals("!set") || cmd.equals("set")) {
            printDetailedHelpByKey("help.set");
        } else if (cmd.equals("!openter") || cmd.equals("openter")) {
            printDetailedHelpByKey("help.openter");
        } else if (cmd.equals("!opexit") || cmd.equals("opexit")) {
            printDetailedHelpByKey("help.opexit");
        } else if (cmd.startsWith("\\")) {
            printDetailedHelpByKey("help.multiline");
        } else if (cmd.startsWith("check")) {
            printDetailedHelpByKey("help.check");
        } else if (cmd.startsWith("genmm")) {
            printDetailedHelpByKey("help.genmm");
        } else if (cmd.startsWith("help")) {
            printDetailedHelpByKey("help.help");
        } else if (cmd.startsWith("info class")) {
            printDetailedHelpByKey("help.info.class");
        } else if (cmd.startsWith("info model")) {
            printDetailedHelpByKey("help.info.model");
        } else if (cmd.startsWith("info state")) {
            printDetailedHelpByKey("help.info.state");
        } else if (cmd.startsWith("info opstack")) {
            printDetailedHelpByKey("help.info.opstack");
        } else if (cmd.startsWith("info prog")) {
            printDetailedHelpByKey("help.info.prog");
        } else if (cmd.startsWith("info vars")) {
            printDetailedHelpByKey("help.info.vars");
        } else if (cmd.startsWith("info")) {
            printDetailedHelpByKey("help.info");
        } else if (cmd.startsWith("net")) {
            printDetailedHelpByKey("help.net");
        } else if (cmd.startsWith("open")) {
            printDetailedHelpByKey("help.open");
        } else if (cmd.startsWith("reopen")) {
        	printDetailedHelpByKey("help.reopen");
        } else if (cmd.startsWith("readq")) {
            printDetailedHelpByKey("help.readq");
        } else if (cmd.startsWith("read")) {
            printDetailedHelpByKey("help.read");
        } else if (cmd.startsWith("reset")) {
            printDetailedHelpByKey("help.reset");
        } else if (cmd.startsWith("step on")) {
            printDetailedHelpByKey("help.stepon");
        } else if (cmd.equals("q") || cmd.equals("quit") || cmd.equals("exit")) {
            printDetailedHelpByKey("help.quit");
        } else if (cmd.startsWith("undo")) {
            printDetailedHelpByKey("help.undo");
        } else if (cmd.startsWith("write")) {
            printDetailedHelpByKey("help.write");
        } else if (cmd.startsWith("constraints -load")) {
        	printDetailedHelpByKey("help.constraints.load");
        } else if (cmd.startsWith("constraints -unload")) {
        	printDetailedHelpByKey("help.constraints.unload");
        } else if (cmd.startsWith("constraints -loaded")) {
        	printDetailedHelpByKey("help.constraints.loaded");
        } else if (cmd.startsWith("constraints -flags")) {
        	printDetailedHelpByKey("help.constraints.flags");
        } else if (cmd.startsWith("gen load")) { //TODO deprecated since USE 4.1, remove in USE 4.2
            printDetailedHelpByKey("help.gen.load");
        } else if (cmd.startsWith("gen unload")) {
            printDetailedHelpByKey("help.gen.unload");
        } else if (cmd.startsWith("gen loaded")) {
            printDetailedHelpByKey("help.gen.loaded");
        } else if (cmd.startsWith("gen flags")) {
            printDetailedHelpByKey("help.gen.flags");
        } else if (cmd.startsWith("gen start")) {
            printDetailedHelpByKey("help.gen.start");
        } else if (cmd.startsWith("gen result accept")) {
            printDetailedHelpByKey("help.gen.result.accept");
        } else if (cmd.startsWith("gen result inv")) {
            printDetailedHelpByKey("help.gen.result.inv");
        } else if (cmd.startsWith("gen result")) {
            printDetailedHelpByKey("help.gen.result");   
		} else if (cmd.startsWith("plugins")) {
			printDetailedHelpByKey("help.plugins");
		} else if (cmd.startsWith("delay")) {
			printDetailedHelpByKey("help.delay");
		} else if (cmd.startsWith("coverage")) {
			printDetailedHelpByKey("help.info.coverage");
        } else
            Log.error("Unknown command `" + cmd + "'. " + "Try `help'.");
    }

    /**
     * Prints overview of all commands (one line per command).
     */
    private void printHelpOverview() {
        printHeader("General commands");
        printOneLineHelpByKey("help.help");
        printOneLineHelpByKey("help.delay");
        printHeader("Evaluation commands");
        printOneLineHelpByKey("help.eval");
        printOneLineHelpByKey("help.eval.verbose");
        printOneLineHelpByKey("help.statictype");
        printOneLineHelpByKey("help.multiline");
        printHeader("State manipulation commands");
        printOneLineHelpByKey("help.cmd");
        printOneLineHelpByKey("help.create");
        printOneLineHelpByKey("help.destroy");
        printOneLineHelpByKey("help.insert");
        printOneLineHelpByKey("help.delete");
        printOneLineHelpByKey("help.set");
        printOneLineHelpByKey("help.openter");
        printOneLineHelpByKey("help.opexit");
        printOneLineHelpByKey("help.check");
        printOneLineHelpByKey("help.stepon");
        printHeader("File input");
        printOneLineHelpByKey("help.open");
        printOneLineHelpByKey("help.reopen");
        printOneLineHelpByKey("help.read");
        printOneLineHelpByKey("help.readq");
        printOneLineHelpByKey("help.reset");
        printOneLineHelpByKey("help.quit");
        printOneLineHelpByKey("help.undo");
        printHeader("Information commands");
        printOneLineHelpByKey("help.info");
        printOneLineHelpByKey("help.info.class");
        printOneLineHelpByKey("help.info.model");
        printOneLineHelpByKey("help.info.state");
        printOneLineHelpByKey("help.info.opstack");
        printOneLineHelpByKey("help.info.prog");
        printOneLineHelpByKey("help.info.vars");
        printOneLineHelpByKey("help.info.coverage");
        printHeader("Constraint commands");
        printOneLineHelpByKey("help.constraints.load");
        printOneLineHelpByKey("help.constraints.unload");
        printOneLineHelpByKey("help.constraints.loaded");
        printOneLineHelpByKey("help.constraints.flags");
        printHeader("Generator commands");
        printOneLineHelpByKey("help.gen.start");
        printOneLineHelpByKey("help.gen.result");
        printOneLineHelpByKey("help.gen.result.inv");
        printOneLineHelpByKey("help.gen.result.accept");
		printHeader("Plugin commands");
		printOneLineHelpByKey("help.plugins");
    }
}

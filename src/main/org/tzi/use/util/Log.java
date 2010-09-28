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

package org.tzi.use.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

/** 
 * Class Log provides a set of static methods for writing log messages on output
 * streams.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class Log {

    private static PrintStream fOut = System.out;
    private static PrintStream fErr = System.err;
    private static PrintStream fDbg = System.out;

    private static DateFormat fDateFormat = null;

    private static boolean fVerbose = false;
    private static boolean fPrintTime = false;
    private static boolean fTrace = false;
    private static boolean fPrintStackTraces = true;
    private static boolean fDidOutput = false;
    private static boolean showWarnings = true;
    private static boolean fDebug = false;
    
	// utility class
    private Log() {
    }
    
    /**
     * Sets verbose flag. Determines whether verbose messages are printed.
     */
    public static void setVerbose(boolean onOff) {
        fVerbose = onOff;
    }

    public static boolean isVerbose() {
        return fVerbose;
    }

    public static boolean isShowWarnings() {
		return showWarnings;
	}


	public static void setShowWarnings(boolean showWarnings) {
		Log.showWarnings = showWarnings;
	}

    /**
     * Sets print time flag. Determines whether messages are prefixed with the
     * current time.
     */
    public static void setPrintTime(boolean onOff) {
        fPrintTime = onOff;
    }

    public static boolean isPrintingTime() {
        return fPrintTime;
    }

    /**
     * Sets trace flag. Determines whether trace messages are printed.
     */
    public static void setTrace(boolean onOff) {
        fTrace = onOff;
    }

    public static boolean isTracing() {
        return fTrace;
    }

    /**
     * Turns printing of stack traces on/off.
     */
    public static void setPrintStackTrace(boolean onOff) {
        fPrintStackTraces = onOff;
    }

    public static boolean isPrintingStackTraces() {
        return fPrintStackTraces;
    }

    /**
     * Resets flag indicating that output occurred. Flag is set by output
     * methods. After some complex operations clients can ask if some output
     * occurred without bothering about verbosity settings.
     */
    public static void resetOutputFlag() {
        fDidOutput = false;
    }

    public static boolean didOutput() {
        return fDidOutput;
    }

    /**
     * Sets debug flag. Determines whether debug messages are printed.
     */
    public static void setDebug(boolean onOff) {
    	fDebug = onOff;
    }

    public static boolean isDebug() {
    	return fDebug;
    }

    public static PrintStream out() {
        return fOut;
    }

    /**
     * Output routines.
     */
    public static synchronized void println(String s) {
        if (fPrintTime ) {
            if (fDateFormat == null )
                fDateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
            fOut.print(fDateFormat.format(new Date()) + ": ");
        }
        fOut.println(s);
        fDidOutput = true;
    }
    
    public static synchronized void print(String s) {
        fOut.print(s);
        fDidOutput = true;
    }
    
    public static synchronized void println() {
        fOut.println();
        fDidOutput = true;
    }
     
    /**
     * Print messages only if verbose flag is on.
     */
    public static synchronized void verbose(String s) {
        if (fVerbose )
            println(s);
    }

    /**
     * Print messages only if trace flag is on.
     */
    public static synchronized void trace(String msg) {
        if (fTrace )
            println("* " + msg);
    }

    public static synchronized void trace(Object location, String msg) {
        trace(location, msg, false);
    }

    public static synchronized void trace(Object location, String msg, boolean flush) {
        if (fTrace) {
            String className = location.getClass().getName();
            if (className.startsWith("org.tzi.use") )
                className = className.substring("org.tzi.use".length());
            println("* " + className + ": " + msg);
            if (flush) {
                fOut.flush();
            }
        }
    }

    /**
     * Print error messages.
     */
    public static synchronized void error(String s) {
        fErr.println("Error: " + s);
        fDidOutput = true;
    }

    public static synchronized void error(Object location, String msg) {
        String className = location.getClass().getName();
        String err = "error in " + className + ": " + msg;
        
        fErr.println(err);
    }

    public static synchronized void error(Exception e) {
        String className = e.getClass().getName();
        String err = "exception " + className + ": " + e.getMessage();
        
        fErr.println(err);
        
        if (fPrintStackTraces ) {
            e.printStackTrace();
        }
        fDidOutput = true;
    }

    public static synchronized void error(String s, Exception e) {
        String className = e.getClass().getName();
        String err = "exception " + className + ": " + s + " reason: " + e.getMessage();
        
        fErr.println(err);
        
        if ( fPrintStackTraces ) {
            e.printStackTrace();
        }
        
        fDidOutput = true;
    }

	public static synchronized void warn(String string) {
		if (Log.isShowWarnings()) {
			Log.println(string);
		}
	}
		
    /**
     * Print debug messages.
     */
    public static synchronized void debug(String s) {
		if (fDebug) {
		    fDbg.println("Debug: " + s);
		    fDidOutput = true;
		}
	}

}

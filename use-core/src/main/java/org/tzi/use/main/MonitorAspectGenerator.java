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

package org.tzi.use.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Iterator;

import org.tzi.use.config.Options;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MOperation;

/** 
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class MonitorAspectGenerator {
    private static final String INDENT = "    ";
    private static final String INDENT2 = INDENT + INDENT;
//    private static final String INDENT3 = INDENT2 + INDENT;
//    private static final String NL = Options.LINE_SEPARATOR;

    private MModel fModel;
//    private PrintWriter fErr;
    private PrintWriter fOut;

    private static final String FILE_HEADER = 
        "@FILE_HEADER@";
    private static final String INTRODUCTION_PARENTS = 
        "@INTRODUCTION_PARENTS@";
    private static final String INTRODUCTION_USEID_FIELD = 
        "@INTRODUCTION_USEID_FIELD@";
    private static final String INTRODUCTION_USEID_GETTER = 
        "@INTRODUCTION_USEID_GETTER@";
    private static final String INTRODUCTION_USEID_SETTER = 
        "@INTRODUCTION_USEID_SETTER@";
    private static final String POINTCUT_CLASS = 
        "@POINTCUT_CLASS@";
    private static final String POINTCUT_METHOD = 
        "@POINTCUT_METHOD@";
    private static final String POINTCUT_SETTER = 
        "@POINTCUT_SETTER@";
    
    public MonitorAspectGenerator(PrintWriter out, MModel model) {
        fModel = model;
        fOut = out;
    }

    public void write() {
        
        try (BufferedReader in = Files.newBufferedReader(Options.MONITOR_ASPECT_TEMPLATE, Charset.defaultCharset())) {
            while (true) {
                String line = in.readLine();
                if (line == null )
                    break;
                String s = line.trim();
                if (s.startsWith("@") && s.endsWith("@") )
                    replaceLine(s);
                else // copy
                    fOut.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void replaceLine(String line) {
        if (line.equals(FILE_HEADER) )
            fOut.println("// Automatically generated from template file. DO NOT EDIT!");
        else if (line.equals(INTRODUCTION_PARENTS) )
            writeClassList();
        else if (line.equals(INTRODUCTION_USEID_FIELD) )
            writeClassList();
        else if (line.equals(INTRODUCTION_USEID_SETTER) )
            writeClassList();
        else if (line.equals(INTRODUCTION_USEID_GETTER) )
            writeClassList();
        else if (line.equals(POINTCUT_CLASS) )
            writePointcutClass();
        else if (line.equals(POINTCUT_METHOD) )
            writePointcutMethod();
        else if (line.equals(POINTCUT_SETTER) )
            writePointcutSetter();
        else
            throw new RuntimeException("template file contains unknown " +
                                       "replacement token: `" + line + "'.");
    }

    /**
     * Writes pointcuts for operations to be monitored.
     */
    private void writePointcutMethod() {
        writeStartComment();
        foreach(fModel.classes(), new Function() {
                public void apply(Object obj, boolean isFirst, boolean isLast) {
                    foreach(((MClass) obj).operations(), new Function() {
                            public void apply(Object obj, boolean isFirst, 
                                              boolean isLast) {
                                MOperation op = (MOperation) obj;
                                fOut.println(INDENT2 + ( isFirst ? "   " : "|| ")
                                             + "execution(* *.." 
                                             + op.cls().name() + 
                                             "." + op.name() + "(..))");
                            }});
                }});
        writeEndComment();
    }

    /**
     * Writes pointcuts for attributes to be monitored.
     */
    private void writePointcutSetter() {
        writeStartComment();
        foreach(fModel.classes(), new Function() {
                public void apply(Object obj, final boolean isFirst1, boolean isLast) {
                    foreach(((MClass) obj).attributes(), new Function() {
                            public void apply(Object obj, final boolean isFirst2, 
                                              boolean b2) {
                                MAttribute attr = (MAttribute) obj;
                                fOut.println(INDENT2 
                                             + ( isFirst1 && isFirst2 ? "   " : "|| ")
                                             + "set(* *.." 
                                             + attr.owner().name() + 
                                             "." + attr.name() + ")");
                            }});
                }});
        writeEndComment();
    }
    
    // output: c_1 || ... || c_n
    private void writeClassList() {
        writeStartComment();
        foreach(fModel.classes(), new Function() {
                public void apply(Object obj, boolean isFirst, boolean isLast) {
                    fOut.println(INDENT2 + ( isFirst ? "   " : "|| ") 
                                 + "*.." + ((MClass) obj).name());
                }
            });
        writeEndComment();
    }

    // output: for each c: within(c) && ! within(c.*)
    private void writePointcutClass() {
        writeStartComment();
        foreach(fModel.classes(), new Function() {
                public void apply(Object obj, boolean isFirst, boolean isLast) {
                    String s = "*.." + ((MClass) obj).name();
                    // second part is to inhibit monitoring of nested
                    // classes C.* within a class C
                    fOut.println(INDENT2 + ( isFirst ? "   " : "|| ") 
                                 + "(within(" + s + ") && ! within(" + s + ".*))");
                }
            });
        writeEndComment();
    }

    private void writeStartComment() {
        fOut.println("// Start of model-dependent information generated by USE");
    }

    private void writeEndComment() {
        fOut.println("// End of model-dependent information generated by USE");
    }

    private interface Function {
        void apply(Object obj, boolean isFirst, boolean isLast);
    }

    private static void foreach(Collection<?> coll, Function fun) {
        Iterator<?> it = coll.iterator();
        boolean isFirst = true;
        while (it.hasNext() ) {
            Object obj = it.next();
            boolean isLast = ! it.hasNext();
            fun.apply(obj, isFirst, isLast);
            isFirst = false;
        }
    }
}


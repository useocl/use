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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A Report formats data as a table. The complete report is built up
 * in memory before it can be printed. This is necessary to determine
 * column widths. Each column may be left-aligned, centered, or
 * right-aligned.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class Report {

    /*
     * Format specifiers for columns.
     */
    public static final char FMT_LEFT = 'l';
    public static final char FMT_RIGHT = 'r';
    public static final char FMT_CENTER = 'c';

    private ArrayList<Object> fRows;    // (String[], String, or Character)
    private String[] fCurrentRow;
    private int fColumns;
    private int fCurrentColumn;
    private String[] fFiller;
    private char[] fColumnFormat;

    /**
     * Creates a new report with the specified number of columns. Each
     * column is left-aligned. A single blank character separates
     * columns. 
     */
    public Report(int columns) {
        this(columns, null);
    }

    /**
     * Creates a new report with the specified number of columns. The
     * format string defines the layout of each row when the report is
     * printed. The position of a column in the format string is
     * specified by a <code>$</code> sign followed by either a
     * <code>l</code> character for a left-aligned column, a
     * <code>c</code> character for a centered column, or a
     * <code>r</code> character for a right-aligned column. All other
     * characters are copied verbatim into the output. If there are
     * more format specifiers than columns, the excess specifiers are
     * ignored. If there are fewer format specifiers than columns,
     * all columns with no format specifier are left-aligned.
     *
     * Example:
     * <pre>
     * Report r = new Report(4, "[ $l = $r, $r, $c ]");
     * </pre>
     * A possible result is:
     * <pre>
     * [  foo   =      3,          1.2, false ]
     * [ foobar = 453453, -1.245345345, true  ]
     * [  line  =    555,        999.0, true  ]
     * </pre>
     *
     * @throws IllegalArgumentException the fmt string is not well-formed.  
     */
    public Report(int columns, String fmt) {
        fRows = new ArrayList<Object>();
        fColumns = columns;
        fFiller = new String[columns + 1];
        Arrays.fill(fFiller, " ");
        fColumnFormat = new char[columns];
        Arrays.fill(fColumnFormat, FMT_LEFT);

        // parse format string
        if (fmt != null && fmt.length() > 0 ) {
            char[] f = fmt.toCharArray();
            int col = 0;
            int fillIndex = 0;
            StringBuffer fill = new StringBuffer();
            for (int i = 0; i < f.length; i++) {
                if (f[i] != '$' )
                    fill.append(f[i]);
                else {
                    fFiller[fillIndex++] = fill.toString();
                    fill.setLength(0);
                    i++;
                    if (i == f.length )
                        throw new IllegalArgumentException("format string ends with $");
                    if (col < columns ) {
                        switch ( f[i] ) {
                        case FMT_LEFT: case FMT_CENTER: case FMT_RIGHT:
                            fColumnFormat[col++] = f[i];
                            break;
                        default:
                            throw new IllegalArgumentException("format character: " + 
                                                               f[i]);
                        }
                    }
                }
            }
            fFiller[col++] = fill.toString();
        }
    }

    /**
     * Adds a new row to this report.
     */
    public void addRow() {
        fCurrentRow = new String[fColumns];
        fCurrentColumn = 0;
        fRows.add(fCurrentRow);
    }

    /**
     * Adds a new ruler to this report. The ruler is copied verbatim
     * into the output.
     */
    public void addRuler(String ruler) {
        fCurrentRow = null;
        fCurrentColumn = 0;
        fRows.add(ruler);
    }

    /**
     * Adds a new ruler to this report. The ruler is produced by
     * repeating the specified character to the width of the report.  
     */
    public void addRuler(char rulerChar) {
        fCurrentRow = null;
        fCurrentColumn = 0;
        fRows.add(Character.valueOf(rulerChar));
    }

    /**
     * Adds a new cell to the current row. The toString method is
     * called for the cell argument.
     */
    public void addCell(Object cell) {
        fCurrentRow[fCurrentColumn++] = cell.toString();
    }

    /**
     * Adds a new cell to the current row.
     */
    public void addCell(String cell) {
        fCurrentRow[fCurrentColumn++] = cell;
    }

    /**
     * Prints the final report to the specified stream.
     */
    public void printOn(PrintStream out) {
        printOn(new PrintWriter(out));
    }

    /**
     * Prints the final report to the specified writer.
     */
    public void printOn(PrintWriter out) {
        // determine column widths
        int[] widths = new int[fColumns];
        Iterator<Object> rowIter = fRows.iterator();
        while (rowIter.hasNext() ) {
            Object r = rowIter.next();
            if (r instanceof String[] ) {
                String[] row = (String[]) r;
                for (int i = 0; i < fColumns; i++)
                    widths[i] = Math.max(widths[i], row[i].length());
            }
        }

        // determine total width
        int width = 0;
        for (int i = 0; i < fColumns; i++)
            width += widths[i];
        for (int i = 0; i <= fColumns; i++)
            width += fFiller[i].length();

        // print rows
        rowIter = fRows.iterator();
        while (rowIter.hasNext() ) {
            Object r = rowIter.next();
            if (r instanceof String[] ) {
                String[] row = (String[]) r;
                out.print(fFiller[0]);
                for (int i = 0; i < fColumns; i++) {
                    switch ( fColumnFormat[i] ) {
                    case FMT_LEFT: 
                        out.print(StringUtil.pad(row[i], widths[i]));
                        break;
                    case FMT_CENTER: 
                        out.print(StringUtil.center(row[i], widths[i]));
                        break;
                    case FMT_RIGHT:
                        out.print(StringUtil.leftPad(row[i], widths[i]));
                        break;
                    default:
                        // This cannot happen ;-)
                    }
                    out.print(fFiller[i + 1]);
                }
                out.println();
            } else if (r instanceof String ) {
                out.println(r);
            } else if (r instanceof Character ) {
                char[] ruler = new char[width];
                Arrays.fill(ruler, ((Character) r).charValue());
                out.println(ruler);
            }
        }
        out.flush();
    }
}

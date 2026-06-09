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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link UserOutput} abstraction (issue #60).
 *
 * @author Claude
 */
public class UserOutputTest {

    private static final String NL = System.lineSeparator();

    @Test
    public void collectingCapturesAllOutputInOrder() {
        CollectingUserOutput out = UserOutput.createCollectingOutput();
        out.printlnNormal("hello");
        out.printlnError("oops");
        assertEquals("hello" + NL + "oops" + NL, out.getOutput());
    }

    @Test
    public void collectingSeparatesOutputByLevel() {
        CollectingUserOutput out = new CollectingUserOutput();
        out.printlnNormal("n");
        out.printlnWarn("w");
        out.printlnError("e");
        assertEquals("n" + NL, out.getOutput(OutputLevel.NORMAL));
        assertEquals("w" + NL, out.getOutput(OutputLevel.WARN));
        assertEquals("e" + NL, out.getOutput(OutputLevel.ERROR));
        assertEquals("", out.getOutput(OutputLevel.INFO));
    }

    @Test
    public void convenienceMethodsUseTheMatchingLevel() {
        CollectingUserOutput out = new CollectingUserOutput();
        out.printInfo("i");
        out.printTrace("t");
        assertEquals("i", out.getOutput(OutputLevel.INFO));
        assertEquals("t", out.getOutput(OutputLevel.TRACE));
    }

    @Test
    public void streamRoutesNormalToOutAndWarningsErrorsToErr() {
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        ByteArrayOutputStream errBuf = new ByteArrayOutputStream();
        UserOutput out = new StreamUserOutput(
                new PrintStream(outBuf, true, StandardCharsets.UTF_8),
                new PrintStream(errBuf, true, StandardCharsets.UTF_8));

        out.printNormal("N");
        out.printInfo("I");
        out.printTrace("T");
        out.printWarn("W");
        out.printError("E");

        assertEquals("NIT", outBuf.toString(StandardCharsets.UTF_8));
        assertEquals("WE", errBuf.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void printlnAppendsLineSeparator() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        UserOutput out = new StreamUserOutput(new PrintStream(buf, true, StandardCharsets.UTF_8));
        out.printlnNormal("x");
        assertEquals("x" + NL, buf.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void silentOutputDiscardsEverythingWithoutError() {
        UserOutput out = UserOutput.getSilentOutput();
        assertDoesNotThrow(() -> {
            out.printlnNormal("ignored");
            out.printlnError("ignored");
        });
    }

    @Test
    public void factoriesReturnUsableInstances() {
        assertNotNull(UserOutput.getDefaultOutput());
        assertNotNull(UserOutput.getSilentOutput());
        assertNotNull(UserOutput.createCollectingOutput());
    }
}

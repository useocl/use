package org.tzi.use.output.printwriters;

import java.io.Writer;

/**
 * This class suppresses any color information by ignoring them.
 *
 */
public class NonColoredPrintWriter extends ColoredPrintWriter {

    public NonColoredPrintWriter(Writer out) {
        super(out);
    }

    @Override
    protected void printHighlightEnd() {
        // We do nothing here
    }

    @Override
    protected void printHighlightStart() {
        // We do nothing here
    }

    @Override
    protected void printColorStart() {
        // We do nothing here
    }

    @Override
    protected void printColorEnd() {
        // We do nothing here
    }
}

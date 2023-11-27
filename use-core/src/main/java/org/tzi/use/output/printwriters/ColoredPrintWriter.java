package org.tzi.use.output.printwriters;

import org.tzi.use.output.HighlightAs;
import org.tzi.use.output.OutputColors;

import java.io.PrintWriter;
import java.io.Writer;

public class ColoredPrintWriter extends PrintWriter {
    private HighlightAs highlight = HighlightAs.NORMAL;

    private OutputColors color = OutputColors.TEXT_DEFAULT;

    public ColoredPrintWriter(Writer out) {
        super(out, true);
    }

    public HighlightAs getHighlight() {
        return highlight;
    }

    public void setHighlight(HighlightAs highlight) {
        this.highlight = highlight;
    }

    public OutputColors getColor() {
        return color;
    }

    public void setColor(OutputColors color) {
        this.color = color;
    }

    public void resetColor() {
        this.color = OutputColors.TEXT_DEFAULT;
    }

    @Override
    public void write(int c) {
        printColorStart();
        printHighlightStart();
        super.write(c);
        printHighlightEnd();
        printColorEnd();
    }

    protected void printHighlightEnd() {
        super.write(OutputColors.TEXT_RESET, 0, OutputColors.TEXT_RESET.length());
    }

    protected void printHighlightStart() {
        super.write(highlight.getEscapeSequence(), 0, highlight.getEscapeSequence().length());
    }

    protected void printColorStart() {
        super.write(color.getColorText(), 0, color.getColorText().length());
    }

    protected void printColorEnd() {
        super.write(OutputColors.TEXT_RESET, 0, color.getColorText().length());
    }

    @Override
    public final void write(char[] buf, int off, int len) {
        printColorStart();
        printHighlightStart();
        super.write(buf, off, len);
        printHighlightEnd();
    }

    @Override
    public final void write(String s, int off, int len) {
        printColorStart();
        printHighlightStart();
        super.write(s, off, len);
        printHighlightEnd();
    }
}

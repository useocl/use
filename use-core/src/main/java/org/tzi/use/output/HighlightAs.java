package org.tzi.use.output;

public enum HighlightAs {
    NORMAL(""),
    NAME("\u001B[4m"),
    FILE("\u001B[4m"),
    COMMAND("\u001B[4m"),
    OPTION("\u001B[4m");

    private final String escapeSequence;

    HighlightAs(String escapeSequence) {
        this.escapeSequence = escapeSequence;
    }

    public String getEscapeSequence() {
        return escapeSequence;
    }
}

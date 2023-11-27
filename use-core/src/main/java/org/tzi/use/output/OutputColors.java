package org.tzi.use.output;

public enum OutputColors {
    //Set of strings with the preset ANSI color codes

    TEXT_DEFAULT(""),
    TEXT_BLACK("\u001B[30m"),
    TEXT_RED("\u001B[31m"),
    TEXT_GREEN("\u001B[32m"),
    TEXT_YELLOW("\u001B[33m"),
    TEXT_BLUE("\u001B[34m"),
    TEXT_PURPLE("\u001B[35m"),
    TEXT_CYAN("\u001B[36m"),
    TEXT_WHITE("\u001B[37m"),

    TEXT_BRIGHT_BLACK("\u001B[90m"),
    TEXT_BRIGHT_RED("\u001B[91m"),
    TEXT_BRIGHT_GREEN("\u001B[92m"),
    TEXT_BRIGHT_YELLOW("\u001B[93m"),
    TEXT_BRIGHT_BLUE("\u001B[94m"),
    TEXT_BRIGHT_PURPLE("\u001B[95m"),
    TEXT_BRIGHT_CYAN("\u001B[96m"),
    TEXT_BRIGHT_WHITE("\u001B[97m"),

    TEXT_BG_BLACK("\u001B[40m"),
    TEXT_BG_RED("\u001B[41m"),
    TEXT_BG_GREEN("\u001B[42m"),
    TEXT_BG_YELLOW("\u001B[43m"),
    TEXT_BG_BLUE("\u001B[44m"),
    TEXT_BG_PURPLE("\u001B[45m"),
    TEXT_BG_CYAN("\u001B[46m"),
    TEXT_BG_WHITE("\u001B[47m"),

    TEXT_BRIGHT_BG_BLACK("\u001B[100m"),
    TEXT_BRIGHT_BG_RED("\u001B[101m"),
    TEXT_BRIGHT_BG_GREEN("\u001B[102m"),
    TEXT_BRIGHT_BG_YELLOW("\u001B[103m"),
    TEXT_BRIGHT_BG_BLUE("\u001B[104m"),
    TEXT_BRIGHT_BG_PURPLE("\u001B[105m"),
    TEXT_BRIGHT_BG_CYAN("\u001B[106m"),
    TEXT_BRIGHT_BG_WHITE("\u001B[107m");

    private final String colorText;

    /*
     Text to reset the colour of the text.
     Since this is not a valid colour, but related we place it here.
     */
    public static final String TEXT_RESET = "\u001B[0m";

    OutputColors(String colorText) {
        this.colorText = colorText;
    }

    public String getColorText() {
        return this.colorText;
    }
}

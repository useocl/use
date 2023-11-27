package org.tzi.use.output;

public abstract class UserOutputBase implements UserOutput {

    @Override
    public UserOutput print(String s) {
        return print(OutputLevel.NORMAL, s);
    }

    @Override
    public UserOutput printHighlighted(String s, HighlightAs h) {
        return printHighlighted(OutputLevel.NORMAL, s, h);
    }

    @Override
    public UserOutput println(String s) {
        return println(OutputLevel.NORMAL, s);
    }

    @Override
    public UserOutput println() {
        return println(OutputLevel.NORMAL, "");
    }

    @Override
    public UserOutput printError(String s) {
        return print(OutputLevel.ERROR, s);
    }

    @Override
    public UserOutput printHighlightedError(String s, HighlightAs h) {
        return printHighlighted(OutputLevel.ERROR, s, h);
    }

    @Override
    public UserOutput printlnError(String s) {
        return println(OutputLevel.ERROR, s);
    }

    @Override
    public UserOutput printlnError() {
        return println(OutputLevel.ERROR, "");
    }

    @Override
    public UserOutput printWarn(String s) {
        return print(OutputLevel.WARNING, s);
    }

    @Override
    public UserOutput printHighlightedWarn(String s, HighlightAs h) {
        return printHighlighted(OutputLevel.WARNING, s, h);
    }

    @Override
    public UserOutput printlnWarn(String s) {
        return println(OutputLevel.WARNING, s);
    }

    @Override
    public UserOutput printlnWarn() {
        return println(OutputLevel.WARNING, "");
    }

    @Override
    public UserOutput printVerbose(String s) {
        return print(OutputLevel.TRACE, s);
    }

    @Override
    public UserOutput printHighlightedVerbose(String s, HighlightAs h) {
        return printHighlighted(OutputLevel.TRACE, s, h);
    }

    @Override
    public UserOutput printlnVerbose(String s) {
        return println(OutputLevel.TRACE, s);
    }

    @Override
    public UserOutput printlnVerbose() {
        return println(OutputLevel.TRACE, "");
    }

    @Override
    public UserOutput printTrace(String s) {
        return print(OutputLevel.TRACE, s);
    }

    @Override
    public UserOutput printHighlightedTrace(String s, HighlightAs h) {
        return printHighlighted(OutputLevel.TRACE, s, h);
    }

    @Override
    public UserOutput printlnTrace(String s) {
        return println(OutputLevel.TRACE, s);
    }

    @Override
    public UserOutput printlnTrace() {
        return println(OutputLevel.TRACE, "");
    }
}

package org.tzi.use.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This UserOutput does not output anything.
 * It is used to suppress all output in special situations.
 */
public class VoidUserOutput implements UserOutput {

    private final static VoidUserOutput singleton;

    static {
        singleton = new VoidUserOutput();
    }

    public static UserOutput getInstance() {
        return VoidUserOutput.singleton;
    }

    private VoidUserOutput() { }

    @Override
    public void setOutputLevel(UserOutputType outputType, OutputLevel level) {
        // Nothing to set.
    }

    @Override
    public void setOutputLevel(OutputLevel level) {
        // Nothing to set.
    }

    @Override
    public OutputLevel getOutputLevel() {
        return OutputLevel.NONE;
    }

    @Override
    public UserOutput print(String s) {
        return this;
    }

    @Override
    public UserOutput printHighlighted(String s, HighlightAs h) {
        return this;
    }

    @Override
    public UserOutput println(String s) {
        return this;
    }

    @Override
    public UserOutput println() {
        return this;
    }

    @Override
    public UserOutput printError(String s) {
        return this;
    }

    @Override
    public UserOutput printHighlightedError(String s, HighlightAs h) {
        return this;
    }

    @Override
    public UserOutput printlnError(String s) {
        return this;
    }

    @Override
    public UserOutput printlnError() {
        return this;
    }

    @Override
    public UserOutput printWarn(String s) {
        return this;
    }

    @Override
    public UserOutput printHighlightedWarn(String s, HighlightAs h) {
        return this;
    }

    @Override
    public UserOutput printlnWarn(String s) {
        return this;
    }

    @Override
    public UserOutput printlnWarn() {
        return this;
    }

    @Override
    public UserOutput printVerbose(String s) {
        return this;
    }

    @Override
    public UserOutput printHighlightedVerbose(String s, HighlightAs h) {
        return this;
    }

    @Override
    public UserOutput printlnVerbose(String s) {
        return this;
    }

    @Override
    public UserOutput printlnVerbose() {
        return this;
    }

    @Override
    public UserOutput printTrace(String s) {
        return this;
    }

    @Override
    public UserOutput printHighlightedTrace(String s, HighlightAs h) {
        return this;
    }

    @Override
    public UserOutput printlnTrace(String s) {
        return this;
    }

    @Override
    public UserOutput printlnTrace() {
        return this;
    }

    @Override
    public UserOutput print(OutputLevel level, String s) {
        return this;
    }

    @Override
    public UserOutput println(OutputLevel level, String s) {
        return this;
    }

    @Override
    public UserOutput printHighlighted(OutputLevel level, String s, HighlightAs h) {
        return this;
    }

    @Override
    public UserOutput printlnHighlighted(OutputLevel level, String s, HighlightAs h) {
        return this;
    }

    @Override
    public PrintStream getPrintStream(OutputLevel level) {
        return new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // We do nothing..
            }
        });
    }

    public UserOutput createCopy() {
        try {
            return (UserOutput)this.clone();
        } catch (CloneNotSupportedException e) {
            // Currently there is no situation where
            // cloning of a UserOutput is not suppoerted.
            return this;
        }
    };
}

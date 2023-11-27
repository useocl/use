package org.tzi.use.output;

import org.tzi.use.config.Options;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Simple <code>UserOutput</code> that stores the output
 * in an internal buffer.
 * No output to the user is provided.
 * Example usage is to capture multiple compile errors and throw a single exception.
 */
public class InternalUserOutput extends UserOutputBase {

    private OutputLevel outputLevel;

    private final StringBuilder output = new StringBuilder();

    /**
     * Creates a new instance with an OutputLevel as configured
     * by the startup parameters of USE.
     * @see org.tzi.use.config.Options
     */
    public InternalUserOutput() {
        Options.configureOutput(this);
    }
    public InternalUserOutput(OutputLevel level) {
        this.outputLevel = level;
    }

    @Override
    public void setOutputLevel(UserOutputType outputType, OutputLevel level) {
        this.outputLevel = level;
    }

    @Override
    public void setOutputLevel(OutputLevel level) {
        this.outputLevel = level;
    }

    @Override
    public OutputLevel getOutputLevel() {
        return this.outputLevel;
    }

    @Override
    public UserOutput print(OutputLevel level, String s) {
        if (!level.isMoreDetailedThan(this.outputLevel)) {
            this.output.append(s);
        }
        return this;
    }

    @Override
    public UserOutput println(OutputLevel level, String s) {
        if (!level.isMoreDetailedThan(this.outputLevel)) {
            this.output.append(s);
            this.output.append(System.lineSeparator());
        }
        return this;
    }

    @Override
    public UserOutput printHighlighted(OutputLevel level, String s, HighlightAs h) {
        // We ignore highlighting
        return print(level, s);
    }

    @Override
    public UserOutput printlnHighlighted(OutputLevel level, String s, HighlightAs h) {
        // We ignore highlighting
        return println(level, s);
    }

    @Override
    public PrintStream getPrintStream(OutputLevel level) {
        return new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                output.append((char)b);
            }
        }, true, StandardCharsets.UTF_8);
    }

    public String getOutput() {
        return this.output.toString();
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

    public boolean hasOutput() {
        return !this.output.isEmpty();
    }
}

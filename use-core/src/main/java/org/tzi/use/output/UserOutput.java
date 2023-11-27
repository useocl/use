package org.tzi.use.output;

import java.io.PrintStream;

public interface UserOutput extends Cloneable {
    void setOutputLevel(UserOutputType outputType, OutputLevel level);

    void setOutputLevel(OutputLevel level);

    OutputLevel getOutputLevel();

    UserOutput print(String s);
    UserOutput printHighlighted(String s, HighlightAs h);
    UserOutput println(String s);
    UserOutput println();
    UserOutput printError(String s);
    UserOutput printHighlightedError(String s, HighlightAs h);
    UserOutput printlnError(String s);
    UserOutput printlnError();
    UserOutput printWarn(String s);
    UserOutput printHighlightedWarn(String s, HighlightAs h);
    UserOutput printlnWarn(String s);
    UserOutput printlnWarn();
    UserOutput printVerbose(String s);
    UserOutput printHighlightedVerbose(String s, HighlightAs h);
    UserOutput printlnVerbose(String s);
    UserOutput printlnVerbose();
    UserOutput printTrace(String s);
    UserOutput printHighlightedTrace(String s, HighlightAs h);
    UserOutput printlnTrace(String s);
    UserOutput printlnTrace();
    UserOutput print(OutputLevel level, String s);
    UserOutput println(OutputLevel level, String s);
    UserOutput printHighlighted(OutputLevel level, String s, HighlightAs h);
    UserOutput printlnHighlighted(OutputLevel level, String s, HighlightAs h);
    PrintStream getPrintStream(OutputLevel level);

    /**
     * Creates a copy of the current <code>UserOutput</code>.
     * Can be used to modify the existing output for special purposes
     * without the need to restore the original state.
     * @return A shallow copy of the UserOutput.
     */
    UserOutput createCopy();
}

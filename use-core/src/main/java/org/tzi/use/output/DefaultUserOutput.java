package org.tzi.use.output;

import org.tzi.use.output.printwriters.ColoredPrintWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

public class DefaultUserOutput extends UserOutputBase {

    private final Map<OutputLevel, List<ColoredPrintWriter>> output;

    private final Map<OutputLevel, List<ColoredPrintWriter>> deactivatedOutput;

    private final Map<OutputLevel, OutputColors> outputColors;

    private OutputLevel outputlevel = OutputLevel.NORMAL;

    private DefaultUserOutput() {
        this.output = new EnumMap<>(OutputLevel.class);
        this.deactivatedOutput = new EnumMap<>(OutputLevel.class);

        for (OutputLevel l : OutputLevel.values()) {
            this.output.put(l, new ArrayList<>());
            this.deactivatedOutput.put(l, new ArrayList<>());
        }

        this.outputColors = new EnumMap<>(OutputLevel.class);

        this.outputColors.put(OutputLevel.TRACE, OutputColors.TEXT_BLUE);
        this.outputColors.put(OutputLevel.INFO, OutputColors.TEXT_GREEN);
        this.outputColors.put(OutputLevel.NORMAL, OutputColors.TEXT_DEFAULT);
        this.outputColors.put(OutputLevel.WARNING, OutputColors.TEXT_YELLOW);
        this.outputColors.put(OutputLevel.ERROR, OutputColors.TEXT_RED);
    }

    public static DefaultUserOutput createSystemOutOutput() {
        DefaultUserOutput newInstance = DefaultUserOutput.createEmptyOutput();

        List<ColoredPrintWriter> defaultWriter = new ArrayList<>(3);

        defaultWriter.add(new ColoredPrintWriter(new PrintWriter(System.out)));

        for (OutputLevel l : OutputLevel.values()) {
            newInstance.output.put(l, new ArrayList<>(defaultWriter));
        }

        return newInstance;
    }

    /**
     * Creates a new DefaultUserOutput instance with no configured
     * output writers.
     *
     * @return A new instance without any writer
     */
    public static DefaultUserOutput createEmptyOutput() {
        return new DefaultUserOutput();
    }

    @Override
    public void setOutputLevel(UserOutputType outputType, OutputLevel level) {
        boolean activateOutput = level.isMoreDetailedThan(this.outputlevel);

        if (activateOutput) {
            Arrays.stream(OutputLevel.values())
                    .filter(l -> level.isMoreDetailedThan(this.outputlevel))
                    .forEach(l -> {
                        this.output.get(l).addAll(this.deactivatedOutput.get(l));
                        this.deactivatedOutput.get(l).clear();
                    });
        } else {
            Arrays.stream(OutputLevel.values())
                    .filter(l -> l.isMoreDetailedThan(level))
                    .forEach(l -> {
                        this.deactivatedOutput.get(l).addAll(this.output.get(l));
                        this.output.get(l).clear();
                    });
        }
    }

    @Override
    public void setOutputLevel(OutputLevel level) {
        Arrays.stream(UserOutputType.values()).forEach(t -> setOutputLevel(t, level));

        this.outputlevel = level;
    }

    @Override
    public OutputLevel getOutputLevel() {
        return this.outputlevel;
    }

    public void registerWriter(OutputLevel startingLevel, ColoredPrintWriter writer) {
        OutputLevel.levelsFrom(startingLevel).forEach( l -> output.get(l).add(writer));
    }

    @Override
    public UserOutput print(OutputLevel level, String s) {
        for (ColoredPrintWriter output : this.output.get(level)) {
            printInternal(output, this.outputColors.get(level), s, HighlightAs.NORMAL);
        }
        return this;
    }

    @Override
    public UserOutput println(OutputLevel level, String s) {
        for (ColoredPrintWriter output : this.output.get(level)) {
            printlnInternal(output, this.outputColors.get(level), s, HighlightAs.NORMAL);
        }
        return this;
    }

    @Override
    public UserOutput printHighlighted(OutputLevel level, String s, HighlightAs h) {
        for (ColoredPrintWriter output : this.output.get(level)) {
            printInternal(output, this.outputColors.get(level), s, h);
        }
        return this;
    }

    @Override
    public UserOutput printlnHighlighted(OutputLevel level, String s, HighlightAs h) {
        for (ColoredPrintWriter output : this.output.get(level)) {
            printlnInternal(output, this.outputColors.get(level), s, h);
        }
        return this;
    }

    @Override
    public PrintStream getPrintStream(OutputLevel level) {
        return new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                for (ColoredPrintWriter output : output.get(level)) {
                    output.setColor(outputColors.get(level));
                    output.write(b);
                }
            }

            @Override
            public void flush() throws IOException {
                for (ColoredPrintWriter output : output.get(level)) {
                    output.flush();
                }
            }
        }, true);
    }

    private void printInternal(ColoredPrintWriter out, OutputColors color, String text, HighlightAs h) {
        out.setColor(color);
        out.setHighlight(h);
        out.print(text);
    }

    private void printlnInternal(ColoredPrintWriter out, OutputColors color, String text, HighlightAs h) {
        out.setColor(color);
        out.setHighlight(h);
        out.print(text);
        out.println();
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

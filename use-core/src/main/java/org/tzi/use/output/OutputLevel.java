package org.tzi.use.output;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * The different "levels" of output
 * used inside the USE system.
 * It is called levels, because it builds a hierarchie
 * starting with the most important <code>output</code> and <code>error</code> to
 * the least important level <code>trace</code>.
 */
public enum OutputLevel implements Comparable<OutputLevel> {

    /**
     * Detailed information that is only needed in
     * special cases. E.g., debugging OCL expressions.
     */
    TRACE,
    /**
     * Nice information but not really important, i.e., additional statistics.
     */
    INFO,
    /**
     * Normal output to the user.
     */
    NORMAL,
    /**
     * Useful information about issues
     * that might cause trouble if ignored.
     */
    WARNING,
    /**
     * Errors displayed to the user, i.e.,
     * unrecoverable issues with input.
     */
    ERROR,

    /**
     * Used to suppress all output
     */
    NONE;

    public static Stream<OutputLevel> levelsFrom(OutputLevel startingLevel) {
        return Arrays.stream(OutputLevel.values()).filter( outputLevel ->  outputLevel.covers(startingLevel) );
    }

    public boolean isMoreDetailedThan(OutputLevel level) {
        return this.ordinal() < level.ordinal();
    }

    /**
     * Returns <code>true</code>, if this level is the same ore
     * more detailed than the given one.
     * @param level The level to query for
     * @return <code>true</code>, if <code>level</code> is same ore more detailed than this level.
     */
    public boolean covers(OutputLevel level) {
        return this.ordinal() <= level.ordinal();
    }
}

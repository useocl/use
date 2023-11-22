package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents a specific autocompletion parser result type associated with the USE command "step".
 * It extends {@link ResultTypeUseCommands}.
 */
public class ResultTypeUseStep extends ResultTypeUseCommands{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

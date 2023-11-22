package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents a specific autocompletion parser result type associated with the USE command "check".
 * It is a subtype of {@link ResultTypeUseCommands}.
 */
public class ResultTypeUseCheck extends ResultTypeUseCommands{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents an autocompletion parser result type associated with the USE command "info" when the class parameter was already given.
 * It is a subtype of {@link ResultTypeUseInfo}.
 */
public class ResultTypeUseInfoClass  implements ResultTypeUseInfo{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

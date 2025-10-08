package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents an autocompletion parser result type associated with the USE command "create" when there was no association name already given.
 * It is a subtype of {@link ResultTypeUseCreate}.
 */
public class ResultTypeUseCreateDefault implements ResultTypeUseCreate{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

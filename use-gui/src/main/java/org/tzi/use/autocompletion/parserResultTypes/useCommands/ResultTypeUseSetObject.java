package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents an autocompletion parser result type associated with the USE command "set" when the object name was already given.
 * It is a subtype of {@link ResultTypeUseSet}.
 */
public class ResultTypeUseSetObject extends ResultTypeUseSet{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

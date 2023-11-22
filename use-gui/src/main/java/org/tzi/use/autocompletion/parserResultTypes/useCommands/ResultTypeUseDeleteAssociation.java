package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents an autocompletion parser result type associated with the USE command "delete" when object names were already given.
 * It is a subtype of {@link ResultTypeUseDelete}.
 */
public class ResultTypeUseDeleteAssociation extends ResultTypeUseDelete {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

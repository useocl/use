package org.tzi.use.autocompletion.parserResultTypes.useCommands;

/**
 * Represents an autocompletion parser result type associated with the USE command "insert" when objects names were already given.
 * It is a subtype of {@link ResultTypeUseInsert}.
 */
public class ResultTypeUseInsertAssociation extends ResultTypeUseInsert{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}

package org.tzi.use.util.input;

/**
 * Represents an Operation within the context of the autocompletion with a name and a parameter.
 * <p>
 * This class is specifically used to describe overloaded operations to the user and is not used to define
 * operations within USE. It is necessary because {@link org.tzi.use.util.OperationType} saves parameters in a way
 * that is not gui-friendly and does not help the user to understand every possible argument an operation can receive.
 */
public class AutoCompletionOperation {
    /**
     * The name of the operation.
     */
    public String name;

    /**
     * The parameters of the operation describing which type of expression is allowed (e.g. iterator, boolean expressions, ...)
     */
    public String param;
}

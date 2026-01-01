package org.tzi.use.uml.api;

/**
 * API-level exception that represents an invalid expression in invariant
 * handling. This class centralizes the exception type that mm expects.
 *
 * The concrete OCL exception `org.tzi.use.uml.ocl.expr.ExpInvalidException`
 * may extend this class so that ocl can still throw its own concrete type
 * while mm only depends on this API abstraction.
 */
public class InvariantExpressionException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvariantExpressionException() {
        super();
    }

    public InvariantExpressionException(String message) {
        super(message);
    }

    public InvariantExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvariantExpressionException(Throwable cause) {
        super(cause);
    }
}


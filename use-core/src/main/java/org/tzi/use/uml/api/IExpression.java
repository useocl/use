package org.tzi.use.uml.api;

/**
 * Abstrakte Sicht auf eine OCL-Expression, die von High-Level-Layern
 * (mm, sys) genutzt werden kann, ohne direkt vom OCL-Parser abzuhängen.
 */
public interface IExpression {

    /**
     * @return {@code true}, wenn der Ergebnistyp dieser Expression boolesch ist.
     */
    boolean isBooleanType();

    /**
     * Stellt sicher, dass es sich um eine boolesche Expression handelt.
     * Implementierungen sollen eine Runtime-Exception werfen,
     * wenn dies nicht der Fall ist.
     */
    void assertBooleanType();

    /**
     * @return {@code true}, wenn diese Expression (oder eine Unterexpression)
     * auf einen Pre-State ("@pre") zugreift.
     */
    boolean requiresPreState();

    /**
     * String-Repräsentation der Expression.
     */
    String asString();

    /**
     * Führt einen API-basierten Ausdrucksvisitor auf dieser Expression aus.
     */
    void processWithVisitor(IExpressionVisitor visitor);
}

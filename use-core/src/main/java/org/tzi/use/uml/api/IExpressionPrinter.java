package org.tzi.use.uml.api;

/**
 * API-level printer for expressions. High-level code (mm) depends only on this
 * interface to obtain concrete-syntax representation of expressions.
 */
public interface IExpressionPrinter {
    /**
     * Return a concrete-syntax representation of the given expression.
     */
    String toConcreteSyntax(IExpression expr);
}


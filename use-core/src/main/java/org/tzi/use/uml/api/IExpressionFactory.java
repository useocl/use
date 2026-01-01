package org.tzi.use.uml.api;

/**
 * API-level factory to create generic IExpression instances used by mm.
 */
public interface IExpressionFactory {
    /** Create an undefined expression instance */
    IExpression createUndefined();
}


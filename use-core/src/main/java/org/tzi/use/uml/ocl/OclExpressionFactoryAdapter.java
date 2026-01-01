package org.tzi.use.uml.ocl;

import org.tzi.use.uml.api.IExpressionFactory;
import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.ocl.expr.ExpUndefined;

/** Adapter that provides IExpressionFactory backed by OCL expression types. */
public class OclExpressionFactoryAdapter implements IExpressionFactory {
    @Override
    public IExpression createUndefined() {
        return new ExpUndefined();
    }
}


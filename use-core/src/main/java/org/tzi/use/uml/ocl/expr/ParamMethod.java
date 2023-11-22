package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.ocl.type.Type;

/**
 * The ParamMethod interface is a functional interface.
 */
public interface ParamMethod {
    /**
     * The SAM implemented when a new operation is defined
     * <p>
     * This method is called in the context of {@link org.tzi.use.uml.ocl.expr.operations.OpGeneric OpGeneric}'s matches method.
     *
     * @implSpec
     * The implementation typically checks whether the parameter is a subtype of a certain type.
     *
     * @param param The parameter on which the method is executed.
     * @return true if the method execution is successful; false otherwise.
     */
    boolean method(Type param);
}

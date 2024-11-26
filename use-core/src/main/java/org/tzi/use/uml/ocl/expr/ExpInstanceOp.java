package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Abstract class for operations and constructors defined by a classifier.
 *
 * @author Stefan Schoon
 */
public abstract class ExpInstanceOp extends Expression {
    protected final MOperation fOp;

    /**
     * The arguments, first one is "receiver" object
     */
    protected final Expression[] fArgs;

    protected final MClassifier fClassifier;

    public ExpInstanceOp(MOperation op, Expression[] args) {
        super(op.resultType());
        fOp = op;
        fArgs = args;
        fClassifier = op.cls();
    }

    public abstract Value eval(EvalContext ctx);

    public MOperation getOperation() {
        return fOp;
    }

    /**
     * All arguments of the expression.
     * Index 0 is the receiver object (self)
     */
    public Expression[] getArguments() {
        return fArgs;
    }

    @Override
    public void processWithVisitor(ExpressionVisitor visitor) {
        visitor.visitInstanceOp(this);
    }

    @Override
    protected boolean childExpressionRequiresPreState() {
        for (Expression e : fArgs) {
            if (e.requiresPreState()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String name() {
        return fOp.name();
    }
}

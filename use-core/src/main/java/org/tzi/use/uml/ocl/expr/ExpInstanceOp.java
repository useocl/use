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

    public ExpInstanceOp(MOperation op, Expression[] args) throws ExpInvalidException {
        super(op.resultType());
        fOp = op;
        fArgs = args;
        fClassifier = op.cls();

        if (!(args[0].type().isTypeOfClass() || args[0].type().isTypeOfDataType()))
            throw new ExpInvalidException(
                    "Target expression of object operation must have " +
                            "object type, found `" + args[0].type() + "'.");

        // check for matching arguments
        VarDeclList params = fOp.paramList();
        if (params.size() != (args.length - 1) )
            throw new ExpInvalidException(
                    "Number of arguments does not match declaration of operation `" +
                            fOp.name() + "'. Expected " + params.size() + " argument(s), found " +
                            (args.length - 1) + ".");

        for (int i = 1; i < args.length; i++)
            if (! args[i].type().conformsTo(params.varDecl(i - 1).type()) )
                throw new ExpInvalidException(
                        "Type mismatch in argument `" + params.varDecl(i - 1).name() +
                                "'. Expected type `" + params.varDecl(i - 1).type() +
                                "', found `" + args[i].type() + "'.");
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

package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.api.IExpressionPrinter;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Adapter that uses the existing OCL ExpressionPrintVisitor to produce concrete syntax
 * and exposes it via the API IExpressionPrinter.
 */
public class OclExpressionPrinterAdapter implements IExpressionPrinter {

    @Override
    public String toConcreteSyntax(IExpression expr) {
        if (expr == null) return "";
        // We know that IExpression is implemented by org.tzi.use.uml.ocl.expr.Expression
        if (expr instanceof Expression oclExpr) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ExpressionPrintVisitor v = new ExpressionPrintVisitor(pw);
            oclExpr.processWithVisitor(v);
            pw.flush();
            return sw.toString();
        }
        // Fallback
        return expr.asString();
    }
}


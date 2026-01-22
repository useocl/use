package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.api.IInvariantExpressionFactory;
import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.api.IVarDeclList;
import org.tzi.use.uml.ocl.type.TypeAdapters;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.api.IVarDecl;

/**
 * OCL-spezifische Implementierung der InvariantExpressionFactory.
 * Hier werden konkrete OCL-Expressions zusammengesetzt.
 */
public class OclInvariantExpressionFactory implements IInvariantExpressionFactory {

    @Override
    public IExpression buildExpandedInvariant(IExpression body, boolean existential, IType classifier, IVarDeclList vars) {
        // Erwartet, dass der Body bereits eine Expression ist
        Expression bodyExpr = (Expression) body;
        try {
            // Wenn keine Variablen deklariert sind oder die Liste leer ist,
            // können wir nichts aufbauen -> Fallback: return body as-is
            if (vars == null || !vars.iterator().hasNext()) {
                return bodyExpr; // fallback: return body as-is
            }

            // create range expression: Class.allInstances()
            Type classType = TypeAdapters.asOclType(classifier);
            Expression rangeExp = new ExpAllInstances(classType);

            // adapt vars to concrete VarDeclList if necessary
            VarDeclList oclVarDecls;
            if (vars instanceof VarDeclList) {
                oclVarDecls = (VarDeclList) vars;
            } else {
                // convert IVarDeclList -> VarDeclList
                oclVarDecls = new VarDeclList(false);
                for (IVarDecl iv : vars) {
                    String name = iv.name();
                    org.tzi.use.uml.api.IType apiType = iv.type();
                    Type oclType = TypeAdapters.asOclType(apiType);
                    oclVarDecls.add(new VarDecl(name, oclType));
                }
            }

            // build quantifier expression
            if (existential) {
                return new ExpExists(oclVarDecls, rangeExp, bodyExpr);
            } else {
                return new ExpForAll(oclVarDecls, rangeExp, bodyExpr);
            }
        } catch (ExpInvalidException e) {
            throw new RuntimeException("Failed to build expanded invariant", e);
        }
    }

    @Override
    public IExpression buildViolatingInstancesExpr(IExpression expandedBody, boolean negated) {
        Expression current = (Expression) expandedBody;
        try {
            if (negated) {
                current = ExpStdOp.create("not", new Expression[]{ current });
            }
            // Ohne Klassen-/Variablenkontext kann hier nur die Negation
            // des expandierten Ausdrucks zurückgegeben werden.
            return current;
        } catch (ExpInvalidException e) {
            throw new RuntimeException("Failed to build violating-instances expression", e);
        }
    }

    @Override
    public IExpression buildSatisfyingInstancesExpr(IExpression expandedBody, boolean negated) {
        Expression current = (Expression) expandedBody;
        try {
            if (negated) {
                current = ExpStdOp.create("not", new Expression[]{ current });
            }
            // Analog zu oben: ohne Kontext geben wir (ggf. negierten)
            // expandierten Ausdruck zurück.
            return current;
        } catch (ExpInvalidException e) {
            throw new RuntimeException("Failed to build satisfying-instances expression", e);
        }
    }
}

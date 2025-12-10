package org.tzi.use.uml.ocl.expr;

import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.api.IInvariantExpressionFactory;

/**
 * OCL-spezifische Implementierung der InvariantExpressionFactory.
 * Hier werden konkrete OCL-Expressions zusammengesetzt.
 */
public class OclInvariantExpressionFactory implements IInvariantExpressionFactory {

    @Override
    public IExpression buildExpandedInvariant(IExpression body, boolean existential) {
        // Erwartet, dass der Body bereits eine Expression ist
        Expression bodyExpr = (Expression) body;
        // Die ursprüngliche Logik benötigt MClassifier/VarDeclList; diese
        // Information liegt im aktuellen Modell weiterhin in MClassInvariant
        // und dem OCL-Umfeld. Ohne zusätzlichen Kontext kann hier nur ein
        // Platzhalter bleiben oder eine später zu ergänzende Strategie.
        // Für die Architekturabstraktion ist entscheidend, dass api die
        // Signatur nicht mehr auf mm/ocl-Typen festlegt.
        return bodyExpr;
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

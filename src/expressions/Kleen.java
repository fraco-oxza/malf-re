package expressions;

import automatons.NFA;

public class Kleen extends Expression {
    protected Expression expression;

    public Kleen(Expression expression) {
        this.expression = expression;
    }

    @Override
    public NFA getNFA() {
        return null;
    }
}

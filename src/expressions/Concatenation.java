package expressions;

import automatons.NFA;

public class Concatenation extends Expression {
    protected Expression left;
    protected Expression right;

    public Concatenation(Expression expression, Expression expression1) {
        left = expression;
        right = expression1;
    }

    @Override
    public NFA getNFA() {
        return null;
    }
}

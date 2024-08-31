package expressions;

import automatons.NFA;

public class Parenthesis extends Expression {
    protected Expression expr;

    public Parenthesis(Expression expression) {
        expr = expression;
    }

    @Override
    public NFA getNFA() {
        return null;
    }
}

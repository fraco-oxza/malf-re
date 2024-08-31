package expressions;

import automatons.NFA;

public class Parenthesis extends Expression {
    protected Expression expr;

    @Override
    public NFA getNFA() {
        return null;
    }
}

package expressions;

import automatons.NFA;

public class Disjunction extends Expression {
    protected Expression left;
    protected Expression right;

    public Disjunction(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public NFA getNFA() {
        return null;
    }
}

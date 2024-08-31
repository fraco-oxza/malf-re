package expressions;

import automatons.NFA;

public class Disjunction extends Expression {
    protected Expression left;
    protected Expression right;


    @Override
    public NFA getNFA() {
        return null;
    }
}

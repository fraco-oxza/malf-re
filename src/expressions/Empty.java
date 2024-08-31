package expressions;

import automatons.NFA;

public class Empty extends Expression {
    @Override
    public NFA getNFA() {
        return null;
    }
}

package expressions;

import automatons.NFA;

public class Char extends Expression {
    char character;

    Char(char character) {
        this.character = character;
    }

    @Override
    public NFA getNFA() {
        return null;
    }
}

package expressions;

import automatons.NFA;
import automatons.Transition;

import java.util.HashSet;

public class Kleen extends Expression {
    protected Expression expression;

    public Kleen(Expression expression) {
        this.expression = expression;
    }

    @Override
    public NFA getNFA() {
        var e1 = expression.getNFA();

        e1.incrementStateNumericValues(1);

        var states = e1.getStates();
        states.add(0);
        states.add(e1.numberOfStates()+1);

        var alphabet = e1.getAlphabet();

        var initialState = 0;
        var finalStates = new HashSet<Integer>();
        finalStates.add(e1.numberOfStates()+1);

        var transitions = new HashSet<>(e1.getTransitions());
        transitions.add(new Transition(0, '_', e1.getInitialState()));
        transitions.add(new Transition(0, '_', e1.numberOfStates() + 1));

        for (int finalState : e1.getFinalStates()) {
            transitions.add(new Transition(finalState, '_', e1.getInitialState()));
            transitions.add(new Transition(finalState, '_', e1.numberOfStates() +1));
        }

        return new NFA(states, alphabet, transitions, initialState, finalStates);
    }
}

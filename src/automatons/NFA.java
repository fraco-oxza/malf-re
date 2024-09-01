package automatons;

import java.util.HashSet;

public class NFA extends Automaton {

    public NFA(HashSet<Integer> states, HashSet<Character> alphabet, HashSet<Transition> transitions, int initialState, HashSet<Integer> finalStates) {
        super(states, alphabet, transitions, initialState, finalStates);
    }

    public NFA(expressions.Expression expression) {
        var nfa = expression.getNFA();
        this.states = nfa.getStates();
        this.alphabet = nfa.getAlphabet();
        this.transitions = nfa.getTransitions();
        this.initialState = nfa.getInitialState();
        this.finalStates = nfa.getFinalStates();
    }
}

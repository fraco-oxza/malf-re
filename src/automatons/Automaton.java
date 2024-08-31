package automatons;

import java.util.Set;


public abstract class Automaton {
    protected Set<Integer> states;
    protected Set<Character> alphabet;
    protected Set<Transition> transitions;
    protected int initialState;
    protected Set<Integer> finalState;

    public Automaton(Set<Integer> states, Set<Character> alphabet, Set<Transition> transitions, int initialState, Set<Integer> finalState) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalState = finalState;
    }

    @Override
    public String toString() {
        return "";
    }
}

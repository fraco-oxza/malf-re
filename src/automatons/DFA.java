package automatons;

import java.util.HashSet;

public class DFA extends Automaton {

    public DFA(HashSet<Integer> states, HashSet<Character> alphabet, HashSet<Transition> transitions, int initialState, HashSet<Integer> finalStates) {
        super(states, alphabet, transitions, initialState, finalStates);
    }

    public DFA(NFA nfa) {
        super(null,null,null,0,null);
    }
}

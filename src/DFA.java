import java.util.Set;

public class DFA extends Automaton {

    public DFA(Set<Integer> states, Set<Character> alphabet, Set<Transition> transitions, int initialState, Set<Integer> finalState) {
        super(states, alphabet, transitions, initialState, finalState);
    }

    public DFA(NFA nfa) {
    }
}

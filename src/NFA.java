import java.util.Set;

public class NFA extends Automaton {
    public NFA(Set<Integer> states, Set<Character> alphabet, Set<Transition> transitions, int initialState, Set<Integer> finalState) {
        super(states, alphabet, transitions, initialState, finalState);
    }

    public NFA(Expression expression) {

    }
}

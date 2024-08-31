package automatons;

import java.util.HashSet;

public abstract class Automaton {
    protected HashSet<Integer> states;
    protected HashSet<Character> alphabet;
    protected HashSet<Transition> transitions;
    protected int initialState;
    protected HashSet<Integer> finalStates;

    public Automaton(HashSet<Integer> states, HashSet<Character> alphabet, HashSet<Transition> transitions, int initialState, HashSet<Integer> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public Automaton() {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.transitions = new HashSet<>();
        this.initialState = 0;
        this.finalStates = new HashSet<>();
    }

    @Override
    public String toString() {
        String stateSymbol = "q";
        String separatorString = ", ";
        StringBuilder statesString = new StringBuilder();
        StringBuilder alphabetString = new StringBuilder();
        StringBuilder transitionsString = new StringBuilder();
        StringBuilder initialStateString = new StringBuilder();
        StringBuilder finalStatesString = new StringBuilder();


        for (Integer state : this.states) {
            statesString.append(stateSymbol).append(state).append(separatorString);
        }

        statesString.delete(statesString.lastIndexOf(separatorString), statesString.length());

        for (Character alphabet : this.alphabet) {
            alphabetString.append(alphabet).append(separatorString);
        }

        alphabetString.delete(alphabetString.lastIndexOf(separatorString), alphabetString.length());

        for (Transition transition : this.transitions) {
            transitionsString.append(transition).append(separatorString);
        }

        transitionsString.delete(transitionsString.lastIndexOf(separatorString), transitionsString.length());

        initialStateString.append(stateSymbol).append(this.initialState);

        for (Integer finalState : this.finalStates) {
            finalStatesString.append(finalState).append(separatorString);
        }

        finalStatesString.delete(finalStatesString.lastIndexOf(separatorString), finalStatesString.length());


        return "K={" + statesString + "}\n" +
                "Sigma={" + alphabetString + "}\n" +
                "Delta:{" + transitionsString + "}\n" +
                "s=" + initialStateString + "\n" +
                "F={" + finalStatesString + "}";
    }
}

package automatons;

import java.util.*;

public class DFA extends Automaton {
    public DFA(HashSet<Integer> states, HashSet<Character> alphabet, HashSet<Transition> transitions, int initialState, HashSet<Integer> finalStates) {
        super(states, alphabet, transitions, initialState, finalStates);
    }

    public DFA(NFA nfa) {
        this.states = new HashSet<>();
        this.alphabet = nfa.getAlphabet();
        this.finalStates = new HashSet<>();
        this.transitions = new HashSet<>();
        this.initialState = 0;
        char[][] matrixTransitions = getTransitionsInMatrix(nfa);
        transformNFAToDFA(matrixTransitions, nfa);
    }

    private char[][] getTransitionsInMatrix(NFA nfa) {
        char[][] transitions = new char[nfa.states.size()][nfa.states.size()];

        for (Transition t : nfa.transitions) {
            transitions[t.getFromNode()][t.getToNode()] = t.getCharacter();
        }
        return transitions;
    }

    private void transformNFAToDFA(char[][] transitions, NFA nfa) {
        ArrayList<TreeSet<Integer>> rawStates = new ArrayList<>();
        TreeSet<Integer> newState = getNewState(transitions, nfa.initialState, '_', new TreeSet<>());
        newState.add(nfa.initialState);
        rawStates.add(newState);
        this.states.add(0);
        if (isFinalState(newState, nfa.finalStates)) this.finalStates.add(0);
        int currentState = 0;

        while (currentState < rawStates.size()) {
            for (char c : this.alphabet) {
                if (rawStates.get(currentState).isEmpty()) {
                    this.transitions.add(new Transition(currentState, c, currentState));
                    continue;
                }

                newState = new TreeSet<>();
                for (int i : rawStates.get(currentState)) {
                    newState.addAll(getNewState(transitions, i, c, new TreeSet<>()));
                }

                Integer posNewState = isSetInArrayList(newState, rawStates);
                if (posNewState == null) {
                    rawStates.add(newState);
                    this.states.add(rawStates.size() - 1);
                    this.transitions.add(new Transition(currentState, c, rawStates.size() - 1));
                    if (isFinalState(newState, nfa.finalStates)) this.finalStates.add(rawStates.size() - 1);
                } else {
                    this.transitions.add(new Transition(currentState, c, posNewState));
                    if (isFinalState(newState, nfa.finalStates)) this.finalStates.add(posNewState);
                }
            }
            currentState++;
        }
    }

    private TreeSet<Integer> getNewState(char[][] transitions, int current, char character, TreeSet<Integer> state) {
        for (int i = 0; i < transitions[current].length; i++) {
            if (transitions[current][i] == character && !state.contains(i)) {
                state.add(i);
                getNewState(transitions, i, character, state);
            }
        }

        for (int i : state) {
            if (!state.contains(current)) getNewState(transitions, i, '_', state);
        }
        return state;
    }

    private Integer isSetInArrayList(TreeSet<Integer> set, ArrayList<TreeSet<Integer>> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(set)) {
                return i;
            }
        }
        return null;
    }

    protected HashMap<Integer, int[]> getMapFromStateCharacterToState() {
        var matrix = new HashMap<Integer, int[]>();
        var arr = new int[256];
        Arrays.fill(arr, -1);
        matrix.put(initialState, arr);

        for (var transition : transitions) {
            var fromNode = transition.getFromNode();
            var toNode = transition.getToNode();
            var character = transition.getCharacter();

            if (!matrix.containsKey(fromNode)) {
                // Only works with ASCII
                arr = new int[256];
                Arrays.fill(arr,-1);
                matrix.put(fromNode, arr);
            }

            matrix.get(fromNode)[character] = toNode;
        }

        return matrix;
    }

    public boolean isMatch(String word) {
        var currentNode = initialState;
        var transitionsMatrix = getMapFromStateCharacterToState();

        for (var c : word.toCharArray()) {
            currentNode = transitionsMatrix.get(currentNode)[c];
            if (currentNode == -1)
                return false;
        }

        return finalStates.contains(currentNode);
    }

    private boolean isFinalState(TreeSet<Integer> state, HashSet<Integer> finalStates) {
        for (int i : state) {
            if (finalStates.contains(i)) return true;
        }
        return false;
    }
}

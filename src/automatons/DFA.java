package automatons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class DFA extends Automaton {

  public DFA(
      HashSet<Integer> states,
      HashSet<Character> alphabet,
      HashSet<Transition> transitions,
      int initialState,
      HashSet<Integer> finalStates) {
    super(states, alphabet, transitions, initialState, finalStates);
  }

  public DFA(NFA nfa) {
    this.states = new HashSet<>();
    this.alphabet = new HashSet<>();
    this.finalStates = new HashSet<>();
    this.transitions = new HashSet<>();
    this.initialState = 0;
    fillAlphabet(nfa);
    char[][] matrixTransitions = getTransitionsInMatrix(nfa);
    fillStatesTable(matrixTransitions, nfa);
  }

  private void fillAlphabet(NFA nfa) {
    for (Transition t : nfa.transitions) {
      if (t.getCharacter() == '_' || t.getCharacter() == '~') continue;
      this.alphabet.add(t.getCharacter());
    }
  }

  private char[][] getTransitionsInMatrix(NFA nfa) {
    char[][] transitions = new char[nfa.states.size()][nfa.states.size()];

    for (Transition t : nfa.transitions) {
      transitions[t.getFromNode()][t.getToNode()] = t.getCharacter();
    }
    return transitions;
  }

  private void fillStatesTable(char[][] transitions, NFA nfa) {
    ArrayList<TreeSet<Integer>> rawStates = new ArrayList<>();
    TreeSet<Integer> newState = getNewState(transitions, nfa.initialState, '_');
    newState.add(nfa.initialState);
    rawStates.add(newState);
    this.states.add(0);
    int currentState = 0;

    while (currentState < rawStates.size()) {
      for (char c : this.alphabet) {
        if (rawStates.get(currentState).isEmpty()) {
          this.transitions.add(new Transition(currentState, c, currentState));
          continue;
        }

        newState = new TreeSet<>();
        for (int i : rawStates.get(currentState)) {
          newState.addAll(getNewState(transitions, i, c));
        }

        if (!isSetInArrayList(newState, rawStates)) {
          rawStates.add(newState);
          this.states.add(rawStates.size() - 1);
        }
        this.transitions.add(new Transition(currentState, c, rawStates.size() - 1));

        if (isFinalState(newState, nfa.finalStates)) this.finalStates.add(rawStates.size() - 1);
      }
      currentState++;
    }
  }

  private TreeSet<Integer> getNewState(char[][] transitions, int current, char character) {
    TreeSet<Integer> state = new TreeSet<>();

    for (int i = 0; i < transitions[current].length; i++) {
      if (transitions[current][i] == character) {
        state.add(i);
        state.addAll(getNewState(transitions, i, character));
      }
    }

    for (int i : state) {
      state.addAll(getNewState(transitions, i, '_'));
    }
    return state;
  }

  private boolean isSetInArrayList(TreeSet<Integer> set, ArrayList<TreeSet<Integer>> arrayList) {
    for (TreeSet<Integer> i : arrayList) {
      if (i.equals(set)) {
        return true;
      }
    }
    return false;
  }

  private boolean isFinalState(TreeSet<Integer> state, HashSet<Integer> finalStates) {
    for (int i : state) {
      if (finalStates.contains(i)) return true;
    }
    return false;
  }
}

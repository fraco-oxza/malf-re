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
    char[][] transitions2 = getTransitions(nfa);
    fillStatesTable(transitions2, nfa);
  }

  private void fillAlphabet(NFA nfa) {
    for (Transition t : nfa.transitions) {
      if (t.getCharacter() == '_' || t.getCharacter() == '~') continue;
      this.alphabet.add(t.getCharacter());
    }
  }

  private char[][] getTransitions(NFA nfa) {
    char[][] transitions = new char[nfa.states.size()][nfa.states.size()];

    for (Transition t : nfa.transitions) {
      transitions[t.getFromNode()][t.getToNode()] = t.getCharacter();
    }
    return transitions;
  }

  private void fillStatesTable(char[][] transitions, NFA nfa) {
    ArrayList<TreeSet<Integer>> states = new ArrayList<>();
    TreeSet<Integer> newState = getState(transitions, nfa.initialState, '_');
    newState.add(nfa.initialState);
    states.add(newState);
    this.states.add(0);
    int currentState = 0;

    while (currentState < states.size()) {
      for (int i : states.get(currentState)) {
        for (char c : this.alphabet) {
          newState = getState(transitions, i, c);
          int flag = 0;
          for (TreeSet<Integer> j : states) {
            if (j.equals(newState)) {
              flag = 1;
              break;
            }
          }
          if (flag == 1) continue;
          states.add(newState);
          this.states.add(states.size() - 1);
          this.transitions.add(new Transition(currentState, c, states.size() - 1));
          for (int j : newState) {
            if (nfa.finalStates.contains(j)) this.finalStates.add(states.size() - 1);
          }
        }
      }
      currentState++;
    }
  }

  private TreeSet<Integer> getState(char[][] transitions, int current, char character) {
    TreeSet<Integer> state = new TreeSet<>();

    for (int i = 0; i < transitions[current].length; i++) {
      if (transitions[current][i] == character) {
        state.add(i);
        state.addAll(getState(transitions, i, character));
      }
    }

    for (int i : state) {
      state.addAll(getState(transitions, i, '_'));
    }
    return state;
  }
}

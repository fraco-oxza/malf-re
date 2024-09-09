package automatons;

import java.util.*;


public class NFA extends Automaton {
  private Set<String> visited;
  private Set<String> entered;

  public NFA(
      HashSet<Integer> states,
      HashSet<Character> alphabet,
      HashSet<Transition> transitions,
      int initialState,
      HashSet<Integer> finalStates) {
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

  public boolean isMatch(String word) {
    visited = new HashSet<>();
    entered = new HashSet<>();
    return isMatch(word, initialState);
  }

  private boolean isMatch(String word, int currentState) {
    String memoKey = word + " " +currentState;

    if (entered.contains(memoKey) && !visited.contains(memoKey)) {
      return false;
    }
    entered.add(memoKey);

    boolean result = word.isEmpty() && finalStates.contains(currentState);

    var availableTransitions = getTransitionsFromState(currentState);
    for (Transition t : availableTransitions) {
      if (result) break;

      if (!word.isEmpty() && t.getCharacter() == word.charAt(0))
        result = isMatch(word.substring(1), t.getToNode());
      else if (t.getCharacter() == '_') {
        result = isMatch(word, t.getToNode());
      }
    }

    visited.add(memoKey);
    return result;
  }
}

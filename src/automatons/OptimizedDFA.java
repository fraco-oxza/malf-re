package automatons;

import java.util.HashSet;

public class OptimizedDFA extends DFA {

  public OptimizedDFA(DFA dfa) {
    super(dfa.states, dfa.alphabet, dfa.transitions, dfa.initialState, dfa.finalStates);
  }

  private HashSet<Integer> getUnreachableStates(HashSet<Integer> allStates) {
    HashSet<Integer> reachableStates = new HashSet<>();
    HashSet<Integer> newStates = new HashSet<>();
    HashSet<Integer> unreachableStates = new HashSet<>(allStates);

    reachableStates.add(0);
    newStates.add(0);

    do {
      HashSet<Integer> temp = new HashSet<>();

      for (Integer state : newStates) {
        HashSet<Transition> stateTransitions = this.getTransitionsFromState(state);

        for (Transition transition : stateTransitions) {
          temp.add(transition.getToNode());
        }
      }

      newStates.addAll(temp);
      newStates.removeAll(reachableStates);

      reachableStates.addAll(newStates);
    } while (!newStates.isEmpty());

    unreachableStates.removeAll(reachableStates);

    return unreachableStates;
  }

  private void removeUnreachableStates() {
    HashSet<Integer> unreachableStates = this.getUnreachableStates(this.states);

    this.states.removeAll(unreachableStates);
  }
}

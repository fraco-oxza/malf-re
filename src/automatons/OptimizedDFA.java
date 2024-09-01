package automatons;

import java.util.HashSet;
import java.util.Objects;

public class OptimizedDFA extends DFA {
  public OptimizedDFA(DFA dfa) {
    super(dfa.states, dfa.alphabet, dfa.transitions, dfa.initialState, dfa.finalStates);

    optimize();
  }

  private void optimize() {
    removeUnreachableStates();
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

  private HashSet<HashSet<Integer>> getEquivalentStates(
      HashSet<Integer> allStates, HashSet<Integer> allFinalStates) {
    HashSet<Integer> finalStates = new HashSet<>(allFinalStates);
    HashSet<Integer> nonFinalStates = new HashSet<>(allStates);
    HashSet<HashSet<Integer>> previousPartitions = new HashSet<>();
    HashSet<HashSet<Integer>> actualPartitions;
    boolean arePartitionsEqual;

    nonFinalStates.removeAll(allFinalStates);
    previousPartitions.add(nonFinalStates);
    previousPartitions.add(finalStates);

    do {
      arePartitionsEqual = false;

      actualPartitions = new HashSet<>();

      for (HashSet<Integer> partition : previousPartitions) {
        if (partition.size() == 1) {
          actualPartitions.add(partition);
          continue;
        }

        HashSet<Integer> sameSubPartition = new HashSet<>();

        // Groups states that goes to the same partition group
        for (Integer stateA : partition) {
          for (Integer stateB : partition) {
            if (Objects.equals(stateA, stateB)) continue;

            if (sameSubPartition.size() == partition.size()) break;

            if (areStatesInSamePartition(stateA, stateB, partition)) {
              sameSubPartition.add(stateA);
              sameSubPartition.add(stateB);
            }
          }
        }

        actualPartitions.add(sameSubPartition);

        // Adds remaining states
        for (Integer state : partition) {
          if (!sameSubPartition.contains(state)) {
            actualPartitions.add(new HashSet<>(state));
          }
        }

        if (previousPartitions.equals(actualPartitions)) {
          arePartitionsEqual = true;
        }

        previousPartitions = actualPartitions;
      }
    } while (!arePartitionsEqual);

    return actualPartitions;
  }

  private boolean areStatesInSamePartition(
      Integer stateA, Integer stateB, HashSet<Integer> partition) {
    HashSet<Transition> transitionsStateA = getTransitionsFromState(stateA);
    HashSet<Transition> transitionsStateB = getTransitionsFromState(stateB);
    boolean isSamePartition = true;

    for (Transition transitionA : transitionsStateA) {
      if (!partition.contains(transitionA.getToNode())) {
        isSamePartition = false;
        break;
      }
    }

    for (Transition transitionB : transitionsStateB) {
      if (!partition.contains(transitionB.getToNode())) {
        isSamePartition = false;
        break;
      }
    }

    return isSamePartition;
  }
}

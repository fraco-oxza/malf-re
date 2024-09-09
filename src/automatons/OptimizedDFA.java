package automatons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class OptimizedDFA extends DFA {
  public OptimizedDFA(DFA dfa) {
    super(dfa.states, dfa.alphabet, dfa.transitions, dfa.initialState, dfa.finalStates);

    optimize();
  }

  private void optimize() {
    removeUnreachableStates();
    mergeEquivalentTransitions();
    removeUnreachableStates();
  }

  private HashSet<Integer> getUnreachableStates(HashSet<Integer> allStates) {
    HashSet<Integer> reachableStates = new HashSet<>();
    HashSet<Integer> newStates = new HashSet<>();
    HashSet<Integer> unreachableStates = new HashSet<>(allStates);

    reachableStates.add(this.initialState);
    newStates.add(this.initialState);

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
    HashSet<HashSet<Integer>> actualPartitions = new HashSet<>();
    boolean arePartitionsEqual;

    nonFinalStates.removeAll(allFinalStates);
    previousPartitions.add(nonFinalStates);
    previousPartitions.add(finalStates);

    do {
      arePartitionsEqual = false;

      actualPartitions.clear();

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

            if (areStatesEquivalent(stateA, stateB, previousPartitions)) {
              sameSubPartition.add(stateA);
              sameSubPartition.add(stateB);
            }
          }
        }

        if (!sameSubPartition.isEmpty()) actualPartitions.add(sameSubPartition);

        // Adds remaining states
        for (Integer state : partition) {
          if (!sameSubPartition.contains(state)) {
            HashSet<Integer> singlePartition = new HashSet<>();
            singlePartition.add(state);
            actualPartitions.add(singlePartition);
          }
        }
      }

      if (previousPartitions.equals(actualPartitions)) {
        arePartitionsEqual = true;
      }

      previousPartitions.clear();
      previousPartitions.addAll(actualPartitions);
    } while (!arePartitionsEqual);

    return actualPartitions;
  }

  private HashSet<Transition> getNewTransitions(HashSet<HashSet<Integer>> equivalentStates) {
    HashSet<Transition> newTransitions = new HashSet<>();
    HashMap<HashSet<Integer>, Integer> renamedStates = new HashMap<>();
    HashSet<Integer> newFinalStates = new HashSet<>();
    int newInitialState = 0;

    int counter = 0;

    for (HashSet<Integer> states : equivalentStates) {
      renamedStates.put(states, counter++);
    }

    for (HashSet<Integer> states : equivalentStates) {
      HashSet<Transition> temp = new HashSet<>();

      for (Integer state : states) {
        HashSet<Transition> stateTransitions = this.getTransitionsFromState(state);

        for (Transition transition : stateTransitions) {
          temp.add(
              new Transition(
                  renamedStates.get(states), transition.getCharacter(), transition.getToNode()));
        }

        if (this.initialState == state) {
          newInitialState = renamedStates.get(states);
        }

        if (this.finalStates.contains(state)) {
          newFinalStates.add(renamedStates.get(states));
        }
      }

      newTransitions.addAll(temp);
    }

    HashSet<Transition> updatedTransitions = new HashSet<>();

    for (Transition transition : newTransitions) {
      int newToState = getNewStateNumber(renamedStates, transition.getToNode());

      updatedTransitions.add(
          new Transition(transition.getFromNode(), transition.getCharacter(), newToState));
    }

    newTransitions.clear();
    newTransitions.addAll(updatedTransitions);

    updateInitialAndFinalStates(newInitialState, newFinalStates);

    return newTransitions;
  }

  private void mergeEquivalentTransitions() {
    HashSet<HashSet<Integer>> equivalentStates = getEquivalentStates(this.states, this.finalStates);

    this.transitions = getNewTransitions(equivalentStates);
  }

  private void updateInitialAndFinalStates(Integer initialState, HashSet<Integer> finalStates) {
    this.initialState = initialState;
    this.finalStates = finalStates;
  }

  private boolean areStatesEquivalent(
      Integer stateA, Integer stateB, HashSet<HashSet<Integer>> AllPartitions) {
    HashSet<Transition> transitionsStateA = getTransitionsFromState(stateA);
    HashSet<Transition> transitionsStateB = getTransitionsFromState(stateB);
    boolean areSameEndStates = true;
    boolean isSamePartition = true;

    // Checks if each end states are the same
    for (Transition transitionA : transitionsStateA) {
      Transition transitionB =
          getSameCharacterTransition(transitionsStateB, transitionA.getCharacter());

      if (transitionB == null) continue;

      if (transitionA.getToNode() != transitionB.getToNode()) areSameEndStates = false;
    }

    if (areSameEndStates) return true;

    // If not, checks if end states are in the same partition
    for (Transition transitionA : transitionsStateA) {
      Transition transitionB =
          getSameCharacterTransition(transitionsStateB, transitionA.getCharacter());
      boolean areContainedInPartition = false;

      if (transitionB == null) continue;

      for (HashSet<Integer> partition : AllPartitions) {
        if (partition.contains(transitionA.getToNode())
            && partition.contains(transitionB.getToNode())) {
          areContainedInPartition = true;
          break;
        }
      }

      if (!areContainedInPartition) {
        isSamePartition = false;
        break;
      }
    }

    return isSamePartition;
  }

  private int getNewStateNumber(HashMap<HashSet<Integer>, Integer> renamedStates, Integer state) {
    HashSet<Integer> stateSet = new HashSet<>();

    stateSet.add(state);

    if (renamedStates.containsKey(stateSet)) return renamedStates.get(stateSet);

    for (HashSet<Integer> renamedState : renamedStates.keySet()) {
      if (renamedState.contains(state)) return renamedStates.get(renamedState);
    }

    return -1;
  }

  private Transition getSameCharacterTransition(HashSet<Transition> transitions, char character) {
    for (Transition transition : transitions) {
      if (transition.getCharacter() == character) return transition;
    }

    return null;
  }
}

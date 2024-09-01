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

    private HashSet<HashSet<Integer>> getEquivalentStates(HashSet<Integer> allStates, HashSet<Integer> allFinalStates) {
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

                //Groups states that goes to the same partition group
                for (Integer stateA : partition) {
                    for (Integer stateB : partition) {
                        if (Objects.equals(stateA, stateB)) continue;

                        if (sameSubPartition.size() == partition.size()) break;

                        if (areStatesEquivalent(stateA, stateB, partition)) {
                            sameSubPartition.add(stateA);
                            sameSubPartition.add(stateB);
                        }
                    }
                }

                if (!sameSubPartition.isEmpty()) actualPartitions.add(sameSubPartition);

                //Adds remaining states
                for (Integer state : partition) {
                    if (!sameSubPartition.contains(state)) {
                        HashSet<Integer> singlePartition = new HashSet<>(state);
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

    private HashSet<Transition> getNewTransitionsFromEquivalentStates(HashSet<HashSet<Integer>> equivalentStates) {
        HashSet<Transition> newTransitions = new HashSet<>();


        return newTransitions;
    }

    private void updateNewTransitions() {
        HashSet<HashSet<Integer>> equivalentStates = getEquivalentStates(this.states, this.finalStates);
        HashSet<Transition> newTransitions = getNewTransitionsFromEquivalentStates(equivalentStates);
    }


    private boolean areStatesEquivalent(Integer stateA, Integer stateB, HashSet<Integer> partition) {
        HashSet<Transition> transitionsStateA = getTransitionsFromState(stateA);
        HashSet<Transition> transitionsStateB = getTransitionsFromState(stateB);
        boolean isSamePartition = true;
        boolean areSameEndStates = true;

        //Checks if each end states are the same
        for (Transition transitionA : transitionsStateA) {
            Transition transitionB = getSameCharacterTransition(transitionsStateB, transitionA.getCharacter());

            if (transitionB == null) continue;

            if (transitionA.getToNode() != transitionB.getToNode()) areSameEndStates = false;
        }

        if (areSameEndStates) return true;

        //If not, checks if end states are in the same partition
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

    private Transition getSameCharacterTransition(HashSet<Transition> transitions, char character) {
        for (Transition transition : transitions) {
            if (transition.getCharacter() == character) return transition;
        }

        return null;
    }

}

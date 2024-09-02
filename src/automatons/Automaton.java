package automatons;

import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class Automaton {
  protected HashSet<Integer> states;
  protected HashSet<Character> alphabet;
  protected HashSet<Transition> transitions;
  protected int initialState;
  protected HashSet<Integer> finalStates;

  protected Automaton(
      HashSet<Integer> states,
      HashSet<Character> alphabet,
      HashSet<Transition> transitions,
      int initialState,
      HashSet<Integer> finalStates) {
    this.states = states;
    this.alphabet = alphabet;
    this.transitions = transitions;
    this.initialState = initialState;
    this.finalStates = finalStates;
  }

  protected Automaton() {
    this.states = new HashSet<>();
    this.alphabet = new HashSet<>();
    this.transitions = new HashSet<>();
    this.initialState = 0;
    this.finalStates = new HashSet<>();
  }

  public HashSet<Integer> getStates() {
    return new HashSet<>(this.states);
  }

  public HashSet<Character> getAlphabet() {
    return new HashSet<>(this.alphabet);
  }

  public HashSet<Transition> getTransitions() {
    return this.transitions.stream()
        .map(t -> new Transition(t.getFromNode(), t.getCharacter(), t.getToNode()))
        .collect(Collectors.toCollection(HashSet::new));
  }

  public int getInitialState() {
    return this.initialState;
  }

  public HashSet<Integer> getFinalStates() {
    return new HashSet<>(this.finalStates);
  }

  public void incrementStateNumericValues(int dv) {
    HashSet<Integer> newStates = new HashSet<>();
    for (Integer state : states) {
      newStates.add(state + dv);
    }
    this.states = newStates;

    for (Transition t : transitions) {
      t.setFromNode(t.getFromNode() + dv);
      t.setToNode(t.getToNode() + dv);
    }

    this.initialState += dv;

    HashSet<Integer> newFinalStates = new HashSet<>();
    for (Integer state : finalStates) {
      newFinalStates.add(state + dv);
    }
    this.finalStates = newFinalStates;
  }

  public int numberOfStates() {
    return states.size();
  }

  public HashSet<Transition> getTransitionsFromState(Integer state) {
    HashSet<Transition> transitions = new HashSet<>();

        for (Transition t : this.transitions) {
            if (t.getFromNode() == state) {
                Transition foundTransition = new Transition(t.getFromNode(),t.getCharacter(),t.getToNode());
                transitions.add(foundTransition);
            }
        }

    return transitions;
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

    transitionsString.delete(
        transitionsString.lastIndexOf(separatorString), transitionsString.length());

    initialStateString.append(stateSymbol).append(this.initialState);

    for (Integer finalState : this.finalStates) {
      finalStatesString.append(stateSymbol).append(finalState).append(separatorString);
    }

    finalStatesString.delete(
        finalStatesString.lastIndexOf(separatorString), finalStatesString.length());

    return "K={"
        + statesString
        + "}\n"
        + "Sigma={"
        + alphabetString
        + "}\n"
        + "Delta:{"
        + transitionsString
        + "}\n"
        + "s="
        + initialStateString
        + "\n"
        + "F={"
        + finalStatesString
        + "}"
        + "\n";
  }
}

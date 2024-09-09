package expressions;

import automatons.NFA;
import automatons.Transition;
import java.util.HashSet;

public class Char extends Expression {
  char character;

  Char(char character) {
    this.character = character;
  }

  @Override
  public NFA getNFA() {
    // Initialize with two states
    HashSet<Integer> states = new HashSet<>();
    states.add(0);
    states.add(1);

    HashSet<Character> alphabet = new HashSet<>();
    alphabet.add(character);

    int initialState = 0;
    HashSet<Integer> finalStates = new HashSet<>();
    finalStates.add(1);

    HashSet<Transition> transitions = new HashSet<>();
    transitions.add(new Transition(0, character, 1));

    return new NFA(states, alphabet, transitions, initialState, finalStates);
  }
}

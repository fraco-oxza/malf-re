package expressions;

import automatons.NFA;
import automatons.Transition;
import java.util.HashSet;

public class Concatenation extends Expression {
  protected Expression left;
  protected Expression right;

  public Concatenation(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public NFA getNFA() {
    var e1 = left.getNFA();
    var e2 = right.getNFA();

    e2.incrementStateNumericValues(e1.numberOfStates());

    var states = new HashSet<Integer>();
    states.addAll(e1.getStates());
    states.addAll(e2.getStates());

    var alphabet = new HashSet<Character>();
    alphabet.addAll(e1.getAlphabet());
    alphabet.addAll(e2.getAlphabet());

    var initialState = e1.getInitialState();
    var finalStates = e2.getFinalStates();

    var transitions = new HashSet<Transition>();
    transitions.addAll(e1.getTransitions());
    transitions.addAll(e2.getTransitions());

    for (int finalState : e1.getFinalStates()) {
      transitions.add(new Transition(finalState, '_', e2.getInitialState()));
    }

    return new NFA(states, alphabet, transitions, initialState, finalStates);
  }
}

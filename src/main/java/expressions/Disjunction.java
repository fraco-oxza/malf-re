package expressions;

import automatons.NFA;
import automatons.Transition;
import java.util.HashSet;

public class Disjunction extends Expression {
  protected Expression left;
  protected Expression right;

  public Disjunction(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public NFA getNFA() {
    var e1 = left.getNFA();
    var e2 = right.getNFA();

    e1.incrementStateNumericValues(1);
    e2.incrementStateNumericValues(e1.numberOfStates() + 1);

    var states = new HashSet<Integer>();
    states.add(0);
    states.addAll(e1.getStates());
    states.addAll(e2.getStates());
    states.add(e1.numberOfStates() + e2.numberOfStates() + 1);

    var alphabet = new HashSet<Character>();
    alphabet.addAll(e1.getAlphabet());
    alphabet.addAll(e2.getAlphabet());

    var initialState = 0;
    var finalStates = new HashSet<Integer>();
    finalStates.add(e1.numberOfStates() + e2.numberOfStates() + 1);

    var transitions = new HashSet<Transition>();
    transitions.addAll(e1.getTransitions());
    transitions.addAll(e2.getTransitions());

    transitions.add(new Transition(0, '_', e1.getInitialState()));
    transitions.add(new Transition(0, '_', e2.getInitialState()));

    for (int finalState : e1.getFinalStates()) {
      transitions.add(
          new Transition(finalState, '_', e1.numberOfStates() + e2.numberOfStates() + 1));
    }

    for (int finalState : e2.getFinalStates()) {
      transitions.add(
          new Transition(finalState, '_', e1.numberOfStates() + e2.numberOfStates() + 1));
    }

    return new NFA(states, alphabet, transitions, initialState, finalStates);
  }
}

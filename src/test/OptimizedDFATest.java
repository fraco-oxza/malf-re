package test;

import automatons.DFA;
import automatons.OptimizedDFA;
import expressions.InvalidExpression;

public class OptimizedDFATest extends DFATest {
  @Override
  protected OptimizedDFA createDFA(String expression) throws InvalidExpression {
    DFA dfa = super.createDFA(expression);
    return new OptimizedDFA(dfa);
  }
}

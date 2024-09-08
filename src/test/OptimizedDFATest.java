package test;

import automatons.DFA;
import automatons.NFA;
import automatons.OptimizedDFA;
import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;
import org.junit.jupiter.api.Test;

public class OptimizedDFATest extends DFATest {
  @Override
  protected OptimizedDFA createDFA(String expression) throws InvalidExpression {
    Expression exp = ExpressionFactory.parseExpression(expression);
    NFA nfa = new NFA(exp);
    DFA dfa = new DFA(nfa);
    return new OptimizedDFA(dfa);
  }

  @Test
  @Override
  public void testSimpleExpression() throws InvalidExpression {
    super.testSimpleExpression();
  }

  @Test
  @Override
  public void testMediumExpression() throws InvalidExpression {
    super.testMediumExpression();
  }

  @Test
  @Override
  public void testComplexExpression() throws InvalidExpression {
    super.testComplexExpression();
  }
}

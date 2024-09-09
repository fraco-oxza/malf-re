import automatons.Automaton;
import automatons.DFA;
import automatons.NFA;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;

public class DFATest extends AutomatonBaseTest {
  @Override
  protected Automaton createAutomaton(String expression) throws InvalidExpression {
    var exp = ExpressionFactory.parseExpression(expression);
    var nfa = new NFA(exp);

    return new DFA(nfa);
  }
}

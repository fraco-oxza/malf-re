import automatons.Automaton;
import automatons.DFA;
import automatons.NFA;
import automatons.OptimizedDFA;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;

public class OptimizedDFATest extends AutomatonBaseTest {
  @Override
  protected Automaton createAutomaton(String expression) throws InvalidExpression {
    var exp = ExpressionFactory.parseExpression(expression);
    var nfa = new NFA(exp);
    var dfa = new DFA(nfa);

    return new OptimizedDFA(dfa);
  }
}

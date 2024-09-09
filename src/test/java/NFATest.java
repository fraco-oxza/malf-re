import automatons.Automaton;
import automatons.DFA;
import automatons.NFA;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;

public class NFATest extends AutomatonBaseTest {
    @Override
    protected Automaton createAutomaton(String expression) throws InvalidExpression {
        var exp = ExpressionFactory.parseExpression(expression);
        return new NFA(exp);
    }
}
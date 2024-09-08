package test;

import automatons.DFA;
import automatons.OptimizedDFA;
import automatons.NFA;
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
}
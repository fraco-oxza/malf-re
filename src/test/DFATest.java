package test;

import automatons.DFA;
import automatons.NFA;
import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DFATest {
    protected DFA createDFA(String expression) throws InvalidExpression {
        Expression exp = ExpressionFactory.parseExpression(expression);
        NFA nfa = new NFA(exp);
        return new DFA(nfa);
    }

    protected void generalTest(DFA automaton, String[] positiveCases, String[] negativeCases) {
        for (String word : positiveCases) {
            assertTrue(automaton.isMatch(word), "Expected '" + word + "' to match");
        }

        for (String word : negativeCases) {
            assertFalse(automaton.isMatch(word), "Expected '" + word + "' not to match");
        }
    }

    @Test
    public void testSimpleExpression() throws InvalidExpression {
        DFA dfa = createDFA("a*.b*.c*");

        String[] positiveCases = {"aabbcc", "aaaabbbbbccc", "bbbccc", "bc", "", "a", "b", "c"};
        String[] negativeCases = {"aaccbb", "bca", "bba", "bac", "d"};

        generalTest(dfa, positiveCases, negativeCases);
    }

    @Test
    public void testMediumExpression() throws InvalidExpression {
        DFA dfa = createDFA("0*.(1.0*.1.0*)*");

        String[] positiveCases = {"0001010100101010101000110", "000", "11", "101", "1111", ""};
        String[] negativeCases = {"111", "0111", "1", "110100000010010101"};

        generalTest(dfa, positiveCases, negativeCases);
    }

    @Test
    public void testComplexExpression() throws InvalidExpression {
        DFA dfa = createDFA("(a|b)*.((c.d*)|e).(f|g)*");

        String[] positiveCases = {
                "acf", "bcg", "abacef", "bbbcddddfff", "aabbbcg", "e", "ef", "abbacdg", "abbaefgfgf",
                "cdfg", "abbabacddddd", "abababababefgfgfgfg"
        };
        String[] negativeCases = {
                "abc", "cab", "abba", "cd", "fg", "h", "abcde", "acdef", "bbbcccdddfff",
                "aaaabbbbeee", "abcdefg", "gfedcba", "ababababab"
        };

        generalTest(dfa, positiveCases, negativeCases);
    }
}
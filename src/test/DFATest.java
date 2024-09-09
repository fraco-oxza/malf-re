package test;

import static org.junit.jupiter.api.Assertions.*;

import automatons.DFA;
import automatons.NFA;
import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;
import org.junit.jupiter.api.Test;

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
      "acf",
      "bcg",
      "bbbcddddfff",
      "aabbbcg",
      "e",
      "ef",
      "abbacdg",
      "abbaefgfgf",
      "cdfg",
      "abbabacddddd",
      "abababababefgfgfgfg",
      "abc",
      "cd"
    };
    String[] negativeCases = {
      "cab",
      "abba",
      "fg",
      "h",
      "abcde",
      "acdef",
      "bbbcccdddfff",
      "abacef",
      "aaaabbbbeee",
      "abcdefg",
      "gfedcba",
      "ababababab"
    };

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testSingleCharacter() throws InvalidExpression {
    DFA dfa = createDFA("a");

    String[] positiveCases = {"a"};
    String[] negativeCases = {"", "b", "aa"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void concatenationTest() throws InvalidExpression {
    DFA dfa = createDFA("h.e.l.l.o");

    String[] positiveCases = {"hello"};
    String[] negativeCases = {"Hello", "he", "", "h", "helLo"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testEmptyLanguage() throws InvalidExpression {
    DFA dfa = createDFA("~");

    String[] positiveCases = {};
    String[] negativeCases = {"", "a", "aa", "b"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testEpsilonLanguage() throws InvalidExpression {
    DFA dfa = createDFA("_");

    String[] positiveCases = {""};
    String[] negativeCases = {"a", "aa", "b"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testUnion() throws InvalidExpression {
    DFA dfa = createDFA("a|b");

    String[] positiveCases = {"a", "b"};
    String[] negativeCases = {"", "aa", "bb", "ab", "ba", "c"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testConcatenation() throws InvalidExpression {
    DFA dfa = createDFA("a.b");

    String[] positiveCases = {"ab"};
    String[] negativeCases = {"", "a", "b", "ba", "aa", "bb", "aba", "bab"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testKleeneStar() throws InvalidExpression {
    DFA dfa = createDFA("a*");

    String[] positiveCases = {"", "a", "aa", "aaa", "aaaa"};
    String[] negativeCases = {"b", "ab", "ba", "aab"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testParentheses() throws InvalidExpression {
    DFA dfa = createDFA("(a|b)*.c");

    String[] positiveCases = {"c", "ac", "bc", "aac", "abc", "bac", "bbc", "aaac"};
    String[] negativeCases = {"", "a", "b", "ca", "cb", "aba", "bab", "cca"};

    generalTest(dfa, positiveCases, negativeCases);
  }

  @Test
  public void testSpecialCharacters() throws InvalidExpression {
    DFA dfa = createDFA("?|&");

    String[] positiveCases = {"?", "&"};
    String[] negativeCases = {"", "a", "??", "&&", "?&", "&?"};

    generalTest(dfa, positiveCases, negativeCases);
  }
}

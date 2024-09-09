package test;

import static org.junit.jupiter.api.Assertions.*;

import automatons.Automaton;
import expressions.InvalidExpression;
import org.junit.jupiter.api.Test;

public abstract class AutomatonBaseTest {
  protected abstract Automaton createAutomaton(String expression) throws InvalidExpression;

  protected void generalTest(Automaton automaton, String[] positiveCases, String[] negativeCases) {
    for (String word : positiveCases) {
      assertTrue(automaton.isMatch(word), "Expected '" + word + "' to match");
    }

    for (String word : negativeCases) {
      assertFalse(automaton.isMatch(word), "Expected '" + word + "' not to match");
    }
  }

  @Test
  public void testSimpleExpression() throws InvalidExpression {
    Automaton automaton = createAutomaton("a*.b*.c*");

    String[] positiveCases = {"aabbcc", "aaaabbbbbccc", "bbbccc", "bc", "", "a", "b", "c"};
    String[] negativeCases = {"aaccbb", "bca", "bba", "bac", "d"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testMediumExpression() throws InvalidExpression {
    Automaton automaton = createAutomaton("0*.(1.0*.1.0*)*");

    String[] positiveCases = {"0001010100101010101000110", "000", "11", "101", "1111", ""};
    String[] negativeCases = {"111", "0111", "1", "110100000010010101"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testComplexExpression() throws InvalidExpression {
    Automaton automaton = createAutomaton("(a|b)*.((c.d*)|e).(f|g)*");

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

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testSingleCharacter() throws InvalidExpression {
    Automaton automaton = createAutomaton("a");

    String[] positiveCases = {"a"};
    String[] negativeCases = {"", "b", "aa"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void concatenationTest() throws InvalidExpression {
    Automaton automaton = createAutomaton("h.e.l.l.o");

    String[] positiveCases = {"hello"};
    String[] negativeCases = {"Hello", "he", "", "h", "helLo"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testEmptyLanguage() throws InvalidExpression {
    Automaton automaton = createAutomaton("~");

    String[] positiveCases = {};
    String[] negativeCases = {"", "a", "aa", "b"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testEpsilonLanguage() throws InvalidExpression {
    Automaton automaton = createAutomaton("_");

    String[] positiveCases = {""};
    String[] negativeCases = {"a", "aa", "b"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testUnion() throws InvalidExpression {
    Automaton automaton = createAutomaton("a|b");

    String[] positiveCases = {"a", "b"};
    String[] negativeCases = {"", "aa", "bb", "ab", "ba", "c"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testConcatenation() throws InvalidExpression {
    Automaton automaton = createAutomaton("a.b");

    String[] positiveCases = {"ab"};
    String[] negativeCases = {"", "a", "b", "ba", "aa", "bb", "aba", "bab"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testKleeneStar() throws InvalidExpression {
    Automaton automaton = createAutomaton("a*");

    String[] positiveCases = {"", "a", "aa", "aaa", "aaaa"};
    String[] negativeCases = {"b", "ab", "ba", "aab"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testParentheses() throws InvalidExpression {
    Automaton automaton = createAutomaton("(a|b)*.c");

    String[] positiveCases = {"c", "ac", "bc", "aac", "abc", "bac", "bbc", "aaac"};
    String[] negativeCases = {"", "a", "b", "ca", "cb", "aba", "bab", "cca"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testSpecialCharacters() throws InvalidExpression {
    Automaton automaton = createAutomaton("?|&");

    String[] positiveCases = {"?", "&"};
    String[] negativeCases = {"", "a", "??", "&&", "?&", "&?"};

    generalTest(automaton, positiveCases, negativeCases);
  }

  @Test
  public void testInfiniteLoop() throws InvalidExpression {
    Automaton automaton = createAutomaton("_*");

    String[] positiveCases = {""};
    String[] negativeCases = {"c", "asdc", "csdb", "asdcas", "asdof", "$$cdfa%"};

    generalTest(automaton, positiveCases, negativeCases);
  }
}

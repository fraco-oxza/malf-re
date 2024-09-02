package utils;

import automatons.DFA;
import automatons.NFA;
import automatons.Transition;
import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;
import java.util.*;

public class Examples {
  // TODO: Temporary! The alphabet should be somewhere else
  public static HashSet<Character> alphabet =
      new HashSet<>(
          Arrays.asList(
              'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
              'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
              'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
              'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '?', '&'));
  public static ArrayList<NFA> nfas;
  public static ArrayList<DFA> dfas =
      new ArrayList<>(
          Arrays.asList(
              new DFA( // accepts those string which starts with 1 and ends with 0.
                  new HashSet<>(Arrays.asList(0, 1, 2)),
                  alphabet,
                  new HashSet<>(
                      Arrays.asList(
                          new Transition(0, '1', 1),
                          new Transition(1, '1', 1),
                          new Transition(1, '0', 2),
                          new Transition(2, '1', 1),
                          new Transition(2, '0', 2))),
                  0,
                  new HashSet<>(List.of(2))),
              new DFA( // accepts the only input 101.
                  new HashSet<>(Arrays.asList(0, 1, 2, 3)),
                  alphabet,
                  new HashSet<>(
                      Arrays.asList(
                          new Transition(0, '1', 1),
                          new Transition(1, '0', 2),
                          new Transition(2, '1', 3))),
                  0,
                  new HashSet<>(List.of(3))),
              new DFA( // accepts even number of 0's and even number of 1's.
                  new HashSet<>(Arrays.asList(0, 1, 2, 3)),
                  alphabet,
                  new HashSet<>(
                      Arrays.asList(
                          new Transition(0, '0', 1),
                          new Transition(0, '1', 3),
                          new Transition(1, '1', 0),
                          new Transition(1, '1', 2),
                          new Transition(2, '1', 1),
                          new Transition(2, '0', 3),
                          new Transition(3, '0', 2),
                          new Transition(3, '1', 0))),
                  0,
                  new HashSet<>(List.of(0))),
              new DFA(
                  new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5)),
                  alphabet,
                  new HashSet<>(
                      Arrays.asList(
                          new Transition(0, 'a', 1),
                          new Transition(0, 'b', 2),
                          new Transition(1, 'a', 1),
                          new Transition(1, 'b', 3),
                          new Transition(2, 'a', 1),
                          new Transition(2, 'b', 2),
                          new Transition(3, 'a', 1),
                          new Transition(3, 'b', 4),
                          new Transition(4, 'a', 1),
                          new Transition(4, 'b', 2),
                          new Transition(5, 'b', 3))),
                  0,
                  new HashSet<>(List.of(4)))));
  public static ArrayList<Expression> expressions;

  static {
    try {
      expressions =
          new ArrayList<>(
              Arrays.asList(
                  ExpressionFactory.parseExpression("h.e.l.l.o"), // accepts only the word "hello"
                  ExpressionFactory.parseExpression(
                      "(d.a*)*"), // accepts the strings on which when there's an 'a' there has to
                  // be a 'd' before it
                  ExpressionFactory.parseExpression(
                      "0.0.(0|1)*"), // accepts any combination of 1's and 0's which start with "00"
                  ExpressionFactory.parseExpression(
                      "0.(1.0)*"), // accepts strings that start with 0 and then have a
                  ExpressionFactory.parseExpression(
                      "((a|b).(a|b))*"), // accepts any combination of a's and b's that have a
                  // length pair
                  ExpressionFactory.parseExpression("((?|&)*.?)*"),
                  ExpressionFactory.parseExpression("(((a.b.?)*.(c|d)*)|((c.d.&)*.(a|b)*))|_"),
                  ExpressionFactory.parseExpression("a.b.c")));
    } catch (InvalidExpression e) {
      throw new RuntimeException(e);
    }
  }
}

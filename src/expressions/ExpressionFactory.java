package expressions;

import java.util.Stack;

public class ExpressionFactory {
  public static Expression parseExpression(String raw_expression) throws InvalidExpression {
    // Different symbols for the same symbol
    if (raw_expression.isEmpty()
        || raw_expression.equals("∼")
        || raw_expression.equals("~")
        || raw_expression.equals("⁓")) {
      return new Empty();
    }
    int length = raw_expression.length();
    int index;

    index = searchOutOfParenthesis(raw_expression, '|');
    if (index != -1) {
      return new Disjunction(
          parseExpression(raw_expression.substring(0, index)),
          parseExpression(raw_expression.substring(index + 1, length)));
    }

    index = searchOutOfParenthesis(raw_expression, '.');
    if (index != -1) {
      return new Concatenation(
          parseExpression(raw_expression.substring(0, index)),
          parseExpression(raw_expression.substring(index + 1, length)));
    }

    if (raw_expression.charAt(length - 1) == '*') {
      return new Kleene(parseExpression(raw_expression.substring(0, length - 1)));
    }

    if (raw_expression.charAt(0) == '(' && raw_expression.charAt(length - 1) == ')') {
      return new Parenthesis(parseExpression(raw_expression.substring(1, length - 1)));
    }

    if (length == 1) {
      return new Char(raw_expression.charAt(0));
    }

    return new Empty();
  }

  private static int searchOutOfParenthesis(String str, char toSearch) throws InvalidExpression {
    Stack<Character> stack = new Stack<>();

    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '(') {
        stack.push(c);
      }
      if (c == ')') {
        if (stack.isEmpty()) {
          throw new InvalidExpression("Bad enclosed parenthesis");
        }
        stack.pop();
      }
      if (stack.isEmpty() && c == toSearch) {
        return i;
      }
    }

    if (!stack.isEmpty()) {
      throw new InvalidExpression("Bad enclosed parenthesis");
    }

    return -1;
  }
}

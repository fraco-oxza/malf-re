import automatons.DFA;
import automatons.NFA;
import automatons.OptimizedDFA;
import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;

public class Application {
  public static void main(String[] args) throws InvalidExpression {
    Expression expression = ExpressionFactory.parseExpression(args[0]);

    var nfa = new NFA(expression);
    System.out.println("AFND M:");
    System.out.println(nfa);

    var dfa = new DFA(nfa);
    System.out.println("AFD M:");
    System.out.println(dfa);

    var optimizedDfa = new OptimizedDFA(dfa);
    System.out.println("AFD Optimizado M:");
    System.out.println(optimizedDfa);

  }
}

import automatons.Transition;
import automatons.DFA;
import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;
import utils.Examples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class Application {
    public static void main(String[] args) throws InvalidExpression {
        Expression expression = ExpressionFactory.parseExpression(args[0]);
        var automata = expression.getNFA();


        System.out.println(automata);
    }
}

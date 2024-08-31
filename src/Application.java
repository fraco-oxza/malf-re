import expressions.Expression;
import expressions.ExpressionFactory;
import expressions.InvalidExpression;

public class Application {
    public static void main(String[] args) throws InvalidExpression {
        Expression expression = ExpressionFactory.parseExpression(args[0]);
    }
}
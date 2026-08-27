package tgtlib.definitions.expression;

/**
 * useful if not id expression for CNF and DNF expressions
 * 
 * @author garganti
 *
 */
public class NotIDExpression extends NotExpression implements IdUNotIdExpression{

	
	public NotIDExpression(IdExpression operand) {
		super(operand);
	}

}

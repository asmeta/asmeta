package tgtlib.definitions.expression;

import java.util.Arrays;
import java.util.List;

import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.Type;

/**
 * function term ID + arguments (sort of arrays)
 * 
 * @author garganti
 *
 */
public class FunctionTerm implements Expression {

	// these cannot be used a function names - this library has specific classes for
	// this
	static private List<String> stdOperator = Arrays.asList("and", "or", "eq", "not", "implies");

	// to be used when it is not sure itf cab on operator
	public static Expression createFunctionTerm(IdExpression name, Type funcCoDomain, List<? extends Expression> args) {
		switch (name.getIdString()) {
		case "and":
			assert args.size() == 2;
			assert funcCoDomain == BoolType.BOOLTYPE;
			return new tgtlib.definitions.expression.AndExpression(args.get(0), args.get(1));
		case "eq":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.EqualsExpression(args.get(0), args.get(1));
		case "not":
			assert args.size() == 1;
			return new tgtlib.definitions.expression.NotExpression(args.get(0));
		default:
			return new FunctionTerm(name, funcCoDomain, args);
		}
	}

	private IdExpression function;

	private List<? extends Expression> arguments;

	private Type funcdomain;

	/**
	 * Instantiates a new function term.
	 *
	 * @param name         the name (and the domain - values of the argument of this
	 *                     function term)
	 * @param funcCoDomain the function codomain (the value of the application of
	 *                     this function will be in this set)
	 * @param args         the args
	 */
	public FunctionTerm(IdExpression name, Type funcCoDomain, List<? extends Expression> args) {
		assert !stdOperator.contains(name.getIdString()) : "operator " +name.getIdString();
		// assert args.size() == 1;
		function = name;
		arguments = args;
		this.funcdomain = funcCoDomain;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> visitor) {
		return visitor.forFunctionTerm(this);
	}

	public IdExpression getFunction() {
		return function;
	}

	public List<? extends Expression> getArguments() {
		return arguments;
	}

	public Type getCoDomain() {
		return funcdomain;
	}

	@Override
	public String toString() {
		return function.toString() + arguments.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof FunctionTerm) {
			FunctionTerm ft = (FunctionTerm) obj;
			return ft.getFunction().equals(this.getFunction()) && arguments.equals(((FunctionTerm) obj).arguments);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}

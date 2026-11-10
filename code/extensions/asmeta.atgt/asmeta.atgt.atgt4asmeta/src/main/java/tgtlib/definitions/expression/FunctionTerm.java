package tgtlib.definitions.expression;

import java.util.ArrayList;
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
	static private List<String> logicOperators = Arrays.asList("and", "or", "not", "implies", "xor");
	
	static private List<String> relOperators = Arrays.asList("eq", "neq", "ge", "le", "gt", "lt");
	static private List<String> mathOperators = Arrays.asList("div", "plus", "minus", "mod", "mult");

	static private List<String> stdOperator = new ArrayList<>();
	static {
		stdOperator.addAll(logicOperators);
		stdOperator.addAll(relOperators);
		stdOperator.addAll(mathOperators);
	}

	// to be used when it is not sure itf cab on operator
	public static Expression createFunctionTerm(IdExpression name, Type funcCoDomain, List<? extends Expression> args) {
		switch (name.getIdString()) {
		case "and":
			assert args.size() == 2;
			assert funcCoDomain == BoolType.BOOLTYPE;
			return new tgtlib.definitions.expression.AndExpression(args.get(0), args.get(1));
		case "or":
			assert args.size() == 2;
			assert funcCoDomain == BoolType.BOOLTYPE;
			return new tgtlib.definitions.expression.OrExpression(args.get(0), args.get(1));
		case "eq":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.EqualsExpression(args.get(0), args.get(1));
		case "neq":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.NotEqualsExpression(args.get(0), args.get(1));
		case "not":
			assert args.size() == 1;
			assert funcCoDomain == BoolType.BOOLTYPE;
			return new tgtlib.definitions.expression.NotExpression(args.get(0));
		case "lt":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.LessThanExpression(args.get(0),args.get(1));
		case "le":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.LessEqualExpression(args.get(0),args.get(1));
		case "gt":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.GreaterThanExpression(args.get(0),args.get(1));
		case "ge":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.GreaterEqualExpression(args.get(0),args.get(1));
		case "minus":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.MinusExpression(args.get(0),args.get(1));
		case "plus":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.PlusExpression(args.get(0),args.get(1));
		case "mod":
			assert args.size() == 2;
			return new tgtlib.definitions.expression.ModuloExpression(args.get(0),args.get(1));
		default:
			// simplest case - function term with arguments as IdExpression
			List<IdExpression> idargs = args.stream().map(x -> {
				assert x instanceof IdExpression : "function term argument not supported " + x + " in " + name;
				return (IdExpression) x;
			}).toList();
			return new FunctionTerm(name, funcCoDomain, idargs);
		}
	}

	// function + arguments
	private IdExpression function;
	private List<IdExpression> arguments;

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
	public FunctionTerm(IdExpression name, Type funcCoDomain, List<IdExpression> args) {
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

	public List<IdExpression> getArguments() {
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

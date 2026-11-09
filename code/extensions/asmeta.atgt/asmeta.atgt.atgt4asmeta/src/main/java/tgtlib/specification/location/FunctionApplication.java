package tgtlib.specification.location;

import java.util.List;
import java.util.stream.Collectors;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Type;

/**
 * location represented by a function application like functionTerm of
 * expression - function term in other cicurstances 
 * 
 */
public class FunctionApplication extends Location {

	private Function function;

	private List<IdExpression> args;
	

	public FunctionApplication(Function func, List<IdExpression> args) {		
		super(func.getIdExpression(), func.getValuesExpression());
		function = func;
		assert args != null;
		//assert args.size() == func.getDomain().getElements().size() : "Function application " + this.toString() + " has wrong number of arguments: expected " + func.getDomain().getElements().size() + ", got " + args.size();
		// check that every element in args is not null
		for (IdExpression arg : args) {
			assert arg != null : "Argument in function application " + this.toString() + " is null";
		}
		this.args = args;
	}

	public List<IdExpression> getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return name.toString() + "(" + args.stream ().map (i -> i.toString ()).collect (Collectors.joining (",")) + ")";
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionApplication) {
			return super.equals(obj) && this.args.equals(((FunctionApplication)obj).args);
		}
		return false;
	}

	@Override
	public <T> T accept(LocationVisitorI<T> ask) {
		return ask.forFunction(function);
	}

	public Type getDomain() {
		return function.getDomain();
	}

	public Type getCodomain() {
		return function.getCodomain();
	}
	
	public List<IdExpression> getArguments() {
		return args;
	}

	public Function getFunction() {
		return function;
	}
}

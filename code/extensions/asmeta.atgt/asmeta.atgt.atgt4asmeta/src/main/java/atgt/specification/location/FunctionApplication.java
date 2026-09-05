package atgt.specification.location;

import java.util.List;
import java.util.stream.Collectors;

import tgtlib.definitions.expression.IdExpression;

import org.junit.jupiter.api.Assertions;

/**
 * location represented by a function application like functionTerm of
 * expression
 * 
 */
public class FunctionApplication extends Function {
	private List<IdExpression> args;

	public FunctionApplication(Function var, List<IdExpression> args) {
		super(var.getIdExpression(), var.getDomain(), var.getCodomain(), var.getValue());
		assert args != null;
		// every element is not null
		args.stream().forEach(Assertions::assertNotNull);
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
	

}

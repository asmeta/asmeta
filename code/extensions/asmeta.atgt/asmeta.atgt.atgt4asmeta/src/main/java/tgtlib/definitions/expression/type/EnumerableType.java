package tgtlib.definitions.expression.type;

import java.util.List;

/**
 * types defined by its elements (like enumerative, bool)
 * 
 * @author garganti
 * 
 */
public abstract class EnumerableType extends Type {

	public EnumerableType(String _name) {
		super(_name);
	}

	abstract public List<?> allElements();

	
}

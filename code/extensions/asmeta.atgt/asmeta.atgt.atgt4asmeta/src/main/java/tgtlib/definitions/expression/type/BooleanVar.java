package tgtlib.definitions.expression.type;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;

/** represents a Boolean variable (not controlled)
 * 
 * @author garganti
 *
 */
public class BooleanVar implements Variable, Comparable<Variable>{

	IdExpression id;
	
	public BooleanVar(IdExpression id) {
		this.id = id;
	}
	
	@Override
	public boolean isControlled() {
		return false;
	}

	@Override
	public Expression getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return id.toString();
	}

	@Override
	public IdExpression getIdExpression() {
		return id;
	}

	@Override
	public Type getType() {
		return BoolType.BOOLTYPE;
	}
	
	@Override
	public String toString() {
		return id.getIdString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Variable) {
			Variable v = (Variable) obj;
			if (v.getIdExpression().equals(this.getIdExpression())){
				// must have same identical type and same ID
				assert v.getType() == this.getType() : v.getType().getName() +"!=" + this.getType().getName();
				assert v.getIdExpression() == this.getIdExpression() : "v1=" + this + " v2="+obj + "=>" + ((v.getIdExpression())) + "!=" + (this.getIdExpression());
				return true;
			} else{
				return false;
			}
		}
		return super.equals(obj);
	}


	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public int compareTo(Variable v2) {
		return id.compareTo(v2.getIdExpression());
	}
	
	
}

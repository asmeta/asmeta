/*
 * VectorOfConjonts.java
 *
 * Created on September 18, 2003, 9:12 PM
 */

package mcdc.scrtgtool.ucmcdc;

import java.util.Vector;

import tgtlib.definitions.expression.Expression;

/**
 * is a vector of Vectors of SimpleExpressions. every element has to be intended
 * as conjoint: this[0] = A,B,C => A and B and C this[1] = D, not A => D and not
 * A the meaning is like DNF: this[0] or this[1] see also CNFExpression.java
 * 
 * @author garganti
 */
public class VectorOfConjonts extends Vector<Vector<Expression>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 897696362447238372L;

	/** Creates a new instance of VectorOfConjonts */
	public VectorOfConjonts() {
	}

	/*
	 * build a singleton with only one expression exp -> {{exp}}
	 */
	public static VectorOfConjonts signleton(Expression exp) {
		VectorOfConjonts result = new VectorOfConjonts();
		Vector<Expression> singleton = new Vector<Expression>(1);
		singleton.add(exp);
		result.add(singleton);
		return result;
	}

	/**
	 * correspond to the v1 and v2 combine two voc: v1 =
	 * {{x11,x12,x13}{x21,...}...} v2 = {{y11,y12,y13}{y21,...}...} result =
	 * {{x11,x12,x13, y11,y12,y13}{x21,...y11,y12,y13}...}
	 */
	public static VectorOfConjonts combineAND(VectorOfConjonts v1,
			VectorOfConjonts v2) {
		if (v1 == VectorOfConjonts.EMPTY_CONJ)
			return v2;
		if (v2 == VectorOfConjonts.EMPTY_CONJ)
			return v1;
		// both different from empty
		VectorOfConjonts result = new VectorOfConjonts();
		for (Vector<Expression> i1 : v1) {
			for (Vector<Expression> i2 : v2) {
				Vector<Expression> oneList = new Vector<Expression>();
				oneList.addAll(i1);
				oneList.addAll(i2);
				result.add(oneList);
			}
		}
		return result;
	}

	/**
	 * @uml.property name="eMPTY_CONJ"
	 * @uml.associationEnd
	 */
	static public VectorOfConjonts EMPTY_CONJ = new VectorOfConjonts();

	@Override
	public String toString() {
		String result = new String("[");
		for (Vector<Expression> element_i : this) {
			boolean first = true;
			result += "[";
			for (Expression element : element_i) {
				if (!first)
					result += ",";
				else
					first = false;
				result += "" + element.toString();
			}
			result += "]";
		}
		result += "]";
		return result;
	}
}
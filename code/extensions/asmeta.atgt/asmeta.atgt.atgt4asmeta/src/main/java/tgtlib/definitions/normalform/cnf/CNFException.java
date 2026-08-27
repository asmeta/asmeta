package tgtlib.definitions.normalform.cnf;


import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.type.BoolType;

// if it is not traslable to CNF
public class CNFException extends RuntimeException {

	public BoolType.BoolConst equivalent;
	
	public CNFException(Expression expr, BoolType.BoolConst eqConst) {
		super(expr.toString() + " simplified to " + eqConst.toString() + " is not translable to CNF");
		equivalent = eqConst;
	}
	

}

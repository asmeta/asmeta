package atgt.specification.expression;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.XOrExpression;

/**
 * replaces an id with another
 * 
 * @author garganti
 * 
 */
public class IdReplacerVisitor extends AsmExpressionVisitor<Expression> {

	
	static 	Logger logger = Logger.getLogger(IdReplacerVisitor.class);
	
	IdExpression tobereplaced;
	IdExpression replacement;

	/**
	 * R[a <- b]
	 * 
	 * @param a
	 *            to be replaced
	 * @param b
	 *            replacement
	 */
	public IdReplacerVisitor(IdExpression a, IdExpression b) {
		assert a != b;
		assert (!a.toString().equals(b.toString()));
		tobereplaced = a;
		replacement = b;
	}

	@Override
	public Expression forIdExpression(IdExpression idExpression) {
		if (idExpression == tobereplaced)
			return replacement;
		// never equals the strings if they are not ==
		assert (!idExpression.getIdString().equals(tobereplaced.getIdString())) : "'" + idExpression.getIdString() + "' != '" + tobereplaced.getIdString()+ "'";
		// return the original (could be even the replacement itself)
		return idExpression;
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		boolean changed = false;		
		IdExpression newFN = (IdExpression) ft.getFunction().accept(this);
		if (newFN!= ft.getFunction()) changed = true;
		List<Expression> newExps = new ArrayList<Expression>(ft.getArguments().size());
		for(Expression a : ft.getArguments()){
			Expression newA = a.accept(this);
			newExps.add(newA);
			if (newA != a)
				changed = true;
		}
		if (changed) {
			FunctionTerm result = new FunctionTerm(newFN, ft.getCoDomain(), newExps);
			logger.debug(ft + "-> " + result);
			return result;
		}
		logger.debug(ft + " not changed - replacing " +tobereplaced + " with "+ replacement);
		return ft;
	}

	
	// To be implemented yet

	@Override
	public Expression forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		return forBinaryExpression(andExpression, Operator.AND);
	}

	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		return forBinaryExpression(orExpression, Operator.OR);
	}

	@Override
	public Expression forXOrExpression(XOrExpression xOrExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forImpliesExpression(ImpliesExpression impliesExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forEqualsExpression(EqualsExpression equalsExpression) {
		return forBinaryExpression(equalsExpression, Operator.EQ);
	}

	@Override
	public Expression forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forLessThanExpression(
			LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return forBinaryExpression(notEqualsExpression, Operator.NEQ);
	}

	@Override
	public Expression forDivExpression(DivExpression divExpression) {
		return forBinaryExpression(divExpression,Operator.DIV);
	}

	@Override
	public Expression forPlusExpression(PlusExpression plusExpression) {
		return forBinaryExpression(plusExpression,Operator.PLUS);
	}

	private Expression forBinaryExpression(BinaryExpression expr,
			Operator op) {
		Expression e1 = expr.getFirstOperand();
		Expression e2 = expr.getSecondOperand();
		Expression er1 = e1.accept(this);
		Expression er2 = e2.accept(this);
		if (er1 == e1 && er2 == e2) return expr;
		else return BinaryExpression.mkBinExpr(er1, op, er2);
	}

	@Override
	public Expression forMinusExpression(MinusExpression minusExpression) {
		return forBinaryExpression(minusExpression,Operator.MINUS);
	}

	@Override
	public Expression forMultExpression(MultExpression multExpression) {
		return forBinaryExpression(multExpression,Operator.MULT);
	}

	@Override
	public Expression forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forModuloExpression(ModuloExpression moduloExpression) {
		return forBinaryExpression(moduloExpression,Operator.MOD);
	}

	@Override
	public Expression forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

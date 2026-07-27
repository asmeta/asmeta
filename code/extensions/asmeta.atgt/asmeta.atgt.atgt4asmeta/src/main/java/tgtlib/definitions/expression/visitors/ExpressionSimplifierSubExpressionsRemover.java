package tgtlib.definitions.expression.visitors;

import java.util.HashSet;
import java.util.Set;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
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
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoolType.BoolConst;

/**
 * It simplifies the expression by only keeping the subexpressions
 * containing the variables in idsToKeep.
 *
 */
public class ExpressionSimplifierSubExpressionsRemover {
	private Set<IdExpression> idsToKeep;
	
	public ExpressionSimplifierSubExpressionsRemover(Set<IdExpression> idsToKeep) {
		this.idsToKeep = idsToKeep;
	}

	public Expression simplify(Expression exp) {
		Set<IdExpression> setExp = new HashSet<IdExpression>(exp.accept(IDExprCollector.instance));
		setExp.retainAll(idsToKeep);
		if(setExp.isEmpty()) {
			return BoolType.TRUE_CONST; 
		}
		else {
			ExpressionSimplifierSubExpressionsRemoverVisitor visitor = new ExpressionSimplifierSubExpressionsRemoverVisitor(idsToKeep, true);
			return exp.accept(visitor);
		}		
	}
		
}

class ExpressionSimplifierSubExpressionsRemoverVisitor implements ExpressionVisitor<Expression> {
	private Set<IdExpression> idsToKeep;
	private boolean positivePolarity;
	
	//private Expression polarity;
		
	public ExpressionSimplifierSubExpressionsRemoverVisitor(Set<IdExpression> idsToKeep,boolean p) {
		this.idsToKeep = idsToKeep;
		positivePolarity = true;
	}

	@Override
	public Expression forIdExpression(IdExpression idExpression) {
		return idExpression;
	}

	@Override
	public Expression forImpliesExpression(ImpliesExpression impliesExpression) {
		//return forBinaryExpression(impliesExpression,Operator.IMPLIES);
		//return BinaryExpression.mkBinExpr(NotExpression.createNotExpression(impliesExpression.getFirstOperand()), Operator.OR, impliesExpression.getSecondOperand()).accept(this);
		Expression e1 = impliesExpression.getFirstOperand();
		Expression e2 = impliesExpression.getSecondOperand();
		Set<IdExpression> set1 = new HashSet<IdExpression>(e1.accept(IDExprCollector.instance));
		Set<IdExpression> set1temp = new HashSet<IdExpression>(set1);
		Set<IdExpression> set2 = new HashSet<IdExpression>(e2.accept(IDExprCollector.instance));
		set1temp.retainAll(set2);
		//the two subexpressions do not have common variables
		if(set1temp.isEmpty()) {
			set1.retainAll(idsToKeep);
			set2.retainAll(idsToKeep);
			assert !set1.isEmpty() || !set2.isEmpty();
			if(positivePolarity) {
				if(set2.isEmpty() || set1.isEmpty()) {
					return BoolType.TRUE_CONST;
				}
			}
			else {
				if(set1.isEmpty()) {
					return e2.accept(this);
				}
				else if(set2.isEmpty()) {
					changePolarity();
					Expression out = e1.accept(this);
					changePolarity();
					return out;
				}
			}
			Expression newe1 = e1.accept(this);
			Expression newe2 = e2.accept(this);
			if(newe1.equals(BoolType.FALSE_CONST) || newe2.equals(BoolType.TRUE_CONST)) {
				return BoolType.TRUE_CONST;
			}
			else if(newe1.equals(BoolType.TRUE_CONST) & newe2.equals(BoolType.FALSE_CONST)) {
				return BoolType.FALSE_CONST;
			}
			return BinaryExpression.mkBinExpr(newe1, Operator.IMPLIES, newe2);
		}
		return impliesExpression;
	}

	private void changePolarity() {
		positivePolarity = !positivePolarity;
	}

	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		//return forBinaryExpression(andExpression,Operator.AND);
		Expression e1 = andExpression.getFirstOperand();
		Expression e2 = andExpression.getSecondOperand();
		Set<IdExpression> set1 = new HashSet<IdExpression>(e1.accept(IDExprCollector.instance));
		Set<IdExpression> set1temp = new HashSet<IdExpression>(set1);
		Set<IdExpression> set2 = new HashSet<IdExpression>(e2.accept(IDExprCollector.instance));
		set1temp.retainAll(set2);
		//the two subexpressions do not have common variables
		if(set1temp.isEmpty()) {
			set1.retainAll(idsToKeep);
			set2.retainAll(idsToKeep);
			assert !set1.isEmpty() || !set2.isEmpty();
			if(positivePolarity) {
				if(set1.isEmpty()) {
					return e2.accept(this);
				}
				else if(set2.isEmpty()) {
					return e1.accept(this);
				}
			}
			else if(set1.isEmpty() || set2.isEmpty()) {
				//bisognerebbe controllare che quella vuota sia SAT
				return BoolType.FALSE_CONST;
			}
			Expression newe1 = e1.accept(this);
			Expression newe2 = e2.accept(this);
			if(newe1.equals(BoolType.FALSE_CONST) || newe2.equals(BoolType.FALSE_CONST)) {
				return BoolType.FALSE_CONST;
			}
			else if(newe1.equals(BoolType.TRUE_CONST)) {
				if(newe2.equals(BoolType.TRUE_CONST)) {
					return BoolType.TRUE_CONST;
				}
				else {
					return newe2;
				}
			}
			else if(newe2.equals(BoolType.TRUE_CONST)) {
				return newe1;
			}
			return BinaryExpression.mkBinExpr(newe1, Operator.AND, newe2);
		}
		return andExpression;
	}

	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		//return forBinaryExpression(orExpression,Operator.OR);
		Expression e1 = orExpression.getFirstOperand();
		Expression e2 = orExpression.getSecondOperand();
		Set<IdExpression> set1 = new HashSet<IdExpression>(e1.accept(IDExprCollector.instance));
		Set<IdExpression> set1temp = new HashSet<IdExpression>(set1);
		Set<IdExpression> set2 = new HashSet<IdExpression>(e2.accept(IDExprCollector.instance));
		set1temp.retainAll(set2);
		//the two subexpressions do not have common variables
		if(set1temp.isEmpty()) {
			set1.retainAll(idsToKeep);
			set2.retainAll(idsToKeep);
			assert !set1.isEmpty() || !set2.isEmpty();
			if(positivePolarity) {
				if(set1.isEmpty() || set2.isEmpty()) {
					//bisognerebbe controllare che quella vuota sia SAT
					return BoolType.TRUE_CONST;
				}
			}
			else {
				if(set1.isEmpty()) {
					return e2.accept(this);
				}
				else if(set2.isEmpty()) {
					return e1.accept(this);
				}
			}
			Expression newe1 = e1.accept(this);
			Expression newe2 = e2.accept(this);
			if(newe1.equals(BoolType.TRUE_CONST) || newe2.equals(BoolType.TRUE_CONST)) {
				return BoolType.TRUE_CONST;
			}
			else if(newe1.equals(BoolType.FALSE_CONST)) {
				return newe2;
			}
			else if(newe2.equals(BoolType.FALSE_CONST)) {
				return newe1;
			}
			return BinaryExpression.mkBinExpr(newe1, Operator.OR, newe2);
		}
		return orExpression;
	}

	@Override
	public Expression forXOrExpression(XOrExpression xOrExpression) {
		//return forBinaryExpression(xOrExpression, Operator.XOR);
		Expression e1 = xOrExpression.getFirstOperand();
		Expression e2 = xOrExpression.getSecondOperand();
		Set<IdExpression> set1 = new HashSet<IdExpression>(e1.accept(IDExprCollector.instance));
		Set<IdExpression> set1temp = new HashSet<IdExpression>(set1);
		Set<IdExpression> set2 = new HashSet<IdExpression>(e2.accept(IDExprCollector.instance));
		set1temp.retainAll(set2);
		//the two subexpressions do not have common variables
		if(set1temp.isEmpty()) {
			set1.retainAll(idsToKeep);
			set2.retainAll(idsToKeep);
			//it should not depend on the polarity
			assert !set1.isEmpty() || !set2.isEmpty();
			if(set1.isEmpty() || set2.isEmpty()) {
				//we should check that the expression with no variables to keep is SAT
				if(positivePolarity) {
					return BoolType.TRUE_CONST;
				}
				else {
					return BoolType.FALSE_CONST;
				}
			}
			assert false;
			//Do I have to change the polarity? 
			Expression newe1 = e1.accept(this);
			Expression newe2 = e2.accept(this);
			return BinaryExpression.mkBinExpr(newe1, Operator.XOR, newe2);
		}
		return xOrExpression;
	}

	private Expression forBinaryExpression(BinaryExpression e, Operator op) {
		assert false;
		return null;
		/*Expression e1 = e.getFirstOperand();
		Expression e2 = e.getSecondOperand();
		Set<IdExpression> set1 = new HashSet<IdExpression>(e1.accept(IDExprCollector.instance));
		Set<IdExpression> set1temp = new HashSet<IdExpression>(set1);
		Set<IdExpression> set2 = new HashSet<IdExpression>(e2.accept(IDExprCollector.instance));
		set1temp.retainAll(set2);
		//the two subexpressions do not have common variables
		if(set1temp.isEmpty()) {
			set1.retainAll(idsToKeep);
			set2.retainAll(idsToKeep);
			if(set1.isEmpty()) {
				if(set2.isEmpty()) {
					return polarity;
				}
				else {
					return e2.accept(this);
				}
			}
			else {
				if(set2.isEmpty()) {
					return e1.accept(this);
				}
				else {
					Expression newe1 = e1.accept(this);
					Expression newe2 = e2.accept(this);
					return BinaryExpression.mkBinExpr(newe1, op, newe2);
				}
			}
		}
		return e;*/
	}

	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		changePolarity();
		Expression operand = notExpression.getOperand().accept(this);
		if(operand instanceof BoolConst) {
			return BoolType.not((BoolConst)operand);
		}
		return NotExpression.createNotExpression(operand);
	}

	@Override
	public Expression forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("not implemented yet");
	}


	@Override
	public Expression forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forEqualsExpression(EqualsExpression equalsExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forLessThanExpression(
			LessThanExpression lessThanExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return forBinaryExpression(notEqualsExpression,Operator.NEQ);
	}

	@Override
	public Expression forDivExpression(DivExpression divExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forPlusExpression(PlusExpression plusExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forMinusExpression(MinusExpression minusExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forMultExpression(MultExpression multExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forNegExpression(NegExpression negExpression) {

		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forModuloExpression(ModuloExpression moduloExpression) {

		throw new RuntimeException("not implemented yet");
	}
	@Override
	public Expression forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Expression forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}

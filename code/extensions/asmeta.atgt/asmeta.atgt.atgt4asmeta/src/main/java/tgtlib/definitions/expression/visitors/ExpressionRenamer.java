package tgtlib.definitions.expression.visitors;

import java.util.HashMap;
import java.util.Map;

import tgtlib.definitions.expression.AndExpression;
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
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.EnumConstCreator;

public class ExpressionRenamer implements ExpressionVisitor<Expression> {
	private EnumConstCreator idcreator;
	private Map<IdExpression, IdExpression> mapIds;
	private String prefix;

	public ExpressionRenamer(String prefix) {
		idcreator = new EnumConstCreator();
		mapIds = new HashMap<IdExpression, IdExpression>();
		this.prefix = prefix;
	}

	@Override
	public Expression forIdExpression(IdExpression idExpression) {
		if(mapIds.containsKey(idExpression)) {
			return mapIds.get(idExpression);
		}
		IdExpression newId = idcreator.createIdExpression(prefix + idExpression.getIdString(), null);
		mapIds.put(idExpression, newId);
		return newId;
	}

	@Override
	public Expression forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		assert false;
		return null;
	}

	@Override
	public Expression forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forAndExpression(AndExpression andExpression) {
		Expression first = andExpression.getFirstOperand().accept(this);
		Expression second = andExpression.getSecondOperand().accept(this);
		return new AndExpression(first, second);
	}

	@Override
	public Expression forOrExpression(OrExpression orExpression) {
		Expression first = orExpression.getFirstOperand().accept(this);
		Expression second = orExpression.getSecondOperand().accept(this);
		return new OrExpression(first, second);
	}

	@Override
	public Expression forXOrExpression(XOrExpression xOrExpression) {
		Expression first = xOrExpression.getFirstOperand().accept(this);
		Expression second = xOrExpression.getSecondOperand().accept(this);
		return new XOrExpression(first, second);
	}

	@Override
	public Expression forNotExpression(NotExpression notExpression) {
		return NotExpression.createNotExpression(notExpression.getOperand().accept(this));
	}

	@Override
	public Expression forImpliesExpression(ImpliesExpression impliesExpression) {
		Expression first = impliesExpression.getFirstOperand().accept(this);
		Expression second = impliesExpression.getSecondOperand().accept(this);
		return new ImpliesExpression(first, second);
	}

	@Override
	public Expression forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forEqualsExpression(EqualsExpression equalsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forLessThanExpression(
			LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forDivExpression(DivExpression divExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forPlusExpression(PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forMinusExpression(MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		return null;
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
package tgtlib.definitions.expression.visitors;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.XOrExpression;

public class IsomorphicComparator implements Comparator<Expression> {

	@Override
	public int compare(Expression arg0, Expression arg1) {
		Boolean res = arg0.accept(new ExpressionsComparator(arg1));
		//System.out.println(arg0 + "\t" + arg1 + "\t\tres = " + res);
		if (res)
			return 0;
		return arg0.toString().compareTo(arg1.toString());
	}
}

class ExpressionsComparator implements ExpressionVisitor<Boolean> {
	Expression currArg1;
	BijectiveMap map;

	public ExpressionsComparator(Expression arg1) {
		this(arg1, new BijectiveMap());
	}

	public ExpressionsComparator(Expression arg1, BijectiveMap map) {
		currArg1 = arg1;
		ExpressionRenamer renamer = new ExpressionRenamer("secondExpression");
		currArg1 = currArg1.accept(renamer);//all the ids of the second expression are renamed
		this.map = map;
	}

	public BijectiveMap getMap() {
		return map;
	}

	@Override
	public Boolean forAndExpression(AndExpression andExpression) {
		if (!(currArg1 instanceof AndExpression))
			return false;
		return visitBinaryExpr(andExpression);
	}

	@Override
	public Boolean forOrExpression(OrExpression orExpression) {
		if (!(currArg1 instanceof OrExpression))
			return false;
		return visitBinaryExpr(orExpression);
	}

	@Override
	public Boolean forXOrExpression(XOrExpression xOrExpression) {
		if (!(currArg1 instanceof XOrExpression))
			return false;
		return visitBinaryExpr(xOrExpression);
	}

	@Override
	public Boolean forImpliesExpression(ImpliesExpression impliesExpression) {
		if (!(currArg1 instanceof ImpliesExpression))
			return false;
		return visitBinaryExpr(impliesExpression);
	}

	@Override
	public Boolean forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	private Boolean visitBinaryExpr(BinaryExpression binExpr) {
		assert currArg1.getClass() == binExpr.getClass();
		BinaryExpression biNCurr = (BinaryExpression) currArg1;
		currArg1 = biNCurr.getFirstOperand();
		Boolean resLeft = binExpr.getFirstOperand().accept(this);
		if (!resLeft)
			return false;
		// right
		currArg1 = biNCurr.getSecondOperand();
		Boolean resRight = binExpr.getSecondOperand().accept(this);
		return resRight;

		//second version
		/*
		//given "a op b" and "c op d"
		//we check if "a" is isomorphic to "c" and "b" to "d"
		Expression arg1 = currArg1;
		BinaryExpression biNCurr = (BinaryExpression) arg1;
		currArg1 = biNCurr.getFirstOperand();
		//left
		if (binExpr.getFirstOperand().accept(this)) {
			// right
			currArg1 = biNCurr.getSecondOperand();
			if (binExpr.getSecondOperand().accept(this))
				return true;
		}

		//we check if "a" is isomorphic to "d" and "b" to "c"
		biNCurr = (BinaryExpression) arg1;
		currArg1 = biNCurr.getSecondOperand();
		// left with right
		if (binExpr.getFirstOperand().accept(this)) {
			// right with left
			currArg1 = biNCurr.getFirstOperand();
			if (binExpr.getSecondOperand().accept(this))
				return true;
		}
		return false;*/
	}

	@Override
	public Boolean forNotExpression(NotExpression notExpression) {
		if (currArg1.getClass() != notExpression.getClass())
			return false;
		return visitUnaryExpr(notExpression);
	}

	private Boolean visitUnaryExpr(UnaryExpression unaryExpr) {
		assert currArg1.getClass() == unaryExpr.getClass(): currArg1.getClass().getSimpleName() + "\n" +unaryExpr.getClass().getSimpleName() + "\ncurrArg1 = " + currArg1  + "\nunaryExpr = " + unaryExpr;
		UnaryExpression biNCurr = (UnaryExpression) currArg1;
		currArg1 = biNCurr.getOperand();
		Boolean resLeft = unaryExpr.getOperand().accept(this);
		return resLeft;
	}

	@Override
	public Boolean forIdExpression(IdExpression id0) {
		if (!(currArg1 instanceof IdExpression))
			return false;
		// both id
		IdExpression id1 = (IdExpression) currArg1;
		return map.associate(id1,id0);
	}

	@Override
	public Boolean forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		throw new RuntimeException();
	}

	@Override
	public Boolean forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forEqualsExpression(EqualsExpression equalsExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forLessThanExpression(
			LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forDivExpression(DivExpression divExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forPlusExpression(PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forMinusExpression(MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public Boolean forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}

// assume che due id con stesso nome sono anche ==
class BijectiveMap {
	// keep both associations (a->b e b->a)
	Map<IdExpression, IdExpression> idMap = new HashMap<IdExpression, IdExpression>();

	// return true if id1 is already associated to id0 or they are both free 
	// and become associated
	public Boolean associate(IdExpression id0, IdExpression id1) {
		assert id0 != null && id1 != null;
		IdExpression ida0 = idMap.get(id0);			
		if (ida0 == id1)
			return true;
		if (ida0 != null)
			return false;
		//ida0 == null
		IdExpression ida1 = idMap.get(id1);
		if (ida1 != null)
			return false;
		// both null add links (both)
		idMap.put(id0, id1);
		idMap.put(id1, id0);
		return true;

		//this version is not correct (see test "testWithConstraints10")
		/*IdExpression ida0 = idMap.get(id0);			
		if (ida0 == id1)
			return true;
		if (ida0 != null)
			return false;
		idMap.put(id0, id1);
		return true;*/
	}
	
}
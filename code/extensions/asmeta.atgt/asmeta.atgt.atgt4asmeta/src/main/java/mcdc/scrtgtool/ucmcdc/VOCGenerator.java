package mcdc.scrtgtool.ucmcdc;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GetOperator;
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
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.XOrExpression;

public class VOCGenerator implements ExpressionVisitor<VectorOfConjonts> {

	/** */
	protected boolean valueDesired;

	private VOCGeneratorFactory factory;
	
	public VOCGenerator(boolean valueDesired) {
		super();
		this.valueDesired = valueDesired;
	}
	
	public void setVOCGeneratorFactory(VOCGeneratorFactory vocf){
		factory = vocf;
	}
	

	public VectorOfConjonts forBinaryExpression(BinaryExpression bex) {
		VectorOfConjonts result = new VectorOfConjonts();
		Operator op = bex.accept(GetOperator.INSTANCE);
		if ((op == Operator.AND) || (op == Operator.OR)) {
			// get the pair for the first one
			VOCGenerator vg1 = factory.createVectorOCGenerator(valueDesired);
			VectorOfConjonts vc1 = bex.getFirstOperand().accept(vg1);
			VOCGenerator vg2 = factory.createVectorOCGenerator(valueDesired);
			VectorOfConjonts vc2 = bex.getSecondOperand().accept(vg2);
			// start with the value desired for both
			// in any case if both have value desired => final value is desired
			// the final result is what desired
			result.addAll(VectorOfConjonts.combineAND(vc1, vc2));
			// if AND and want false or OR and want true
			// I have to add other combinations
			if (((op == Operator.AND) && !valueDesired)
					|| ((op == Operator.OR) && valueDesired)) {
				// second case:
				VectorOfConjonts nv1 = bex.getFirstOperand().accept(
						factory.createVectorOCGenerator(!valueDesired));
				VectorOfConjonts nv2 = bex.getSecondOperand().accept(
						factory.createVectorOCGenerator(!valueDesired));
				// first case op2 opposite
				result.addAll(VectorOfConjonts.combineAND(nv1, vc2));
				// second case: not l and m
				result.addAll(VectorOfConjonts.combineAND(nv2, vc1));
			}
		} else {
			// ATOMIC
			if (valueDesired)
				result = VectorOfConjonts.signleton(bex);
			else
				result = VectorOfConjonts.signleton(UnaryExpression
						.mkUnExpr(Operator.NOT, bex));
		}
		return result;
	
	}

	@Override
	public VectorOfConjonts forIdExpression(IdExpression id) {
		if (valueDesired)
			return VectorOfConjonts.signleton(id);
		else
			return VectorOfConjonts.signleton(UnaryExpression
					.mkUnExpr(Operator.NOT, id));
	}

	public VectorOfConjonts forUnaryExpression(UnaryExpression u) {
		if (u.accept(GetOperator.INSTANCE) == Operator.NOT)
			return u.getOperand()
					.accept(factory.createVectorOCGenerator(!valueDesired));
		else {
			if (valueDesired)
				return VectorOfConjonts.signleton(u);
			else
				return VectorOfConjonts.signleton(UnaryExpression
						.mkUnExpr(Operator.NOT, u));
		}
	}

	@Override
	public VectorOfConjonts forPrimedIdExpression(PrimedIdExpression id) {
		// TODO
		assert (false);
				throw new RuntimeException("not implemented");
	}

	@Override
	public VectorOfConjonts forAndExpression(AndExpression andExpression) {
		return forBinaryExpression(andExpression);
	}

	@Override
	public VectorOfConjonts forDivExpression(DivExpression divExpression) {
		return forBinaryExpression(divExpression);
	}

	@Override
	public VectorOfConjonts forEqualsExpression(
			EqualsExpression equalsExpression) {
		return forBinaryExpression(equalsExpression);
	}

	@Override
	public VectorOfConjonts forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		return forBinaryExpression(greaterEqualExpression);
	}

	@Override
	public VectorOfConjonts forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		return forBinaryExpression(greaterThanExpression);
	}

	@Override
	public VectorOfConjonts forImpliesExpression(
			ImpliesExpression impliesExpression) {
		return forBinaryExpression(impliesExpression);
	}

	@Override
	public VectorOfConjonts forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		return forBinaryExpression(lessEqualExpression);
	}

	@Override
	public VectorOfConjonts forLessThanExpression(
			LessThanExpression lessThanExpression) {
		return forBinaryExpression(lessThanExpression);
	}

	@Override
	public VectorOfConjonts forMinusExpression(MinusExpression minusExpression) {
		return forBinaryExpression(minusExpression);
	}

	@Override
	public VectorOfConjonts forMultExpression(MultExpression multExpression) {
		return forBinaryExpression(multExpression);
	}

	@Override
	public VectorOfConjonts forNegExpression(NegExpression negExpression) {
		return forUnaryExpression(negExpression);
	}

	@Override
	public VectorOfConjonts forNextExpression(NextExpression nextExpression) {
		return forUnaryExpression(nextExpression);
	}

	@Override
	public VectorOfConjonts forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		return forBinaryExpression(notEqualsExpression);
	}

	@Override
	public VectorOfConjonts forNotExpression(NotExpression notExpression) {
		return forUnaryExpression(notExpression);
	}

	@Override
	public VectorOfConjonts forOrExpression(OrExpression orExpression) {
		return forBinaryExpression(orExpression);
	}

	@Override
	public VectorOfConjonts forPlusExpression(PlusExpression plusExpression) {
		return forBinaryExpression(plusExpression);
	}

	@Override
	public VectorOfConjonts forXOrExpression(XOrExpression xOrExpression) {
		return forBinaryExpression(xOrExpression);
	}

	@Override
	public VectorOfConjonts forModuloExpression(
			ModuloExpression moduloExpression) {
		return forBinaryExpression(moduloExpression);
	}

	@Override
	public VectorOfConjonts forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
				throw new RuntimeException("not implemented");
	}
	@Override
	public VectorOfConjonts forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public VectorOfConjonts forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
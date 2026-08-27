package extgt.coverage.mcdc;

import java.util.ArrayList;
import java.util.List;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
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
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.util.Pair;

/**
 * Given an expression it computes the build the derivative as P[a <- true] xor
 * P[a<-false]. It returns the substituted id and the pair of its derivatives
 * e.g. a
 */
public class BoolDerivativeVisitor extends
		MCDCExprVisitor<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> {

	static BoolDerivativeVisitor instance = new BoolDerivativeVisitor();

	private BoolDerivativeVisitor() {
	}

	@Override
	public final List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forXOrExpression(XOrExpression e) {
		return forBinaryExpression(e, Operator.XOR);
	}

	
	@Override
	protected Pair<IdExpression, Pair<NamedTerm, NamedTerm>> makeTFPair(
			Expression e) {
		Pair<NamedTerm, NamedTerm> makeTF = new Pair<NamedTerm, NamedTerm>(
				new NamedTerm("T", BoolType.TRUE_CONST), new NamedTerm("F",
						BoolType.FALSE_CONST));
		return new Pair<IdExpression, Pair<NamedTerm, NamedTerm>>((IdExpression) e, makeTF);
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forNotExpression(
			NotExpression notExpression) {
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> innerResult = notExpression.getOperand().accept(this);
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = new ArrayList<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>>();
		// put now the not inside
		for (Pair<IdExpression, Pair<NamedTerm, NamedTerm>> r : innerResult) {
			Expression c1 = r.getSecond().getFirst().getCondition();
			Expression c2 = r.getSecond().getSecond().getCondition();
			NotExpression nc1 = NotExpression.createNotExpression(c1);
			NotExpression nc2 = NotExpression.createNotExpression(c2);
			Pair<NamedTerm, NamedTerm> np = new Pair<NamedTerm, NamedTerm>(
					new NamedTerm(r.getSecond().getFirst().getName(), nc1),
					new NamedTerm(r.getSecond().getSecond().getName(), nc2));
			result.add(new Pair<IdExpression, Pair<NamedTerm, NamedTerm>>(r
					.getFirst(), np));
		}
		return result;
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forPrimedIdExpression(
			PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forNextExpression(
			NextExpression nextExpression) {
		throw new RuntimeException("not implemented");
	}

	@Override
	protected void addToSet(
			List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> set1,
			Expression e2,
			List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result,
			Operator op, boolean putfirst) {
		assert (op == Operator.AND || op == Operator.OR || op == Operator.XOR);
		// walk the vector X
		for (Pair<IdExpression, Pair<NamedTerm, NamedTerm>> x : set1) {
			NamedTerm n1 = x.getSecond().getFirst();
			NamedTerm n2 = x.getSecond().getSecond();
			Expression n1e;
			Expression n2e;
			if (!putfirst) {
				n1e = BinaryExpression.mkBinExpr(n1.getCondition(), op, e2);
				n2e = BinaryExpression.mkBinExpr(n2.getCondition(), op, e2);
			} else {
				n1e = BinaryExpression.mkBinExpr(e2, op, n1.getCondition());
				n2e = BinaryExpression.mkBinExpr(e2, op, n2.getCondition());
			}
			NamedTerm toAdd1 = new NamedTerm(n1.getName(), n1e);
			NamedTerm toAdd2 = new NamedTerm(n2.getName(), n2e);
			result.add(new Pair<IdExpression, Pair<NamedTerm, NamedTerm>>(x
					.getFirst(), new Pair<NamedTerm, NamedTerm>(toAdd1, toAdd2)));
		}
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forEqualsExpression(
			EqualsExpression equalsExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forLessEqualExpression(
			LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forLessThanExpression(
			LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forNotEqualsExpression(
			NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forDivExpression(
			DivExpression divExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forPlusExpression(
			PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forMinusExpression(
			MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forMultExpression(
			MultExpression multExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forNegExpression(
			NegExpression negExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forModuloExpression(
			ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
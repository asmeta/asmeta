package extgt.coverage.mcdc;

import java.util.ArrayList;
import java.util.List;

import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;
import tgtlib.util.Pair;

/**
 * build the coverage tree using the BoolDerivative
 * 
 * @author garganti
 * 
 * @param <S>
 * @param <P>
 * @param <C>
 */
public abstract class MaskingMCDCDerivCovBuilder<S extends Specification, P extends TestPredicate<?,?>, C extends CoverageTree<P>>
		extends SpecAnalyzerCovBuilder<Expression, S, P, C> {

	public MaskingMCDCDerivCovBuilder(
			SpecificationAnalyzer<Expression, S> specAn,
			CoverageTreeFactory<C> ctf, TestPredicateFactory<? extends P> tpf) {
		super(specAn, ctf, tpf);
	}

	@Override
	public String getCoveragePrefix() {
		return "mMCDCd";
	}

	@Override
	protected Iterable<NamedTerm> getTestPredicates(Expression expr) {
		// get all the derivatives
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> res = expr.accept(BoolDerivativeVisitor.instance);
		List<NamedTerm> result = new ArrayList<NamedTerm>();
		for (Pair<IdExpression, Pair<NamedTerm, NamedTerm>> r : res) {
			IdExpression id = r.getFirst();
			NamedTerm n1 = r.getSecond().getFirst();
			NamedTerm n2 = r.getSecond().getSecond();
			BinaryExpression der = BinaryExpression.mkBinExpr(
					n1.getCondition(), Operator.XOR, n2.getCondition());
			// id and (n1 xor n2) 
			Expression tpTRUE = BinaryExpression.mkBinExpr(id, Operator.AND,
					der);
			result.add(new NamedTerm(id.toString() + "T", tpTRUE));
			// not id and (n1 xor n2) 
			Expression tpFALSE = BinaryExpression.mkBinExpr(
					NotExpression.createNotExpression(id), Operator.AND, der);
			result.add(new NamedTerm(id.toString() + "F", tpFALSE));
		}
		return result;

	}

}
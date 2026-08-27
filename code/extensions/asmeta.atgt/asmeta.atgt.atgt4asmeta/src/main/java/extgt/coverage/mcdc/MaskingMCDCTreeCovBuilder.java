package extgt.coverage.mcdc;

import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.Expression;
import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;

/**
 * build the masking MCDC using the MaskMCDCExprVisitor
 * 
 * @author garganti
 * 
 * @param <S>
 * @param <P>
 * @param <C>
 */
public abstract class MaskingMCDCTreeCovBuilder<S extends Specification, P extends TestPredicate<?,?>, C extends CoverageTree<P>>
		extends SpecAnalyzerCovBuilder<Expression, S, P, C> {

	public MaskingMCDCTreeCovBuilder(
			SpecificationAnalyzer<Expression, S> specAn,
			CoverageTreeFactory<C> ctf, TestPredicateFactory<? extends P> tpf) {
		super(specAn, ctf, tpf);
	}

	@Override
	public String getCoveragePrefix() {
		return "mMCDC";
	}

	@Override
	protected Iterable<NamedTerm> getTestPredicates(Expression expr) {
		return MaskMCDCTPBuilder.getMCDCVisitor().analyze(expr);
	}

}
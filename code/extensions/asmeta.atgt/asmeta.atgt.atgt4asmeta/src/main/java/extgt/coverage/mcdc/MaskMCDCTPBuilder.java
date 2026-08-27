package extgt.coverage.mcdc;

import java.util.Collections;
import java.util.Iterator;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionAnalyzer;
import tgtlib.definitions.expression.visitors.EvaluationNotSupported;

/**
 * The Class MaskMCDCVisitor. given an expression, returns the list of test
 * predicates for it
 */
public class MaskMCDCTPBuilder implements ExpressionAnalyzer<Iterable<NamedTerm>> {

	private MaskMCDCTPBuilder() {
	}

	private static MaskMCDCTPBuilder instance = new MaskMCDCTPBuilder();

	public static MaskMCDCTPBuilder getMCDCVisitor() {
		return instance;
	}

	@Override
	public Iterable<NamedTerm> analyze(final Expression e) {
		try{
		return new IterableFromPairList<NamedTerm>(e.accept(visitor));
		} catch (EvaluationNotSupported ne){
			System.err.println(ne.getMessage());
			return Collections.EMPTY_LIST;
		}
	}

	private MaskMCDCExprVisitor visitor = new MaskMCDCExprVisitor(null);
}
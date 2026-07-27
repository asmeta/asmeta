package extgt.coverage.mcdc;

import tgtlib.coverage.CoverageBuilder;
import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;

/**
 * build the tree using a spec analyzer
 * 
 * @author garganti
 *
 * @param <T>
 * @param <S>
 * @param <P>
 * @param <C>
 */
public abstract class SpecAnalyzerTreeCovBuilder<T,S extends Specification, P extends TestPredicate<?,?>, C extends CoverageTree<P>> implements CoverageBuilder<S, C>{

	SpecificationAnalyzer<T,S> specAn;
	CoverageTreeFactory<C> ctf;
	TestPredicateFactory<? extends P> tpf;
	
	
	public SpecAnalyzerTreeCovBuilder(SpecificationAnalyzer<T,S> specAn,
			CoverageTreeFactory<C> ctf, TestPredicateFactory<? extends P> tpf) {
		this.specAn = specAn;
		this.ctf = ctf;
		this.tpf = tpf;
	}
	
	
	@Override
	public C getTPTree(S spec) {
		// get the object 
		T expr = specAn.analyze(spec);
		// build the list of tps
		Iterable<NamedTerm> result = getTestPridcates(expr);
		C cov = ctf.buildEmptyCovTree(getCoveragePrefix());
		for (NamedTerm tc : result) {
			P newTP = tpf.buildTestPredicate(tc.getName(),tc.getCondition());
			cov.addNode(newTP);
		}
		return cov;
	}

	protected abstract Iterable<NamedTerm> getTestPridcates(T expr);
	
}
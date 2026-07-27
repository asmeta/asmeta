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
public abstract class SpecAnalyzerCovBuilder<T,S extends Specification, P extends TestPredicate<?,?>, C extends CoverageTree<P>> implements CoverageBuilder<S, C>{

	SpecificationAnalyzer<T,S> specAn;
	CoverageTreeFactory<C> ctf;
	TestPredicateFactory<? extends P> tpf;
	
	
	public SpecAnalyzerCovBuilder(SpecificationAnalyzer<T,S> specAn,
			CoverageTreeFactory<C> ctf, TestPredicateFactory<? extends P> tpf) {
		this.specAn = specAn;
		this.ctf = ctf;
		this.tpf = tpf;
	}
	
	
	@Override
	public final C getTPTree(S spec) {
		// get the object 
		T expr = specAn.analyze(spec);
		// build the list of tps
		Iterable<NamedTerm> result = getTestPredicates(expr);
		C cov = ctf.buildEmptyCovTree(getCoveragePrefix());
		for (NamedTerm tc : result) {
			P newTP = tpf.buildTestPredicate(tc.getName(),tc.getCondition());
			cov.addNode(newTP);
		}
		return cov;
	}

	/** evry subclass shoudk imoplement only this
	 * 
	 * @param expr
	 * @return
	 */
	protected abstract Iterable<NamedTerm> getTestPredicates(T expr);
	
}
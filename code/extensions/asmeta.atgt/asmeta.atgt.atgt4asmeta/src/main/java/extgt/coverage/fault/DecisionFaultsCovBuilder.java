package extgt.coverage.fault;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.VisitNotSupportedExc;
import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.ExpressionVisitMutator;
/**
 * faulty coverage of the decision in the specification
 * 
 * @author angelo.gargantini
 *
 * @param <T> type of the specification
 * @param <P> test predicates
 * @param <Q> Coverage Tree
 */
public class DecisionFaultsCovBuilder<T extends Specification, P extends TestPredicate<?,?>, Q extends CoverageTree<P>> extends FaultCoverageBuilder<T, P, Q,Expression> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DecisionFaultsCovBuilder.class);

	
	/** The fault mutator. */
	private ExpressionVisitMutator<?> faultMutator;
	private SpecificationAnalyzer<List<NamedTerm>, T> specExtractor;
	private CoverageTreeFactory<Q> ctFact;
	
	/**
	 * 
	 * @param fev the mutation to be used by this class
	 * @param specAn
	 * @param ctf
	 * @param tpf
	 */
	public DecisionFaultsCovBuilder(ExpressionVisitMutator<?> fev,
			SpecificationAnalyzer<List<NamedTerm>, T> specAn,
			CoverageTreeFactory<Q> ctf, TestPredicateFactory<P> tpf) {
		super(fev, specAn, ctf, tpf);
		this.ctFact = ctf;
		specExtractor = specAn;
		this.faultMutator = fev;
	}

	/**
	 * given the specification returns the coverage for that fault expression
	 * visitor by applying the mutation to all the guards.
	 * 
	 * @param SP
	 *            the sP
	 * 
	 * @return the coverage
	 */
	@Override
	public final Q getTPTree(T SP) {
		// get all the decisions
		List<NamedTerm> decisions = specExtractor.analyze(SP);
		// build the coverage
		Q coverage = ctFact.buildEmptyCovTree(this.faultMutator.getName());
		List<P> listFultyTPs = getFaultyCoverage(decisions);
		for (P tc : listFultyTPs)
			coverage.addNode(tc);
		return coverage;
	}

	/**
	 * given a list of expression and a fault mutator, returns the tree with
	 * all the tp for all the original expressions
	 * 
	 * @param decisions
	 * @param fExprVi
	 * @return
	 */
	private List<P> getFaultyCoverage(List<? extends NamedTerm> decisions) {
		List<P> result = new Vector<P>();	
		for (NamedTerm d : decisions) {
			Expression condition = d.getCondition();
			List<NamedTerm> faults = getMutants(condition);
			for (NamedTerm f : faults) {
				String name = f.getName() + "_" + d.getName();
				P toAdd = makeTestPredicate(condition, f.getCondition(), name);
				result.add(toAdd);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return this.getClass().getCanonicalName() + " for fault "
				+ faultMutator.getAbbrvName();
	}

	@Override
	public String getCoveragePrefix() {
		return faultMutator.getAbbrvName();
	}

	/* (non-Javadoc)
	 * @see extgt.coverage.fault.FaultCoverageBuilder#getMutants(tgtlib.definitions.expression.Expression)
	 */
	@Override
	protected List<NamedTerm> getMutants(Expression e) {
		List<NamedTerm> result = new Vector<NamedTerm>();
		List<Pair<Integer, Expression>> faults;
		try {
			ExpressionMutator mut = this.faultMutator.getExpressionMutator(e);
			faults = mut.getMutations(e);
		} catch (VisitNotSupportedExc exc) {
			// if the visitor does not support, list return the empty 
			logger.debug(exc.getMessage());				
			faults = Collections.EMPTY_LIST;
		}
		int count = 1;
		for (Pair<Integer, Expression> f : faults) {
			String name = faultMutator.getAbbrvName();
			if (faults.size() > 1)
				name += (count++);
			result.add(new NamedTerm(name,f.getSecond()));
		}
		return result ;
	}
}

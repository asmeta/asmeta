package extgt.coverage.fault;

import java.util.List;

import tgtlib.coverage.CovBuilderBySubCov;
import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.specification.Specification;
import tgtlib.specification.SpecificationAnalyzer;
import extgt.coverage.fault.mutators.foms.AssociativeShiftFault;
import extgt.coverage.fault.mutators.foms.ExpressionNegationFault;
import extgt.coverage.fault.mutators.foms.MissingVariableFault;
import extgt.coverage.fault.mutators.foms.OperatorReferenceFault;
import extgt.coverage.fault.mutators.foms.RelationalOperatorFault;
import extgt.coverage.fault.mutators.foms.StuckAt;
import extgt.coverage.fault.mutators.foms.VariableNegationFault;

/**
 * build a test coverage tree for all the classical faults defined in the system.
 *
 * @param <T> the generic type
 * @param <P> the generic type
 * @param <Q> the generic type
 * @author garganti
 */
public final class ClassicalFaultsCoverageBuilder<T extends Specification, P extends TestPredicate<?,?>, Q extends CoverageTree<P>>
		extends CovBuilderBySubCov<T, P, Q> {

	/**
	 * Instantiates a new fault based coverage tree.
	 *
	 * @param string the string
	 * @param ctf the ctf
	 */
	private ClassicalFaultsCoverageBuilder(String string, CoverageTreeFactory<Q> ctf) {
		super(string, ctf);
	}

	/**
	 * Make all coverages.
	 *
	 * @param <T> specification type
	 * @param <P> test predicate
	 * @param <Q> coverage tree
	 * @param ctfact the coverage tree factory
	 * @param specAn the spec analyzer
	 * @param tpFact the tp factory
	 * @return the fault based coverage tree (with the classical faults)
	 */
	public static <T extends Specification, P extends TestPredicate<?,?>, Q extends CoverageTree<P>> ClassicalFaultsCoverageBuilder<T, P, Q> makeAllCoverages(
			CoverageTreeFactory<Q> ctfact,
			SpecificationAnalyzer<List<NamedTerm>, T> specAn,
			TestPredicateFactory<P> tpFact) {

		final DecisionFaultsCovBuilder<T, P, Q> ASF = new DecisionFaultsCovBuilder<T, P, Q>(
				AssociativeShiftFault.ASF, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> ENF = new DecisionFaultsCovBuilder<T, P, Q>(
				ExpressionNegationFault.ENF, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> CNF = new DecisionFaultsCovBuilder<T, P, Q>(
				VariableNegationFault.VNF, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> MCF = new DecisionFaultsCovBuilder<T, P, Q>(
				MissingVariableFault.MVF, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> ORF = new DecisionFaultsCovBuilder<T, P, Q>(
				OperatorReferenceFault.ORF, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> ST0 = new DecisionFaultsCovBuilder<T, P, Q>(
				StuckAt.STUCK_AT0, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> ST1 = new DecisionFaultsCovBuilder<T, P, Q>(
				StuckAt.STUCK_AT1, specAn, ctfact, tpFact);
		final DecisionFaultsCovBuilder<T, P, Q> ROF = new DecisionFaultsCovBuilder<T, P, Q>(
				RelationalOperatorFault.ROF, specAn, ctfact, tpFact);
		ClassicalFaultsCoverageBuilder<T, P, Q> result = new ClassicalFaultsCoverageBuilder<T, P, Q>(
				"Fault based coverage", ctfact);

		// add fault based coverages
		result.register(ASF);
		result.register(ENF);
		result.register(CNF);
		result.register(MCF);
		result.register(ORF);
		result.register(ST0);
		result.register(ST1);
		result.register(ROF);
		return result;
	}
}
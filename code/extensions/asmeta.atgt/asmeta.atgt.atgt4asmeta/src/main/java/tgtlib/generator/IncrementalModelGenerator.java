package tgtlib.generator;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.expression.Expression;

/** incremental test generator tool
 * 
 * @author garganti
 *
 * @param <Q>
 * @param <T>
 * @param <P>
 */
//public interface IncrementalModelGenerator<Q extends TestPredicate<T,?>, T extends tgtlib.definitions.TestSequence<Q>, P extends MCExecutionResult> {
public interface IncrementalModelGenerator<Q extends TestPredicate<?,?>, T extends tgtlib.definitions.TestSequence<?>, P extends MCExecutionResult> {

	/** can be called after the first run model checker and it adds the new test predicate
	 * 
	 * @param firstRun: it is modified to include the new tp in case is accepted
	 * @param tp: test predicate to be added
	 * @return ADDED if tp has been added to the context firstRUn, otherwise retune refused.
	 */
	public AssertPlusResult andAlso(P firstRun, Expression tpExpr, String tpName);

	public enum AssertPlusResult {ADDED, REFUSED}

	// derived from Test generator
	// necessary to be used as test generator
	// run the model checker and return the result, directly over the expression
	public P runModelChecker(Expression ptc) throws ModelCheckerExecutionException;
	
	public MCAnalysisResult analyses(P exresult);
	
	public void buildTest(P in, T tp);
	
	public T buildTestFor(Q tp, P execResult);


}


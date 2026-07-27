package tgtlib.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.definitions.expression.visitors.IDExprCollector;
import tgtlib.definitions.normalform.cnf.CNFExprConverter.CNFExprConverterFactory;
import tgtlib.specification.Axiom;

/** generic SAT solver using JNA or Java directly (like Sat4j)
 * 
 * @author garganti
 *
 * @param <Q>
 * @param <T>
 * @param <I>
 * @param <R>
 */
public abstract class SatSolverTestGenLib<Q extends TestPredicate<? extends T,?>, T extends tgtlib.definitions.TestSequence<? extends Q>, R extends MCExecutionResult> 
extends TestSequenceGenerator<Q, T, R> {
	
	// get a real random
	static final protected Random rnd = new Random();
	
	/** the converter to CNFs factory*/
	protected CNFExprConverterFactory cnfConverterFactory;

	//the id used by the solver - it may be a superset of the ids of the single test predicate
	// but it may also be subset in case the transformation inserts new ids (like Tseitin)
	/** the ids in the expression*/
	// ids 
	protected List<IdExpression> ids;
	// and variables TODO fuse Ids and vars together
	protected List<? extends Variable> variables;
	// the constraints
	protected Collection<Axiom> constraints;


	/**
	 * Instantiates a new sat solver.
	 *
	 * @param q factory
	 * @param ids variable (as boolean)
	 * @param constraints the constraints
	 * @param cnfConverter the cnf converter
	 */
	protected SatSolverTestGenLib(TestSequenceFactory<T, ? super Q> q, 
			List<? extends Variable> variables, 
			Collection<Axiom> constraints, 
			CNFExprConverterFactory cnfConverter) {
		super(q);
		this.ids = getIdsFromVars(variables);
		// also from constraints?
		assert ids.containsAll(IDExprCollector.collectIds(Axiom.getExpressions(constraints)));
		this.cnfConverterFactory = cnfConverter;
		this.constraints = constraints;
		this.variables = variables;
	}


	// return the ids given a set of variables (useful when ids are needed insread of vars
	private static List<IdExpression> getIdsFromVars(Collection<? extends Variable> collection) {
		List<IdExpression> res = new ArrayList<IdExpression>();
		for (Variable v: collection){
			res.add(v.getIdExpression());
		}
		return res;
	}
	
	@Override
	public final R runModelChecker(Q tp)
			throws ModelCheckerExecutionException{
		//logger.debug("ids " + ids + " variables " + variables);
		//logger.debug("running sat for " + tp.getName() + " " + tp.getCondition());
		return runModelChecker(tp.getCondition());
	}
	/** works for the expression
	 * 
	 * @param condition
	 * @return
	 */
	protected abstract R runModelChecker(Expression condition) throws ModelCheckerExecutionException;
	

}

package tgtlib.generator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;

/** common super class for all the sat solvers using command line (TODO unite with those for JNA or sat4j)
 * 
 * @author garganti
 *
 * @param <Q>
 * @param <T>
 */
public abstract class SatTestGeneratorCLI<Q extends TestPredicate<? extends T,?>, T extends tgtlib.definitions.TestSequence<? extends Q>>
extends ExternalToolTGen<Q, T, TestPredMCInput<Q>> {

	/**
	 * Dati di input del modello del sistema in ingresso. Gli inputs devo
	 * tradurli in minisat. The variables are to be considered monitored
	 * Using list since sometime sthe order is important
	 */
	protected List<? extends Variable> inputs;

	/** axioms contiene i vincoli del sistema in ingresso. */
	protected Collection<Axiom> axioms;

	
	
	protected SatTestGeneratorCLI(TestSequenceFactory<T, ? super Q> q, List<? extends Variable> variables, Collection<Axiom> constraints) {
		super(q);
		inputs = variables;
		axioms = constraints;
	}
	
	
	@Override
	final public MCExecutionResultReader runModelChecker(TestPredMCInput<Q> tp)
			throws ModelCheckerExecutionException {
		try {
			// use only the expression
			return runModelChecker(tp.tc);
		}
		catch (Exception e) {
			throw new ModelCheckerExecutionException(e);
		}
	}
	/** given a test predicate, it adds the constraints*/
	protected Expression extractExpression(Q tp) {
		Expression e = tp.getCondition();
		if (axioms.size() > 0) {
			Iterator<Axiom> contIterator = axioms.iterator();
			while (contIterator.hasNext()) {
				//e = new AndExpression(e, contIterator.next().getBody());
				e = BinaryExpression.mkBinExpr(e, Operator.AND, contIterator.next().getBody());
			}
		}
		return e;
	}

	


}

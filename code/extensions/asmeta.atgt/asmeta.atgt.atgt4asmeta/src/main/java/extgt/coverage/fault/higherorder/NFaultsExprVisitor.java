/*
 * 
 */
package extgt.coverage.fault.higherorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.Expression;
import tgtlib.util.Pair;
import tgtlib.util.combinatorial.VariatorWithRepetition;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.ExpressionVisitMutator;

/**
 * Definition of HOM operator. It applies several faults in order (in cascade). If one of the fault applied does not change, it does
 * not change, i.e. returns the empty list
 * 
 * @author garganti
 * 
 */
public class NFaultsExprVisitor extends ExpressionVisitMutator {

	/** The faults visitors that constitute this hom */
	private List<ExpressionVisitMutator<?>> faultsVisitor;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(NFaultsExprVisitor.class);

	/** the name as composed by the constituents */
	private String composedName;

	/**
	 * Instantiates a new n faults expr visitor.
	 * 
	 * @param expressionVisitors
	 *            the expression visitors
	 */
	NFaultsExprVisitor(ExpressionVisitMutator<?>... expressionVisitors) {
		this(new ArrayList<ExpressionVisitMutator<?>>(
				Arrays.asList(expressionVisitors)));
	}
	/**
	 * Instantiates a new n faults expr visitor.
	 * 
	 * @param expressionVisitors
	 *            the expression visitors
	 */
	private NFaultsExprVisitor(List<ExpressionVisitMutator<?>> expressionVisitors) {
		faultsVisitor = expressionVisitors;
		for (ExpressionVisitMutator<?> faultExpressionVisitor : expressionVisitors) {
			if (composedName == null)
				composedName = "";
			else
				composedName += "_";
			composedName += faultExpressionVisitor.getAbbrvName();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see extgt.coverage.fault.mutators.FaultExpressionVisitor#getName()
	 */
	@Override
	public String getName() {
		return composedName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see extgt.coverage.fault.mutators.FaultExpressionVisitor#getAbbrvName()
	 */
	@Override
	public String getAbbrvName() {
		return composedName;
	}

	/**
	 * Make all the possible permutations among the fom operators to obtain a list of HOM of size size.
	 *
	 * @param size the size
	 * @param expressionVisitors the expression visitors
	 * @return the iterator over the new HOM operators
	 */
	static public Iterator<NFaultsExprVisitor> makeAllCombinations(int size,
			ExpressionVisitMutator<?>... expressionVisitors) {
		final VariatorWithRepetition<ExpressionVisitMutator<?>> comb = new VariatorWithRepetition<ExpressionVisitMutator<?>>(expressionVisitors, size);		
		Iterator<NFaultsExprVisitor> result = new Iterator<NFaultsExprVisitor>() {

			@Override
			public boolean hasNext() {
				return comb.hasNext();
			}

			@Override
			public NFaultsExprVisitor next() {
				return new NFaultsExprVisitor(comb.next());
			}

			@Override
			public void remove() {
				comb.remove();
			}
		};
		return result;
	}

	@Override
	public ExpressionMutator getExpressionMutator(Expression e) {
		/** The faults visitors that constitute this hom */
		// build the expression mutators
		final List<ExpressionMutator> mutators = new ArrayList<ExpressionMutator>();
		for (ExpressionVisitMutator<?> f : faultsVisitor) {
			// build the expression mutator
			mutators.add(f.getExpressionMutator(e));
		}
		return new ExpressionMutator() {								
			@Override
			public String getName() {
				return composedName;
			}
			
			@Override
			public List<Pair<Integer, Expression>> getMutations(Expression e) {
					// apply all the faults
					List<Pair<Integer, Expression>> intermediate = new ArrayList<Pair<Integer, Expression>>();
					// initially only e
					intermediate.add(new Pair<Integer, Expression>(0, e));
					for (ExpressionMutator f : mutators) {
						// DO NOT build the expression mutators here
						intermediate = appliesFault(f, intermediate);
						logger.debug(f.getAbbrvName() + " " + intermediate);
						// if the intermediate is empty, then quit
						if (intermediate.isEmpty())
							return Collections.emptyList();
					}
					return intermediate;
			}

			/**
			 * applies f to every expression in intermediate
			 * 
			 * @param expressionMutator
			 *            the expression mutator (not the factory)
			 * @param intermediate
			 *            the list obtained in the previous step
			 * @return the list
			 */
			private List<Pair<Integer, Expression>> appliesFault(ExpressionMutator expressionMutator, List<Pair<Integer, Expression>> intermediate) {
				List<Pair<Integer, Expression>> result = new ArrayList<Pair<Integer, Expression>>();
				for (Pair<Integer, Expression> e : intermediate) {
					// be careful: the mutation must be done with respect the original one???
					// not mutators are simply applied in sequence
					Expression e1 = e.getSecond();	
					result.addAll(expressionMutator.getMutations(e1));
				}
				return result;
			}

			@Override
			public String getAbbrvName() {
				return composedName;
			}
		};
	}
}

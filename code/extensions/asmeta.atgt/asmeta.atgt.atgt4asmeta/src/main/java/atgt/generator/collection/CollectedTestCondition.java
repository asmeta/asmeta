package atgt.generator.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import atgt.coverage.TestCondition;
import atgt.generator.testsuite.ordering.TPCollectedChangeListener;
import atgt.specification.location.Variable;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.visitors.ExpressionEvaluator;

/**
 * collected feasible test conditions in an unique test
 * condition. It is itself a test condition (desired of type T, but not possible
 *
 * @param <T> the test condition type to be collected
 * @param <Q> the test sequences 
 */
// TODO link T and Q
//public abstract class CollectedTestCondition<T extends TestCondition<? extends Q>, Q extends TestSequence<? extends T>> extends TestCondition<Q> {
//public abstract class CollectedTestCondition<T extends TestCondition/*<? extends Q>*/, Q extends TestSequence/*<? extends T>*/> extends AsmTestCondition{
//public abstract class CollectedTestCondition<T extends TestCondition/*<? extends Q>*/, Q extends TestSequence/*<? extends T>*/> extends T{
//public abstract class CollectedTestCondition<T extends TestCondition<? extends Q>, Q extends TestSequence<? extends T>> extends TestCondition<Q>{
//public abstract class CollectedTestCondition<T extends TestCondition<?>, Q extends TestSequence<?>> extends TestCondition<Q>{
public abstract class CollectedTestCondition<T extends TestCondition<?>, Q extends TestSequence<?>> extends TestCondition<Q>{

	/** used for naming the collection*/
	static int counter = 1;

	protected static final Logger log = Logger.getLogger(CollectedTestCondition.class);
	
	/**
	 * observer pattern
	 */
	protected List<TPCollectedChangeListener<T>> observers = new ArrayList<TPCollectedChangeListener<T>>();

	/** the test condition that it collects*/
	protected List<T> tps;
		
	protected CollectedTestCondition() {
		super("collected" + (counter++), null);
		tps = new ArrayList<T>();
	}

	public boolean contains(T tp){
		return tps.contains(tp);
	}
	
	
	/** return true if no tp is already in the collection
	 * 
	 * @return
	 */
	public boolean isEmpty(){
		return tps.isEmpty();
	}
		
	/** add a test condition to this collection */
	public void addTestCondition(T tc) {
		log.debug("adding test condition " + tc.getName() + " to collection " + this.getName());
		tps.add(tc);
		assert witness == null || witnessCovers(tc);
		// forward the listeners
		for (TPCollectedChangeListener<T> o : observers) {
			o.TPAdded(tc);
		}
	}

	public void registerListener(TPCollectedChangeListener<T> tclistener) {
		observers.add(tclistener);
	}

	/***
	 * 
	 * @return the number of tps inserted in this collection
	 */
	public long size() {
		return tps.size();
	}

	/** representation as AND expression */
	public final Expression asAndExpression(){
		return asAndExpression;
	}
	
	private Expression asAndExpression;
	
	protected void updateAsAnd(Expression ee) {
		if (asAndExpression == null)
			asAndExpression = ee;
		else
			asAndExpression = new AndExpression(asAndExpression, ee);
	}

	
	/**
	 * Conjoint of all the expressions in the test + the test condition passed in the argument
	 *
	 * @param ptc the ptc
	 * @return the test condition to test consistency
	 */
	public TestCondition<?> conjointt(T ptc) {
		if (! isEmpty()) {
			Expression e = asAndExpression();
			assert e != null;
			// add this
			e = new AndExpression(e, ptc.getCondition());
			return new TestCondition<>("TestConsistencyFor"+getName(), e); 
		} else {
			// collection is empty
			return ptc;
		}
	}


	

	@Override
	public final void setAssertViolated(boolean v) {
		log.debug("setting assert violated to " + v + " to the collection " + this.getName());
		super.setAssertViolated(v);
		for (T tp : tps) tp.setAssertViolated(v);
	}

	@Override
	public final void setRunning() {
		super.setRunning();
		for (T tp : tps) tp.setRunning();
	}
	
	/**
	 * Gets expression equivalent condition (as and expression)
	 * 
	 * @return the condition
	 */
	@Override
	public final Expression getCondition() {
		assert tps.size() >= 1;
		if (tps.size() == 1) 
			return tps.get(0).getCondition();
		else
			// calcolare come as AND
			return asAndExpression();
	}

	@Override
	public final void bindTestSeqTestPred(Q testCase) {
//	public final void bindTestSeqTestPred(AsmTestSequence testCase) {
		// bind this as generated for done from outside
		//TODO bring inside and use only bind for this goal
		// testCase.setGeneratedFor(this);
		log.debug("binding collected " + this.getName() + " with " + testCase + "| compound of "+ tps.toString());
		// DO NOT bind THIS information
		// otherwise the reduction algorithm will consider also this
		// test predicates
		// super.bindTestSeqTestPred(testCase);
		for (TestCondition tp : tps)
			tp.bindTestSeqTestPred(testCase);
		//
	}
	
	/// if the collection has a witness it can be useful
	
	Q witness;

	/** return th witness for the collection
	 * 
	 * @return
	 */
	public Q getWitness(){
		// if its not null,it must cover all the tps collected
		assert witness == null || witnessCoversAllInCollect() : "the witness is not a real winess";
		return witness;		
	}

	public void setWitness(Q testSeq){
		// the witness should be generated for this collection
		assert testSeq.getGeneratedFor() == this: "generated for: " + testSeq.getGeneratedFor().getName() + "  this: " + this.getName() + " tc class" + testSeq.getGeneratedFor().getClass(); 
		log.debug("setting witness with " + testSeq.numberOfStates() + "states  for the collection with " + tps.size() + " in it");
		// not potrebbe anche essere zero (e la condizione essere aggiunta dopo)
		witness = testSeq;		
		//assert size() > 0;
		assert testSeq.numberOfStates() == 1;
	}

	/** the name in the collected test predicate */
	public String getNames(){
		String names = "";
		for (T tp : tps) names += tp.getName() + ",";
		return names;
	}

	// only used in the assertions
	// check if the witness covers all the tp in collect
	public boolean witnessCoversAllInCollect() {
		assert witness != null;
		// TODO extend to other types 
		Map<Variable, String> allInstructions = (Map<Variable, String>) witness.getState(0);
		ExpressionEvaluator eval = new ExpressionEvaluator(allInstructions);
		for(T tc: tps){
			if (!tc.getCondition().accept(eval)) {
				System.out.println(" ********** " + tc.getName());
				return false; 
			}
		}
		return true;
	}

	public boolean witnessCovers(T tc) {
		assert witness != null;
		assert witness.numberOfStates() == 1;
		Map<Variable, String> allInstructions = (Map<Variable, String>) witness.getState(0);
		ExpressionEvaluator eval = new ExpressionEvaluator(allInstructions);
		return tc.getCondition().accept(eval);
	}
}
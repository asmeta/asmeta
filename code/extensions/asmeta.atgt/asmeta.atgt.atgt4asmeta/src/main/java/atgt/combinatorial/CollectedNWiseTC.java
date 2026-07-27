package atgt.combinatorial;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.CoveragesVisitorI;
import atgt.coverage.TestConditionListener;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.collection.CollectedTestCondition;
import extgt.coverage.combinatorial.EqTestCondition;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.type.EnumConst;

/**
 * collect Nwise test conditions only ((NWiseEqTestCondition) they must be
 * feasible CombinatorialTestCondition can be only EqtestConditions
 * 
 * */
public class CollectedNWiseTC extends
		CollectedTestCondition<CombinatorialTestCondition, AsmTestSequence>{

	// the assignemens giwvn so far
	Map<TypedInitExpression, EnumConst> map;

	public CollectedNWiseTC() {
		map = new HashMap<TypedInitExpression, EnumConst>();
	}

	/**
	 * tc deve essere consistente con gli altri nella lista e con gli assiomi,
	 * cioè deve esister un modello (è un aprecondition, il metodo non
	 * controlla )
	 * */
	@Override
	public void addTestCondition(CombinatorialTestCondition ptc) {
		super.addTestCondition(ptc);
		EqTestCondition tc = (EqTestCondition) ptc;
		for (int i = 0; i < tc.size(); i++) {
			TypedInitExpression var = tc.getVar(i);
			if (map.get(var) == null){
				log.debug("collection has changed " + var +"<-"+ tc.getVal(i));
				// put in the new map
				map.put(var, tc.getVal(i));
				// rebuild andExpression
				Expression ee = BinaryExpression.mkBinExpr(var.getIdExpression(), Operator.EQ, tc.getVal(i));
				updateAsAnd(ee);
			}
			// if not set it must be equal
			assert map.get(var) == tc.getVal(i);
		}
	}

	public enum CHECK_RESULT {
		/** no counter example can be found */
		INCONSISTENT,
		/** it is implied, it can be collected in any case */
		IMPLIED,
		/** a cex can be found, but it may be excluded by other constraints */
		NOT_IMPLIED;
	};

	/**
	 * return true se tc è compatibile per quanto riguarda i valori a this
	 * tutti i valori di tc devono essere contenuti tali e quali nella mappa.
	 * check the consistency looking at the values (first check) ignore the
	 * axioms. No constraint solver should be involved at this point. If the
	 * check has no meaning return NOT_IMPLIED.
	 * 
	 * Assuming that the collectionis not empty, otherwise is useless to call
	 * this.
	 * 
	 * @param ctc
	 *            the ctc
	 * @return the cHEC k_ result
	 */
	public CHECK_RESULT checkConsistencyByValue(CombinatorialTestCondition ctc) {
		assert tps.size() > 0 : "useless ";
		EqTestCondition tc = (EqTestCondition) ctc;
		// get all the var and values in the tc
		CHECK_RESULT result = CHECK_RESULT.IMPLIED;
		for (int i = 0; i < tc.size(); i++) {
			TypedInitExpression var = tc.getVar(i);
			EnumConst mapVal = map.get(var);
			if (mapVal == null)
				// qualche valore non contenuto
				result = CHECK_RESULT.NOT_IMPLIED;
			else if (!mapVal.equals(tc.getVal(i)))
				// qualche valore diverso
				return CHECK_RESULT.INCONSISTENT;
			// tcVal e mapVal coincidono
		}
		return result;

	}

	public boolean isSettingVar(TypedInitExpression var) {
		return map.containsKey(var);
	}

	@Override
	public String toString() {
		return "collected [" + tps.size() + "] tcs - values : "
				+ map.toString() + " tcs: " + tps.toString();
	}

	@Override
	public void markInfeasible() {
		// it should never occur:
		// a collection should not be uinfeasible
		throw new RuntimeException("colelction should never be infeasible");
	}

	public int numVariablesFixed() {
		return map.keySet().size();
	}

	//
	// used to fake an ASMTestCondition
	// TODO use the collection in the hierarchy of AsmTestCondition
	//
	public AsmTestCondition asAsmCondition() {
		log.debug("computing as AsmCondition " + getName() + " = "+ asAndExpression());
		final CollectedNWiseTC proxy = this;
		return new AsmTestCondition(getName(),
				proxy.asAndExpression()) {
			@Override
			public void setAssertViolated(boolean b) {
				proxy.setAssertViolated(b);
			}

			@Override
			public void setRunning() {
				proxy.setRunning();
			}

			@Override
			public void setToVerify(boolean b) {
				proxy.setToVerify(b);
			}

			@Override
			public Collection<AsmTestSequence> allCoveredBy() {
				return proxy.allCoveredBy();
			}

			@Override
			public void bindTestSeqTestPred(AsmTestSequence testCase) {
				proxy.bindTestSeqTestPred(testCase);
			}

			@Override
			protected Collection<AsmTestSequence> buildCoveredBy() {
				return proxy.buildCoveredBy();
			}

			@Override
			public Expression getCondition() {
				return proxy.getCondition();
			}

			@Override
			public <T> T accept(CoveragesVisitorI<T> ask) {
				throw new RuntimeException();
			}

			@Override
			public void addTestConditionListener(TestConditionListener l) {
				throw new RuntimeException();
			}

			@Override
			public int compareTo(NamedTerm o) {
				return proxy.compareTo(o);
			}

			@Override
			public String getName() {
				return proxy.getName();
			}

			@Override
			public boolean equals(Object o) {
				throw new RuntimeException();
			}

			@Override
			public TestConditionState getPreviousStatus() {
				throw new RuntimeException();
			}

			@Override
			public TestConditionState getStatus() {
				throw new RuntimeException();
			}

			@Override
			public void fireTestConditionStateChanged() {
				proxy.fireTestConditionStateChanged();
			}

			@Override
			public String getStatusDescription() {
				throw new RuntimeException();
			}

			@Override
			@Deprecated
			public AsmTestSequence getTestResult() {
				throw new RuntimeException();
			}

			@Override
			public String getUniqueID() {
				return proxy.getUniqueID();
			}

			@Override
			public boolean isAssertViolated() {
				return proxy.isAssertViolated();
			}

			@Override
			public boolean isToVerify() {
				return proxy.isToVerify();
			}

			@Override
			public void markInfeasible() {
				proxy.markInfeasible();
			}

			@Override
			public void removeTestConditionListener(TestConditionListener l) {
				throw new RuntimeException();
			}

			@Override
			public void reset() {
				throw new RuntimeException();
			}
		};
	}
}

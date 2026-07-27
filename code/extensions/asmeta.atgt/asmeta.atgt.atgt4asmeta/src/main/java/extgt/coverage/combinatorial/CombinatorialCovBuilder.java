package extgt.coverage.combinatorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tgtlib.coverage.CoverageBuilder;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.specification.Specification;
import tgtlib.util.Pair;

/** combinatorial builder. 
 * 
 * @author garganti
 *
 * @param <S>
 * @param <C>
 */
public abstract class CombinatorialCovBuilder<S extends Specification, C extends CoverageTree<?>>
		implements CoverageBuilder<S, C> {

	public CombinatorialCovBuilder(MonitorDataExtractor<S> m) {
		mde = m;
	}

	private MonitorDataExtractor<S> mde;

	/**
	 * builds the Coverage by extracting the monitored data and calling an
	 * aiuxiliary method
	 * 
	 */
	@Override
	public final C getTPTree(S sp) {
		// extract the monitoreddata
		assert mde != null;
		assert sp != null;
		MonitoredData inputs = mde.analyze(sp);
		return computeTPs(inputs);
	}

	/**
	 * returns the tree of TP starting from a MonitoredData.
	 * 
	 * @param vars
	 *            the vars
	 * 
	 * @return the test predicate tree node
	 */
	public abstract C computeTPs(MonitoredData vars);

	/**
	 * build the expression var = val.
	 * 
	 * @param var
	 *            the var
	 * @param val
	 *            the val
	 * 
	 * @return the expression
	 */
	public static <T> List<T> makeListFromPair(T t1, T t2) {
		List<T> result = new ArrayList<T>();
		result.add(t1);
		result.add(t2);
		return result;
	}

	/** the eq expressions already created. It could be moved to EqualsExpression itself
	 * 
	 */
	private static Map<Pair<IdExpression, EnumConst>,EqualsExpression> eqExpressions = new HashMap<Pair<IdExpression,EnumConst>, EqualsExpression>();
	
	/**
	 * returns the expression var = val. If the same expression was created, returns the already created exrpession.
	 * 
	 * @param var
	 *            the var
	 * @param val
	 *            the val
	 * 
	 * @return the expression
	 */
	public static EqualsExpression makeEqExpression(IdExpression var, EnumConst val) {
		Pair<IdExpression, EnumConst> idVal = new Pair<IdExpression, EnumConst>(var,val);
		EqualsExpression created = eqExpressions.get(idVal);
		if (created != null) return created;
		else{
			EqualsExpression equalsExpression = new EqualsExpression(var, val);
			eqExpressions.put(idVal, equalsExpression);
			return equalsExpression;
		}
	}

	/** builds the expression and between vars and vals
	 * 
	 * @param list
	 * @param _vals
	 * @return
	 */
	public static Expression makeAndExpression(List<? extends TypedInitExpression> list,
			List<EnumConst> _vals) {
		Expression result = null;
		Iterator<EnumConst> ci = _vals.iterator();
		for (TypedInitExpression l : list) {
			EnumConst c = ci.next();
			if (result == null)
				result = makeEqExpression(l.getIdExpression(), c);
			else
				result = new AndExpression(result, makeEqExpression(l.getIdExpression(), c));
		}
		return result;
	}

}
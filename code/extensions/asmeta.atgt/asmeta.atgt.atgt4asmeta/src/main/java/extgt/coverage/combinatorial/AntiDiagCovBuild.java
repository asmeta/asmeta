/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package extgt.coverage.combinatorial;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.specification.Specification;

/**
 * The Class AntiDiagCovBuild.
 */
public class AntiDiagCovBuild<S extends Specification, P extends TestPredicate,C extends CoverageTree<P>>  extends PairwiseCovBuilder<S, P, C> {



	public AntiDiagCovBuild(MonitorDataExtractor<S> monDatExt,
			PairEqTestCondFactory<P> pf, CoverageTreeFactory<? extends C> cf) {
		super(monDatExt, pf, cf);
	}

	/** Logger for this class. */
	private static final Logger log = Logger.getLogger(AntiDiagCovBuild.class);

	
	/**
	 * Compute all test predicate pairs in anti-diagonal order.
	 *
	 * @param monData
	 *            the v
	 *
	 * @return the test predicate tree node
	 *
	 * @author Andrea Calvagna 2007
	 */
	
	@Override
	public C computeTPs(MonitoredData monData) {

		Vector<TypedInitExpression> vars = new Vector<TypedInitExpression>(monData.getVars());
		//
		
		C result = covFactory.buildEmptyCovTree("Antidiagonal Pairwise Coverage");
		TypedInitExpression row;
		TypedInitExpression column;
		EnumType rtype, ctype; // explicit types

		// sort the variables by range (see Variable class)
		// algorithm requires cardinality of column parameter higher than row's
		Collections.sort(vars,new VarRangeComparator());
		log.info("antidiagonal: sorted vars: " + vars.toString());
		for (int i = 0; i < vars.size() - 1; i++) {
			column = vars.get(i);
			ctype = (EnumType) column.getType();
			int M = ctype.range(); // column var, type, size
			for (int j = i + 1; j < vars.size(); j++) {
				row = vars.get(j);
				rtype = (EnumType) row.getType();
				int N = rtype.range(); // row var, type, size
				for (int K = 1; K < M + N; K++) { // iterate all N+M-1
													// anti-diagonals
					if (K < N) {
						for (int x = K - 1, y = 0; x >= 0 && y <= K - 1; x--, y++) {
							P tc = petFact.buildTestPredicate(("AD_pair_" + (i + 1) + "_" + x + "_"
									+ (j + 1) + "_" + y), row, rtype
							.value(x), column, ctype.value(y));
							result.addNode(tc);
						}
						continue;
					}

					if (N <= K && K <= M) {
						for (int x = N - 1, y = K - N; x >= 0 && y <= K; x--, y++) {
							P tc = petFact.buildTestPredicate(("pair_"
									+ (i + 1) + "_" + x + "_" + (j + 1) + "_"
									+ y), row, rtype.value(x), column, ctype
							.value(y));
							result.addNode(tc);
						}
						continue;
					}

					if (K < M + N) {
						for (int x = N - 1, y = K - N; x >= K - M && y <= M - 1; x--, y++) {
							P tc = petFact.buildTestPredicate(("pair_"
									+ (i + 1) + "_" + x + "_" + (j + 1) + "_"
									+ y), row, rtype.value(x), column, ctype
							.value(y));
							result.addNode(tc);
						}
						continue;
					}
				}
			}
		}
		log.debug("ANTIDIAGONAL: Candidates: "
				+ result.allTPs().toString());
		return result;
	}

	@Override
	public String getCoveragePrefix() {
		return "A2WISE";
	}


}

class VarRangeComparator implements Comparator<TypedInitExpression>{

	@Override
	public int compare(TypedInitExpression o1, TypedInitExpression o2) {	
		return o1.getType().range() - o2.getType().range();
	}
	
}


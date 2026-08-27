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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.specification.Specification;

/**
 * The Class StdPairwiseCovBuild builds the PairwiseCoverage of a monitored data
 * 
 * @author garganti
 */
public class StdPairwiseCovBuild<S extends Specification, P extends TestPredicate<?,?>, C extends CoverageTree<P>>
		extends PairwiseCovBuilder<S, P, C> {

	// ordering the variables according to the size of their domain?
	// TODO ascending or descending
	public static boolean orderBySize = true;
	
	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(StdPairwiseCovBuild.class);

	/**
	 * 
	 * @param monDatExt
	 * @param pf PairEqTestCondFactory
	 * @param cf CoverageTreeFactory
	 */
	public StdPairwiseCovBuild(MonitorDataExtractor<S> monDatExt,
			PairEqTestCondFactory<P> pf, CoverageTreeFactory<? extends C> cf) {
		super(monDatExt, pf, cf);
	}

	/**
	 * Compute T ps.
	 * 
	 * @param v
	 *            the v
	 * 
	 * @return the test predicate tree node
	 */
	@Override
	public C computeTPs(MonitoredData v) {
		// copy the array
		List<TypedInitExpression> vars = new ArrayList<TypedInitExpression>(v.getVars());
		//
		if (orderBySize) 
			Collections.sort(vars, new Comparator<TypedInitExpression>() {
				@Override
				public int compare(TypedInitExpression v1, TypedInitExpression v2) {
					return v1.getType().range() - v2.getType().range(); 
				}
			});
		//
		logger.debug("computing std pairwise for " + vars + " " + (orderBySize? "odered by range":"not ordered"));
		//
		C result = covFactory.buildEmptyCovTree("Standard Pairwise Coverage");
		// algorthm as explained in draft
		for (int k = 0; k < vars.size() - 1; k++) {
			TypedInitExpression var_k = vars.get(k);
			if (!(var_k.getType() instanceof ElementsType)){
				logger.debug("skipping " + var_k);
				continue;
			}
			for (int j = k + 1; j < vars.size(); j++) {
				TypedInitExpression var_j = vars.get(j);
				if (!(var_j.getType() instanceof ElementsType)){
					logger.debug("skipping " + var_j);
					continue;
				}
				// take the values for v_k and
				int ek_num = 0;
				for (EnumConst valk : ((ElementsType) var_k.getType()).allElements()) {
					ek_num++;
					int ej_n = 0;
					for (EnumConst valj : ((ElementsType) var_j.getType()).allElements()) {
						ej_n++;
						P tc = petFact.buildTestPredicate(
								("pair_" + (k + 1) + "_" + ek_num + "_"
										+ (j + 1) + "_" + ej_n), var_k, valk,
								var_j, valj);
						result.addNode(tc);
					}
				}
			}

		}
		return result;
	}

	@Override
	public String getCoveragePrefix() {
		return "2WISE";
	}

}

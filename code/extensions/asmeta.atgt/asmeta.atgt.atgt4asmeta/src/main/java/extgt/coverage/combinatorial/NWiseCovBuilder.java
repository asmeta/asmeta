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
import java.util.Iterator;
import java.util.List;

import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.specification.Specification;
import tgtlib.util.combinatorial.CombinationGeneratorList;

/**
 * The Class NWiseCovBuilder. works only for Enum variables !!!
 * For N = 2 it shcould work as the StdPairwise cov builder
 * 
 * @author garganti
 */
public class NWiseCovBuilder<S extends Specification, P extends TestPredicate<?,?>,C extends CoverageTree<P>> extends CombinatorialCovBuilder<S, C>{

	protected NwiseEqTestCondFactory<? extends P> petFact;
	
	protected CoverageTreeFactory<? extends C> covFactory;

	private final int N;

	/**
	 * Instantiates a new n-wise cov build.
	 * @param newParam TODO
	 * 
	 */
	public NWiseCovBuilder(int n, MonitorDataExtractor<S> newParam, CoverageTreeFactory<? extends C> cf, NwiseEqTestCondFactory<? extends P> tpF) {
		super(newParam);
		assert n > 2;
		N = n;
		covFactory = cf;
		petFact = tpF;
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
	public C /*NWiseCoverage*/ computeTPs(MonitoredData v) {
		//
		C result = covFactory.buildEmptyCovTree(N + "-wise Coverage");

		List<TypedInitExpression> vars = v.getVars();
		// if the combination does not make sense, return empty
		if (vars.size() < 1 || vars.size() < N)
			return result;
		// check that all are enums or booleans
		for (TypedInitExpression vi: vars) assert vi.getType() instanceof ElementsType;
		CombinationGeneratorList<TypedInitExpression> gen = new CombinationGeneratorList<TypedInitExpression>(vars, N);
		while (gen.hasNext()) {
			List<TypedInitExpression> vs = gen.next();
			List<ElementsType> vt = getElementsTypes(vs);
			List<List<EnumConst>> lE = all(vt);
			for (List<EnumConst> ecl : lE) {
				StringBuffer desc = getInitial(vs, ecl);
				result.addNode(petFact.buildTestPredicate(N + "-wise_"	+ desc, vs, ecl));
			}
		}
		return result;
	}

	/**
	 * build the initials
	 * 
	 * @param vs
	 * @param ecl
	 * @return
	 */
	private StringBuffer getInitial(List<TypedInitExpression> vs, List<EnumConst> ecl) {
		assert vs.size() == ecl.size();
		StringBuffer sb = new StringBuffer();
		Iterator<EnumConst> ci = ecl.iterator();
		for (TypedInitExpression l : vs) {
			sb.append("__");
			EnumConst c = ci.next();
			sb.append(l.getName().charAt(0));
			sb.append('_');
			sb.append(c.getIdString().charAt(0));
		}
		return sb;
	}

	private List<ElementsType> getElementsTypes(List<TypedInitExpression> vs) {
		List<ElementsType> et = new ArrayList<ElementsType>();
		for (TypedInitExpression v : vs) {
			et.add((ElementsType) v.getType());
		}
		return et;
	}

	/**
	 * list all the possible combinations of the enum const of such list of
	 * enumtypes
	 */
	public static List<List<EnumConst>> all(List<? extends ElementsType> et) {
		return allT(getElements(et));
	}

	private static List<List<EnumConst>> getElements(List<? extends ElementsType> etL) {
		List<List<EnumConst>> result = new ArrayList<List<EnumConst>>();
		for (ElementsType et : etL)
			result.add(et.allElements());
		return result;
	}

	/**
	 * given a list of list of elements, return all the possible combinations
	 * 
	 */
	public static <T> List<List<T>> allT(List<List<T>> et) {
		return allT(et, et.size() - 1);
	}

	/**
	 * 
	 * @param et
	 * @param n
	 * @return
	 */
	private static <T> List<List<T>> allT(List<List<T>> et, int n) {
		List<T> t = et.get(n);
		List<List<T>> result = new ArrayList<List<T>>();
		if (n == 0) {
			for (T ec : t) {
				result.add(Collections.singletonList(ec));
			}
			return result;
		} else {
			List<List<T>> partial = allT(et, n - 1);
			for (List<T> i : partial) {
				for (T ec : t) {
					List<T> partialRes = new ArrayList<T>(i);
					partialRes.add(ec);
					result.add(partialRes);
				}
			}
			return result;
		}

	}


	@Override
	public String toString() {
		return N + "-" + this.getClass().getSimpleName();
	}

	@Override
	public String getCoveragePrefix() {
		// TODO Auto-generated method stub
		return null;
	}


}

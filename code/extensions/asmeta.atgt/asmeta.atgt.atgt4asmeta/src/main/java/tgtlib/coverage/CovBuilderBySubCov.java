/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestPredicate;
import tgtlib.specification.Specification;

/**
 * Builds the coverage by using other coverage builders
 * 
 * S: specification
 * Q: test predicates
 * C: coverage
 * 
 * @author garganti
 */
public class CovBuilderBySubCov<S extends Specification, Q extends TestPredicate<?,?>, C extends CoverageTree<Q>>
		implements CoverageBuilder<S, C> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(CovBuilderBySubCov.class);

	/** can be null, in that case take the name of the spec
	 * 
	 */
	protected String name;

	// coverage builder and if they are selected or not
	private Map<CoverageBuilder<S, ? extends C>, Boolean> covBuildersEnabled;

	private List<CoverageBuilder<S, ? extends C>> covBuilders;

	private CoverageTreeFactory<? extends C> covTreeFact;

	/**
	 *
	 * @param n the  name of the coverage to be build: if null take the name of the spec.
	 * @param ctf the ctf coverage tree factory to build an empty tree
	 */
	protected CovBuilderBySubCov(String n, CoverageTreeFactory<? extends C> ctf) {
		name = n;
		covTreeFact = ctf;
		covBuilders = new ArrayList<CoverageBuilder<S,? extends C>>();
		covBuildersEnabled = new HashMap<CoverageBuilder<S, ? extends C>, Boolean>();
	}

	/**
	 * register a coverage builder which is enabled
	 * 
	 * @param covBuilder
	 */
	public final void register(CoverageBuilder<S, ? extends C> covBuilder) {
		LOGGER.debug("adding the coverage builder " + covBuilder + "  to "	+ this.toString());
		assert ! covBuilders.contains(covBuilder);
		Boolean insert = covBuilders.add(covBuilder);
		assert insert;
		Boolean oldVal = covBuildersEnabled.put(covBuilder, true);
		assert oldVal == null;
	}

	/**
	 * it builds the tree (it does not assign the ID).
	 * 
	 * @param spec
	 *            the specification to be analyzed
	 * 
	 * @return the tree done in this way:
	 */
	@Override
	public final C getTPTree(S spec) {
		C result = covTreeFact.buildEmptyCovTree(name == null ? spec.getName(): name);
		setEnabled();
		// append the coverages obtained by the set of sub coverage registered
		for (CoverageBuilder<S, ? extends C> p : covBuilders) {
			// if enabled
			if (covBuildersEnabled.get(p)) {
				LOGGER.debug("computing the coverage tree for " + p.toString());
				C partialTree = p.getTPTree(spec);
				assignID(p, partialTree);
				result.addNode(partialTree);
			} else {
				LOGGER.debug("skipping coverage " + p.toString());
			}
		}
		return result;
	}

	/**
	 * enable the single coverage (already registered)
	 * 
	 * @param cov
	 * @param covSelected
	 * @return
	 */
	protected Boolean setEnabled(CoverageBuilder<S, C> cov, boolean covSelected) {
		assert covBuilders.contains(cov);
		Boolean oldValue = covBuildersEnabled.put(cov, covSelected);
		assert oldValue != null;
		return oldValue;
	}

	/**
	 * set the enabled coverages
	 * 
	 * @param p
	 * @return
	 */
	protected void setEnabled() { // DO NOTHING
	}

	/**
	 * assign the necessary ID TODO: useful only for SCRCoverage
	 * 
	 * @param coverageBuilder
	 * @param partialTree
	 */
	protected void assignID(CoverageBuilder<S, ? extends C> coverageBuilder, C partialTree) {

	}

	/**
	 * returns the unique prefix for the test goal generated for this coverage.
	 * 
	 * @return the coverage prefix
	 */
	@Override
	public String getCoveragePrefix() {
		if (name != null) return name.replaceAll(" ", "_"); 
		return "";
	}

}

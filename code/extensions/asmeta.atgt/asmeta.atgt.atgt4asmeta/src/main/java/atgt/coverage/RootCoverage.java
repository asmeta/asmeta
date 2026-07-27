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
package atgt.coverage;

import atgt.combinatorial.AsmCombCovBuilder;
import atgt.combinatorial.NWiseCoverage;
import atgt.preferences.ATGToolPreferences;
import atgt.specification.ASMSpecification;
import extgt.coverage.combinatorial.NWiseCovBuilder;
import extgt.coverage.combinatorial.PairwiseCovBuilder;
import extgt.coverage.fault.ClassicalFaultsCoverageBuilder;
import tgtlib.coverage.CovBuilderBySubCov;
import tgtlib.coverage.CoverageTreeFactory;

/**
 * Gestisce la lista dei AsmCoverageBuilder o <I>visitor</I> per la generazione
 * dei coverage. Per ogni criterio di copertura si aggiunge nella lista una
 * istanza del corrispondente SpecificationVisitor. Alcuni visitor vengono
 * aggiunti di default. Per modificare i criteri della strategia basta cambiare
 * i <I>visitors</I> della lista.
 * 
 * Questa potrebbe essere sostituita da i plugin di eclispe
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */
abstract class CoveragesStrategy extends
		CovBuilderBySubCov<ASMSpecification, AsmTestCondition, AsmCoverage> {

	protected CoveragesStrategy(final String n) {		
		this(n, AsmCoverageTree.factory);	}

	protected CoveragesStrategy(final String n, CoverageTreeFactory<? extends AsmCoverage> cft) {		
		super(n, cft);	}
	
}

/**
 * root coverage: it contains all the coverages
 * 
 * @author garganti
 * 
 */
public class RootCoverage extends CoveragesStrategy {

	private RootCoverage() {
		// specialized constructor
		super(ROOT_NAME,AsmCoverageTree.factory);
		// structural
		this.register(StructuralCoverage.STRUCT_COV);
		// FAULT BASED
		this.registerAllFaults();
		// COMBINATORIAL
		this.register(CombinatorialCoverage.COMB_COV);
	}
	
	public final static CovBuilderBySubCov<ASMSpecification, AsmTestCondition, AsmCoverage> STRUCT_COV = StructuralCoverage.STRUCT_COV;

	public final static RootCoverage ROOT = new RootCoverage();

	public static final String ROOT_NAME = "COVERAGES";

	void registerAllFaults() {
		CoverageTreeFactory<AsmCoverage> cft = Coverage.factory;
		ClassicalFaultsCoverageBuilder<ASMSpecification, AsmTestCondition, AsmCoverage> allCoverages = ClassicalFaultsCoverageBuilder
				.makeAllCoverages(cft, DecisionVisitor.computeDecisions,
						TestConditionFactory.factory);
		this.register(allCoverages);
	}

}

class CombinatorialCoverage extends CoveragesStrategy {

	static final int N_WISE_MAX = ATGToolPreferences.COMB_DEGREE.getValue();

	private CombinatorialCoverage() {
		super("Combinatorial Coverage");
		PairwiseCovBuilder<ASMSpecification, AsmTestCondition, AsmCoverage> visitor = AsmCombCovBuilder.makePairwiseCovBuilder();
		// pairwise
		if (N_WISE_MAX >= 2) super.register(visitor);
		// n- wise
		for (int i = 3; i <= N_WISE_MAX; i++) {
			NWiseCovBuilder<ASMSpecification, AsmTestCondition, NWiseCoverage> visitor1 = AsmCombCovBuilder.createNWiseCovBuilder(i);
			super.register(visitor1);
		}
	}

	public static final CombinatorialCoverage COMB_COV = new CombinatorialCoverage();

}

/* the main sub strateg */
class StructuralCoverage extends CoveragesStrategy {

	private StructuralCoverage() {
		super("Structural Coverage");
		// Aggiunge i visitor di default
		// Basic Rule Coverage Visitor
		register(new BasicRuleVisitor());
		// Complete Rule Coverage Visitor
		register(new CompleteRuleVisitor());
		// Rule Update Visitor
		register(new RuleUpdateVisitor());
		// Full Predicate / MCDC Visitor
		register(MCDCCoverage.getCoverage());
		// Rule Guard Visitor
		// NOT SURE IT WORKS
		// visitorCollection.add(new RuleGuardVisitor());

	}

	public static final StructuralCoverage STRUCT_COV = new StructuralCoverage();
}

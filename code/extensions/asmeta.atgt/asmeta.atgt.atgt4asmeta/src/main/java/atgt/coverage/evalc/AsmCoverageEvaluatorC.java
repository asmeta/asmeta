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

package atgt.coverage.evalc;

import org.apache.log4j.Logger;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.TPIndex;
import atgt.coverage.ToTpIndex;
import atgt.coverage.VisitableTPTreeNode;
import atgt.specification.ASMSpecification;
import tgtlib.evalcoverage.CoverageEvaluatorC;

/**
 * The Class AsmCoverageEvaluatorC.
 *
 * @author garganti
 */
public class AsmCoverageEvaluatorC extends
		CoverageEvaluatorC<AsmTestCondition, ASMSpecification> {

	/** The log. */
	static Logger log = Logger.getLogger(AsmCoverageEvaluatorC.class);

	/** The test predicates. */
	final TPIndex testPredicates;

	/**
	 * Creates a new instance of CoverageEvaluator for a specification and a
	 * tree PUT these in the constructor because I could call the method cover
	 * for the same Spec and tree without.
	 *
	 * @param s
	 *            the s
	 * @param tgtn
	 *            the tgtn
	 */
	public AsmCoverageEvaluatorC(ASMSpecification s, VisitableTPTreeNode tgtn) {
		super(new AsmTranslatorInputsToC(s, tgtn));
		testPredicates = tgtn.accept(ToTpIndex.INSTANCE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.CoverageEvaluatorC#getTPwithID(java.lang.String)
	 */
	@Override
	protected AsmTestCondition getTPwithID(String tpID) {
		return testPredicates.getTPbyID(tpID);
	}
}

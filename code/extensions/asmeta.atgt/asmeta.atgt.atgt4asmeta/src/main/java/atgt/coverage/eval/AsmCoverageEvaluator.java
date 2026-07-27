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
package atgt.coverage.eval;

import java.util.ArrayList;
import java.util.Vector;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.TestCondition;
import atgt.coverage.evalc.NavigableAsmInputs;
import tgtlib.definitions.NavigableInputSequence;
import tgtlib.definitions.expression.visitors.EvaluationNotSupported;
import tgtlib.definitions.expression.visitors.ModelIncomplete;
import tgtlib.evalcoverage.CoverageEvaluator;

/**
 * The Class AsmCoverageEvaluator computes the coverage without passing thru the
 * C file. It is used when adding seeds (instead of producing a c file)
 */
public class AsmCoverageEvaluator implements CoverageEvaluator<AsmTestCondition> {

	/** The covtree. */
	AsmCoverage covtree;

	/**
	 * Instantiates a new coverage evaluator.
	 * 
	 * @param ct
	 *            the ct
	 */
	public AsmCoverageEvaluator(AsmCoverage ct) {
		covtree = ct;
	}

	/**
	 * scroll all the tps in the coverage tree and if t is covered by tr 1) add
	 * t to the to covered tr 2) add mark those covered.
	 * see also method searchOtherCoverages of AsmTestSuiteGenerator
	 * @param tr
	 *            the tr
	 */
	public Vector<AsmTestCondition> markCoverage(AsmTestSequence tr) {
		Vector<AsmTestCondition> covered = new Vector<>();
		ExpressionEvaluatorSeq ev = new ExpressionEvaluatorSeq(tr.allInstructions());
		for (TestCondition otc : covtree.allTPs()) {
			try {
				boolean result = otc.getCondition().accept(ev);
				// log.debug("checking " + otc.getCondition().toString() + " ->
				// " +
				// result);
				if (result) {
					otc.bindTestSeqTestPred(tr);
					covered.add((AsmTestCondition) otc);
				}
			} catch (EvaluationNotSupported ee) {
				ee.printStackTrace();
				System.err.println("evaluation of " + otc.getName() + ":"
						+ otc.getCondition() + " not supported yet");
			} catch (ModelIncomplete mi) {
				System.err.println("model not complete:" + mi.getMessage());
				System.err.println("TP:" + otc.getCondition());
				System.err.println("test:" + tr.allInstructions());
			}
		}
		return covered;
	}

	@Override
	public Vector<AsmTestCondition> computeCoverage(NavigableInputSequence inputs) {
		if (inputs instanceof NavigableAsmInputs) {
			NavigableAsmInputs asmtestSeq = (NavigableAsmInputs) inputs;
			return markCoverage(asmtestSeq.getAsmTesSequence());
		}
		throw new RuntimeException("not implemented yet");
	}

}

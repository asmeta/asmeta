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

import atgt.coverage.tpstatus.TestConditionState;

/**
 * The Class CoverageInfoBuilder: builds 
 */
public class CoverageInfoBuilder implements TPTreeNodeVisitor<CoverageInfo> {

	static public final CoverageInfoBuilder INSTANCE = new CoverageInfoBuilder();
	
	private CoverageInfoBuilder(){}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverageTree(atgt.coverage.CoverageTree)
	 */
	@Override
	public CoverageInfo forCoverageTree(AsmCoverageTree c) {
		CoverageInfo result = new CoverageInfo();
		for (VisitableTPTreeNode tc : c.allCoverages()) {
			CoverageInfo intermediate = tc.accept(this);
			result.addCoverageInfo(intermediate);
			result.time += intermediate.time;
			result.notCoveredByOthers += intermediate.notCoveredByOthers;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverage(atgt.coverage.Coverage)
	 */
	@Override
	public CoverageInfo forCoverage(Coverage c) {
		CoverageInfo result = new CoverageInfo();
		for (TestCondition tc : c.allTPs()) {
			TestConditionState status = tc.getStatus();
			result.addTestWithState(status);
			if (tc.allCoveredBy().isEmpty())
				result.notCoveredByOthers++;
			AsmTestSequence tr = (AsmTestSequence)tc.getTestResult();
			if (tr != null)
				result.time += tr.time;
		}
		return result;
	}
}

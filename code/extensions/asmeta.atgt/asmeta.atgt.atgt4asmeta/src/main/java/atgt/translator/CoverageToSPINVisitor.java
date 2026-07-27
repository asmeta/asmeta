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
package atgt.translator;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.Coverage;
import atgt.coverage.CoveragesVisitorI;
import atgt.coverage.TestCondition;
import atgt.coverage.VisitableTPTreeNode;

/**
 * returns the representation of a CoverageTree in spin it translates all the tp
 * as printf statement.
 *
 * @author garganti
 */
public class CoverageToSPINVisitor implements CoveragesVisitorI<StringBuffer> {

	/** The coverages. */
	protected AsmCoverageTree coverages;

	/** The test condition. */
	protected TestCondition testCondition;

	/** The indent. */
	protected String indent;

	/**
	 * Instantiates a new coverage to spin visitor.
	 *
	 * @param _indent
	 *            the _indent
	 * @param _testCondition
	 *            the _test condition
	 */
	public CoverageToSPINVisitor(String _indent, TestCondition _testCondition) {
		this.indent = _indent;
		this.testCondition = _testCondition;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverageTree(atgt.coverage.CoverageTree)
	 */
	@Override
	public StringBuffer forCoverageTree(AsmCoverageTree c) {
		StringBuffer result = new StringBuffer();
		for (VisitableTPTreeNode e : c.allCoverages())
			result.append(e.accept(this));
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverage(atgt.coverage.Coverage)
	 */
	@Override
	public StringBuffer forCoverage(Coverage c) {
		StringBuffer result = new StringBuffer();
		for (TestCondition tc : c.allTPs()) {
			if (!tc.equals(this.testCondition))
				result.append(tc.accept(this));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.CoveragesVisitorI#forTestCondition(atgt.coverage.TestCondition)
	 */
	@Override
	public StringBuffer forTestCondition(AsmTestCondition test) {
		StringBuffer result = new StringBuffer();
		StringBuffer testExpr = test.getCondition().accept(
				ExpressionToSPINVisitor.SINGLETON);
		result.append(this.indent + "printf(\"_Covered: " + test.getUniqueID()
				+ " %d\\n\"," + testExpr + ");\n");
		return result;
	}

	public void setSearchCommonCoverage(boolean searchCommonCoverage) {
	}
}

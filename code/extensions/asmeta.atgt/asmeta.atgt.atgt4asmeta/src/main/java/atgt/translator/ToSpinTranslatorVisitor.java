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
import atgt.coverage.TestCondition;

/**
 * The Class ToSpinTranslatorVisitor, traslates a specification to Promela
 */
public abstract class ToSpinTranslatorVisitor extends TranslatorVisitor {

	/** The indent. */
	protected String indent;

	/** The changed monitored output. */
	protected boolean changedMonitoredOutput; // if the test sequence must
	// contain only variables that
	// change or all

	/** The search common coverage. */
	protected boolean searchCommonCoverage;

	/** The coverages. */
	protected AsmCoverageTree coverages;

	/**
	 * Instantiates a new to spin translator visitor.
	 */
	public ToSpinTranslatorVisitor() {
		this.indent = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.translator.TranslatorVisitor#setTestCondition(atgt.coverage.TestCondition)
	 */
	@Override
	public void setTestCondition(TestCondition _tc) {
		super.setTestCondition(_tc);
		this.indent = "";
	}

	/**
	 * Sets the search common coverage.
	 * 
	 * @param b
	 *            the new search common coverage
	 */
	public void setSearchCommonCoverage(boolean b) {
		this.searchCommonCoverage = b;
	}

	/**
	 * Sets the coverages.
	 * 
	 * @param cvgs
	 *            the new coverages
	 */
	public void setCoverages(AsmCoverageTree cvgs) {
		this.coverages = cvgs;
	}

	public boolean isSearchCommonCoverage() {
		return this.searchCommonCoverage;
	}

}

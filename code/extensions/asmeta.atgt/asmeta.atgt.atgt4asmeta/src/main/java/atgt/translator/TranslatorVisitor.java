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

import atgt.coverage.TestCondition;
import atgt.specification.ASMSpecification;
import tgtlib.specification.SpecificationAnalyzer;

/**
 * The Class TranslatorVisitor.
 */
public abstract class TranslatorVisitor implements
		SpecificationAnalyzer<StringBuffer,ASMSpecification> {

	/** test condition to be tested it can be null. */
	protected TestCondition tc;

	// it can be null
	// normally contains only one
	// private Set<TestCondition> testpredicates;
	// Collections.singleton(tc);

	/**
	 * Instantiates a new translator visitor.
	 */
	public TranslatorVisitor() {
		super();
	}

	/**
	 * SP: the specification to be translated.
	 * 
	 * @param sp
	 *            the sp
	 * 
	 * @return the string buffer
	 */
	@Override
	public abstract StringBuffer analyze(ASMSpecification sp);

	/**
	 * set the test condition to be used in the translation.
	 * 
	 * @param _tc
	 *            the _tc
	 */
	public void setTestCondition(TestCondition _tc) {
		this.tc = _tc;
	}

}

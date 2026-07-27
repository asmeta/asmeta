///*******************************************************************************
// * Copyright (c) 2008 Angelo Gargantini.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Contributors:
// *     Angelo Gargantini - initial API and implementation
// ******************************************************************************/
//package atgt.generator;
//
//import atgt.generator.testsuite.TestSuiteGeneratorForTC;
//import atgt.project.AsmProject;
//
///**
// * generated the test sequence for a given test condition by SAL
// * 
// * it uses a needs a test generator for SAL to be called
// * 
// * in the future all similar TestGeneratorForTC should be grouped
// * 
// * this is peculiar because SAL needs a file instead of a spec.
// */
//public class SalTSuiteGenForTC extends TestSuiteGeneratorForTC<SalTSeqGenerator> {
//
//	/**
//	 * Instantiates a new sal test suite gen for tc.
//	 * 
//	 * @param _project
//	 *            the _project
//	 */
//	public SalTSuiteGenForTC(AsmProject _project) {
//		super(_project, new SalTSeqGenerator(_project.specification));
//	}
//}

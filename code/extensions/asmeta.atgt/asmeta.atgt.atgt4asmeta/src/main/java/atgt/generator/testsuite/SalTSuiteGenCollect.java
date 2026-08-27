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
//package atgt.generator.testsuite;
//
//import atgt.coverage.AsmTestSuite;
//import atgt.generator.SalTSeqGenerator;
//import atgt.specification.ASMSpecification;
//import tgtlib.coverage.CoverageTree;
//import tgtlib.definitions.TestPredicate;
//import tgtlib.definitions.TestSequence;
//import tgtlib.definitions.TestSuite;
//import tgtlib.project.Project;
//import tgtlib.specification.Specification;
//
///**
// * the generator for SAL by using collect.
// */
//public class SalTSuiteGenCollect<S extends Specification,  T extends TestSequence<? extends TC>, TC extends TestPredicate<? extends T,?>,TS extends TestSuite, C extends CoverageTree<? extends TC>> extends TestGeneratorCollectTP<S,T,TC,TS,C> {
//
//	/**
//	 * Instantiates a new sal t suite gen collect.
//	 * 
//	 * @param _project
//	 *            the _project
//	 */
//	public SalTSuiteGenCollect(Project _project) {
//		super(_project, new SalTSeqGenerator((ASMSpecification) _project.specification), AsmTestSuite.getAsmTestSuiteFactory());
//	}
//}

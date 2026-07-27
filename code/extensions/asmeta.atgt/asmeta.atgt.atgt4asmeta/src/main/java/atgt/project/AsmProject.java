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
package atgt.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.Coverage;
import atgt.coverage.TestCondition;
import atgt.coverage.eval.AsmCoverageEvaluator;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.parser.asmgofer.AsmGoferLoader;
import atgt.project.parser.ExtraTests;
import atgt.project.parser.ParseException;
import atgt.project.parser.ReadTestsAndTps;
import atgt.specification.ASMSpecification;
import tgtlib.project.Project;

/**
 * this class represents an ASM project: a specification, a coverage tree and a
 * list of tests.
 */
public class AsmProject extends Project<ASMSpecification,AsmTestCondition, AsmTestSequence, AsmCoverage> {

	/** The log. */
	static Logger log = Logger.getLogger(AsmProject.class);

	
	// add the Tree Model
	// TODO avoid duplication: CoverageTree should implement AbstractTreeModel
	// it is here to allow the adding of new coverage during the execution
	/** The coverage model. */
	private DefaultMutableTreeNode coverageModel;

	// and the list Model:
	// TODO avoid the duplication of info (in the model and in the test suite)
	// with wrappers or something
	// it contains TestSequences
	/** The tests suite model. */
	private DefaultListModel testsSuiteModel;

	/**
	 * build an empty project.
	 */
	public AsmProject() {
		super(new AsmetaLLoader(), new AsmGoferLoader());
		testSuite = new AsmTestSuite();
		testsSuiteModel = new DefaultListModel();
		testpredicateTree = new AsmCoverageTree("ROOT");
	}

	/**
	 * build a project for the specifiaition and the coveragetree.
	 * 
	 * @param spec
	 *            the spec
	 * @param ct
	 *            the coverage tree (also a simple Cveragetree is acceptable)
	 */
	public AsmProject(ASMSpecification spec, AsmCoverage ct) {
		this();
		specification = spec;
		testpredicateTree = ct;
	}

	// add a test suite to this project
	/**
	 * Adds the asm test suite.
	 * 
	 * @param ts
	 *            the ts
	 */
	public void addAsmTestSuite(AsmTestSuite ts) {
		testSuite.addAllTest(ts);
		// add also to the model
		for (AsmTestSequence tseq : ts) {
			testsSuiteModel.addElement(tseq);
		}
	}

	/**
	 * add a new coverage to this project: to the data and to the tree model.
	 * 
	 * @param c
	 *            the c
	 */
	private void addCoverage(Coverage c) {
		testpredicateTree.addNode(c);
		// get the tree for c
		DefaultMutableTreeNode node = c.accept(new CoverageTreeModelVisitor());
		getCoveragesModel().add(node);
	}

	/**
	 * add extra test predicates and tests read from a file.
	 * 
	 * @param file
	 *            the file
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public void addExtraTps(File file) throws FileNotFoundException,
			ParseException {
		// parses the file
		FileReader fr = new FileReader(file);
		if (ReadTestsAndTps.token_source == null)
			new ReadTestsAndTps(fr);
		else
			ReadTestsAndTps.ReInit(fr);
		ExtraTests extra = ReadTestsAndTps.readInfo();
		// add all the tests
		for (AsmTestSequence ts : extra.getTests())
			addTestSequence(ts);
		// add the coverage
		addCoverage(extra.getDefinedTps());
		// reavalutes the coverage
		// 1. check if the tests (including the new ones) cover the tps
		AsmCoverageEvaluator coverageEvaluator = new AsmCoverageEvaluator(testpredicateTree);
		for (AsmTestSequence tr : testSuite) {
			coverageEvaluator.markCoverage(tr);
		}
		// 2. check which the new tps are covered by old tests
		for (TestCondition tc : testpredicateTree.allTPs()) {
			// TODO
		}
	}

	/**
	 * Adds the test sequence.
	 * 
	 * @param ts
	 *            the ts
	 * 
	 * @return true, if successful
	 */
	private boolean addTestSequence(AsmTestSequence ts) {
		boolean result = testSuite.addTest(ts);
		// update also the model
		testsSuiteModel.addElement(ts);
		return result;
	}

	/**
	 * Gets the asm test suite.
	 * 
	 * @return the test suite
	 */
	public AsmTestSuite getAsmTestSuite() {
		return (AsmTestSuite) testSuite;
	}

	/**
	 * return the tree model for the coverage tree note that if the coverages
	 * changes, the tree does not if one wants to change the coverages, it must
	 * use add Coverage.
	 * 
	 * @return the coverages model
	 */
	public DefaultMutableTreeNode getCoveragesModel() {
		if (coverageModel == null)
			coverageModel = testpredicateTree.accept(new CoverageTreeModelVisitor());
		return coverageModel;
	}

	/**
	 * return the tree model for the specification.
	 * 
	 * @return the spec model
	 */
	public DefaultTreeModel getSpecModel() {
		if (specification == null)
			return new DefaultTreeModel(new DefaultMutableTreeNode(
					"Specification"));
		return new DefaultTreeModel(new TreeModelSpecificationVisitor()
				.analyze(specification));
	}

	@Override
	public boolean reduceTestSuite(){
		if (super.reduceTestSuite()){
			fireTestsStatusChanged();
			log.info("test suite reduced");
			return true;
		} else{
			log.info("test suite not reduced");
			return false;
		}
	}
	
	/**
	 * return the list model for the test suite andrebbe eliminato.
	 * 
	 * @return the tests model
	 */
	public ListModel getTestsModel() {
		return testsSuiteModel;
	}


	private void fireTestsStatusChanged() {
		((AsmTestSuite) testSuite).fireTestsStatusChanged();
	}

	@Override
	public boolean readTest(InputStreamReader inputStreamReader) {
		throw new RuntimeException("not implemented yet");
	}


}

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
package tgtlib.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

//import de.schlichtherle.io.ArchiveException;
import tgtlib.coverage.CoverageBuilder;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSuite;
import tgtlib.reduction.TestSuiteRed;
import tgtlib.specification.ParseException;
import tgtlib.specification.SpecReader;
import tgtlib.specification.Specification;

/**
 * represents a project: 1) a specification 2) a test goal tree 3) a set of test
 * sequences A subclass can instantiate the project with a spectype.
 * 



 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */
abstract public class Project<S extends Specification, 
							  PR extends TestPredicate<? extends TS,?>, 
							  TS extends TestSequence<? extends PR>,
							  COV extends CoverageTree<? extends PR>> { // ,
	// TS
	// extends
	// AsmTestSuite>{

	/**
	 * name of the project
	 */
	protected String name;

	/** the specification of the project. */
	public S specification;
	
	
	/** build an anonymous project
	 * 
	 * @param s SpecReader<S>[]
	 */
	protected Project(SpecReader<S> ... s) {
		specReaders = s;
	}

	/**
	 * Creates a new instance of an empty project with a zip file associated to it. no spec no
	 * other data (not anonymous project)
	 * 
	 * @param projectName
	 *            the prj name (the zip file will have the same name + zip)
	
	
	 * @param s SpecReader<S>[]
	 * @throws ProjectAlreadyExisting
	 *             the project already existing * @throws IOException */
	public Project(String projectName, SpecReader<S> ... s)
			throws ProjectAlreadyExisting, IOException {
		this(s);
		assert(!projectName.endsWith(".zip"));
		String zipFilename = projectName + ".zip";
		// check it already exists
		File zipFile = new File(zipFilename);
		if (zipFile.exists())
			throw new ProjectAlreadyExisting(zipFilename);
		// set the file field
		name = projectName;

		// zipFile.createNewFile(); --> STOPS WORKING LOAD SPEC
		/*
		 * // otherwise create this new Zip File de.schlichtherle.io.File
		 * zipProject = new de.schlichtherle.io.File(zipFilename); // close the
		 * zip file to allow access to data during generation
		 * de.schlichtherle.io.File.umount(true, true, true, true);
		 */
	}


	/**
	 * the set of test sequences found so far. per garantire il polimorfismo,
	 * l'utente deve lui creare un test suite adatto a assegnarlo - per questo
	 * project non ha costruttori, perch� anche il test suite � polimorfisco -
	 * in futuro forse no
	 */
	protected TestSuite<PR, TS> testSuite;

	/**
	 * Method getTestSuite.
	 * @return TestSuite<PR,TS>
	 */
	public TestSuite<PR, TS> getTestSuite() {
		return testSuite;
	}

	/** all the specification reader, it can be multiple
	 * 
	 */
	SpecReader<S>[] specReaders;

	/**
	 * Method isSpecLoadable.
	 * @param f String
	 * @return boolean
	 */
	public boolean isSpecLoadable(String f) {
		for(SpecReader<S> s: specReaders)
			if (s.isFileLoadable(f)) return true;
		return false;
	}

	/**
	 * load the specification from a a input reader it does not compute the TP
	 * 
	
	 * @param in java.io.Reader
	 * @return boolean
	 * @throws ParseException */
	public final boolean readSpec(java.io.Reader in) throws ParseException {
		assert specReaders.length == 1;
		specification = specReaders[0].read(in);
		return (specification != null);
	}

	
	
	/**
	 * load the specification from a a input reader it does not compute the TP
	 * 
	
	
	 * @param f File
	 * @return boolean
	 * @throws FileNotFoundException * @throws ParseException */
	final boolean readSpec(File f) throws FileNotFoundException,
			ParseException {
		for(SpecReader<S> s: specReaders){
			if (s.isFileLoadable(f.getAbsolutePath())){
				specification = s.read(f);
				break;
			}
		}
		if (specification != null)
			return true;
		else
			return false;
	}

	/** Load a specification in the project: reads the spec
	 * tree and add the spec to the project file. It does not compute the test tree
	 * 
	 * @param specFile the spec file (with the right extension)
	 * 
	
	
	
	
	
	
//	 * @throws FileNotFoundException the spec file is not found * @throws ParseException  * @throws ArchiveException the archive exception * @throws ProjectAlreadyExisting * @throws IOException * @throws ArchiveException  */
//	public void loadSpec(File specFile) throws FileNotFoundException, ParseException, ArchiveException {
//		// if the project is not empty
//		if (!specFile.exists()) throw new FileNotFoundException();
//		// set the spec
//		readSpec(specFile);
//		//
//		// add the spec to the zip file
//		//
//		// put sal in the project (need to be done by truezip)
//		if (name != null) {
//			String zipFilename = name + ".zip";
//			de.schlichtherle.io.File zipProject = new de.schlichtherle.io.File(
//					zipFilename);
//			de.schlichtherle.io.File out = new de.schlichtherle.io.File(
//					zipProject, specFile.getName());
//			out.copyFrom(specFile);
//			// close the zip file to allow access to data during generation
//			de.schlichtherle.io.File.umount(true, true, true, true);
//		}
//	}


    /** the tree of test predicates
     * 
     */
	protected COV testpredicateTree;


	/**
	 * compute the test tree
	 * @param covBuilder CoverageBuilder<S,COV>
	 */
	public void computeTestTree(CoverageBuilder<S, COV> covBuilder){
		assert covBuilder != null;
		testpredicateTree = covBuilder.getTPTree(this.specification);
	}
	
	/**
	 * 
	
	 * @return the coverage tree */
	public COV getTestTree() {
		return testpredicateTree;
	}
		
	/**
	 * load a test from an input stream reader (file
	 * 
	 * @param inputStreamReader
	
	 * @return boolean
	 */
	public abstract boolean readTest(InputStreamReader inputStreamReader);

	/**
	 * Reduce test suite.
	 * 
	
	 * @return true, if successful */
	public boolean reduceTestSuite() {
		return TestSuiteRed.reduce(testSuite);
	}

}

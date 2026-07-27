package tgtlib.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipException;

import tgtlib.coverage.CoverageBuilder;
import tgtlib.coverage.CoverageTree;
import tgtlib.specification.ParseException;
import tgtlib.specification.Specification;

/**
 * Represents an instance of Test Generator TOOL contains all the data necessary
 * to run a tool NO USER INTERFACE (no gui, command and so on) methods permit to
 * change the project, change the generation methods and so on It adds to the
 * project other info about the test generation.
 * @param <COV>
 */

//public abstract class TGTool<S extends Specification, PR extends TestPredicate<?,?>,
                             //COV extends CoverageTree<PR>,P extends Project<S, PR, ?, COV>> {

public abstract class TGTool<S extends Specification, COV extends CoverageTree<?>, P extends Project<S,?,?,COV>> {

	ProjectFactory<P> projectFactory;

	public TGTool(ProjectFactory<P> pf) {
		projectFactory = pf;
		project = pf.createNewEmptyProject();
	}

	/**
	 * the project where to store tps and tests if it is null, there is no
	 * project associated
	 */
	public P project;

	/**
	 * return the current project
	 * 
	 * @return
	 */
	public P getProject() {
		return project;
	}

	/**
	 * load an exiting project as zip file
	 * 
	 * @param fileZip
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws ZipException
	 */
	public void loadProject(String fileZip) throws IOException, ParseException {
		project = projectFactory.load(fileZip);

	}

	/**
	 * creates a new project with the specification
	 * 
	 * @return
	 * @throws ParseException
	 * @throws FileNotFoundException
	 */
	public void loadSpecification(File f) throws FileNotFoundException,
			ParseException {
		project.readSpec(f);
		// build the test tree
		computeTestTree();
	}
	/**
	 * load a specification from the reader
	 * 
	 * @param in
	 * @throws ParseException
	 */
	public void loadSpecification(java.io.Reader in) throws ParseException {
		project.readSpec(in);
		computeTestTree();		
	}

	
	/**
	 * returns the way to generate the complete tree (from the root);
	 */
//	public abstract CovBuilderBySubCov<S,PR, COV> getCoverageBuilder();
	public abstract CoverageBuilder<S, COV> getCoverageBuilder();

	/** load a new empty project
	 * */
	public void newProject() {
		project = projectFactory.createNewEmptyProject();
	}

	/**
	 * compute the test tree
	 */
	public void computeTestTree(){
		// build the test tree
		project.computeTestTree(getCoverageBuilder());
	}

}

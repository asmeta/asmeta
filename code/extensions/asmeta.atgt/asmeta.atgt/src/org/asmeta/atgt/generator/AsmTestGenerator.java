package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import tgtlib.coverage.CovBuilderBySubCov;
import tgtlib.coverage.CoverageBuilder;
import tgtlib.specification.ParseException;
import tgtlib.specification.Specification;

/**
 * abstract test generator for asm specifications - they can be asmeta specs of ASMSpec (old format)
 */
public abstract class AsmTestGenerator<S extends Specification>{

	static private Logger logger = Logger.getLogger(AsmTestGenerator.class);

	public static final List<String> DEFAULT_FORMATS = FormatsEnum
			.toListOfString(new FormatsEnum[] { FormatsEnum.AVALLA });

	public static final boolean DEFAULT_COMPUTE_COVERAGE = true;

	/** compute coverage?*/
	protected final boolean coverageTp;

	protected S spec;

	protected AsmCoverage ct;

	AsmTestGenerator(String asmfile, boolean coverageTp, SpecificationReader<S> specReader) throws ParseException, Exception {
		assert new File(asmfile).exists() : asmfile + "not existing";
		// read the spec
		spec = specReader.read(asmfile);
		// should never happen because read will throw its own exception
		if (spec == null) {
			throw new RuntimeException("errors in reading the ASM specification");
		}
		this.coverageTp = coverageTp; 
	}

	/**
	 * Generate testfor asm.
	 *
	 * @param asmfile the asmfile
	 * @param asm     the asm specification
	 * @param ct
	 * @param spec
	 *
	 * @return the asm test suite
	 * @throws Exception
	 */
	abstract protected AsmTestSuite generateTestforASM() throws Exception;

	/**
	 * generate the test starting from the CovergaeTree fo tps already generated
	 * 
	 * @return
	 * @throws Exception
	 */
	protected AsmTestSuite generateTests() throws Exception {
		assert ct != null: "build first the coverage tree by using the buildTree method";
		// generate tests
		AsmTestSuite ts = generateTestforASM();
		assert ts != null;
		return ts;
	}

	/**
	 * Builds the TP tree and queue all the termTran according to the regex
	 *
	 * @param criteria the criteria
	 * @param maxTests the max tests
	 * @param regex the regex
	 */
	//public void buildTPTree(CovBuilderBySubCov<S, AsmTestCondition, AsmCoverage> criteria, int maxTests, String regex) {
	public void buildTPTree(CoverageBuilder<S,AsmCoverage> criteria, int maxTests, String regex) {
		logger.debug("generating the tp tree for criteria " + criteria.getCoveragePrefix());
		// build the tree depending on the criteria
		ct = criteria.getTPTree(spec);
		// queue tps
		queueTPs(maxTests, regex);
		// print them
		for (AsmTestCondition tp : ct.allTPs()) {
			logger.debug(tp.getName() + "\t" + tp.getCondition());
		}
	}

	/** Queue all tops for test generation
	 * @param maxNTP
	 * @param ct
	 * @param regex
	 */
	public void queueTPs(int maxNTP, String regex) {
		// queue all TPs
		int i = 1;
		for (Iterator<AsmTestCondition> iterator = ct.allTPs().iterator(); iterator.hasNext() && i <= maxNTP;) {
			AsmTestCondition tc = iterator.next();
			String name = tc.getName();
			if (!name.matches(regex)) {
				continue;
			}
			tc.setToVerify(true);
			logger.debug("test predicate " + name + " queued");
			if (i++ > maxNTP) {
				logger.debug("max number of test predicates reached");
				break;
			}
		}
	}
}

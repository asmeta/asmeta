package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.coverage.AsmetaAsSpec;
import org.asmeta.atgt.generator.coverage.AsmetaCoverageBuilder;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CovBuilderBySubCov;

//public abstract class AsmTestGenerator {
public abstract class AsmTestGenerator {


	static private Logger logger = Logger.getLogger(AsmTestGenerator.class);

	/** compute coverage??? */
	protected final boolean coverageTp;

	// CAMBIO NON E' pù questa ma 
	//protected final ASMSpecification spec;
	protected final AsmetaAsSpec spec;

	// the coverage tree built according to some criteria
	protected AsmCoverage ct;

	protected AsmetaAsSpec getSpec() {
		return spec;
	}

	public static final List<CriteriaEnum> DEFAULT_CRITERIA = Arrays.asList(CriteriaEnum.BASIC_RULE);//,CriteriaEnum.COMPLETE_RULE, CriteriaEnum.RULE_UPDATE);
	
	public static final List<AsmetaCoverageBuilder> DEFAULT_COV_BUILDER = CriteriaEnum.getCoverageCriteria(DEFAULT_CRITERIA);
	
	
	public static final List<String> DEFAULT_FORMATS = FormatsEnum
			.toListOfString(new FormatsEnum[] { FormatsEnum.AVALLA, });

	public static final boolean DEFAULT_COMPUTE_COVERAGE = true;

	public AsmTestGenerator(String asmfile, boolean coverageTp) throws Exception {
		assert new File(asmfile).exists() : asmfile + "not existing";
		// read the spec
		//spec = new AsmetaLLoader().read(new File(asmfile));
		spec = AsmetaAsSpec.read(new File(asmfile));
		// should never happen because read will throw its own exception
		if (spec == null)
			throw new RuntimeException("errors in converting the asmeta for ATGT");
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
	 * @return
	 * @throws Exception
	 */
	public AsmTestSuite generateTests() throws Exception {
		// generate tests
		AsmTestSuite ts = generateTestforASM();
		assert ts != null;
		return ts;
	}

	/**
	 * Builds the TP tree and queue all the tp according to the regex
	 *
	 * @param criteria the criteria
	 * @param maxTests the max tests
	 * @param regex the regex
	 */
	public void buildTPTree(MBTCoverage criteria, int maxTests, String regex) {
		logger.debug("generating the tp tree for criteria " + criteria.getCoveragePrefix());
		// build the tree depending on the criteria
		ct = criteria.getTPTree(spec);
		// queue tps
		quequeTPs(maxTests, regex);
		// print them
		for (AsmTestCondition tp : ct.allTPs()) {
			logger.debug(tp.getName() + "\t" + tp.getCondition());
		}
	}
	
	/**
	 * 
	 * @param maxTests
	 * @param regex
	 * @return
	 * @throws Exception
	 */
	public AsmTestSuite generateAbstractTests(int maxTests, String regex) throws Exception {
		// collect the coverage criteria
		buildTPTree(new MBTCoverage(DEFAULT_COV_BUILDER), maxTests, regex);
		return generateTests();
	}


	public AsmTestSuite generateAbstractTests(Collection<AsmetaCoverageBuilder> coverageCriteria, int maxTests, String regex) throws Exception {
		buildTPTree(new MBTCoverage(coverageCriteria), maxTests, regex);
		return generateTests();
	}

	
	
	/**
	 * @param maxNTP
	 * @param ct
	 * @param regex
	 */
	protected void quequeTPs(int maxNTP, String regex) {
		// queue all TPs
		int i = 1;
		for (Iterator<AsmTestCondition> iterator = ct.allTPs().iterator(); iterator.hasNext() && i <= maxNTP;) {
			AsmTestCondition tc = iterator.next();
			String name = tc.getName();
			if (!name.matches(regex))
				continue;
			tc.setToVerify(true);
			logger.debug("tp " + name + " queued");
			if (i++ > maxNTP) {
				logger.debug("max number of tp reached");
				break;
			}
		}
	}

	/**
	 * Structural except MCDC which is difficult to use because there is an equal
	 * and Booleans
	 */
	public static class MBTCoverage extends CovBuilderBySubCov<AsmetaAsSpec, AsmTestCondition, AsmCoverage> {

		public MBTCoverage(Collection<AsmetaCoverageBuilder> criteria) {
			super("MBT Coverage", AsmCoverageTree.factory);

			for (AsmetaCoverageBuilder c : criteria)
				register(c);

			// Aggiunge i visitor di default
			// Basic Rule Coverage Visitor
//			register(new BasicRuleVisitor());
			// Complete Rule Coverage Visitor
//			register(new CompleteRuleVisitor());
			// Rule Update Visitor
//			register(new RuleUpdateVisitor());
			// Full Predicate / MCDC Visitor
			// register(MCDCCoverage.getCoverage());
			// Rule Guard Visitor
			// NOT SURE IT WORKS
			// visitorCollection.add(new RuleGuardVisitor());
		}
	}

}

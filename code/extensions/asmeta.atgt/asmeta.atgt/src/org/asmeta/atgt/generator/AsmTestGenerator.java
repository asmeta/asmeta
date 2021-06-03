package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CovBuilderBySubCov;
import tgtlib.specification.ParseException;

public abstract class AsmTestGenerator {


	static private Logger logger = Logger.getLogger(AsmTestGenerator.class);

	/** compute coverage??? */
	protected final boolean coverageTp;

	private final ASMSpecification spec;

	protected ASMSpecification getSpec() {
		return spec;
	}

	public static final List<CriteriaEnum> DEFAULT_CRITERIA = Arrays.asList(CriteriaEnum.BASIC_RULE,CriteriaEnum.COMPLETE_RULE, CriteriaEnum.RULE_UPDATE);
	
	public static final List<AsmCoverageBuilder> DEFAULT_COV_BUILDER = CriteriaEnum.getCoverageCriteria(DEFAULT_CRITERIA);
	
	
	public static final List<String> DEFAULT_FORMATS = FormatsEnum
			.toListOfString(new FormatsEnum[] { FormatsEnum.AVALLA, });

	public static final boolean DEFAULT_COMPUTE_COVERAGE = true;

	public AsmTestGenerator(String asmfile, boolean coverageTp) throws ParseException {
		assert new File(asmfile).exists();
		// read the spec
		spec = new AsmetaLLoader().read(new File(asmfile));
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
	abstract protected AsmTestSuite generateTestforASM(AsmCoverage ct) throws Exception;

	/**
	 * 
	 * @param maxTests
	 * @param regex
	 * @return
	 * @throws Exception
	 */
	public AsmTestSuite generateAbstractTests(MBTCoverage criteria, int maxTests, String regex) throws Exception {
		logger.debug("generating the tp tree for criteria " + criteria.getCoveragePrefix());
		// build the tree depending on the criteria
		AsmCoverage ct = criteria.getTPTree(spec);
		// queue tps
		quequeTPs(maxTests, ct, regex);
		// print them
		for (AsmTestCondition tp : ct.allTPs()) {
			logger.debug(tp.getName() + "\t" + tp.getCondition());
		}
		// generate tests
		AsmTestSuite ts = generateTestforASM(ct);
		assert ts != null;
		return ts;		
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
		return generateAbstractTests(new MBTCoverage(DEFAULT_COV_BUILDER), maxTests, regex);
	}


	public AsmTestSuite generateAbstractTests(Collection<AsmCoverageBuilder> coverageCriteria, int maxTests, String regex) throws Exception {
		return generateAbstractTests(new MBTCoverage(coverageCriteria), maxTests, regex);
	}

	
	
	/**
	 * @param maxNTP
	 * @param ct
	 * @param regex
	 */
	protected void quequeTPs(int maxNTP, AsmCoverage ct, String regex) {
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
	public static class MBTCoverage extends CovBuilderBySubCov<ASMSpecification, AsmTestCondition, AsmCoverage> {

		public MBTCoverage(Collection<AsmCoverageBuilder> criteria) {
			super("MBT Coverage", AsmCoverageTree.factory);

			for (AsmCoverageBuilder c : criteria)
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

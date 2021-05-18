package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmPrinter;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CovBuilderBySubCov;

public abstract class AsmTestGenerator {

	static private Logger logger = Logger.getLogger(AsmTestGenerator.class);

	/** compute coverage??? */
	protected final boolean coverageTp;

	/** coverage criteria to register */
	protected static Collection<AsmCoverageBuilder> criteria;

	private final ASMSpecification spec;

	protected ASMSpecification getSpec() {
		return spec;
	}

	public static final List<CriteriaEnum> DEFAULT_CRITERIA = Arrays.asList(CriteriaEnum.BASIC_RULE,
			CriteriaEnum.COMPLETE_RULE, CriteriaEnum.RULE_UPDATE);
	
	
	public static final List<String> DEFAULT_FORMATS = FormatsEnum
			.toListOfString(new FormatsEnum[] { FormatsEnum.AVALLA, });

	public static final boolean DEFAULT_COMPUTE_COVERAGE = true;

	public AsmTestGenerator(String asmfile, boolean coverageTp, Collection<AsmCoverageBuilder> criteria) {
		assert new File(asmfile).exists();
		// read the spec
		spec = new AsmetaLLoader().read(new File(asmfile));
		this.coverageTp = coverageTp;
		AsmTestGenerator.criteria = criteria;
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
	public AsmTestSuite generateAbstractTests(int maxTests, String regex) throws Exception {
		// collect the coverage criteria
		MBTCoverage criteria = new MBTCoverage();
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
	 * @param maxNTP
	 * @param ct
	 * @param regex
	 */
	protected void quequeTPs(int maxNTP, AsmCoverage ct, String regex) {
		// queue all TPs
		int i = 1;
		for (AsmTestCondition tc : ct.allTPs()) {
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
	static class MBTCoverage extends CovBuilderBySubCov<ASMSpecification, AsmTestCondition, AsmCoverage> {

		MBTCoverage() {
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

package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.base.AsmTestGeneratorBase;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CovBuilderBySubCov;
import tgtlib.specification.ParseException;

/**
 * test generator based on "atgt.specification.ASMSpecification" , the old asmeta specification
 */
public abstract class AsmTestGenerator extends AsmTestGeneratorBase<ASMSpecification>{

	static private Logger logger = Logger.getLogger(AsmTestGenerator.class);

	public static final List<CriteriaEnum> DEFAULT_CRITERIA = Arrays.asList(CriteriaEnum.BASIC_RULE,CriteriaEnum.COMPLETE_RULE, CriteriaEnum.RULE_UPDATE);

	public static final List<AsmCoverageBuilder> DEFAULT_COV_BUILDER = CriteriaEnum.getCoverageCriteria(DEFAULT_CRITERIA);


	public static final List<String> DEFAULT_FORMATS = FormatsEnum
			.toListOfString(new FormatsEnum[] { FormatsEnum.AVALLA, });

	public static final boolean DEFAULT_COMPUTE_COVERAGE = true;

	AsmTestGenerator(String asmfile, boolean coverageTp) throws ParseException {
		super(coverageTp);
		assert new File(asmfile).exists() : asmfile + "not existing";
		// read the spec
		spec = new AsmetaLLoader().read(new File(asmfile));
		// should never happen because read will throw its own exception
		if (spec == null) {
			throw new RuntimeException("errors in converting the asmeta for ATGT");
		}
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


	public AsmTestSuite generateAbstractTests(Collection<AsmCoverageBuilder> coverageCriteria, int maxTests, String regex) throws Exception {
		buildTPTree(new MBTCoverage(coverageCriteria), maxTests, regex);
		return generateTests();
	}

	/**
	 * Structural except MCDC which is difficult to use because there is an equal
	 * and Booleans
	 */
	public static class MBTCoverage extends CovBuilderBySubCov<ASMSpecification, AsmTestCondition, AsmCoverage> {

		public MBTCoverage(Collection<AsmCoverageBuilder> criteria) {
			super("MBT Coverage", AsmCoverageTree.factory);

			for (AsmCoverageBuilder c : criteria) {
				register(c);
			}

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

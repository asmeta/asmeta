package org.asmeta.atgt.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
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
import tgtlib.specification.Specification;

/**
 * test generator based on "atgt.specification.ASMSpecification" , the old asm specification
 */
public abstract class AsmTestGenerator<S extends Specification> extends AsmTestGeneratorBase<S>{

	static private Logger logger = Logger.getLogger(AsmTestGenerator.class);

	public static final List<CriteriaEnum> DEFAULT_CRITERIA = Arrays.asList(CriteriaEnum.BASIC_RULE,CriteriaEnum.COMPLETE_RULE, CriteriaEnum.RULE_UPDATE);

	public static final List<AsmCoverageBuilder> DEFAULT_COV_BUILDER = CriteriaEnum.getCoverageCriteria(DEFAULT_CRITERIA);


	public static final List<String> DEFAULT_FORMATS = FormatsEnum
			.toListOfString(new FormatsEnum[] { FormatsEnum.AVALLA});

	public static final boolean DEFAULT_COMPUTE_COVERAGE = true;

	AsmTestGenerator(String asmfile, boolean coverageTp, SpecificationReader<S> specReader) throws ParseException {
		super(coverageTp);
		assert new File(asmfile).exists() : asmfile + "not existing";
		// read the spec
		spec = specReader.read(asmfile);
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




}

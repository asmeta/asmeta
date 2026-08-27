package atgt.combinatorial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.CoverageInfo;
import atgt.coverage.CoverageInfoBuilder;
import atgt.coverage.CoveragesVisitorI;
import atgt.coverage.SkipCoveredTCFilter;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.AsmTestSeqGenerator;
import atgt.generator.SALGenerationUtil;
import atgt.generator.testsuite.SalTSuiteGenCollect;
import atgt.generator.testsuite.TestSuiteGeneratorForTC;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.preferences.ATGToolPreferences;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.util.IterableEnumeration;

/**
 * data for combinatorial test generators test
 * 
 * @author garganti
 * 
 */
public abstract class CombinatorialTestsGeneratorTest {

	// nota che le specifiche non possono essere caricate perchè se
	// cambio progetto i riferimenti a file cambiano

	/** The Cruise control. */
	protected static ASMSpecification CruiseControl;
	/** The Three power four. */
	protected static ASMSpecification ThreePowerFour;
	/** The Two3_ four2. */
	protected static ASMSpecification Two3_Four2;
	/** The BBS. */
	protected static ASMSpecification BBS;

	/** Logger for this class. */
	protected static final Logger logger = Logger
			.getLogger(CombinatorialTestsGeneratorTest.class);

	/**
	 * Load specs.
	 */
	@BeforeClass
	public static void loadSpecs() {
		CruiseControl = ASMParserTest.getCruiseControlNoAxiom();
		BBS = AsmMLoaderTest.BasicBillingSystem();
		ThreePowerFour = AsmMLoaderTest.TP4();
		Two3_Four2 = AsmMLoaderTest.TP2_3_4_4();
	}

	/**
	 * test the following properties with CC:
	 * 
	 * cruiseEvent = Activate
	 * 
	 * @param stgen
	 * 
	 */
	protected <T extends AsmTestSeqGenerator> void testCCwith(CoveragesVisitorI<AsmTestSuite> stgen) {
		// set the ignore init next
		ATGToolPreferences.ConsiderInitNext.setChecked(false);
		TypedInitExpression cruiseEvent = getVariable(CruiseControl, "cruiseEvent");
		EnumConst activate = ((EnumType) CruiseControl.getTypeFor(cruiseEvent
				.getType().getName())).getEnumConst("Activate");
		assertNotNull(activate);
		// cruise = activate
		NWiseEqTestCondition tc = new NWiseEqTestCondition("cruiseativated", Collections.singletonList(cruiseEvent),Collections.singletonList(activate));
		AsmTestSuite testSuite = stgen.forTestCondition(tc);
		// only one test !!!
		assertEquals(1, testSuite.size());
		AsmTestSequence res = testSuite.iterator().next();
		logger.debug(res.toVideo());
		// only one instruction
		assertEquals(1, res.allInstructions().size());
		Map<Location, String> test = res.allInstructions().get(0);
		assertEquals(5, test.size());
		assertEquals(SALGenerationUtil.getValue(cruiseEvent.getName(), test),	activate.getIdString());
		// check the status of tc
		assertEquals(TestConditionState.AssertViolated, tc.getStatus());
		// check that tc is covered by res
		assertEquals(1, tc.allCoveredBy().size());
		assertTrue(tc.allCoveredBy().contains(res));
		// and res covers tc
		assertEquals(1, res.tpCovered().size());
		assertTrue(res.tpCovered().contains(tc));
	}

	private TypedInitExpression getVariable(ASMSpecification spec, String string) {
		for (TypedInitExpression vr : new IterableEnumeration<Variable>(spec
				.allVariables())) {
			if (vr.getName().equals(string))
				return vr;
		}
		return null;
	}

	/**
	 * Generate.
	 * 
	 * @param ct
	 *            the ct
	 * @param spec
	 *            the spec
	 * 
	 * @return the asm test suite
	 */
	protected static AsmTestSuite generate(AsmCoverage ct, ASMSpecification spec) {
		AsmCoverageTree ctree = new AsmCoverageTree("ROOT");
		ctree.addCoverage(ct);
		SalTSuiteGenCollect<ASMSpecification,AsmTestSequence, AsmTestCondition,AsmTestSuite,?> stgen = new SalTSuiteGenCollect(new AsmProject(spec, ctree));
		stgen.setSearchCommonCoverage(true);
		stgen.setTestConditionFilter(SkipCoveredTCFilter.SkipCoveredTCFilter);
		stgen.generateTestsWait();
		AsmTestSuite result = stgen.getRunResult();
		for (TestCondition otc: ctree.allTPs()){
			logger.debug(" TEST : " + otc.getName() + "-->"	+ otc.getStatusDescription());
			AsmTestSequence tr = (AsmTestSequence) otc.getTestResult();
			if (logger.isDebugEnabled() && tr != null
					&& !tr.tpCovered().isEmpty()) {
				String covered = " covered by";
				for (TestCondition tc : tr.tpCovered())
					covered += tc.getName();
				logger.debug(covered);
			}
		}
		CoverageInfo r = ctree.accept(CoverageInfoBuilder.INSTANCE);
		logger.debug(r);
		return result;
	}

	protected void testWithCollect(TestSuiteGeneratorForTC stgen) {
		String va = "a";
		Variable vva = null;
		String vb = "b";
		Variable vvb = null;
		String vc = "c";
		Variable vvc = null;
		String ea = "ONE";
		EnumConst eea = null;
		CollectedNWiseTC ctc = new CollectedNWiseTC();
		for (Variable v : new IterableEnumeration<Variable>(ThreePowerFour
				.allVariables())) {
			if (v.getName().equals(va))
				vva = v;
			if (v.getName().equals(vb))
				vvb = v;
			if (v.getName().equals(vc))
				vvc = v;
		}
		assertNotNull(vva);
		assertNotNull(vvb);
		assertNotNull(vvc);
		for (EnumConst ec : ((ElementsType) vva.getType()).allElements()) {
			if (ec.getIdString().equals(ea))
				eea = ec;
		}
		assertNotNull(eea);
		PairEqTestCondition p1 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("tc1", vva, eea, vvb, eea);
		PairEqTestCondition p2 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("tc2", vva, eea, vvc, eea);
		ctc.addTestCondition(p1);
		ctc.addTestCondition(p2);
		/// now run the test!!!
		AsmTestSuite resS = stgen.forTestCondition(ctc.asAsmCondition());
		AsmTestSequence res = resS.iterator().next();
		logger.debug(" test content--> " + res.toVideo());
		assertEquals(1, res.allInstructions().size());
		// lo stato di ctc, p1 e p2 è assert violated
		// e non covered
		assertEquals(TestConditionState.AssertViolated,ctc.getStatus());
		assertEquals(TestConditionState.AssertViolated,p1.getStatus());
		assertEquals(TestConditionState.AssertViolated,p1.getStatus());
		// p1 and p2 are covered by the test 
		assertEquals(1, p1.allCoveredBy().size());
		assertTrue(p1.allCoveredBy().contains(res));
		assertEquals(1, p2.allCoveredBy().size());
		assertTrue(p2.allCoveredBy().contains(res));
		// the test covers only p1 and p2
		assertEquals(2,res.tpCovered().size());

	}
}

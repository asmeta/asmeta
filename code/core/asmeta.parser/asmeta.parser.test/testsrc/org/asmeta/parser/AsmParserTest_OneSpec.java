package org.asmeta.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.ChooseRule;

/**
 * classe di appoggio per testare singole specifiche
 * 
 * @author garganti
 *
 */
class AsmParserTest_OneSpec extends AsmParserTest {

	@BeforeAll
	static void setUpLogger() {
//		AsmParserTest.setUpLogger();
//		Logger.getLogger("org.asmeta.parser").setLevel(Level.INFO);
//		// add appender only if empty
//		if (!log.getAllAppenders().hasMoreElements())
//			log.addAppender(new ConsoleAppender(new SimpleLayout()));
	}

	@Test void spec() {
		AsmParserTest.setUpLogger();
		testOneSpec("examples/simple_example/SwapSort.asm");
		testOneSpec("examples/models/SwapSortMon.asm");
	}

	@Test void oneSpecBagCT() {
		String file = "test/parser/bagCT.asm";
		testOneSpec(file);
	}

	@Test void df() {
		String file = "test/parser/Dragonfly_verification.asm";
		testOneSpec(file);
	}

	@Test void chooseNoGuard() {
		String file = "test/parser/ChooseNoCond.asm";
		Asm asm = testOneSpec(file).getMain();
		ChooseRule cr = (ChooseRule) asm.getMainrule().getRuleBody();
		if (cr.getGuard() instanceof BooleanTerm bt) {
			assertEquals("true", bt.getSymbol());
		} else {
			fail("");
		}
	}


	@Test void oneSpec2() {
		String file = "test/simulator/domains/enumDomain.asm";
		testOneSpec(file);
	}

	@Test void oneSpecLIFTDC() {
		String file = "examples/models/LIFTDC.asm";
		testOneSpec(file);
	}

	@Test void oneSpecFileSystem() {
		String file = "examples/agents/FileSystem.asm";
		testOneSpec(file);
	}

	@Test void oneSpecsluiceGateWithAgents() {
		String file = "examples/sluicegate/sluiceGate_with_agents.asm";
		testOneSpec(file);
	}

	@Test void oneSpeconeWayTrafficLightRefined() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined.asm";
		testOneSpec(file);
	}

	@Test void oneSpeconeWayTrafficLight() {
		String file = "examples/traffic_light/oneWayTrafficLight.asm";
		testOneSpec(file);
	}

	@Test void oneSpeconeWayTrafficLight2() {
		String file = "examples/traffic_light/oneWayTrafficLight2.asm";
		testOneSpec(file);
	}

	@Test void oneSpeconeWayTrafficLightRefinedWithAgents() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined_with_agents.asm";
		testOneSpec(file);
	}

	@Test void oneSpeconeWayTrafficLightRefinedWithAgentsProblem() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined_with_agents_problem.asm";
		testOneSpec(file);
	}

	@Test void oneSpeconeWayTrafficLightRefinedWithAgentsSeq() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined_with_agents_seq.asm";
		testOneSpec(file);
	}

	// ALCUNE DI QUESTI DANNO ANCORA ERRORI

	@Test void oneSpecLIFT() {
		String file = "examples/models/LIFT.asm";
		testOneSpec(file);
	}

	@Test void oneSpecFlipFlop() {
		String file = "examples/models/FlipFlop.asm";
		testOneSpec(file);
	}

	@Test void oneSpecDom() {
		String file = "test/errors/rnp/prod.asm";
		testOneSpec(file);
	}

	@Test void oneSpec3() {
		String file = "test/simulator/rules/FLIP_FLOP_4.asm";
		testOneSpec(file);
	}

	@Test void oneSpec4() {
		String file = "test/simulator/terms/MapTerm.asm";
		testOneSpec(file);
	}

	@Test void oneNot() {
		String file = "test/simulator/RelationalExpr01.asm";
		testOneSpec(file);
	}

	@Test void oneSpec6() {
		String file = "test/simulator/macro/macro07.asm";
		// the parser must fail
		testOneSpec(file);
	}

	@Test void oneSpecLibrary() {
		testOneSpec("examples/library/Library.asm");
	}

	@Test void oneSpecParBoolean() {
		testOneSpec("test/errors/rpns/parBoolean.asm");
	}

	@Test void concrDomAndStdl() {
		String file = "test/parser/concrDomAndStdl.asm";
		testOneSpec(file);
	}

	@Test void fmsio3() {
		String file = "erinda/FMS_IO_3.asm";
		testOneSpec(file);
	}

	@Test void swapSortOnSeq() {
		String file = "examples/simple_example/swapSortOnSeq.asm";
		testOneSpec(file);
	}

	@Test void needhamSchroeder() {
		//testOneSpec("examples/NeedhamSchroeder/oldVersion/NeedhamSchroederWithSpy.asm");
	}

	@Test void testNull() {
		AsmCollection asm = testOneAsmFile("test/parser/null/null.asm");
		assertFalse(asm.iterator().hasNext());
	}

	@Test void testonlyComments() {
		AsmCollection asm = testOneAsmFile("test/parser/null/comment.asm");
		assertFalse(asm.iterator().hasNext());
	}

	@Test void setTerm() {
		// {1..1} => shoule be either error or just 1
		AsmCollection collection = testOneSpec("test/parser/setterm.asm");
		EList<DomainDefinition> domainDefinition = collection.getMain().getBodySection().getDomainDefinition();
		assertEquals(1,domainDefinition.size());
		Term body = domainDefinition.get(0).getBody();
		assertInstanceOf(SetTerm.class, body);
		assertEquals(1,((SetTerm) body).getTerm().size());
	}

	@Test void orderSystem() {
		AsmCollection asm = testOneSpec("examples/models/ordersystem_april2010.asm");
		assertNotNull(asm);
	}


	@Test void philo1() {
		Logger log = Logger.getLogger("org.asmeta.parser");
		//log.setLevel(Level.ALL);
		String file = "examples/philosophers/philosophers1.asm";
		testOneSpec(file);
	}
}
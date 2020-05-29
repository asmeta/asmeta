package org.asmeta.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.eclipse.emf.common.util.EList;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.structure.DomainDefinition;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
/**
 * classe di appoggio per testare singole specifiche
 * 
 * @author garganti
 *
 */
public class AsmParserTest_OneSpec extends AsmParserTest {

	//@BeforeClass
	public static void setUpLogger() {
		//AsmParserTest.setUpLogger();
	}

	@Test
	public void testSpec() {
		AsmParserTest.setUpLogger();
		testOneSpec("examples/simple_example/SwapSort.asm");
		testOneSpec("examples/models/SwapSortMon.asm");
	}

	@Test
	public void testOneSpecBagCT() {
		String file = "test/parser/bagCT.asm";
		AsmCollection asm = testOneSpec(file);
		
		System.out.println(Logger.getLogger(OCL_Checker.class).getRootLogger().getLevel());
		
		Logger.getLogger(OCL_Checker.class).log(Priority.DEBUG,"PROVA");
	}
	
	@Test
	public void testOneSpec2() {
		String file = "test/simulator/domains/enumDomain.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpecLIFTDC() {
		String file = "examples/models/LIFTDC.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpecFileSystem() {
		String file = "examples/agents/FileSystem.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpecsluiceGate_with_agents() {
		String file = "examples/sluicegate/sluiceGate_with_agents.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpeconeWayTrafficLight_refined() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpeconeWayTrafficLight() {
		String file = "examples/traffic_light/oneWayTrafficLight.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpeconeWayTrafficLight2() {
		String file = "examples/traffic_light/oneWayTrafficLight2.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpeconeWayTrafficLight_refined_with_agents() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined_with_agents.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpeconeWayTrafficLight_refined_with_agents_problem() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined_with_agents_problem.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpeconeWayTrafficLight_refined_with_agents_seq() {
		String file = "examples/traffic_light/oneWayTrafficLight_refined_with_agents_seq.asm";
		testOneSpec(file);
	}

	// ALCUNE DI QUESTI DANNO ANCORA ERRORI

	@Test
	public void testOneSpecLIFT() {
		String file = "examples/models/LIFT.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpecFlipFlop() {
		String file = "examples/models/FlipFlop.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpecDom() {
		String file = "test/errors/rnp/prod.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpec3() {
		String file = "test/simulator/rules/FLIP_FLOP_4.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpec4() {
		String file = "test/simulator/terms/MapTerm.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneNot() {
		String file = "test/simulator/RelationalExpr01.asm";
		testOneSpec(file);
	}

	@Test
	public void testOneSpec6() {
		String file = "test/simulator/macro/macro07.asm";
		// the parser must fail
		testOneSpec(file);
	}

	@Test
	public void testOneSpecLibrary() {
		testOneSpec("examples/library/Library.asm");
	}

	@Test
	public void testOneSpec_parBoolean() {
		testOneSpec("test/errors/rpns/parBoolean.asm");
	}

	@Test
	public void testConcrDomAndStdl() {
		String file = "test/parser/concrDomAndStdl.asm";
		testOneSpec(file);
	}
	
	@Test
	public void testFMSIO3() {
		String file = "erinda/FMS_IO_3.asm";
		testOneSpec(file);
	}

	@Test
	public void testSwapSortOnSeq() {
		String file = "examples/simple_example/swapSortOnSeq.asm";
		testOneSpec(file);
	}

	@Test
	public void test2() {
		testOneSpec("examples\\NeedhamSchroeder\\oldVersion\\NeedhamSchroederWithSpy.asm");
	}

	@Test
	public void testNull() {
		testOneSpec("test/parser/null/null.asm");
	}
	@Test
	public void testonlyComments() {
		testOneSpec("test/parser/null/comment.asm");
	}
	
	@Test
	public void testSetTerm() {
		// {1..1} => shoule be either error or just 1
		AsmCollection collection = testOneSpec("test/parser/setterm.asm");
		EList<DomainDefinition> domainDefinition = collection.getMain().getBodySection().getDomainDefinition();
		assertEquals(1,domainDefinition.size());
		Term body = domainDefinition.get(0).getBody();
		assertTrue(body instanceof SetTerm);
		assertEquals(1,((SetTerm) body).getTerm().size());
	}

	@Test
	public void testPhilo1() {
		Logger log = Logger.getLogger("org.asmeta.parser");
		if (!log.getAllAppenders().hasMoreElements())
		log.addAppender(new ConsoleAppender(new SimpleLayout()));
		 log.setLevel(Level.ALL);
		String file = "examples/philosophers/philosophers1.asm";
		testOneSpec(file);
	}
}
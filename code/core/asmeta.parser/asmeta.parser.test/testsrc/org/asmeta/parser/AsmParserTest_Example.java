package org.asmeta.parser;
import org.junit.BeforeClass;
import org.junit.Test;

/** test all the specs in the example subdirs. they should all pass
 * 
 * @author garganti
 *
 */
public class AsmParserTest_Example extends AsmParserTest{

	@BeforeClass
	public static void setUpLogger(){
		AsmParserTest.setUpLogger();
	}
	
	@Test
	public void testExamplesAgents(){
		testDir("examples/agents/");
	}
	@Test
	public void testFSM(){
		testDir("examples/fsmsemantics/");
	}

	@Test
	public void testExamplesModels(){
		testDir("examples/models/");
	}	
	@Test
	public void testExamplesPhilo(){
		testDir("examples/philosophers/");
	}	
	@Test
	public void testProductionCell(){
		testDir("examples/production_cell/");
	}	

	@Test
	public void testSimpleEx(){
		testDir("examples/simple_example/");
	}
	@Test
	public void testExamplesSG(){
		testDir("examples/sluicegate/");
	}
	@Test
	public void testTrafficLight(){
		testDir("examples/traffic_light/");
	}
	
	@Test
	public void testLibrary(){
		testDir("examples/library");
	}
	
	// test all the examples
	@Test
	public void testExamples(){
		testDir("examples");
	}

	@Test
	public void testStereoAcuity(){
		testDir("stereoacuity");
	}
	
	
}

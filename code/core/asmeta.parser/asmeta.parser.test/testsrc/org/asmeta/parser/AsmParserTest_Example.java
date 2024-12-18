package org.asmeta.parser;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/** test all the specs in the example subdirs. they should all pass
 * 
 * @author garganti
 *
 */
public class AsmParserTest_Example extends AsmParserTest{

	//@BeforeClass
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
	
	static String[] dirToSkip = {"test","DAS", "drafts", "workspacePatrizia"};	
	// test all the examples except the those for tests
	// too many skip for now
	//@Ignore	
	@Test
	public void testAllExamples(){
		File examplesPath = new File(FILE_BASE);
		Collection<String> errors = new ArrayList<>();
	    //List of all files and directories
	    File[] contents = examplesPath.listFiles();
	    Arrays.sort(dirToSkip);
	    for(File d: contents) {
	    	if (d.isFile()) continue;
	    	if (Arrays.binarySearch(dirToSkip,  d.getName()) >= 0) {
	    		System.err.println("skipping " + d.getName());
	    		continue;
	    	}
	    	Collection<File> testSpecInSubFolder = testSpecInSubFolder(d.getName());
			if (!testSpecInSubFolder.isEmpty())
				errors.add(d.getName());
	    }
	    assertTrue(errors.size() + ":" + errors.toString(), errors.isEmpty());
	}

	@Ignore @Test
	public void testStereoAcuity(){
		testDir("stereoacuity");
	}
	
	
}

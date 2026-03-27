package org.asmeta.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/** test all the specs in the example subdirs. they should all pass
 * 
 * @author garganti
 *
 */
public class AsmParserTest_Example extends AsmParserTest{

	//@BeforeAll
	public static void setUpLogger(){
		AsmParserTest.setUpLogger();
	}

	@Test void examplesAgents(){
		testDir("examples/agents/");
	}

	@Test void fsm(){
		testDir("examples/fsmsemantics/");
	}

	@Test void examplesModels(){
		testDir("examples/models/");
	}

	@Test void examplesPhilo(){
		testDir("examples/philosophers/");
	}

	@Test void productionCell(){
		testDir("examples/production_cell/");
	}

	@Test void simpleEx(){
		testDir("examples/simple_example/");
	}

	@Test void examplesSG(){
		testDir("examples/sluicegate/");
	}

	@Test void trafficLight(){
		testDir("examples/traffic_light/");
	}

	@Test void library(){
		testDir("examples/library");
	}
	
	static String[] dirToSkip = {"test","DAS", "drafts", "workspaceMSL"};

	// test all the examples except the those for tests
	// too many skip for now
	@Disabled
	@Test void allExamples(){
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
				errors.add(testSpecInSubFolder.toString());
	    }
	    assertTrue(errors.isEmpty(), errors.size() + ":" + errors.toString());
	}

	@Disabled @Test void stereoAcuity(){
		testDir("stereoacuity");
	}
	
	
}

package org.asmeta.atgt.generator2;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.junit.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.testseqexport.ToAvallaLastAction;
import atgt.testseqexport.toAvalla;

public class AsmTGBySimulationOnActionTest {

	@Test
	public void test() throws Exception {
		String Aman0 = "../../../../asmeta_based_applications/fMVC/AMAN/model/aman0_wActionUndef.asm";
		
		Environment.timeMngt = TimeMngt.auto_increment;
		AsmCollection asm = ASMParser.setUpReadAsm(new File(Aman0));
		List<String> stepActions = Arrays.asList("action","zoom","timeToLock");
		// azione -> (azione,valore)		
		/*
		zoom != UNDEF ==>  action == UNDEF AND timeToLock == UNDEF
		*/
		AsmTestGeneratorBySimulation atgt = new AsmTGBySimulationOnAction(asm,3,5, stepActions);
		AsmTestSuite tests = atgt.getTestSuite();
		System.out.println(tests);
		AsmTestSequence ts = tests.getTests().get(0);
		toAvalla export =  new ToAvallaLastAction(System.out,ts, Aman0, "test1",stepActions);
		export.saveToStream();
	}

	
	
	
	
	
}

package org.asmeta.atgt.generator.coverage;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import atgt.coverage.AsmCoverage;

public class AsmetaBasicRuleVisitorTest {

	@Test
	public void testGetTPTree() throws Exception {
			Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
			Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);		
			Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
			Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
			Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);	
			
			TestGenerationWithNuSMV.useLTLandBMC = true;
			
			//String ex = "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
			//String ex = "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
			String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
			
			//String ex = "C:\\Users\\garganti\\code_from_repos\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";
			
			asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
			
			AsmetaBasicRuleVisitor tpbuilder = new AsmetaBasicRuleVisitor();
			
			AsmCoverage tp = tpbuilder.getTPTree(new AsmetaAsSpec(asms.getMain()));
			
			tp.allTPs().forEach(x -> System.out.println(x.getCondition()));
			
	}

}

/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/

package atgt.generator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.RootCoverage;
import atgt.coverage.TestCondition;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.Undef;
import tgtlib.specification.ParseException;

/*
Test predicates of atgt_examples/SC3.asm
BR_r_mainSC3_TBR_r_SC3_T: (currAsm = SC3) and (tokens = 27)
BR_r_mainSC3_TBR_r_SC3_FT: (currAsm = SC3) and ((tokens != 27) and (tokens > 28))
BR_r_mainSC3_TBR_r_SC3_FF: (currAsm = SC3) and ((tokens != 27) and (tokens <= 28))
BR_r_mainSC3_F: currAsm != SC3
UR_r_mainSC3_TUR_r_SC3_T: (currAsm = SC3) and ((tokens = 27) and (tokens != 0))
UR_r_mainSC3_TUR_r_SC3_FT: (currAsm = SC3) and (not(tokens = 27) and ((tokens > 28) and (tokens != (tokens - 1))))
MCDC_r_mainSC3_T: currAsm = SC3
MCDC_r_mainSC3_F: currAsm != SC3
MCDC_r_mainSC3_T_MCDC_r_SC3_T: (currAsm = SC3) and (tokens = 27)
MCDC_r_mainSC3_T_MCDC_r_SC3_F: (currAsm = SC3) and (tokens != 27)
MCDC_r_mainSC3_T_MCDC_r_SC3_F_T: (currAsm = SC3) and ((tokens != 27) and (tokens > 28))
MCDC_r_mainSC3_T_MCDC_r_SC3_F_F: (currAsm = SC3) and ((tokens != 27) and (tokens <= 28))
ENF_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor not((currAsm = SC3) and (tokens = 27))
VNF1_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor (not(currAsm = SC3) and (tokens = 27))
VNF2_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor ((currAsm = SC3) and not(tokens = 27))
MVF1_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor (tokens = 27)
MVF2_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor (currAsm = SC3)
ORF_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor ((currAsm = SC3) or (tokens = 27))
SA01_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor false
SA02_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor (false and (tokens = 27))
SA03_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor ((currAsm = SC3) and false)
SA11_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor true
SA12_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor (true and (tokens = 27))
SA13_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor ((currAsm = SC3) and true)
ROF1_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor ((currAsm != SC3) and (tokens = 27))
ROF2_r_mainSC3_Tr_SC3_T: ((currAsm = SC3) and (tokens = 27)) xor ((currAsm = SC3) and (tokens != 27))
*/
public class SpinTestGeneratorMultipleInitStates {

	@BeforeClass
	public static void setUpLogger(){
		Logger.getLogger(SpinTSeqGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.DEBUG);
	}
	
	@Test
	public void test() throws ParseException {
		ASMSpecification spec = new AsmetaLLoader().read(new File("atgt_examples/SC3.asm"));
		// check initial state
		Variable tokens = spec.getVariable("tokens");
		assertNotNull(tokens);
		assertFalse(tokens.getValue().equals(Undef.UNDEF));
		AsmCoverage ct = RootCoverage.ROOT.getTPTree(spec);
		Iterable<AsmTestCondition> tps = ct.allTPs();
		SpinTSuiteGenForTC spt = SpinTSuiteGenForTC.createFlatSpinTSuiteGenForTC(new AsmProject(spec, ct));
		for(TestCondition<?> tc: tps) {
			//BR_r_mainSC3_TBR_r_SC3_T: (currAsm = SC3) and (tokens = 27)
			if(tc.getName().equals("BR_r_mainSC3_TBR_r_SC3_T")) {
				tc.setToVerify(true);
				break;
			}
		}
		AsmTestSuite testsuite = ct.accept(spt);
		assertTrue(testsuite.getNActualTest() > 0);
		System.out.println(testsuite.getTests().get(0).allInstructions());
	}
}
package org.asmeta.tocpp.tocunit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.tocpp.tocunit.AsmToBoostModule.UNITFM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CarSystem2cppTest extends AsmToUnitModuleTest{
	
	private static final UNITFM UNIT_FM = UNITFM.CATCH2;


	@BeforeAll
	public static void setUpLogger() {
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//Logger.getLogger(org.asmeta.parser.ASMParser.class).setLevel(Level.ALL);
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		CppCompiler.setCompiler("g++");
	}
	
	/**
	 *Il file test.cpp viene generato e la sua esecuzione non da errori ma in nessun caso di test sono presenti 
	 *funzioni dei moduli importati.
	 *Ad esempio non viene testata nessuna controlled function. 
	 *Da CarSystem001 a CarSystem006
	 * @throws Exception
	 */
	@Test
	public void CarSystem001MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm", SIMULATOR,"1", "2");
	}
	
	@Test
	public void CarSystem002MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002main.asm", SIMULATOR,"1", "100");
	}
	
	@Test
	public void CarSystem003MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003main.asm", SIMULATOR,"1", "100");
	}
	@Test
	public void CarSystem004MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004main.asm", SIMULATOR,"1", "100");
	}
	
	@Test
	public void CarSystem005MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005main.asm", SIMULATOR,"1", "100");
	}
	
	@Test
	public void CarSystem006MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006main.asm", SIMULATOR,"1", "100");
	}
	
	
	/**
	 * Non funziona. Da un warning su RuleEvaluator.visit e il file test.cpp � vuoto.
	 * Da CarSystem007 a CarSystem008
	 * @throws Exception
	 */
	@Test
	public void CarSystem007MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007main.asm", SIMULATOR,"1", "100");
	}
	
	@Test
	public void CarSystem008MainSim() throws Exception {
		
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007main.asm", SIMULATOR,"1", "100");
	}
	
	
	@Test
	public void CarSystem009MainSim() throws Exception {
		//String dDir ="../asmetal2cpp_codegen/examples/ABZ2020/test/CarSystem000";
		testSpec(UNIT_FM, "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem009/CarSystem009main.asm", SIMULATOR,"1", "101");
	}



}

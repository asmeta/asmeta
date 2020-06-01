package org.asmeta.flattener;

import org.junit.Test;

//asm_examples/examples/hemodialysisDevice/SCP2017/HemodialysisRef3.asm
//asm_examples/examples/landingGearSystem/LGS_3L.asm
//asm_examples/examples/GilbreathCardTrick/GilbreathCardTrickForAsmetaSMV.asm
//asm_examples/examples/coffeeVendingMachine/coffeeVendingMachine.asm
//asm_examples/examples/roulette/roulette.asm
//asm_examples/examples/ticTacToe/ticTacToe_simulator.asm
//asm_examples/examples/philosophers/philosophers3.asm
//asm_examples/examples/conwayGameOfLife/gameOfLife.asm
//asm_examples/examples/petriNets/forAsmetaSMV/petriNet_forNuSMV.asm

//passaggio per riferimento. come appiattirlo?
//asm_examples/examples/traffic_light/forAsmetaSMV/oneWayTrafficLight_refined_with_agents.asm

public class AllCombinationsTest extends FlattenerTest {

	@Test
	public void testHemo() throws Exception {
		//flattenerTest(examplesDir + "examples/hemodialysisDevice/SCP2017/HemodialysisRef3.asm",LetRuleFlattener.class,RemoveArgumentsFlattener.class);
		flattenerTestAllCombinations(examplesDir + "examples/hemodialysisDevice/SCP2017/HemodialysisRef3.asm",
				ALL_FLATTENERS);
	}

	@Test
	public void testLGS() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/landingGearSystem/LGS_3L.asm",
				ALL_FLATTENERS);
	}
	
	@Test
	public void testRoulette() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/roulette/roulette.asm",
				ALL_FLATTENERS);
	}

	@Test
	public void testConwayGameOfLifeAgents() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm",
				ALL_FLATTENERS);
	}

	@Test
	public void testGilbreathCardTrick() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/GilbreathCardTrick/GilbreathCardTrickForAsmetaSMV.asm",
				ALL_FLATTENERS);
		//TODO: errore turbo
	}
	
	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				ALL_FLATTENERS);
		//TODO: errore letRuleFlattener
	}
	
	@Test
	public void testPetriNets() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/petriNets/forAsmetaSMV/petriNet_forNuSMV.asm",
				ALL_FLATTENERS);
		//TODO: errore su variabile
	}

	@Test(expected = RuntimeException.class)
	public void testPhilosophers() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/philosophers/philosophers3.asm",
				ALL_FLATTENERS);
		//TODO: problema parser, non trova eq(Prod(Philosophers,Agent))
		//questo problema lo sappiamo...
	}

	@Test(expected = RuntimeException.class)
	public void testTicTacToe() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/ticTacToe/ticTacToe_simulator.asm",
				ALL_FLATTENERS);
		//TODO: choose con piu' di una variabile
	}
	
}

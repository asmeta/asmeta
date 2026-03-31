package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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

class AllCombinationsTest extends FlattenerTest {

	@Test
	void hemo() throws Exception {
		//flattenerTest(examplesDir + "examples/hemodialysisDevice/SCP2017/HemodialysisRef3.asm",LetRuleFlattener.class,RemoveArgumentsFlattener.class);
		flattenerTestAllCombinations(examplesDir + "examples/hemodialysisDevice/SCP2017/HemodialysisRef3.asm",
				ALL_FLATTENERS);
	}

	@Test
	void lgs() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/landingGearSystem/LGS_3L.asm",
				ALL_FLATTENERS);
	}

	@Test
	void roulette() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/roulette/roulette.asm",
				ALL_FLATTENERS);
	}

	@Test
	void conwayGameOfLifeAgents() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm",
				ALL_FLATTENERS);
	}

	@Test
	void gilbreathCardTrick() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/GilbreathCardTrick/GilbreathCardTrickForAsmetaSMV.asm",
				ALL_FLATTENERS);
		//TODO: errore turbo
	}

	@Test
	void coffeeVendingMachine() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				ALL_FLATTENERS);
		//TODO: errore letRuleFlattener
	}

	@Test
	void petriNets() throws Exception {
		flattenerTestAllCombinations(examplesDir + "examples/petriNets/forAsmetaSMV/petriNet_forNuSMV.asm",
				ALL_FLATTENERS);
		//TODO: errore su variabile
	}

	@Test
	void philosophers() throws Exception {
		assertThrows(RuntimeException.class, () ->
			flattenerTestAllCombinations(examplesDir + "examples/philosophers/philosophers3.asm",
				ALL_FLATTENERS));
		//TODO: problema parser, non trova eq(Prod(Philosophers,Agent))
		//questo problema lo sappiamo...
	}

	@Test
	void ticTacToe() throws Exception {
		assertThrows(RuntimeException.class, () ->
			flattenerTestAllCombinations(examplesDir + "examples/ticTacToe/ticTacToe_simulator.asm",
				ALL_FLATTENERS));
		//TODO: choose con piu' di una variabile
	}
	
}

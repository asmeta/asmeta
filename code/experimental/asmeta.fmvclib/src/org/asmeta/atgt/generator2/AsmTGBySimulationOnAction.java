package org.asmeta.atgt.generator2;

import java.util.List;

import org.asmeta.atgt.rndgenerator.AsmTestGeneratorBySimulation;

import asmeta.AsmCollection;

public class AsmTGBySimulationOnAction extends AsmTestGeneratorBySimulation {

	public AsmTGBySimulationOnAction(AsmCollection asm, int stepNumber, int testNumber, List<String> actions) {
		super(asm, stepNumber, testNumber, new RandomMFReaderMemoryOneAction(actions));
	}

	
	
	
}

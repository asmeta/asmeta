package asmeta.evotest.experiments.modelcollector;

import java.util.Map;

import asmeta.evotest.experiments.modelcollector.ModelCollector.FileLabel;

// TEMPORARY
public class Main {
	
	public static final String ASM_EXAMPLES = "../../../../asm_examples";
	public static final String TARGET_FILE = "data/fm26-exp/model_list.txt";
	
	public static void main(String[] args) {
		Map<String, FileLabel> collectedFiles = ModelCollector.collectModels(ASM_EXAMPLES);
		ModelWriter.writeToFile(collectedFiles, TARGET_FILE);
	}

}
package asmeta.evotest.experiments.dataprep;

import java.util.Map;

import asmeta.evotest.experiments.dataprep.ModelDirectoryScanner.FileLabel;

/**
 * Entrypoint for the generation of a list of models starting from asm_examples
 */
public class ModelScanner {
	
	private static final String ASM_EXAMPLES = "../../../../asm_examples";
	
	/*
	 * Change the following private fields as needed
	 */
	private static final boolean FILTER_NO_PAR = false;
	private static final String TARGET_FILE = "data/icst-26-exp/model_list_temp.txt";
	
	/**
	 * Entry point.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Map<String, FileLabel> collectedFiles = ModelDirectoryScanner.scan(ASM_EXAMPLES, FILTER_NO_PAR);
		int totalAsms = ModelDirectoryScanner.getTotalNumber(ASM_EXAMPLES);
		ModelListWriter.writeToFile(collectedFiles, totalAsms, TARGET_FILE);
	}

}
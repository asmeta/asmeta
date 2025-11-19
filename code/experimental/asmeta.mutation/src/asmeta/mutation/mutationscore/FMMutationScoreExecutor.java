package asmeta.mutation.mutationscore;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.ValidationResult;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ParToSeqMutator;
import asmeta.mutation.operators.AsmetaMutationOperator;

/**
 * it executes a scenario over a mutate set of specifications and return the
 * mutation score
 */
public class FMMutationScoreExecutor {

	// mutation operators to be used
	List<AsmetaMutationOperator> mutOperators = new ArrayList<>();
	

	private static final Logger LOG = Logger.getLogger(FMMutationScoreExecutor.class);

	static class AsmetaMutatedFromAvalla extends AsmetaFromAvallaBuilder {

		AsmetaMutatedFromAvalla(String scenarioPath, File tempAsmPathDir) throws Exception {
			super(scenarioPath, tempAsmPathDir);
		}

		AsmCollection getAsm() {
			return asmCollection;
		}

		void setAsmeta(AsmCollection m) {
			asmCollection = m;
			asm = asmCollection.getMain();
		}
	}
	
	/**
	 * Given a path to an Asm file, mutates it for parallel to sequential experiments
	 * @param pathToAsm
	 * @return
	 * @throws Exception
	 */
	public List<AsmCollection> generateMutants(String pathToAsm) throws Exception{
		AsmCollection originalAsm = ASMParser.setUpReadAsm(new File(pathToAsm));
		ParToSeqMutator mut = new ParToSeqMutator();
		List<AsmCollection> mutants = mut.mutate(originalAsm);
		return mutants;
	}
	
	/**
	 * Computes the mutation score for FM experiments
	 * See parallel to seq study.
	 * Only applies the parallel to sequential mutation operator.
	 * */
	public void computeMutationScore(List<AsmCollection> mutants, Set<Integer> killedMutations, String testSuitePath) throws Exception {
		
		// TEMP. use a temporary directory
		File temp = new File("temp").getAbsoluteFile();
		if (!temp.exists()) {
			Files.createDirectories(temp.toPath());
		}
		
		assert temp.exists() && temp.isDirectory();
		
		
		//base to the test suit folder
		Path base = Path.of(testSuitePath);
		
		//check for each mutant if it is killed with the test suite
		for (int i = 0; i < mutants.size(); i++) {
			
			//check if the index is in the list, if true skip it.
			if (killedMutations.contains(i))
				continue;
			
			AsmCollection m = mutants.get(i);
			
			//From here cycle over the testSuite folder.
			try (Stream<Path> stream = Files.walk(base)) {
			    Iterator<Path> it = stream.iterator();
			    while (it.hasNext()) {
			        Path avalla = it.next();
			        if (avalla.toString().endsWith(".avalla")) {
						
			        	// parse the scenario to get the ref to the asmeta
						AsmetaMutatedFromAvalla asmetaBuilder;
					
						try {
							asmetaBuilder = new AsmetaMutatedFromAvalla(avalla.toFile().toString(), temp);
						
							Map<String, Boolean> allCoveredRules = new HashMap<>();
						
							// modify the scenario to ref to the mutated spec
							// change the asmeta with the mutation
							asmetaBuilder.setAsmeta(m);
							// save the scenario
							asmetaBuilder.save();
							File tempAsmPath = asmetaBuilder.getTempAsmPath();
							// execute now the scenario
							ValidationResult result = AsmetaV.executeAsmetaFromAvalla(false, allCoveredRules, tempAsmPath, false);
							if (!result.isCheckSucceeded()) {
								//add the index to a set of integer of the ones that were killed.
								killedMutations.add(i);
								break;
							}
						} catch (Exception e) {
							LOG.error("Mutation analysis failed.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
						}
					}
			    }
			}
		}
		
		// Delete temp directory
		if (temp.exists()) {
			Files.walk(temp.toPath())
				.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(File::delete);
		}
	}
	
}

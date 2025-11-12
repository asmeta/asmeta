package asmeta.mutation.mutationscore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
			//Arrays.asList(
			//		new RuleRemover(), 
			//		new ChooseRuleMutate(),
			//		new CondRemover(),
			//		new CondNegator()
			//		);


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
	 * Computes the mutation score for FM experiments
	 * See parallel to seq study.
	 * Only applies the parallel to sequential mutation operator.
	 * */
	public Map.Entry<Integer, Integer> computeMutationScore(String testSuitePath) throws Exception {
		//mutOperators.addAll(mutationOps);
		
		// TEMP. use a temporary directory
		File temp = new File("temp/");
		if (!temp.exists()) {
		    temp.mkdir();
		}
		
		//HashMap<String,Map.Entry<Integer, Integer>> results = new HashMap<String, Map.Entry<Integer,Integer>>();
		assert temp.exists() && temp.isDirectory();
		
		AtomicInteger totalKilled = new AtomicInteger(0);
		AtomicInteger totalMutants = new AtomicInteger(0);
		//From here cycle over the testSuite folder.
		Path base = Path.of(testSuitePath);
		Files.walk(base).forEach(avalla -> {
			if (avalla.toFile().toString().endsWith(".avalla")) {
				//correctLoadSpec(avalla);
				// parse the scenario to get the ref to the asmeta
				AsmetaMutatedFromAvalla asmetaBuilder;
				
				try {
					asmetaBuilder = new AsmetaMutatedFromAvalla(avalla.toFile().toString(), temp);
					AsmCollection orginalAsm = asmetaBuilder.getAsm();
					// for every mutation operators
					//for (AsmetaMutationOperator mut : mutOperators) {
					int nKilled = 0;
					ParToSeqMutator mut = new ParToSeqMutator();
					List<AsmCollection> mutants = mut.mutate(orginalAsm);
					Map<String, Boolean> allCoveredRules = new HashMap<>();
					// modify the scenario to ref to the mutated spec
					for (AsmCollection m : mutants) {
						// change the asmeta with the mutation
						asmetaBuilder.setAsmeta(m);
						// save the scenario
						asmetaBuilder.save();
						File tempAsmPath = asmetaBuilder.getTempAsmPath();
						// execute now the scenario
						// ValidationResult result = AsmetaV.executeAsmetaFromAvalla(false,
						// allCoveredRules, tempAsmPath, originalName);
						ValidationResult result = AsmetaV.executeAsmetaFromAvalla(false, allCoveredRules, tempAsmPath);
						if (!result.isCheckSucceeded()) {
							System.err.println("KILLED !!!");
							nKilled++;
						}
					}
					totalKilled.addAndGet(nKilled);
					totalMutants.addAndGet(mutants.size());
					// name of the operator -> pair (nKilled, mutants)
					//}
					//results.put(avalla.toFile().toString(), new AbstractMap.SimpleEntry<Integer, Integer>(nKilled, nMutants));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (temp.exists()) {
							Files.walk(temp.toPath())
								.sorted(Comparator.reverseOrder())
								.map(Path::toFile)
								.forEach(File::delete);
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				
			}
		});
		
		Map.Entry<Integer, Integer> results = new AbstractMap.SimpleEntry<Integer,Integer>(totalKilled.get(), totalMutants.get());
		
		return  results;
	}
	
}

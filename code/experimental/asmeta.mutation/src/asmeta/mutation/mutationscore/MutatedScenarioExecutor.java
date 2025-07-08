package asmeta.mutation.mutationscore;

import java.io.File;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.ValidationResult;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ChooseRuleMutate;
import asmeta.mutation.operators.CondRemover;
import asmeta.mutation.operators.RuleRemover;
import asmeta.mutation.operators.AsmetaMutationOperator;

/**
 * it executes a scenario over a mutate set of specifications and return the
 * mutation score
 */
public class MutatedScenarioExecutor {

	// mutation operators to be used
	List<AsmetaMutationOperator> mutOperators = 
			Arrays.asList(new RuleRemover(), new ChooseRuleMutate(),
					new CondRemover());

	//
	// applies a set of mutants
	// return MAP with key: mutation operator (String) -> a pair (killed,n_mutants)
	//
	public HashMap<String,Map.Entry<Integer, Integer>> computeMutationScore(String scenarioPath) throws Exception {
		// TEMP. use a temporary directory
		File temp = new File("temp/");
		HashMap<String,Map.Entry<Integer, Integer>> results = new HashMap<String, Map.Entry<Integer,Integer>>();
		assert temp.exists() && temp.isDirectory();
		// parse the scenario to get the ref to the asmeta
		AsmetaMutatedFromAvalla asmetaBuilder = new AsmetaMutatedFromAvalla(scenarioPath, temp);
		AsmCollection orginalAsm = asmetaBuilder.getAsm();
		// for every mutation operators
		for (AsmetaMutationOperator mut : mutOperators) {
			int nKilled = 0;
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
				if (!result.isCheckSucceded()) {
					System.err.println("KILLED !!!");
					nKilled++;
				}
			}
			// name of the operator -> pair (nKilled, mutants)
			results.put(mut.getName(), new AbstractMap.SimpleEntry<Integer, Integer>(nKilled, mutants.size()));
		}
		return  results;
	}

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
}

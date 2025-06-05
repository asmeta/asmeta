package asmeta.mutation.mutationscore;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.ValidationResult;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ChooseRuleMutate;
import asmeta.mutation.operators.RuleRemover;

/** it executes a scenario over a mutate set of specifications and return the mutation score*/

public class MutatedScenarioExecutor {


	public  Map.Entry<Integer, Integer>  computeMutationScore(String scenarioPath) throws Exception {
		// TEMP. use a temporary directory		
		File temp = new File("temp/");
		assert temp.exists() && temp.isDirectory();
		// parse the scenario to get the ref to the asmeta
		AsmetaMutatedFromAvalla asmetaBuilder = new AsmetaMutatedFromAvalla(scenarioPath, temp);
		// mutate the asmeta
		//ChooseRuleMutate mut = new ChooseRuleMutate(asmetaBuilder.getAsm().getMain());
		//ChooseRuleMutate mut = new ChooseRuleMutate();
		RuleRemover mut = new RuleRemover();
		AsmCollection orginalAsm = asmetaBuilder.getAsm();
		String originalName = orginalAsm.getMain().getName();
		List<AsmCollection> mutants = mut.mutate(orginalAsm);
		Map<String,Boolean> allCoveredRules = new HashMap<>();
		int nKilled = 0; 
		// modify the scenario to ref to the mutated spec		
		for (AsmCollection m: mutants) {
			// change the asmeta with the mutation 
			asmetaBuilder.setAsmeta(m);
			// save the scenario
			asmetaBuilder.save();
			File tempAsmPath = asmetaBuilder.getTempAsmPath();
			// execute now the scenario
			//ValidationResult result = AsmetaV.executeAsmetaFromAvalla(false, allCoveredRules, tempAsmPath, originalName);
			ValidationResult result = AsmetaV.executeAsmetaFromAvalla(false, allCoveredRules, tempAsmPath);
			if (! result.isCheckSucceded()) {
				System.err.println("KILLED !!!");
				nKilled++;
			}
		}		
		return new  AbstractMap.SimpleEntry<Integer, Integer>(nKilled,mutants.size());
	}

	
	static class AsmetaMutatedFromAvalla extends AsmetaFromAvallaBuilder{

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

package asmeta.mutation.mutationscore;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ChooseRuleMutate;

/** it executes a scenario over a mutate set of specifications and return the mutation score*/

public class ScenarioExecutor {


	public double computeMutationScore(String scenarioPath) throws Exception {
		// TEMP. use a temporary directory		
		File temp = new File("temp/");
		assert temp.exists() && temp.isDirectory();
		// parse the scenario to get the ref to the asmeta
		AsmetaMutatedFromAvalla asmetaBuilder = new AsmetaMutatedFromAvalla(scenarioPath, temp);
		// mutate the asmeta
		//ChooseRuleMutate mut = new ChooseRuleMutate(asmetaBuilder.getAsm().getMain());
		ChooseRuleMutate mut = new ChooseRuleMutate();
		List<AsmCollection> mutants = mut.mutate(asmetaBuilder.getAsm());
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
			boolean result = AsmetaV.executeAsmetaFromAvalla(false, allCoveredRules, tempAsmPath, asmetaBuilder.getAsm().getMain().getName());
			if (!result) {
				System.err.println("KILLED !!!");
				nKilled++;
			}
		}		
		return ((double)nKilled)/mutants.size();
	}

	
	class AsmetaMutatedFromAvalla extends AsmetaFromAvallaBuilder{

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

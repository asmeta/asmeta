package asmeta.mutation.mutationscore;

import java.io.File;
import java.util.List;

import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ChooseRuleMutateToLet;
import asmeta.structure.Asm;

/** it executes a scenario over a mutate set of specifications and return the mutation score*/

public class ScenarioExecutor {


	public double computeMutationScore(String scenarioPath) throws Exception {
		// TEMP. use a temporary directory		
		File temp = new File("temp/");
		assert temp.exists() && temp.isDirectory();
		// parse the scenario to get the ref to the asmeta
		AsmetaMutatedFromAvalla asmetaBuilder = new AsmetaMutatedFromAvalla(scenarioPath, temp);
		// mutate the asmeta
		ChooseRuleMutateToLet mut = new ChooseRuleMutateToLet();
		List<AsmCollection> mutants = mut.mutate(asmetaBuilder.getAsm());
		// modify the scenario to ref to the mutated spec
		for (AsmCollection m: mutants) {
			// change the asmeta with the mutation 
			asmetaBuilder.setAsmeta(m);
			// save the scenario
			asmetaBuilder.save();
		}
		

		return 0;
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

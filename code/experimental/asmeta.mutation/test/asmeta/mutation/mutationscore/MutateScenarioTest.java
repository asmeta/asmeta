package asmeta.mutation.mutationscore;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.mutation.mutationscore.MutatedScenarioExecutor.AsmetaMutatedFromAvalla;
import asmeta.mutation.operators.ChooseRuleMutator;

public class MutateScenarioTest {
	@Test
	public void testComputeMutationExperimentsNFM_Pick() throws Exception {
		File temp = new File("temp/");
		temp.mkdirs();
		assert temp.exists() && temp.isDirectory();
		// parse the scenario to get the ref to the asmeta
		String scenarioPath = "experiments_nfm25/scenario_ref_pick.avalla";
		AsmetaMutatedFromAvalla asmetaBuilder = new AsmetaMutatedFromAvalla(scenarioPath , temp);
		// mutate the asmeta
		//ChooseRuleMutate mut = new ChooseRuleMutate();
		ChooseRuleMutator mut = new ChooseRuleMutator(asmetaBuilder.getAsm().getMain());
		AsmCollection orginalAsm = asmetaBuilder.getAsm();
		List<AsmCollection> mutants = mut.mutate(orginalAsm);
		assertEquals(1, mutants.size());
		// change the asmeta with the mutation 
		asmetaBuilder.setAsmeta(mutants.get(0));
		// save the scenario
		asmetaBuilder.save();
		File tempAsmPath = asmetaBuilder.getTempAsmPath();
		System.out.println(tempAsmPath);
		
	}

}

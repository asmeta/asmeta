package asmeta.mutation.mutationscore;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
import asmeta.mutation.operators.ChooseRuleMutator;
import asmeta.mutation.operators.CondNegator;
import asmeta.mutation.operators.CondRemover;
import asmeta.mutation.operators.ForAllMutator;
import asmeta.mutation.operators.ParToSeqMutator;
import asmeta.mutation.operators.RuleBasedMutator;
import asmeta.mutation.operators.RuleRemover;
import asmeta.mutation.operators.SeqToParMutator;
import asmeta.structure.Asm;
import asmeta.mutation.operators.AsmetaMutationOperator;
import asmeta.mutation.operators.CaseMutator;

/**
 * it executes a scenario over a mutate set of specifications and return the
 * mutation score
 */
public class MutatedScenarioExecutor {
	
	private static final Logger LOG = Logger.getLogger(MutatedScenarioExecutor.class);

	// mutation operators to be used
	List<AsmetaMutationOperator> mutOperators = new ArrayList<AsmetaMutationOperator>();
			//Arrays.asList(
			//		new RuleRemover(), 
			//		new ChooseRuleMutate(),
			//		new CondRemover(),
			//		new CondNegator(),
			//		new CaseMutator(),
			//		new ChooseRuleMutate(),
		//		new ForAllMutator(),
		//			new ParToSeq()
		//	);
			//COMPLETE THIS.
				

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
		
		mutOperators.add(new RuleRemover());
		mutOperators.add(new ChooseRuleMutator(orginalAsm.getMain()));
		mutOperators.add(new CondRemover());
		mutOperators.add(new CondNegator());
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
				if (!result.isCheckSucceeded()) {
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
	
	
	
	
	public HashMap<String,Map.Entry<Integer, Integer>> computeMutationScore(String scenarioPath, List<RuleBasedMutator> mutationOps) throws Exception {
		//mutOperators.addAll(mutationOps);
		HashMap<String,List<AsmCollection>> allMutants = new HashMap<String,List<AsmCollection>>();
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
				if (!result.isCheckSucceeded()) {
					System.err.println("KILLED !!!");
					nKilled++;
				}
			}
			// name of the operator -> pair (nKilled, mutants)
			results.put(mut.getName(), new AbstractMap.SimpleEntry<Integer, Integer>(nKilled, mutants.size()));
		}
		return  results;
	}
	
	
	
	/**
	 * Given a path to an Asm file, generates mutants of the specification for each mutation Operator
	 * @param pathToAsm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,List<AsmCollection>> generateMutants(AsmCollection asms) throws Exception{
		Asm asm = asms.getMain();
		//mutOperators.addAll(
		mutOperators = Arrays.asList(		
				new CaseMutator(),
				new ChooseRuleMutator(asm),
				new CondNegator(),
				new CondRemover(),
				new ForAllMutator(asm),
				new ParToSeqMutator(),
				new RuleRemover(),
				new SeqToParMutator()
				);
		
		HashMap<String, List<AsmCollection>> allMutants = new HashMap<String, List<AsmCollection>>();
		for (AsmetaMutationOperator mutOp : mutOperators) {
			List<AsmCollection> mutants = mutOp.mutate(asms);
			allMutants.put(mutOp.getName(), mutants);
		}
		return allMutants;
	}
	

	/**
	 * Given a list of mutants (ASM models), and a Test Suite. Computes the amount of killedMutations 
	 * @param mutants
	 * @param testSuitePath
	 * @return 
	 * @throws Exception
	 */
	public Set<Integer> computeMutationScore(List<AsmCollection> mutants, String testSuitePath) throws Exception {
		Set<Integer> killedMutations = new HashSet<>();
		
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
			AsmCollection m = mutants.get(i);
			
			//From here cycle over the testSuite folder.
			try (Stream<Path> stream = Files.walk(base)) {
			    Iterator<Path> it = stream.iterator();
			    while (it.hasNext()) {
			        Path avalla = it.next();
			        if (avalla.toString().endsWith(".avalla")) {
						
			        	// parse the scenario to get the ref to the asmeta
						AsmetaMutatedFromAvalla asmetaBuilder;
					
						asmetaBuilder = new AsmetaMutatedFromAvalla(avalla.toFile().toString(), temp);
					
						Map<String, Boolean> allCoveredRules = new HashMap<>();
					
						// modify the scenario to ref to the mutated spec
						// change the asmeta with the mutation
						asmetaBuilder.setAsmeta(m);
						// save the scenario
						asmetaBuilder.save();
						File tempAsmPath = asmetaBuilder.getTempAsmPath();
						// execute now the scenario
						try {	
							ValidationResult result = AsmetaV.executeAsmetaFromAvalla(false, allCoveredRules, tempAsmPath, false);
							if (!result.isCheckSucceeded()) {
								//add the index to a set of integer of the ones that were killed.
								LOG.info("Validation failed => killed mutant.");
								killedMutations.add(i);
								break;
							}
						} catch (Exception e) {
							LOG.info("Validation error for the mutated ASM => killed mutant.\n"
									+ e.getClass().getSimpleName() + ": " + e.getMessage());
							killedMutations.add(i);
							break;
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
		
		return killedMutations;
	}
	
	
	
	
}

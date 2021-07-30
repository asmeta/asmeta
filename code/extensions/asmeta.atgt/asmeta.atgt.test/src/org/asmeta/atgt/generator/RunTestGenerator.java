package org.asmeta.atgt.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import atgt.coverage.AsmTestSuite;

/**
 * It runs the generator command line
 * @author marcoradavelli
 */
public class RunTestGenerator {
	static String asmFile = 
			//"phd_master_struct2.asm";
			//"test.asm";
			//"test2.asm";
			//"phd_master_flat3.asm";
//			"test3.asm";

//			"phd_master_flat2.asm";
//			"phd_master_flat2_v2.asm";
//			"phd_master_flat2_v0.asm";
			
//			"sequences_asm_v3.asm";
			"sequences_asm_v4.asm";

//	"phd_master_flat2_v0_correct.asm";
//	"phd_master_flat2_v1_correct.asm";
//	"phd_master_flat2_v2_correct.asm";

	
	static boolean coverageTp = true;
	static List<CriteriaEnum> criteria = Arrays.asList(
			CriteriaEnum.BASIC_RULE
//			CriteriaEnum.COMPLETE_RULE,
//			CriteriaEnum.RULE_GUARD,
//			CriteriaEnum.MCDC,
//			CriteriaEnum.RULE_UPDATE,
	);
	static List<FormatsEnum> formats = Arrays.asList(new FormatsEnum[] {
			FormatsEnum.AVALLA,
			//FormatsEnum.PROTEST,
			// non esiste più FormatsEnum.PROTEST_ONLY_TRANSITION,
	});
	
	/** Generates the tests using all the criteria specified in the criteria list */
	@Test
	public void testOne() throws Exception {
		execute(asmFile, coverageTp, criteria, formats);
	}
	
	/** Computes with all the possible combinations of criteria.
	 * Experimental only. For "normal" experiments, please use the "testOne" method.
	 * @throws Exception
	 */
	@Test
	public void runAllCriteriaCombinations() throws Exception {
		boolean b[] = new boolean[criteria.size()];
		List<CriteriaEnum> realCriteria = new ArrayList<>();
		while ((b=increment(b))!=null) {
			realCriteria.clear();
			for (int i=0; i<b.length; i++) if (b[i]) realCriteria.add(criteria.get(i));
			execute(asmFile, coverageTp, realCriteria, formats);
		}
	}
	
	public static boolean[] increment(boolean[] a) {
		for (int i=a.length-1; i>=0; i--) {
			if (!a[i]) {
				a[i]=true;
				for (int j=i+1; j<a.length; j++) a[j]=false;
				return a;
			}
		}
		return null;
	}
	
	public void execute(String asmFile, boolean coverageTp, List<CriteriaEnum> criteria, List<FormatsEnum> formats) throws Exception {
		String filename = coverageTp+criteria.toString()+formats.toString();
		System.out.println("Executing .... "+filename);
		AsmTestSuite result = new NuSMVtestGenerator(asmFile, coverageTp).generateAbstractTests(CriteriaEnum.getCoverageCriteria(criteria),Integer.MAX_VALUE, ".*");
		SaveResults.saveResults(result,asmFile,formats, filename);		
	}
	
	public static void main(String[] args) throws Exception {
		new RunTestGenerator().execute(asmFile, coverageTp, criteria, formats);
	}
}

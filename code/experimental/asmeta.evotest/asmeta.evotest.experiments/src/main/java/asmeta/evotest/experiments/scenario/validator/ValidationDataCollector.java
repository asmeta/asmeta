package asmeta.evotest.experiments.scenario.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asmeta.evotest.experiments.utils.CsvManager;

public class ValidationDataCollector {

	/**
	 * Extracts and computes coverage metrics from a coverage CSV file for the
	 * given ASM. If the CSV contains no rows, all coverage values default
	 * to zero.
	 *
	 * @param reportCsv path to the coverage CSV file
	 * @param asmName   name of the ASM
	 * @param modelData data about the ASM model (e.g., n_macro, n_update, ...)
	 * @return a map containing coverage values and the number of failing scenarios
	 * @throws Exception  if any exception occurs when reading the csv
	 */
	public static Map<String, String> collectCoverageData(String reportCsv, String asmName, Map<String, String> modelData)
			throws Exception {
		Map<String, String> coverageData = new HashMap<>();
		// read the csv with coverage data
		List<String[]> covRows = CsvManager.readCsv(reportCsv);
		// If there is only one row (headers row), no scenarios or only empty scenarios
		// were generated
		if (covRows.size() == 1) {
			coverageData.put("macro_coverage", "0.0");
			coverageData.put("update_rule_coverage", "0.0");
			coverageData.put("forall_rule_coverage", "0.0");
			coverageData.put("branch_coverage", "0.0");
			coverageData.put("rule_coverage", "0.0");
			coverageData.put("n_failing_scenarios", "0");
		} else {
			int coveredMacros = 0, coveredUpdate = 0, coveredZeroIterForall = 0, coveredOneIterForall = 0,
					coveredMultIterForall = 0, coveredBranchTrue = 0, coveredBranchFalse = 0, coveredRules = 0;
			for (String[] covRow : covRows) {
				if (asmName.equals(covRow[1])) {
					coveredMacros += 1;
					coveredBranchTrue += Integer.valueOf(covRow[4]);
					coveredBranchFalse += Integer.valueOf(covRow[5]);
					coveredRules += Integer.valueOf(covRow[7]);
					coveredUpdate += Integer.valueOf(covRow[9]);
					coveredZeroIterForall += Integer.valueOf(covRow[11]);
					coveredOneIterForall += Integer.valueOf(covRow[12]);
					coveredMultIterForall += Integer.valueOf(covRow[13]);
				}
			}
			String failingScenarios = covRows.get(1)[14];
			// May produce NaN if denominator is 0 (undefined 0/0)
			float macroCoverage = ((float) coveredMacros) / Integer.valueOf(modelData.get("n_macro"));
			float updateRuleCoverage = ((float) coveredUpdate) / Integer.valueOf(modelData.get("n_update"));
			float forallRuleCoverage = ((float) coveredZeroIterForall + coveredOneIterForall + coveredMultIterForall)
					/ (3 * Integer.valueOf(modelData.get("n_forall")));
			float branchCoverage = ((float) coveredBranchTrue + coveredBranchFalse)
					/ (2 * Integer.valueOf(modelData.get("n_branch")));
			float ruleCoverage = ((float) coveredRules) / Integer.valueOf(modelData.get("n_rule"));
			coverageData.put("macro_coverage", String.valueOf(macroCoverage));
			coverageData.put("update_rule_coverage", String.valueOf(updateRuleCoverage));
			coverageData.put("forall_rule_coverage", String.valueOf(forallRuleCoverage));
			coverageData.put("branch_coverage", String.valueOf(branchCoverage));
			coverageData.put("rule_coverage", String.valueOf(ruleCoverage));
			coverageData.put("n_failing_scenarios", String.valueOf(failingScenarios));
		}
		return coverageData;
	}

}

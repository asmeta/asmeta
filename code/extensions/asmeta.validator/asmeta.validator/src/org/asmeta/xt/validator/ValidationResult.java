package org.asmeta.xt.validator;

import java.util.Map;

/**
 * This class represents the result of the validation process. It contains
 * information about whether the validation check succeeded, as well as coverage
 * data.
 *
 */
public class ValidationResult {
	private boolean checkSucceded;
	Map<String, BranchCovData> branchData;
	Map<String, RuleCovData> ruleData;
	Map<String, UpdateCovData> updateData;
	Map<String, ForallCovData> forallData;

	public boolean isCheckSucceded() {
		return checkSucceded;
	}

	public void setCheckSucceded(boolean checkSucceded) {
		this.checkSucceded = checkSucceded;
	}

	public Map<String, BranchCovData> getBranchData() {
		return branchData;
	}

	public void setBranchData(Map<String, BranchCovData> branchData) {
		this.branchData = branchData;
	}
	
	public Map<String, RuleCovData> getRuleData() {
		return ruleData;
	}

	public void setRuleData(Map<String, RuleCovData> ruleData) {
		this.ruleData = ruleData;
	}

	public Map<String, UpdateCovData> getUpdateData() {
		return updateData;
	}

	public void setUpdateData(Map<String, UpdateCovData> updateData) {
		this.updateData = updateData;
	}

	public Map<String, ForallCovData> getForallData() {
		return forallData;
	}

	public void setForallData(Map<String, ForallCovData> forallData) {
		this.forallData = forallData;
	}
}
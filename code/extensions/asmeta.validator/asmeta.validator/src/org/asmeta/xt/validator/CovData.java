package org.asmeta.xt.validator;

import java.util.HashSet;
import java.util.Set;

public abstract class CovData {

	protected int tot;
	
	public CovData() {
		tot = 0;
	}

}

// the coverage information about the branches inside a rule
class BranchCovData extends CovData {
	Set<Integer> coveredT;
	Set<Integer> coveredF;
	public BranchCovData() {
		super();
		coveredT = new HashSet<>();
		coveredF = new HashSet<>();
	}

	@Override
	public String toString() {
		String result = "(" + tot + " cond rules): ";
		result += "Covered true: " + coveredT.toString() + " - ";
		result += "Covered false: " + coveredF.toString();
		return result;
	}
}

// the coverage information about the branches inside a rule
class UpdateCovData extends CovData {
	Set<Integer> covered;
	int tot;

	public UpdateCovData() {
		super();
		covered = new HashSet<>();
	}

	@Override
	public String toString() {
		String result = "(" + tot + " update rules): ";
		result += "Covered: " + covered.toString();
		return result;
	}
}
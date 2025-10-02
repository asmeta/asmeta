package org.asmeta.xt.validator;

import java.util.HashSet;
import java.util.Set;

public abstract class CovData {

	protected int tot;
	
	public CovData() {
		tot = 0;
	}

}

// the coverage information about the conditional rules (branches) inside a macro rule
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

// the coverage information about the update rules inside a macro rule
class UpdateCovData extends CovData {
	Set<Integer> covered;

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

//the coverage information about the forall rules (loops) inside a macro rule
class LoopCovData extends CovData {
	Set<Integer> zeroIterations;
	Set<Integer> oneIteration;
	Set<Integer> multipleIterations;

	public LoopCovData() {
		super();
		zeroIterations = new HashSet<>();
		oneIteration = new HashSet<>();
		multipleIterations = new HashSet<>();
	}

	@Override
	public String toString() {
		String result = "(" + tot + " forall rules): ";
		result += "Executed zero times: " + zeroIterations.toString() + " - ";
		result += "Executed exactly once: " + oneIteration.toString() + " - ";
		result += "Executed more than once: " + multipleIterations.toString();
		return result;
	}
}
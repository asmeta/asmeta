package asmeta.asmetal2java.codegen.evosuite;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code JavaRule} class represents a java rule from the Asmeta
 * specification, with a name and a list of associated branches, provide methods
 * for adding and retrieve branches.
 */
public class JavaRule {

	/* Constants */
	private static final String MASTER = "master";
	private static final String BRANCH = "branch";
	private static final String UNDERSCORE = RulesMap.UNDERSCORE;
	private static final String RULE = "rule";

	/** name of the rule */
	private String name;

	/**
	 * contains all branches of the current rule, the branch name corresponds to the
	 * flag name
	 */
	private List<String> branches;

	/** number of branches */
	private int count;

	/** ASM signature used to identify the rule in a choice trace. */
	private String asmSignature;

	/** Number of choose rules already translated for this rule. */
	private int chooseCount;

	/**
	 * Default constructor that initializes an empty list of branches and set the
	 * branch count to 0.
	 */
	public JavaRule() {
		this.branches = new LinkedList<>();
		this.count = 0;
		this.chooseCount = 0;
	}

	/**
	 * Retrieves the name of the rule.
	 *
	 * @return the name of the rule
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the rule.
	 *
	 * @param name the new name for the rule
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the original ASM rule signature.
	 *
	 * @return the ASM rule signature
	 */
	public String getAsmSignature() {
		return asmSignature;
	}

	/**
	 * Sets the original ASM rule signature.
	 *
	 * @param asmSignature the ASM rule signature
	 */
	public void setAsmSignature(String asmSignature) {
		this.asmSignature = asmSignature;
	}

	/**
	 * Returns the identifier of the next choose rule in this ASM rule.
	 *
	 * @return a zero-based progressive choose identifier
	 */
	public int addNewChoose() {
		return chooseCount++;
	}

	/**
	 * Retrieves the list of branches associated with the rule. Each branch is saved
	 * as a string containing the name of the flag used to cover it.
	 *
	 * @return the list of branches
	 */
	public List<String> getBranches() {
		return branches;
	}

	/**
	 * Add a new branch to the list and return the name of the newly created branch.
	 *
	 * @return String containing the name of the newly created branch
	 */
	public String addNewBranch() {
		String branchName;
		if (count == 0) {
			branchName = RULE + UNDERSCORE + name + UNDERSCORE + BRANCH + UNDERSCORE + MASTER;
		} else {
			branchName = RULE + UNDERSCORE + name + UNDERSCORE + BRANCH + UNDERSCORE + count;
		}
		branches.add(branchName);
		count += 1;
		return branchName;
	}

}

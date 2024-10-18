package org.asmeta.asm2java.evosuite;

import java.util.List;

/**
 * The {@code JavaRule} interface defines the contract for adding and retrieve branches to a Rule.
 */
public interface JavaRule {
	
    /**
     * Retrieves the name of the rule.
     *
     * @return the name of the rule
     */
	String getName();
	
    /**
     * Sets the name of the rule.
     *
     * @param name the new name for the rule
     */
	void setName(String name);
	
    /**
     * Retrieves the list of branches associated with the rule.
     * Each branch is saved as a string containing the name of the flag used to cover it.
     * 
     * @return the list of branches
     */
	List<String> getBranches();
	
	/**
	 * Add a new branch to the list and return the name of the newly created branch.
	 * 
	 * @return String containing the name of the newly created branch 
	 */
    String addNewBranch();

}

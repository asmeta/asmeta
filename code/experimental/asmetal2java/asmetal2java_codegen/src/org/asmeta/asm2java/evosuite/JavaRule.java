package org.asmeta.asm2java.evosuite;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code JavaRule} class represents a java rule from the Asmeta specification,
 * with a name and a list of associated branches, provide methods for adding and retrieve branches.
 */
public class JavaRule{

	/** name of the rule */
    private String name;
    
    /** contains all branches of the current rule, the branch name corresponds to the flag name */
    private List<String> branches;
    
    /** number of branches */
    private int count;

    /**
     * Default constructor that initializes an empty list of branches
     * and set the branch count to 0.
     */
    public JavaRule() {
        this.branches = new LinkedList<>();
        this.count = 0;
    }

    /**
     * Constructor that initializes the rule with a specified name
     * and an empty list of branches.
     *
     * @param name the name of the rule
     */
    public JavaRule(String name) {
        this();
        this.name = name;
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
     * Retrieves the list of branches associated with the rule.
     * Each branch is saved as a string containing the name of the flag used to cover it.
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
    	if(count==0) {
    		branchName = "branch_" + name + "_" + "master";
    	}
    	else {
    		branchName = "branch_" + name + "_" + count;
    	}
    	branches.add(branchName);
    	count += 1;
    	return branchName;
    }


}


package org.asmeta.asm2java.evosuite.impl;

import java.util.LinkedList;
import java.util.List;

import org.asmeta.asm2java.evosuite.JavaRule;

/**
 * The {@code Rule} class represents a java rule of the Asmeta specification,
 * with a name and a list of associated branches.
 */
public class JavaRuleImpl implements JavaRule{

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
    public JavaRuleImpl() {
        this.branches = new LinkedList<>();
        this.count = 0;
    }

    /**
     * Constructor that initializes the rule with a specified name
     * and an empty list of branches.
     *
     * @param name the name of the rule
     */
    public JavaRuleImpl(String name) {
        this();
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getBranches() {
        return branches;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
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


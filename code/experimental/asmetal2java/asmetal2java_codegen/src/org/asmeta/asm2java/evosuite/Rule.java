package org.asmeta.asm2java.evosuite;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code Rule} class represents a rule of the Asmeta specification,
 * with a name and a list of associated branches.
 */
public class Rule {

	/** name of the rule */
    private String name;
    
    /** branches of the rule */
    private List<String> branches;

    /**
     * Default constructor that initializes an empty list of branches.
     */
    public Rule() {
        this.branches = new LinkedList<>();
    }

    /**
     * Constructor that initializes the rule with a specified name
     * and an empty list of branches.
     *
     * @param name the name of the rule
     */
    public Rule(String name) {
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
     * 
     * @return the list of branches
     */
    public List<String> getBranches() {
        return branches;
    }

    /**
     * Sets the list of branches for the rule.
     *
     * @param branches the new list of branches
     */
    public void setBranches(List<String> branches) {
        this.branches = branches;
    }
}


package org.asmeta.asm2java.evosuite;

import java.util.List;
import java.util.Set;

/**
 * The {@code Rules} interface defines a set of operations for managing and retrieving rules.
 */
public interface Rules {

    /**
     * Checks if the rule collection is empty.
     *
     * @return {@code true} if the collection contains no rules, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Retrieves the names of all the rules in the collection.
     * 
     * @return a {@code Set<String>} containing the names of all the rules
     */
    Set<String> getRulesName();

    /**
     * Retrieves the list of branches associated with the specified rule name.
     * 
     * @param name the name of the rule
     * @return a {@code List<String>} of branches associated with the rule, or an empty list
     *         if the rule does not exist
     */
    List<String> getRuleBranches(String name);

}

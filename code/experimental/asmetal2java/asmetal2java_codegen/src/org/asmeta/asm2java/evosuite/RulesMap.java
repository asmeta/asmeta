package org.asmeta.asm2java.evosuite;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code RulesMap} class is a concrete implementation of both the {@code RulesGetter} and {@code RulesAdder} interfaces.
 */
public final class RulesMap implements RulesGetter, RulesAdder {

	/** rules map {name:Rule} */
    private Map<String, JavaRule> rules;

    /**
     * Constructs an empty {@code RulesImpl} instance with an empty rule collection.
     */
    public RulesMap() {
        this.rules = new HashMap<>();
    }

    @Override
    public void addRule(String name, JavaRule rule) throws IllegalArgumentException {
        if (!name.equals(rule.getName())) {
            throw new IllegalArgumentException("The provided name does not match the rule's name.");
        }
        if (rules.containsKey(name)) {
            throw new IllegalArgumentException("Rule with the same name already exists: " + name);
        }
        rules.put(name, rule);
    }

    @Override
    public boolean isEmpty() {
        return rules.isEmpty();
    }

    @Override
    public Set<String> getRulesName() {
        return rules.keySet();
    }

    @Override
    public List<String> getRuleBranches(String name) {
        if (rules.containsKey(name)) {
            return rules.get(name).getBranches();
        }
        return Collections.emptyList();
    }

}


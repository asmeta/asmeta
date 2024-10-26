package org.asmeta.asm2java.evosuite;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code RulesImpl} class is a concrete implementation of both the {@code Rules} and {@code RulesAdder} interfaces.
 */
public final class RulesImpl implements RulesGetter, RulesAdder {

	/** rules map {name:Rule} */
    private Map<String, JavaRule> rules;

    /**
     * Constructs an empty {@code RulesImpl} instance with an empty rule collection.
     */
    public RulesImpl() {
        this.rules = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRule(String name, JavaRule rule) throws IllegalArgumentException {
        if (rules.containsKey(name)) {
            throw new IllegalArgumentException("Rule with the same name already exists: " + name);
        }
        rules.put(name, rule);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return rules.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getRulesName() {
        return rules.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRuleBranches(String name) {
        if (rules.containsKey(name)) {
            return rules.get(name).getBranches();
        }
        return Collections.emptyList();
    }

}


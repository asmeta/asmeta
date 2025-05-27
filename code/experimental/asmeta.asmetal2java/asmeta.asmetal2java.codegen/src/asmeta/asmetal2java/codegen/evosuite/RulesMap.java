package asmeta.asmetal2java.codegen.evosuite;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code RulesMap} class is a concrete implementation of both the
 * {@code RulesGetter} and {@code RulesAdder} interfaces.
 */
public final class RulesMap implements RulesGetter, RulesAdder {

	/* Constants */
	static final String UNDERSCORE = "_";

	/** rules map {name:Rule} */
	private Map<String, JavaRule> rules;

	/**
	 * {ruleName:numberOfOverloads} map to handle asmeta rule overload, contains the
	 * asmeta rule name with the number of overloads executed.
	 */
	private Map<String, Integer> rulesNameOverload;

	/**
	 * Constructs an empty {@code RulesImpl} instance with an empty rule collection.
	 */
	public RulesMap() {
		this.rules = new HashMap<>();
		this.rulesNameOverload = new HashMap<>();
	}

	@Override
	public String addRule(String name, JavaRule rule) throws IllegalArgumentException {
		if (rulesNameOverload.containsKey(name)) {
			// rule already added, calculate the overload number 
			int overloadNumber = rulesNameOverload.get(name) + 1;
			rulesNameOverload.put(name, overloadNumber);
			// add the overload number to the name
			name = name + UNDERSCORE + rulesNameOverload.get(name);
		} else {
			// first occurrence of the rule
			rulesNameOverload.put(name, 0);
			name = name + UNDERSCORE + 0;
		}
		if (rules.containsKey(name)) {
			throw new IllegalArgumentException("Rule with the same name already exists: " + name);
		}
		rules.put(name, rule);
		return name;
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

	/**
	 * Reset the map, the map will be empty after this call returns.
	 */
	public void resetMap() {
		this.rules.clear();
	}

}

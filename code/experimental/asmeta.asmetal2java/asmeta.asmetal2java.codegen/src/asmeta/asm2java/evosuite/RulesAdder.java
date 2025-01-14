package asmeta.asm2java.evosuite;

/**
 * The {@code RulesAdder} interface defines the contract for adding rules to a collection.
 */
public interface RulesAdder {

    /**
     * Adds a {@code Rule} with the specified name to the collection.
     * 
     * @param name the name of the rule
     * @param rule the {@code Rule} object to be added
     * @throws IllegalArgumentException if a rule with the same name already exists
     */
    void addRule(String name, JavaRule rule) throws IllegalArgumentException;

}

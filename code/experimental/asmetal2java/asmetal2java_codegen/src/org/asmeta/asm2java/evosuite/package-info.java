/**
 * In this package we can find all the classes needed to generate the wrapper
 * class named with {@code _ATG} and the translated class for the tests
 * generated respectively by the
 * {@link org.asmeta.asm2java.generator.JavaAtgGenerator} and
 * {@link org.asmeta.asm2java.generator.JavaTestGenerator} generators. In fact
 * we can find the redefinition of some translator classes via inheritance
 * {@link DomainToJavaEvosuiteSigDef}, {@link FunctionToJavaEvosuiteSig},
 * {@link RuleToJavaEvosuite}, {@link DomainToJavaStringEvosuite}, some classes to support
 * the generation of the _ATG class ({@link AsmMethods}, {@link CoverOutputs},
 * {@link CoverRules}) and classes that manage the java rules. Specifically, the
 * {@link JavaRule} class defines an object that consists of the translated java
 * rule, as fields it has the name of the rule, a list of branches of the rule
 * and the number of branches that are currently in the list. The branch in the
 * list is represented as a string with the format
 * {@code branch_<ruleName>_<count>} , the class also provides a method to allow
 * the ordered insertion of branches in the list. The {@code RulesMap} class
 * instead contains a map with the key the name of the rule and contains the
 * java rules, it performs the task of being a buffer and therefore allows you
 * to add and request the rules: to add the rules use the {@link RulesGetter}
 * interface, while to request them use {@link RulesAdder}.
 */
package org.asmeta.asm2java.evosuite;

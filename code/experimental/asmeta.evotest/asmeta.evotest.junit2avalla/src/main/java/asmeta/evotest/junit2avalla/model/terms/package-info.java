/**
 * This package contains the classes that define and manage the terms involved
 * in the translation process. The base class is the abstract superclass
 * {@link Term}, which serves as the foundation for all term types. From this
 * base class, two other abstract classes emerges, each addressing specific
 * domains: {@code JavaTerm} and {@code AvallaTerm}.
 * <p>
 * The first branch, {@link JavaTerm}, introduces a {@code type} field and
 * provides the structure for representing terms related to Java constructs. It
 * is further extended by two concrete classes: {@code JavaAssertionTerm} and
 * {@code JavaArgumentTerm}. The {@link JavaAssertionTerm} class is designed
 * specifically for mapping JUnit assertions, capturing the {@code actual} and
 * {@code expected} values of these operations. On the other
 * hand,{@link JavaArgumentTerm} models a Java argument, characterized by its
 * {@code name} and a boolean field, {@code isPrimitive}, which indicates
 * whether the argument represents a primitive type. JavaArgument is extended by
 * the {@link JavaVariableTerm} class, which adds a {@code value} field to encapsulate
 * the specific value of a Java variable. 
 * <p>
 * The second branch, {@link AvallaTerm}, shifts focus to Avalla-specific
 * constructs. It is an abstract class extended by several concrete
 * implementations, each represents distinct aspects of Avalla terms. For
 * instance, {@link AvallaSetTerm} is used to map terms that form sets of a
 * variable, incorporating fields for the {@code name} and {@code value} of the
 * term. Similarly, {@link AvallaStepTerm} corresponds to an Avalla set term but
 * does not introduce any additional fields. Beyond these,
 * {@link AvallaCheckTerm} models check terms in Avalla, featuring
 * {@code actual} and {@code expected} fields that mirror its Java counterpart.
 * The package also includes the {@link AvallaHeaderTerm} class, which provides
 * a way to store the name of a scenario through the {@code scenarioName} field.
 * Finally, {@link AvallaLoadTerm} is designed to handle references to specific
 * Asmeta models, storing the model’s name in the {@code asmName} field.
 */
package asmeta.evotest.junit2avalla.model.terms;

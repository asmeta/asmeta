/**
 * This package provides the implementation of the {@link JavaScenarioListener},
 * a crucial component for parsing JUnit scenarios using the grammar defined in
 * the {@code antlr} package. The listener is designed to process JUnit
 * scenarios creating immediately Avalla terms objects that are added to the
 * scenario (a queue of {@code AvallaTerms}). However, in cases where it is not
 * possible to create Avalla objects immediately, the listener generates
 * supporting Java objects instead and then translates it later into Avalla
 * objects. For example, variables are created as temporary placeholders and
 * stored in a dictionary. This allows their values to be resolved later when
 * required, ensuring flexibility and accuracy in the parsing process.
 * <p>
 * To manage the scenario construction, the listener relies on the
 * {@link ScenarioManager} class, which handles the storage
 * and organization of parsed avalla terms. Additionally, the package exposes
 * the {@link ScenarioReader} interface, and its implementation
 * {@link ScenarioReaderImpl},providing external control over the scenario
 * reading process.
 */
package asmeta.evotest.junit2avalla.javascenario;

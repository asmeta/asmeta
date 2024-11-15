/**
 * This package provides a set of interfaces and classes designed to manage
 * Avalla scenarios, focusing on mapping scenario objects to scenario files and
 * enabling writing operations.
 * 
 * Central to the package is the {@link ScenarioListMapper} interface, which
 * establishes a contract for converting a list of {@code Scenario} objects into
 * a corresponding list of {@code ScenarioFile} objects. This functionality
 * allows multiple scenarios to be systematically transformed into their
 * file-based representations.
 * 
 * Additionally, the {@link ScenarioWriter} class provides concrete methods to
 * the implementation {@link ScenarioListMapperImpl} for writing individual
 * {@code Scenario} objects into {@code ScenarioFile} instances.
 */
package org.asmeta.junit2avalla.avallascenario;

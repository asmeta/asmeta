/**
 * This package contains classes and interfaces that define and manage the scenarios in a
 * Java environment.
 *
 * <p>The interfaces are:
 * <ul>
 *   <li>{@link org.asmeta.junit2avalla.javascenario.ScenarioManager},which provides methods to set up
 *   and manage scenarios</li>
 *   <li> {@link org.asmeta.junit2avalla.javascenario.ScenarioReader} that defines the contract for
 *   reading and parsing Java scenario files. </li>
 * </ul>
 * The package is structured with two sub-packages:
 * <ul>
 *   <li>{@code impl} - Contains the implementation of the scenario management functionality,
 *   including {@link org.asmeta.junit2avalla.javascenario.ScenarioManagerImpl} and
 *   {@link org.asmeta.junit2avalla.javascenario.ScenarioReaderImpl}.</li>
 *   <li>{@code listener} - Contains the listener-based implementation for parsing and processing
 *   scenarios, including {@link org.asmeta.junit2avalla.javascenario.JavaScenarioListener}.</li>
 * </ul>
 */

package org.asmeta.junit2avalla.javascenario;
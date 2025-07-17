package org.asmeta.animator;

import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.Value;

/** the interface for Visualizer with all the methods useful during simulation
 * 
 *
 */
public interface VisualizationSimulationI {

	void setInvalidIvariantText(String s);

	void setInitValues(Map<Location, Value> locationsPrevSet2);
}

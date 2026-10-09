package asmeta.asmeta_zeromq;


import org.junit.Test;

import asmeta.asmeta_zeromq.registry.SimulationLauncher;

public class TestOrchandCor {
	
	@Test
	public void cross1_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/nopedestriannotram.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross2_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestriannotram.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross3_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/nopedestriantram.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross4_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestrian2notram.properties","ORCHESTRATED"});
	}

	@Test
	public void cross5_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestriantram.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross6_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestriantramtogether.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross7_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestrianbeforetram.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross8_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestrianaftertram.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross9_orch() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/nopedestriantram2.properties","ORCHESTRATED"});
	}
	
	@Test
	public void cross1_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/nopedestriannotram.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross2_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestriannotram.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross3_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/nopedestriantram.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross4_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestrian2notram.properties","CHOREOGRAPHED"});
	}

	@Test
	public void cross5_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestriantram.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross6_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestriantramtogether.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross7_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestrianbeforetram.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross8_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/pedestrianaftertram.properties","CHOREOGRAPHED"});
	}
	
	@Test
	public void cross9_chor() {
		SimulationLauncher.main(new String[] {"configs/trafficLight/nopedestriantram2.properties","CHOREOGRAPHED"});
	}
}

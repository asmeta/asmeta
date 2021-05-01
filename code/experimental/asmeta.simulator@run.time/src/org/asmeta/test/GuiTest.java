package org.asmeta.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.assertion_catalog.InvariantGUI;
import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.runtime_container.IModelAdaptation;
import org.asmeta.runtime_container.IModelExecution;
import org.asmeta.simulationUI.SimGUI;
import org.asmeta.simulationUI.SimGUILauncher;
import org.asmeta.simulationUI.SimShell;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 */
public class GuiTest {

	public static void main(String[] args) {
		IModelAdaptation imp = new SimulationContainer();
		//imp.init(20);
		//String model = "examples/ferrymanSimulator_raff1.asm";
		//String model = "examples/LGS_GM.asm";
		String model = "examples/lift3.asm";
		try {
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("java files good");
		}
		/*imp.startExecution(model);
		imp.startExecution("examples/LGS_GM.asm");
		imp.startExecution("examples/ferrymanSimulator_raff1.asm");
		imp.startExecution("examples/Lavatrice.asm");*/
		
		//imp.stopExecution(2);
		SimGUILauncher.main(imp);
		SimShell.main(args);
		/*Map<String,String> m=new HashMap<String, String>(); //prova per invalid invariant su aggiunta
		m.put("carry", "CABBAGE");
		imp.runStep(1,m, model);*/
		/*try { //prova per simulazione stoppata nella gui
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		imp.stopExecution(3);*/
	}

}

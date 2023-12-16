package asmeta.fmvc.testrunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.reflect.FieldUtils;

import asmeta.fmvclib.annotations.AsmetaMonitoredLocation;
import asmeta.fmvclib.controller.AsmetaFMVCController;
import asmeta.fmvclib.model.AsmetaFMVCModel;
import asmeta.fmvclib.view.AsmetaFMVCView;

/**
 * The AsmetaFMVCTestRunner class
 */
public class AsmetaFMVCTestRunner {

	/**
	 * The model
	 */
	AsmetaFMVCModel model;

	/**
	 * The view
	 */
	AsmetaFMVCView view;

	/**
	 * The controller
	 */
	AsmetaFMVCController controller;

	/**
	 * The path of the AVALLA scenario to be executed
	 */
	String scenario;

	/**
	 * The constructor
	 * 
	 * @param model      the model
	 * @param view       the view
	 * @param controller the controller
	 * @param scenario   the path of the scenario
	 */
	public AsmetaFMVCTestRunner(AsmetaFMVCModel model, AsmetaFMVCView view, AsmetaFMVCController controller,
			String scenario) {
		super();
		this.model = model;
		this.view = view;
		this.controller = controller;
		this.scenario = scenario;
	}
	
	/** 
	 * The method running the test from the avalla scenario
	 * @throws IOException 
	 */
	public void runTest() throws IOException {
		Scanner scanner = new Scanner(new File(this.scenario));
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
			if (line.startsWith("//"))
				// Comment, skip
				continue;
			
			if (line.startsWith("scenario "))
				// Initial line of the scenario, skip
				continue;
			
			if (line.startsWith("load "))
				// ASM loading instruction, skip
				continue;
			
			if (line.startsWith("step"))
				// Step instruction, skip
				continue;
			
			if (line.startsWith("check "))
				// Check instruction
				runCheck(line.replace("check ", "").replace(";", ""));
			
			if (line.startsWith("set "))
				// Set instruction
				runSet(line.replace("set ", "").replace(";", ""));
		}
		
		scanner.close();
		
	}

	/**
	 * Executes the check
	 * 
	 * @param line
	 */
	private void runCheck(String line) {
		// Extract the name and the value of the location
		String locationName = line.split(" = ")[0];
		String locationValue = line.split(" = ")[1];
		
		// TODO: Find the object annotated with the locationName 
		List<Field> fieldListMonitoredLst = FieldUtils.getFieldsListWithAnnotation(view.getClass(),
				AsmetaMonitoredLocation.class);
		for (Field f : fieldListMonitoredLst) {
			f.setAccessible(true);
			
		}
		
	}
	
	/**
	 * Executes the set
	 * 
	 * @param line
	 */
	private void runSet(String line) {
		// Extract the name and the value of the location
		String locationName = line.split(" := ")[0];
		String locationValue = line.split(" := ")[1];
		
		
		
	}

}

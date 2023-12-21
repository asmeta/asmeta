package asmeta.fmvclib.testrunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;

import asmeta.fmvclib.annotations.AsmetaControlledLocation;
import asmeta.fmvclib.annotations.AsmetaMonitoredLocation;
import asmeta.fmvclib.annotations.AsmetaMonitoredLocations;
import asmeta.fmvclib.controller.AsmetaFMVCController;
import asmeta.fmvclib.controller.ButtonColumn;
import asmeta.fmvclib.view.AsmetaFMVCView;
import asmeta.fmvclib.view.XButtonModel;

/**
 * The AsmetaFMVCTestRunner class
 * 
 * @author Andrea Bombarda
 */
public class AsmetaFMVCTestRunner {

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
	 * The list of values to be ignored
	 */
	List<String> ignoreValues;
	
	/**
	 * Step duration
	 */
	int stepDuration;

	/**
	 * The constructor
	 * 
	 * @param view     the view
	 * @param scenario the path of the scenario
	 * @param controller the controller
	 */
	public AsmetaFMVCTestRunner(AsmetaFMVCView view, AsmetaFMVCController controller, String scenario, List<String> ignoreValues, int stepDuration) {
		super();
		this.view = view;
		this.scenario = scenario;
		this.ignoreValues = ignoreValues;
		this.stepDuration = stepDuration;
		this.controller = controller;
		ASMParser.getResultLogger().setLevel(Level.OFF);
		Logger.getLogger(ASMParser.class).setLevel(Level.OFF);
	}

	/**
	 * The method running the test from the avalla scenario
	 * 
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InterruptedException 
	 */
	public void runTest() throws IOException, IllegalArgumentException, IllegalAccessException, InterruptedException {
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
			
			Thread.sleep(stepDuration);			
		}

		scanner.close();
	}

	/**
	 * Executes the check
	 * 
	 * @param line
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void runCheck(String line) throws IllegalArgumentException, IllegalAccessException {
		// Check execution
		System.out.println("Executing check: " + line);

		// Extract the name and the value of the location
		String locationName = line.split(" = ")[0];
		String locationValue = line.split(" = ")[1];

		// Find the object annotated with the locationName
		Field locationNameAnnotation = FieldUtils
				.getFieldsListWithAnnotation(view.getClass(), AsmetaControlledLocation.class).stream()
				.filter(x -> x.getAnnotation(AsmetaControlledLocation.class).asmLocationName()
						.equals(locationName.split("\\(")[0]))
				.findFirst().orElse(null);

		// Check the value, depending on the object type
		if (locationNameAnnotation != null) {
			locationNameAnnotation.setAccessible(true);
			Object obj = locationNameAnnotation.get(view);

			try {
				if (obj instanceof JTextField) {
					assert ((JTextField) obj).getText().equals(locationValue)
							: "Expected " + locationValue + " - Found: " + ((JTextField) obj).getText();
				} else if (obj instanceof JLabel) {
					assert ((JLabel) obj).getText().equals(locationValue)
							: "Expected " + locationValue + " - Found: " + ((JLabel) obj).getText();
				} else if (obj instanceof ButtonColumn) {
					// Since it is a table, the location name must contain the index
					assert locationName.contains("(");
					
					TableModel model = ((ButtonColumn)obj).getTable().getModel();
					if (model instanceof XButtonModel) {
						XButtonModel xModel = (XButtonModel) model;
						
						// ASMETA undef is Java empty string
						if (locationValue.equals("undef"))
							locationValue = "false";
						
						Boolean locationBool = Boolean.parseBoolean(locationValue); 
						
						// Extract the index
						int index = Integer.parseInt(locationName.split("\\(")[1].split("\\)")[0]);
						assert (locationBool.equals(xModel.getValueAt(index, 0))) : "Expected " + locationValue + " - Found: " + xModel.getValueAt(index, 0);
					} else {
						throw new RuntimeException(
								"This type of TableModel is not yet supported by the fMVC framework: " + model.getClass());
					}
				} else if (obj instanceof JTable) {
					
					// Since it is a table, the location name must contain the index
					assert locationName.contains("(");
					JTable table = ((JTable) obj);

					// ASMETA undef is Java empty string
					if (locationValue.equals("undef"))
						locationValue = "";

					// Extract the index
					int index = Integer.parseInt(locationName.split("\\(")[1].split("\\)")[0]);
					assert (locationValue.equals(table.getModel().getValueAt(index, 0)) || (table.getModel().getValueAt(index, 0) == null && locationValue.equals("")))
							: "Expected " + locationValue + " - Found: " + table.getModel().getValueAt(index, 0);
				} else {
					throw new RuntimeException(
							"This type of component is not yet supported by the fMVC framework: " + obj.getClass());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Executes the set
	 * 
	 * @param line
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void runSet(String line) throws IllegalArgumentException, IllegalAccessException {
		// Check execution
		System.out.println("Executing set: " + line);

		// Extract the name and the value of the location
		String locationName = line.split(" := ")[0];
		String locationValue = line.split(" := ")[1];
		
		// If the value is to be ignored, end the evaluation
		if (ignoreValues.contains(locationValue))
			return;

		// Find the object annotated with the locationName
		Field locationNameAnnotation = FieldUtils
				.getFieldsListWithAnnotation(view.getClass(), AsmetaMonitoredLocation.class).stream()
				.filter(x -> x.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName()
						.equals(locationName.split("\\(")[0]))
				.findFirst().orElse(null);

		// If no location is found, it may be that the location has multiple annotations
		if (locationNameAnnotation == null) {
			List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(view.getClass(),
					AsmetaMonitoredLocations.class);
			for (Field f : fieldList) {
				f.setAccessible(true);
				for (AsmetaMonitoredLocation f1 : f.getAnnotation(AsmetaMonitoredLocations.class).value()) {
					if ((f1.asmLocationName().equals(locationName) && f1.asmLocationValue().equals(""))
							|| (f1.asmLocationName().equals(locationName)
									&& f1.asmLocationValue().equals(locationValue))) {
						setObject(f.get(view), locationValue, locationName);
						break;
					}
				}
			}
		} else {
			// If a single annotation is found
			locationNameAnnotation.setAccessible(true);

			// The same annotation is on more fields. We need to consider the one of the
			// correct component
			if (FieldUtils.getFieldsListWithAnnotation(view.getClass(), AsmetaMonitoredLocation.class).stream()
					.filter(x -> x.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName()
							.equals(locationName.split("\\(")[0]))
					.count() > 1) {				
				// Get the right component
				Field f = FieldUtils.getFieldsListWithAnnotation(view.getClass(), AsmetaMonitoredLocation.class)
						.stream()
						.filter(x -> x.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName()
								.equals(locationName.split("\\(")[0])
								&& x.getAnnotation(AsmetaMonitoredLocation.class).asmLocationValue()
										.equals(locationValue))
						.findFirst().orElse(null);
				// Set the value
				if (f != null) {
					f.setAccessible(true);
					setObject(f.get(view), locationValue, locationName);
				}
			} else {
				setObject(locationNameAnnotation.get(view), locationValue, locationName);
			}
		}
	}

	/**
	 * Utility function used to set the value of an object
	 * 
	 * @param obj           the object to be updated
	 * @param locationValue the value
	 * @param locationName  the name of the location
	 */
	private void setObject(Object obj, String locationValue, String locationName) {
		try {
			if (obj instanceof JTextField) {
				((JTextField) obj).setText(locationValue);
			} else if (obj instanceof JLabel) {
				((JLabel) obj).setText(locationValue);
			} else if (obj instanceof JSpinner) {
				((JSpinner) obj).setValue(Integer.parseInt(locationValue));
			} else if (obj instanceof JSlider) {
				((JSlider) obj).setValue(Integer.parseInt(locationValue));
				for (int i=0; i<((JSlider) obj).getChangeListeners().length; i++) {
					((JSlider) obj).getChangeListeners()[i].stateChanged(new ChangeEvent(obj));
				}		
			} else if (obj instanceof JButton) {
				((JButton) obj).doClick();
			} else if (obj instanceof Timer) {
				for (int i=0; i<((Timer) obj).getActionListeners().length; i++) {
					((Timer) obj).getActionListeners()[i].actionPerformed(null);
				}
			} else if (obj instanceof ButtonColumn) {
				TableModel model = ((ButtonColumn) obj).getTable().getModel();
				if (model instanceof XButtonModel) {
					XButtonModel modelX = (XButtonModel) model;
					System.out.println("Setting row " + Integer.parseInt(locationValue));
					modelX.updateValue(Integer.parseInt(locationValue));
					controller.updateButtonColumnStatus("blocked", ((ButtonColumn) obj).getTable());
					((ButtonColumn) obj).getTable().repaint();
				} else {
					throw new RuntimeException(
							"This type of TableModel is not yet supported by the fMVC framework: " + model.getClass());
				}
			} else if (obj instanceof JTable) {
				JTable table = ((JTable) obj);

				// Since it is a table, the location name must contain the index
				if (locationName.contains("(")) {
					// ASMETA undef is Java empty string
					if (locationValue.equals("undef"))
						locationValue = "";

					// Extract the index
					int index = Integer.parseInt(locationName.split("\\(")[1].split("\\)")[0]);
					table.getModel().setValueAt(locationValue, index, 0);
				} else {
					// If the location does not contain the index, it means that we need to select
					// one of the lines
					for (int i = 0; i < table.getModel().getRowCount(); i++) {
						if (table.getModel().getValueAt(i, 0).equals(locationValue)) {
							table.setRowSelectionInterval(i, i);
							table.setColumnSelectionInterval(0, 0);
						}
					}
					System.out.println("Row " + table.getSelectedRow() + " - Column " + table.getSelectedColumn());
				}
			} else {
				throw new RuntimeException(
						"This type of component is not yet supported by the fMVC framework: " + obj.getClass());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}

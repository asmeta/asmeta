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
	 * @param view       the view
	 * @param scenario   the path of the scenario
	 * @param controller the controller
	 */
	public AsmetaFMVCTestRunner(AsmetaFMVCView view, AsmetaFMVCController controller, String scenario,
			List<String> ignoreValues, int stepDuration) {
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
				runCheck(line.replace("check ", "").replace(";", ""), this.scenario);

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
	 * @param line     the check instruction
	 * @param scenario the name of the scenario
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void runCheck(String line, String scenario) throws IllegalArgumentException, IllegalAccessException {
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
					assert ((JTextField) obj).getText().equals(locationValue) : "Expected " + locationValue
							+ " - Found: " + ((JTextField) obj).getText() + " in scenario " + scenario;
				} else if (obj instanceof JLabel) {
					boolean equals;
					if (isInteger(locationValue)) {
						equals = Integer.parseInt(((JLabel) obj).getText()) 
								== Integer.parseInt(locationValue);
					} else {
						equals = ((JLabel) obj).getText().equals(locationValue);
					}					 
					assert equals : "Expected " + locationValue + " - Found: "
					+ ((JLabel) obj).getText() + " in scenario " + scenario;
				} else if (obj instanceof ButtonColumn) {
					// Since it is a table, the location name must contain the index
					assert locationName.contains("(");

					TableModel model = ((ButtonColumn) obj).getTable().getModel();
					if (model instanceof XButtonModel) {
						XButtonModel xModel = (XButtonModel) model;

						// ASMETA undef is Java empty string
						if (locationValue.equals("undef"))
							locationValue = "";
						else if (locationValue.equalsIgnoreCase("true"))
							locationValue = "X";
						else if (locationValue.equalsIgnoreCase("false"))
							locationValue = "";

						// Extract the index
						int index = Integer.parseInt(locationName.split("\\(")[1].split("\\)")[0]);
						if (index < ((ButtonColumn) obj).getTable().getModel().getRowCount())
							assert (locationValue.equals(xModel.getValueAt(index, 0))) : "Expected " + locationValue
									+ " - Found: " + xModel.getValueAt(index, 0) + " in scenario " + scenario;
					} else {
						throw new RuntimeException(
								"This type of TableModel is not yet supported by the fMVC framework: "
										+ model.getClass());
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
					if (index < table.getModel().getRowCount())
						assert (locationValue.equals(table.getModel().getValueAt(index, 0))
								|| (table.getModel().getValueAt(index, 0) == null && locationValue.equals("")))
								: "Expected " + locationValue + " - Found: " + table.getModel().getValueAt(index, 0)
										+ " in scenario " + scenario;
				} else {
					throw new RuntimeException(
							"This type of component is not yet supported by the fMVC framework: " + obj.getClass());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	static private boolean isInteger(String locationValue) {
		try {
			Integer.valueOf(locationValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Executes the set
	 * 
	 * @param line the set instruction
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
		if (locationValue.equals("undef"))
			return;
		try {
			if (obj instanceof JTextField) {
				((JTextField) obj).setText(locationValue);
			} else if (obj instanceof JLabel) {
				((JLabel) obj).setText(locationValue);
			} else if (obj instanceof JSpinner) {
				((JSpinner) obj).setValue(Integer.parseInt(locationValue));
			} else if (obj instanceof JSlider) {
				int min = ((JSlider) obj).getMinimum();
				float step = ((JSlider) obj).getMinorTickSpacing();
				if (isInteger(Float.toString((Integer.parseInt(locationValue) - min) / step)) || isInteger(Float.toString((Integer.parseInt(locationValue) - min) / step).split("\\.")[1]))
					((JSlider) obj).setValue(Integer.parseInt(locationValue));
				else {
					System.err.println("Not expected value: " + Float.toString((Integer.parseInt(locationValue) - min) / step));
					System.err.println(isInteger(Float.toString((Integer.parseInt(locationValue) - min) / step)));
				}
				for (int i = 0; i < ((JSlider) obj).getChangeListeners().length; i++) {
					((JSlider) obj).getChangeListeners()[i].stateChanged(new ChangeEvent(obj));
				}
			} else if (obj instanceof JButton) {
				((JButton) obj).doClick();
			} else if (obj instanceof Timer) {
				for (int i = 0; i < ((Timer) obj).getActionListeners().length; i++) {
					((Timer) obj).getActionListeners()[i].actionPerformed(null);
				}
			} else if (obj instanceof ButtonColumn) {
				TableModel model = ((ButtonColumn) obj).getTable().getModel();
				if (model instanceof XButtonModel) {
					((ButtonColumn) obj).getTable().setEditingRow(Integer.parseInt(locationValue));
					((ButtonColumn) obj).actionPerformed(null);
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

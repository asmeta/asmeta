package asmeta.fmvclib.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.asmeta.simulator.main.Simulator;

import asmeta.fmvclib.annotations.AsmetaControlledLocation;
import asmeta.fmvclib.annotations.AsmetaRunStep;
import asmeta.fmvclib.annotations.LocationType;
import asmeta.fmvclib.model.AsmetaFMVCModel;
import asmeta.fmvclib.model.InitialStateVisitor;
import asmeta.fmvclib.view.AsmetaFMVCView;
import asmeta.fmvclib.view.RunStepListener;
import asmeta.fmvclib.view.RunStepListenerChangeValue;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;

/**
 * The AsmetaFMVCController is a controller to be used when the pattern fMVC is
 * chosen. It interacts with an ASMETA model and a custom view
 * 
 * @author Andrea Bombarda
 *
 */
@SuppressWarnings("deprecation")
public class AsmetaFMVCController implements Observer, RunStepListener, RunStepListenerChangeValue {
	/**
	 * The model to be used
	 */
	private AsmetaFMVCModel m_model;

	/**
	 * The view to which the listener has to be attached
	 */
	private AsmetaFMVCView m_view;

	/**
	 * The map containing the initial assignments
	 */
	SortedMap<String, String> initMap;

	/**
	 * Builds a new controller to be used when the pattern fMVC is chosen
	 * 
	 * @param model the ASMETA model
	 * @param view  the view
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public AsmetaFMVCController(AsmetaFMVCModel model, AsmetaFMVCView view)
			throws IllegalArgumentException, IllegalAccessException {
		// Store the reference to the model and view
		m_model = model;
		m_view = view;

		// Attach the ActionListener to components annotated with @AsmetaRunStep
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(), AsmetaRunStep.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			if (f.get(m_view) instanceof JButton)
				((JButton) f.get(m_view)).addActionListener(this);
			else if (f.get(m_view) instanceof Timer)
				((Timer) f.get(m_view)).addActionListener(this);
			else if (f.get(m_view) instanceof JSlider)
				((JSlider) f.get(m_view)).addChangeListener(this);
			else if (f.get(m_view) instanceof JToggleButton)
				((JToggleButton) f.get(m_view)).addActionListener(this);
			else
				throw new RuntimeException("Component not yet supported: " + f.get(m_view).getClass());
		}
		// The controller is used as observer for the model
		m_model.addObserver(this);

		// Update the initial state
		initInitialState();
	}

	/**
	 * Initializes the initial state on the view
	 */
	private void initInitialState() {
		Simulator simulator = this.m_model.getSimulator();
		Initialization initialization = simulator.getAsmModel().getDefaultInitialState();
		InitialStateVisitor visitor = new InitialStateVisitor();
		// Visit the function initialization part
		for (FunctionInitialization init : initialization.getFunctionInitialization()) {
			visitor.visitInit(init);
		}

		// Assign the initialization values to the annotated component
		initMap = visitor.initMap;
		updateView(initMap);
	}

	/**
	 * Method automatically called when the controller is modified by the model
	 */
	@Override
	public void update(Observable o, Object arg) {
		updateView(null);
	}

	private void updateView(SortedMap<String, String> initialAssignments) {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(),
				AsmetaControlledLocation.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			// First, get the value (it can be in the initial assignments or in the current
			// state)
			AsmetaControlledLocation annotation = f.getAnnotation(AsmetaControlledLocation.class);
			String value = "";
			if (initialAssignments == null) {
				value = m_model.getValue(annotation.asmLocationName(), annotation.mapKeyType());
				// If value is not valorized, it means that it has never been changed w.r.t. its
				// value in the initial state, so we load the value from the initMap
				if (value.equals(""))
					value = getValueFromInitialAssignments(initMap, annotation);
			} else
				value = getValueFromInitialAssignments(initialAssignments, annotation);
			try {
				if (f.get(m_view) instanceof JTextField) {
					switch (annotation.propertyName()) {
					case VALUE:
						((JTextField) (f.get(m_view))).setText(value);
						break;
					case BG_COLOR:
						((JTextField) (f.get(m_view))).setBackground(Color.getColor(value));
						break;
					case TEXT_COLOR:
						((JTextField) (f.get(m_view))).setForeground(Color.getColor(value));
						break;
					default:
						throw new RuntimeException("Property not yet supported by the fMVC framework");
					}
				} else if (f.get(m_view) instanceof JLabel) {
					switch (annotation.propertyName()) {
					case VALUE:
						((JLabel) (f.get(m_view))).setText(value);
						break;
					case BG_COLOR:
						((JLabel) (f.get(m_view))).setBackground(Color.getColor(value));
						break;
					case TEXT_COLOR:
						((JLabel) (f.get(m_view))).setForeground(Color.getColor(value));
						break;
					default:
						throw new RuntimeException("Property not yet supported by the fMVC framework");
					}
				} else if (f.get(m_view) instanceof JTable) {
					assert annotation.asmLocationType() == LocationType.MAP;
					if (value != null) {
						JTable table = ((JTable) (f.get(m_view)));
						// Iterate over the results
						String[] assignments = value.split(", ");
						int counter = 0;
						switch (annotation.propertyName()) {
						case VALUE:
							for (String assignment : assignments) {
								if (counter < table.getRowCount()) {
									if (assignment.contains("=") && !assignment.split("=")[1].equals("undef"))
										table.getModel().setValueAt(assignment.split("=")[1], counter, 0);
									else
										table.getModel().setValueAt("", counter, 0);
									counter++;
								}
							}
							break;
						default:
							throw new RuntimeException("Property not yet supported by the fMVC framework");
						}
					}
				} else {
					throw new RuntimeException("This type of component is not yet supported by the fMVC framework: "
							+ f.get(m_view).getClass());

				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private String getValueFromInitialAssignments(SortedMap<String, String> initialAssignments,
			AsmetaControlledLocation annotation) {

		SortedMap<String, String> resultStr = new TreeMap<>();
		SortedMap<Long, String> resultInt = new TreeMap<>();
		SortedMap<Float, String> resultFloat = new TreeMap<>();
		SortedMap<Boolean, String> resultBoolean = new TreeMap<>();
		LocationType keyType = annotation.mapKeyType();
		String locationName = annotation.asmLocationName();

		for (Entry<String, String> x : initialAssignments.entrySet()) {

			if (x.getKey().equals(locationName) || x.getKey().startsWith(locationName + "_")) {
				if (keyType == LocationType.UNDEF || keyType == LocationType.STRING || keyType == LocationType.ENUM
						|| keyType == LocationType.CHAR)
					resultStr.put(x.getKey(), x.getValue());
				else if (keyType == LocationType.INTEGER)
					resultInt.put(x.getKey().split("_").length > 0
							? Long.parseLong(x.getKey().split("_")[x.getKey().split("_").length - 1])
							: 0, x.getValue());
				else if (keyType == LocationType.REAL)
					resultFloat.put(x.getKey().split("_").length > 0
							? Float.parseFloat(x.getKey().split("_")[x.getKey().split("_").length - 1])
							: 0, x.getValue());
				else if (keyType == LocationType.BOOLEAN)
					resultBoolean.put(x.getKey().split("_").length > 0
							? Boolean.parseBoolean(x.getKey().split("_")[x.getKey().split("_").length - 1])
							: true, x.getValue());
			}
		}

		// Keep the right map
		String strResult = "";
		if (keyType == LocationType.UNDEF || keyType == LocationType.STRING || keyType == LocationType.ENUM
				|| keyType == LocationType.CHAR)
			strResult = resultStr.toString().replace("{", "").replace("}", "");
		else if (keyType == LocationType.INTEGER)
			strResult = resultInt.toString().replace("{", "").replace("}", "");
		else if (keyType == LocationType.REAL)
			strResult = resultFloat.toString().replace("{", "").replace("}", "");
		else if (keyType == LocationType.BOOLEAN)
			strResult = resultBoolean.toString().replace("{", "").replace("}", "");

		// If it has a single value only
		if (strResult.split("=").length == 2)
			strResult = strResult.split("=")[1];

		return strResult;
	}

	/**
	 * Listener when an action is performer
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		updateAndSimulate(source);
	}

	/**
	 * Updates and simulate the asmeta model
	 */
	public void updateAndSimulate(Object source) {
		try {
			m_model.updateMonitored(m_view, source);
			m_model.runSimulator();
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Listener when the value changes
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSlider && !((JSlider) (e.getSource())).getValueIsAdjusting()) {
			updateAndSimulate(e.getSource());
			List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(), AsmetaRunStep.class);
			for (Field f : fieldList) {
				f.setAccessible(true);
				if (f.getAnnotation(AsmetaRunStep.class).refreshGui()) {
					m_view.refreshView(false);
					updateView(null);
				}
			}
		}
	}
}

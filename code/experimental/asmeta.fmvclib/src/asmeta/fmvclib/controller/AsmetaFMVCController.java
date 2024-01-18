package asmeta.fmvclib.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.value.UndefValue;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.fmvclib.annotations.AsmetaControlledLocation;
import asmeta.fmvclib.annotations.AsmetaMonitoredLocation;
import asmeta.fmvclib.annotations.AsmetaMonitoredLocations;
import asmeta.fmvclib.annotations.AsmetaRunStep;
import asmeta.fmvclib.annotations.LocationType;
import asmeta.fmvclib.model.AsmetaFMVCModel;
import asmeta.fmvclib.view.AsmetaFMVCView;
import asmeta.fmvclib.view.RunStepListener;
import asmeta.fmvclib.view.RunStepListenerChangeValue;
import asmeta.fmvclib.view.XButtonModel;

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
	protected AsmetaFMVCModel m_model;

	/**
	 * The view to which the listener has to be attached
	 */
	protected AsmetaFMVCView m_view;

	/**
	 * The last Update Set
	 */
	UpdateSet updateSet;
	protected SortedMap<String, String> updateSetMap;

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
		updateSet = null;
		updateSetMap = new TreeMap<String, String>();

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
			else if (f.get(m_view) instanceof ButtonColumn) {
				setButtonColumnListener(f);
			} else
				throw new RuntimeException("Component not yet supported: " + f.get(m_view).getClass());
		}
		// The controller is used as observer for the model
		m_model.addObserver(this);

		// Update the initial state
		initInitialState();
	}

	/**
	 * Set the listener for a ButtonColumn object
	 * 
	 * @param f the field
	 * @throws IllegalAccessException
	 */
	public void setButtonColumnListener(Field f) throws IllegalAccessException {
		String locationName = "";

		// Get the other annotation of the field
		List<Field> fieldListMonitoredLst = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(),
				AsmetaMonitoredLocation.class);
		Stream<Field> fieldListMonitored = fieldListMonitoredLst.stream().filter(x -> x.getName().equals(f.getName()));
		Field locationNameAnnotation = FieldUtils
				.getFieldsListWithAnnotation(m_view.getClass(), AsmetaMonitoredLocation.class).stream()
				.filter(x -> x.getName().equals(f.getName())).findFirst().orElse(null);
		if (locationNameAnnotation != null)
			locationName = locationNameAnnotation.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName();

		// If multiple annotations
		if (fieldListMonitored.count() == 0) {
			fieldListMonitoredLst = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(),
					AsmetaMonitoredLocations.class);
			fieldListMonitored = fieldListMonitoredLst.stream().filter(x -> x.getName().equals(f.getName()));
			locationNameAnnotation = FieldUtils
					.getFieldsListWithAnnotation(m_view.getClass(), AsmetaMonitoredLocations.class).stream()
					.filter(x -> x.getName().equals(f.getName())).findFirst().orElse(null);
			if (locationNameAnnotation != null) {
				// More annotations are available. The one of interest is the one that is used
				// in only this case, while the others may be repeated
				for (AsmetaMonitoredLocation annotation : locationNameAnnotation.getAnnotation(AsmetaMonitoredLocations.class)
						.value()) {
					if (FieldUtils.getFieldsListWithAnnotation(m_view.getClass(), AsmetaMonitoredLocation.class).stream()
							.filter(x -> x.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName()
									.equals(annotation.asmLocationName()))
							.count() == 0) {
						locationName = annotation.asmLocationName();
						break;
					}
				}
			}
			
			// No annotation is found
			if (fieldListMonitored.count() == 0)
				throw new RuntimeException("Missing @AsmetaMonitoredLocation annotation for the field " + f.getName());
		}

		((ButtonColumn) f.get(m_view)).setAction(new GetRowAction(locationName, m_model, m_view));
	}

	/**
	 * Initializes the initial state on the view
	 */
	private void initInitialState() {
		this.m_model.initInitalState();

		updateView();
	}

	/**
	 * Method automatically called when the controller is modified by the model
	 */
	@Override
	public void update(Observable o, Object arg) {
		updateView();
	}

	/**
	 * Updates the view by setting the value of controlled variables
	 */
	protected void updateView() {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(),
				AsmetaControlledLocation.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			// First, get the value (it can be in the initial assignments or in the current
			// state)
			AsmetaControlledLocation annotation = f.getAnnotation(AsmetaControlledLocation.class);
			List<Entry<String, String>> value;
			
			// Look for the domain type of the map (if present) in the ASM
			LocationType locationType = LocationType.UNDEF;
			ArrayList<Function> functions = new ArrayList<Function>();
			AsmCollection asms;
			try {
				asms = ASMParser.setUpReadAsm(new File(AsmetaFMVCModel.ASM_PATH + "/" + m_model.getSimulator().getAsmModel().getName() + ASMParser.ASM_EXTENSION));
				asms.forEach(x -> x.getHeaderSection().getSignature().getFunction().stream()
						.filter(fn -> fn.getName().equals(annotation.asmLocationName()))
						.forEach(y -> functions.add(y)));
				assert functions.size() == 1
						: "The function " + annotation.asmLocationName() + " has not been found in the ASM";

				if (functions.get(0).getArity() >= 1) {
					switch (functions.get(0).getDomain().getClass().getSimpleName()) {
						case "EnumTdImpl":
							locationType = LocationType.ENUM;
							break;
						case "AbstractTdImpl":
							locationType = LocationType.RESERVE;
							break;
						case "ConcreteDomainImpl":
							// TODO: Other types may be instead of integer
							locationType = LocationType.INTEGER;
							break;
						default:
							locationType = LocationType.UNDEF;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			m_model.computeValue(annotation.asmLocationName(), locationType);
			value = m_model.getValue(annotation.asmLocationName());

			try {
				String firstValue = value.get(0).getValue();
				if (f.get(m_view) instanceof JTextField) {
					assert value.size() == 1;
					// of undef then do not show anything
					if (firstValue.equals(UndefValue.UNDEF.toString())) 
						firstValue = "";
					((JTextField) (f.get(m_view))).setText(firstValue);
				} else if (f.get(m_view) instanceof JLabel) {
					assert value.size() == 1;
					((JLabel) (f.get(m_view))).setText(firstValue);
				} else if (f.get(m_view) instanceof JTable) {
					if (value != null) {
						JTable table = ((JTable) (f.get(m_view)));
						// Iterate over the results
						int counter = 0;
						for (Entry<String, String> entry : value) {
							if (counter < table.getRowCount()) {
								try {
									if (!entry.getValue().equals("undef"))
										table.getModel().setValueAt(entry.getValue().toLowerCase(),
												Integer.parseInt(entry.getKey().split("_")[1]), 0);
									else
										table.getModel().setValueAt("", Integer.parseInt(entry.getKey().split("_")[1]),
												0);
									counter++;
								} catch (ArrayIndexOutOfBoundsException e) {
									// Sometimes it may happen that the view is not yet correctly updated and it
									// seems that we have more rows than the actually available
								}
							}
						}
					}
				} else if (f.get(m_view) instanceof ButtonColumn) {
					ButtonColumn column = (ButtonColumn) f.get(m_view);
					if (column.getTable().getModel() instanceof XButtonModel) {
						XButtonModel xModel = (XButtonModel) column.getTable().getModel();
						int counter = 0;
						// Iterate over all rows
						for (Entry<String, String> entry : value) {
							if (counter < column.getTable().getRowCount()) {
								try {
									if (entry.getValue().equals("undef") || entry.getValue().equalsIgnoreCase("false"))
										xModel.setValueAt(counter, false);
									else
										xModel.setValueAt(counter, true);
									counter++;
								} catch (ArrayIndexOutOfBoundsException e) {
									// Sometimes it may happen that the view is not yet correctly updated and it
									// seems that we have more rows than the actually available
								}
							}
						}
					} else {
						throw new RuntimeException("This type of TableModel is not yet supported by the fMVC framework: "
								+ column.getTable().getModel().getClass());
					}
				}
				else {
					throw new RuntimeException("This type of component is not yet supported by the fMVC framework: "
							+ f.get(m_view).getClass());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the value of a location in the initial assignments
	 * 
	 * @param initialAssignments the map of the initial assignments
	 * @param keyType            the type of the location
	 * @param locationName       the name of the loaction
	 * @return the string containing the val
	 */
	public String getValueFromInitialAssignments(SortedMap<String, String> initialAssignments, LocationType keyType,
			String locationName) {
		SortedMap<String, String> resultStr = new TreeMap<>();
		SortedMap<Long, String> resultInt = new TreeMap<>();
		SortedMap<Float, String> resultFloat = new TreeMap<>();
		SortedMap<Boolean, String> resultBoolean = new TreeMap<>();

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
			updateSet = m_model.runSimulator();
			// Convert the map of locations in map of strings
			if (updateSet != null)
				updateSet.forEach(x -> {
					this.updateSetMap.put(x.getKey().toString(), x.getValue().toString());
				});
			else
				updateSetMap.clear();
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
				if (f.getAnnotation(AsmetaRunStep.class).repaintView()) {
					m_view.repaintView(false);
					updateView();
				}
			}
		}
	}
	
	/**
	 * Updates the blocked status by using an X on the time instants which are
	 * locked
	 * 
	 * @param functionName the name of the function
	 * @param table the JTable containint the ButtonColumn
	 */
	public void updateButtonColumnStatus(String functionName, JTable table) {
		m_model.computeValue(functionName, LocationType.INTEGER);
		List<Entry<String, String>> value = m_model.getValue(functionName);
		XButtonModel model = (XButtonModel) table.getModel();

		// Iterate over the results
		for (Entry<String, String> assignment : value) {
			if (Integer.parseInt(assignment.getKey().split("_")[1]) < model.getRowCount()) {
				if (!assignment.getValue().equals("undef")) {
					if (assignment.getValue().toLowerCase().equals("true")
							&& model.getValueAt(Integer.parseInt(assignment.getKey().split("_")[1]), 0).equals(""))
						model.updateValue(Integer.parseInt(assignment.getKey().split("_")[1]));
					else if (assignment.getValue().toLowerCase().equals("false")
							&& model.getValueAt(Integer.parseInt(assignment.getKey().split("_")[1]), 0).equals("X"))
						model.updateValue(Integer.parseInt(assignment.getKey().split("_")[1]));
				}
			}
		}
		table.repaint();
	}
}
